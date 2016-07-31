package com.ihelin.car.db.manager;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ihelin.car.db.entity.AccessToken;
import com.ihelin.car.db.mapper.AccessTokenMapper;
import com.ihelin.car.utils.WechatUtil;
import com.ihelin.car.wechat.model.WXAccessToken;

@Service
public class AccessTokenManager {
	@Resource
	private AccessTokenMapper accessTokenMapper;

	// access token 剩余时间预留安全值，半小时
	private static final long SAFE_TOKEN_RESERVE_TIME = 1000L * 30 * 60;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenManager.class);

	// 从数据库中获取token
	public AccessToken getAccessToken() {
		return accessTokenMapper.selectAccessToken();
	}

	// 设置定时器，每20分钟进行一次检查一次
	@PostConstruct
	public void init() {
		Timer t = new Timer("access-token-updater");
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					LOGGER.info("check accessToken...");
					checkAndUpdate();
				} catch (Exception e) {
					LOGGER.error("update access token error", e);
				}
			}
		}, 0, 1000L * 60 * 20);

	}

	/**
	 * 定期检测 access token，若过期时间不足半小时，则更新之
	 */
	private synchronized void checkAndUpdate() {
		AccessToken record = getAccessToken();
		boolean needRefresh = false;
		if (record == null) {
			record = new AccessToken();
			WXAccessToken wxToken = getAccessTokenFromWX();
			record.setToken(wxToken.getToken());
			record.setGenTime(new Date());
			Date validUntil = new Date(new Date().getTime() + (wxToken.getExpiresIn() * 1000L));
			record.setExpiresTime(validUntil);
			insertToken(record);
		} else {
			if (record.getLeftValidTimeMillis() < SAFE_TOKEN_RESERVE_TIME) {
				needRefresh = true;
			} else {
				needRefresh = false;
			}
			if (needRefresh) {
				WXAccessToken wxToken = getAccessTokenFromWX();
				record.setGenTime(new Date());
				record.setToken(wxToken.getToken());
				Date validUntil = new Date(new Date().getTime() + (wxToken.getExpiresIn() * 1000L));
				record.setExpiresTime(validUntil);
				updateAccessToken(record);
			}
		}

	}

	// 从微信服务器获取token
	private static WXAccessToken getAccessTokenFromWX() {
		WXAccessToken wxAccessToken = null;
		try {
			wxAccessToken = WechatUtil.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("从微信服务器获取token成功，有效期为" + wxAccessToken.getExpiresIn());
		return wxAccessToken;
	}

	private int insertToken(AccessToken accessToken) {
		return accessTokenMapper.insert(accessToken);
	}
	
	private int updateAccessToken(AccessToken accessToken) {
		return accessTokenMapper.updateByPrimaryKey(accessToken);
	}

}

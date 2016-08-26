package com.ihelin.car.db.manager;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ihelin.car.config.CommonConfig;
import com.ihelin.car.config.DataConfig;
import com.ihelin.car.db.entity.AccessToken;
import com.ihelin.car.db.mapper.AccessTokenMapper;
import com.ihelin.car.menu.WXAccessToken;
import com.ihelin.car.utils.DateTimeUtil;
import com.ihelin.car.utils.WechatUtil;

@Service
public class AccessTokenManager {
	@Resource
	private AccessTokenMapper accessTokenMapper;

	// access token 剩余时间预留安全值，半小时
	private static final long SAFE_TOKEN_RESERVE_TIME = 1000L * 30 * 60;
	private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenManager.class);

	// 从数据库/文件中获取token
	public AccessToken getAccessToken() {
		return getTokenFromFile();
		// return accessTokenMapper.selectAccessToken();
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

	public boolean forceUpdateAccessToken() {
		WXAccessToken wxToken = getAccessTokenFromWX();
		if (wxToken == null || StringUtils.isBlank(wxToken.getAccess_token())) {
			return false;
		}
		AccessToken accessToken = transAccessToken(wxToken);
		insertToken(accessToken);
		LOGGER.info("强制更新access_token成功。");
		return true;
	}

	/**
	 * 微信accessToken转化为存储Token
	 * 
	 * @param wxAccessToken
	 * @return AccessToken
	 */
	private AccessToken transAccessToken(WXAccessToken wxAccessToken) {
		AccessToken accessToken = new AccessToken();
		accessToken.setToken(wxAccessToken.getAccess_token());
		Date now = new Date();
		accessToken.setGenTime(now);
		Date validUntil = new Date(now.getTime() + (wxAccessToken.getExpires_in() * 1000L));
		accessToken.setExpiresTime(validUntil);
		return accessToken;
	}

	/**
	 * 定期检测access token，若过期时间不足安全时间，则更新token
	 */
	private synchronized void checkAndUpdate() {
		AccessToken record = getAccessToken();
		if (record == null) {
			record = new AccessToken();
			WXAccessToken wxToken = getAccessTokenFromWX();
			Date now = new Date();
			record.setToken(wxToken.getAccess_token());
			record.setGenTime(now);
			Date validUntil = new Date(now.getTime() + (wxToken.getExpires_in() * 1000L));
			record.setExpiresTime(validUntil);
			insertToken(record);
		} else {
			if (record.getLeftValidTimeMillis() < SAFE_TOKEN_RESERVE_TIME) {
				WXAccessToken wxToken = getAccessTokenFromWX();
				record = transAccessToken(wxToken);
				updateAccessToken(record);
			}
		}
	}

	// 从微信服务器获取token
	private static WXAccessToken getAccessTokenFromWX() {
		WXAccessToken wxAccessToken = WechatUtil.getAccessToken(CommonConfig.getAppID(), CommonConfig.getAppSecret());
		LOGGER.info("从微信服务器获取token成功，有效期为" + wxAccessToken.getExpires_in() + "秒");
		return wxAccessToken;
	}

	private AccessToken getTokenFromFile() {
		AccessToken accessToken = new AccessToken();
		String token = (String) DataConfig.getValue("token");
		if (StringUtils.isBlank(token)) {
			return null;
		}
		accessToken.setToken(token);
		accessToken.setExpiresTime((Date) DataConfig.getValue("expiresTime"));
		accessToken.setGenTime((Date) DataConfig.getValue("genTime"));
		return accessToken;
	}

	private int updateTokenToFile(AccessToken accessToken) {
		DataConfig.setValue("updateTime", DateTimeUtil.formatSecond(new Date()));
		DataConfig.setValue("token", accessToken.getToken());
		DataConfig.setValue("genTime", accessToken.getGenTime());
		DataConfig.setValue("expiresTime", accessToken.getExpiresTime());
		return 1;
	}

	private int insertToken(AccessToken accessToken) {
		return updateTokenToFile(accessToken);
		// return accessTokenMapper.insert(accessToken);
	}

	private int updateAccessToken(AccessToken accessToken) {
		return updateTokenToFile(accessToken);
		// return accessTokenMapper.updateByPrimaryKey(accessToken);
	}

}

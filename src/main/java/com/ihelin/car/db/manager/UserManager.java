package com.ihelin.car.db.manager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.ihelin.car.db.entity.User;
import com.ihelin.car.db.mapper.UserMapper;
import com.ihelin.car.model.WXUser;
import com.ihelin.car.utils.JSON;
import com.ihelin.car.utils.WechatUtil;

@Service
public class UserManager {

	@Resource
	private UserMapper userMapper;

	public int insertUser(User user) {
		return userMapper.insert(user);
	}

	public int updateUser(User user) {
		return userMapper.updateByPrimaryKey(user);
	}

	public int deleteUserById(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	public User selectUserByOpenId(String openId) {
		return userMapper.selectByOpenId(openId);
	}

	public List<User> listUserByCondition(String nickName, int offset, int size) {
		Map<String, Object> res = Maps.newHashMap();
		if (StringUtils.isNotEmpty(nickName))
			res.put("nickName", nickName);
		return userMapper.listUserByCondition(res, new RowBounds(offset, size));
	}

	public int listUserCount(String nickName) {
		Map<String, Object> res = Maps.newHashMap();
		if (StringUtils.isNotEmpty(nickName))
			res.put("nickName", nickName);
		return userMapper.listUserCount(res);
	}
	
	public WXUser selectWXUserByOpenId(String openId, String accessToken) {
		String api = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId
				+ "&lang=zh_CN";
		String res = WechatUtil.doGetStr(api);
		return JSON.parseObject(res, WXUser.class);
	}

	public User transWXUserToUser(WXUser wxUser) {
		User user = new User();
		user.setCity(wxUser.getCity());
		user.setCountry(wxUser.getCountry());
		user.setGender(wxUser.getSex());
		user.setGroupid(wxUser.getGroupid());
		user.setHeadimgurl(wxUser.getHeadimgurl());
		user.setLanguage(wxUser.getLanguage());
		user.setNickName(wxUser.getNickname());
		user.setOpenId(wxUser.getOpenid());
		user.setProvince(wxUser.getProvince());
		user.setRemark(wxUser.getRemark());
		user.setSubscribe(wxUser.getSubscribe());
		user.setSubscribeTime(new Date(wxUser.getSubscribe_time() * 1000));
		user.setTagidList(JSON.toJson(wxUser.getTagid_list()));
		return user;
	}
}

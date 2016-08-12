package com.ihelin.car.db.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.ihelin.car.db.entity.User;
import com.ihelin.car.db.mapper.UserMapper;

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
}

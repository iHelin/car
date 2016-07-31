package com.ihelin.car.db.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
	
	public User selectUserByOpenId(String openId){
		return userMapper.selectByOpenId(openId);
	}

}

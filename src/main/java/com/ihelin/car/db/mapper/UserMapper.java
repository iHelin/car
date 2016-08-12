package com.ihelin.car.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.ihelin.car.db.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectByOpenId(String openId);
    
    List<User> listUserByCondition(Map<String, Object> res, RowBounds rowBounds);

	int listUserCount(Map<String, Object> res);
}
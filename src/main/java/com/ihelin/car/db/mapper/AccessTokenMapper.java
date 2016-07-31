package com.ihelin.car.db.mapper;

import com.ihelin.car.db.entity.AccessToken;

public interface AccessTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccessToken record);

    int insertSelective(AccessToken record);

    AccessToken selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccessToken record);

    int updateByPrimaryKey(AccessToken record);
    
    AccessToken selectAccessToken();
}
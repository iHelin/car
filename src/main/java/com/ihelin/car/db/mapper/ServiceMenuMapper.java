package com.ihelin.car.db.mapper;

import java.util.List;

import com.ihelin.car.db.entity.ServiceMenu;

public interface ServiceMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServiceMenu record);

    int insertSelective(ServiceMenu record);

    ServiceMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServiceMenu record);

    int updateByPrimaryKey(ServiceMenu record);
    
    List<ServiceMenu> getAllMenus();
}
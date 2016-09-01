package com.ihelin.car.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.ihelin.car.db.entity.Business;

public interface BusinessMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Business record);

	int insertSelective(Business record);

	Business selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Business record);

	int updateByPrimaryKey(Business record);

	List<Business> listByCondition(Map<String, Object> res, RowBounds rowBounds);

	int listCount(Map<String, Object> res);

}
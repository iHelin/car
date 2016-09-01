package com.ihelin.car.db.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.ihelin.car.db.entity.Business;
import com.ihelin.car.db.mapper.BusinessMapper;

@Service
public class BusinessManager {
	@Resource
	private BusinessMapper businessMapper;
	
	public Business getObjectById(Integer id) {
		return businessMapper.selectByPrimaryKey(id);
	}

	public int insert(Business business) {
		return businessMapper.insert(business);
	}

	public int update(Business business) {
		return businessMapper.updateByPrimaryKey(business);
	}

	public int deleteById(Integer id) {
		return businessMapper.deleteByPrimaryKey(id);
	}

	public List<Business> listByCondition(String name, int offset, int size) {
		Map<String, Object> res = Maps.newHashMap();
		if (StringUtils.isNotBlank(name))
			res.put("name", name);
		return businessMapper.listByCondition(res, new RowBounds(offset, size));
	}

	public int listCount(String name) {
		Map<String, Object> res = Maps.newHashMap();
		if (StringUtils.isNotBlank(name))
			res.put("name", name);
		return businessMapper.listCount(res);
	}

}

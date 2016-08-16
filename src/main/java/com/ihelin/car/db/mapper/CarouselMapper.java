package com.ihelin.car.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.ihelin.car.db.entity.Carousel;

public interface CarouselMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Carousel record);

	int insertSelective(Carousel record);

	Carousel selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Carousel record);

	int updateByPrimaryKey(Carousel record);

	List<Carousel> listCarouselByCondition(Map<String, Object> res);

	List<Carousel> listCarouselByCondition(Map<String, Object> res, RowBounds rowBounds);

	int listCarouselCount();
}
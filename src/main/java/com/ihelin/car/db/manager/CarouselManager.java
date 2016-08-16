package com.ihelin.car.db.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.beust.jcommander.internal.Maps;
import com.ihelin.car.db.entity.Carousel;
import com.ihelin.car.db.mapper.CarouselMapper;

@Service
public class CarouselManager {

	@Resource
	private CarouselMapper carouselMapper;

	public int insert(Carousel carousel) {
		return carouselMapper.insert(carousel);
	}

	public int delete(Integer id) {
		return carouselMapper.deleteByPrimaryKey(id);
	}

	public int update(Carousel carousel) {
		return carouselMapper.updateByPrimaryKey(carousel);
	}

	public Carousel selectCarouselById(Integer id) {
		return carouselMapper.selectByPrimaryKey(id);
	}

	public List<Carousel> selectCarousel() {
		Map<String, Object> res = Maps.newHashMap();
		return carouselMapper.listCarouselByCondition(res);
	}

	public List<Carousel> listCarouselByCondition(int offset, int size) {
		Map<String, Object> res = Maps.newHashMap();
		return carouselMapper.listCarouselByCondition(res, new RowBounds(offset, size));
	}

	public int listCarouselCount() {
		return carouselMapper.listCarouselCount();
	}
}

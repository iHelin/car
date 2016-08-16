package com.ihelin.car.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.ihelin.car.db.entity.Carousel;
import com.ihelin.car.db.plugin.Pagination;
import com.ihelin.car.utils.ResponseUtil;

@Controller
public class AdminCarouselController extends BaseAdminController {

	@RequestMapping("carousel_admin")
	public String carouselAdmin(Model model, Integer pageNum) {
		if (pageNum == null)
			pageNum = 1;
		List<Carousel> carousels = carouselManager.listCarouselByCondition((pageNum - 1) * PAGE_LENGTH, PAGE_LENGTH);
		int totalCount = carouselManager.listCarouselCount();
		model.addAttribute("carousels", carousels);
		model.addAttribute("pagination", new Pagination(totalCount, pageNum, PAGE_LENGTH));
		return ftl("carousel_admin");
	}

	@RequestMapping("add_carousel.do")
	public void addSelfSite(Integer carouselId, String thumbnail, Integer sort, HttpServletResponse response) {
		Carousel carousel = null;
		if (carouselId == null) {
			carousel = new Carousel();
			carousel.setCreateTime(new Date());
			carousel.setSort(sort);
			carousel.setThumbnail(thumbnail);
			carouselManager.insert(carousel);
		} else {
			carousel = carouselManager.selectCarouselById(carouselId);
			carousel.setSort(sort);
			carousel.setThumbnail(thumbnail);
			carouselManager.update(carousel);
		}
		ResponseUtil.writeSuccessJSON(response);
	}

	@RequestMapping("get_carousel")
	public void getCarousel(Integer id, HttpServletResponse response) {
		Carousel carousel = carouselManager.selectCarouselById(id);
		if (carousel == null) {
			ResponseUtil.writeFailedJSON(response, "item_is_null");
			return;
		}
		Map<String, Object> res = Maps.newHashMap();
		res.put("carousel", carousel);
		ResponseUtil.writeSuccessJSON(response, res);
	}

	@RequestMapping("delete_carousel")
	public void deleteCarousel(Integer id, HttpServletResponse response) {
		carouselManager.delete(id);
		ResponseUtil.writeSuccessJSON(response);
	}

}

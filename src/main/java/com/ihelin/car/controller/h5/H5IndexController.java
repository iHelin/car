package com.ihelin.car.controller.h5;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.db.entity.Carousel;

@Controller
public class H5IndexController extends H5BaseController {

	@RequestMapping(value = { "index", "" })
	public String index(Model model) {
		List<Carousel> carousels = carouselManager.selectCarousel();
		model.addAttribute("carousels", carousels);
		return ftl("index");
	}

	@RequestMapping(value = "user_info")
	public String userInfo() {
		return ftl("user_info");
	}

	@RequestMapping(value = "car_manager")
	public String carManager() {
		return ftl("car_manager");
	}

	@RequestMapping(value = "car_list")
	public String carList() {
		return ftl("car_list");
	}

	@RequestMapping("get_code")
	public String getWxCode(String code, String state) {
		System.out.println(code);
		return "redirect:/h5/index";
	}

}

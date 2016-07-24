package com.ihelin.car.controller.h5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "h5")
public class H5IndexController extends H5BaseController {

	@RequestMapping(value = {"index",""})
	public String index() {
		return ftl("index");
	}
	
	@RequestMapping(value = "user_center")
	public String userCenter() {
		return ftl("user_center");
	}
	
	@RequestMapping(value = "car_manager")
	public String carManager() {
		return ftl("car_manager");
	}
	
	@RequestMapping(value = "car_list")
	public String carList() {
		return ftl("car_list");
	}

}

package com.ihelin.car.controller.h5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class H5LoginController extends H5BaseController {

	@RequestMapping(value = "login")
	public String login() {
		return ftl("login");
	}
	
	@RequestMapping(value = "reg")
	public String reg() {
		return ftl("reg");
	}

}

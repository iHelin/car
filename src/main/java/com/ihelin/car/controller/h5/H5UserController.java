package com.ihelin.car.controller.h5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class H5UserController extends H5BaseController {
	@RequestMapping("become_user")
	public String becomeUser() {
		return ftl("become_user");
	}
}

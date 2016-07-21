package com.ihelin.car.controller.h5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "h5")
public class H5IndexController extends H5BaseController {

	@RequestMapping(value = "index")
	public String index() {
		return ftl("index");
	}

}

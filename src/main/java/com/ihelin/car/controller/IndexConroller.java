package com.ihelin.car.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexConroller extends BaseController {

	@RequestMapping("/")
	public String root() {
		return "redirect:index";
	}

	@RequestMapping(value = "index")
	public String index() {
		return "index";
	}

}

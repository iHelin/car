package com.ihelin.car.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexConroller extends BaseController {
	
	private static final Log logger = LogFactory.getLog(IndexConroller.class);

	@RequestMapping("/")
	public String root() {
		return "redirect:index";
	}
	
	@RequestMapping(value = "index")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "test")
	public String test() {
		logger.info("呵呵");
		return "index";
	}

}

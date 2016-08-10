package com.ihelin.car.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminIndexController extends AdminBaseController {
	
	@RequestMapping(value = "index")
	public String index(Model model) {
		int productCount = productManager.listProductCount(null, null);
		model.addAttribute("productCount", productCount);
		return ftl("index");
	}
}

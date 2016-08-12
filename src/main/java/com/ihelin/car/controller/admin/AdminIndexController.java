package com.ihelin.car.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminIndexController extends BaseAdminController {
	
	@RequestMapping(value = "index")
	public String index(Model model) {
		int productCount = productManager.listProductCount(null, null);
		int userCount = userManager.listUserCount(null);
		model.addAttribute("productCount", productCount);
		model.addAttribute("userCount", userCount);
		return ftl("index");
	}
}

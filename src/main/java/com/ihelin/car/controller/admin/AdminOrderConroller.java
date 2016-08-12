package com.ihelin.car.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminOrderConroller extends BaseAdminController {

	@RequestMapping("order_admin")
	public String orderAdmin(Model model) {
		return ftl("order_admin");
	}
}

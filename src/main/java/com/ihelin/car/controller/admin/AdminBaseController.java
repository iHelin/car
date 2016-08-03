package com.ihelin.car.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.controller.BaseController;

@Controller
@RequestMapping("admin")
public class AdminBaseController extends BaseController {
	protected static final String NAMESPACE = "admin";
	public static final String SESSION_KEY_ADMIN = "adminUser";

	protected String ftl(String ftlFileName) {
		return NAMESPACE + "/" + ftlFileName;
	}

}

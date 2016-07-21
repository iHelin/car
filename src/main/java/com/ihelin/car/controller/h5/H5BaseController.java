package com.ihelin.car.controller.h5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.controller.BaseController;

@Controller
@RequestMapping("/h5")
public class H5BaseController extends BaseController {

	protected static final String NAMESPACE = "h5";
	public static final String SESSION_KEY_WEIXIN_USER = "weixinUser";

	protected String ftl(String ftlFileName) {
		return NAMESPACE + "/" + ftlFileName;
	}

}

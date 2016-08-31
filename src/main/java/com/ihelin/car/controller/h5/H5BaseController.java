package com.ihelin.car.controller.h5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.controller.BaseController;
import com.ihelin.car.db.entity.User;
import com.ihelin.car.utils.RequestUtil;

@Controller
@RequestMapping("/h5")
public class H5BaseController extends BaseController {

	protected static final String NAMESPACE = "h5";
	public static final String SESSION_KEY_WEIXIN_USER = "weixinUser";

	protected String ftl(String ftlFileName) {
		return NAMESPACE + "/" + ftlFileName;
	}
	
	public User getWeixinUser() {
		return (User) RequestUtil.getSession().getAttribute(SESSION_KEY_WEIXIN_USER);
	}

	public void setWeixinUser(User user) {
		RequestUtil.getSession().setAttribute(SESSION_KEY_WEIXIN_USER, user);
	}

}

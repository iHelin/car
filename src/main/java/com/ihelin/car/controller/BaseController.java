package com.ihelin.car.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.ihelin.car.db.entity.User;
import com.ihelin.car.db.manager.AccessTokenManager;
import com.ihelin.car.db.manager.CarouselManager;
import com.ihelin.car.db.manager.ProductManager;
import com.ihelin.car.db.manager.ServiceMenuMannger;
import com.ihelin.car.db.manager.UserManager;
import com.ihelin.car.utils.RequestUtil;

@Controller
public abstract class BaseController {
	@Resource
	protected ServiceMenuMannger serviceMenuMannger;
	@Resource
	protected AccessTokenManager accessTokenManager;
	@Resource
	protected UserManager userManager;
	@Resource
	protected ProductManager productManager;
	@Resource
	protected CarouselManager carouselManager;

	protected static final int PAGE_LENGTH = 10;
	protected static final int MAX_LENGTH = 1000;
	
	public static final String UNION_POSTAGE = "union_postage";// 包邮起始价
	public static final String POSTAGE = "postage";// 邮费
	public static final String SESSION_KEY_WEIXIN_USER = "wxUser";

	public User getWeixinUser() {
		return (User) RequestUtil.getSession().getAttribute(SESSION_KEY_WEIXIN_USER);
	}

	public void setWeixinUser(User user) {
		RequestUtil.getSession().setAttribute(SESSION_KEY_WEIXIN_USER, user);
	}

}

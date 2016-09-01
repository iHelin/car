package com.ihelin.car.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.ihelin.car.db.manager.AccessTokenManager;
import com.ihelin.car.db.manager.BusinessManager;
import com.ihelin.car.db.manager.CarouselManager;
import com.ihelin.car.db.manager.MediaManager;
import com.ihelin.car.db.manager.ProductManager;
import com.ihelin.car.db.manager.ServiceMenuMannger;
import com.ihelin.car.db.manager.UserManager;

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
	@Resource
	protected MediaManager mediaManager;
	@Resource
	protected BusinessManager businessManager;

	protected static final int PAGE_LENGTH = 10;
	protected static final int MAX_LENGTH = 1000;

	public static final String UNION_POSTAGE = "union_postage";// 包邮起始价
	public static final String POSTAGE = "postage";// 邮费

}

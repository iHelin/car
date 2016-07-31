package com.ihelin.car.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.ihelin.car.message.manager.AccessTokenManager;
import com.ihelin.car.message.manager.ServiceMenuMannger;

@Controller
public abstract class BaseController {
	@Resource
	protected ServiceMenuMannger serviceMenuMannger;
	@Resource
	protected AccessTokenManager accessTokenManager;

	protected static final int PAGE_LENGTH = 10;
	protected static final int MAX_LENGTH = 1000;

}

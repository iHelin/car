package com.ihelin.car.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.ihelin.car.message.manager.ServiceMenuMannger;

@Controller
public class BaseController {
	@Resource
	protected ServiceMenuMannger serviceMenuMannger;
	
	protected static final int PAGE_LENGTH = 10;
	protected static final int MAX_LENGTH = 1000;
	
}

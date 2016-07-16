package com.ihelin.car.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.controller.BaseController;
@Controller
public class AdminIndexController extends BaseController{
	@RequestMapping(value="index")
	public String index(){
		return "admin/index";
	}
}

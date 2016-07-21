package com.ihelin.car.controller.h5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.controller.BaseController;

@Controller
@RequestMapping(value = "h5")
public class H5IndexController extends BaseController {
	
	@RequestMapping(value="index")
	public String index(){
		return "h5/index";
	}

}

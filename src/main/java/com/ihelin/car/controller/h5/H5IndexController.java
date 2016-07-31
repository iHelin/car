package com.ihelin.car.controller.h5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.config.CommonConfig;
import com.ihelin.car.db.entity.User;

@Controller
public class H5IndexController extends H5BaseController {

	@RequestMapping(value = { "index", "" })
	public String index() {
		return ftl("index");
	}

	@RequestMapping(value = "user_center")
	public String userCenter(Model model) throws UnsupportedEncodingException {
		User wxUser = getWeixinUser();
		if (wxUser == null) {
			String usercenterUrl = "h5/user_center";
			usercenterUrl = URLEncoder.encode(usercenterUrl, "utf-8");
			String url = CommonConfig.getDomainUrl()+"/oauth_url?url=REDIRECT_URI";
			url = url.replace("REDIRECT_URI",
					URLEncoder.encode(CommonConfig.getDomainUrl()+"/weixin_login.do?from=" + usercenterUrl, "utf-8"));
			return "redirect:" + url;
		}
		return ftl("user_center");
	}
	
	@RequestMapping(value = "user_info")
	public String userInfo() {
		return ftl("user_info");
	}

	@RequestMapping(value = "car_manager")
	public String carManager() {
		return ftl("car_manager");
	}

	@RequestMapping(value = "car_list")
	public String carList() {
		return ftl("car_list");
	}

	@RequestMapping("get_code")
	public String getWxCode(String code, String state) {
		System.out.println(code);
		return "redirect:/h5/index";
	}

}

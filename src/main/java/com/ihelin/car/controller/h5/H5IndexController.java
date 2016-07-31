package com.ihelin.car.controller.h5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
			String url = "http://ihelin.vip.natapp.cn/oauth_url?url=REDIRECT_URI";
			url = url.replace("REDIRECT_URI",
					URLEncoder.encode("http://ihelin.vip.natapp.cn/weixin_login.do?from=" + usercenterUrl, "utf-8"));
			return "redirect:" + url;
		}
		return ftl("user_center");
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

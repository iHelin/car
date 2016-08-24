package com.ihelin.car.controller.admin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.config.CommonConfig;
import com.ihelin.car.menu.AdminUser;
import com.ihelin.car.utils.CommonUtils;

@Controller
public class AdminLoginController extends BaseAdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminLoginController.class);

	@RequestMapping("login")
	public String adminLogin(Model model,String from) {
		model.addAttribute("from", from);
		return ftl("login");
	}

	@RequestMapping("login.do")
	public String doLogin(String username, String password, String from, HttpServletRequest request,
			HttpSession session, Model model) {
		AdminUser adminUser = null;
		if (username.equals(CommonConfig.getAdminUser()) && password.equals(CommonConfig.getAdminPassword())) {
			adminUser = new AdminUser();
			adminUser.setAdminId(username);
			adminUser.setNickName("超级管理员");
			adminUser.setLastLoginTime(new Date());
			String rip = CommonUtils.getRealIp(request);
			adminUser.setLastLoginIp(rip);
			session.setAttribute(SESSION_KEY_ADMIN, adminUser);
			LOGGER.info("Admin user: " + username + " login success.");
			if (StringUtils.isNotEmpty(from))
				return "redirect:" + from;
			return "redirect:/admin/index";
		} else {
			model.addAttribute("error", "用户名密码不正确！");
			model.addAttribute("from", from);
			return ftl("login");
		}
	}

	@RequestMapping(value = "logout")
	public String logout(HttpSession session) {
		session.removeAttribute(SESSION_KEY_ADMIN);
		return "redirect:login";
	}

}

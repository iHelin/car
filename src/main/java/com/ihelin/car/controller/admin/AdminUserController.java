package com.ihelin.car.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.db.entity.User;
import com.ihelin.car.db.plugin.Pagination;
import com.ihelin.car.utils.ResponseUtil;

@Controller
public class AdminUserController extends BaseAdminController {

	@RequestMapping("user_admin")
	public String userAdmin(Model model, String nickName, Integer pageNum) {
		if (pageNum == null)
			pageNum = 1;
		List<User> users = userManager.listUserByCondition(nickName, (pageNum - 1) * PAGE_LENGTH, PAGE_LENGTH);
		int totalCount = userManager.listUserCount(nickName);
		model.addAttribute("nickName", nickName);
		model.addAttribute("users", users);
		model.addAttribute("pagination", new Pagination(totalCount, pageNum, PAGE_LENGTH));
		return ftl("user_admin");
	}

	@RequestMapping("delete_user")
	public void deleteProduct(Integer id, HttpServletResponse response) {
		userManager.deleteUserById(id);
		ResponseUtil.writeSuccessJSON(response);
	}

}

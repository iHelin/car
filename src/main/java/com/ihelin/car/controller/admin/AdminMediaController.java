package com.ihelin.car.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.db.plugin.Pagination;
import com.ihelin.car.model.Item;

@Controller
public class AdminMediaController extends BaseAdminController {

	@RequestMapping("media_admin")
	public String mediaAdmin(Model model, Integer pageNum) {
		if (pageNum == null)
			pageNum = 1;
		String accessToken = accessTokenManager.getAccessToken().getToken();
		int totalCount = mediaManager.selectMediaCount(accessToken, "news_count");
		List<Item> items = mediaManager.selectMediaByPage("news", accessToken, (pageNum - 1) * PAGE_LENGTH,
				PAGE_LENGTH);
		model.addAttribute("pagination", new Pagination(totalCount, pageNum, PAGE_LENGTH));
		model.addAttribute("items", items);
		return ftl("media_admin");
	}

}

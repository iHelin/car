package com.ihelin.car.controller.admin;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.config.DataConfig;
import com.ihelin.car.db.entity.AccessToken;
import com.ihelin.car.utils.ResponseUtil;

@Controller
public class AdminMallcontroller extends BaseAdminController {

	@RequestMapping("mall_admin")
	public String mallAdmin(Model model) {
		double postage = (double) DataConfig.getValue(POSTAGE);
		double unionPostage = (double) DataConfig.getValue(UNION_POSTAGE);
		AccessToken accessToken = accessTokenManager.getAccessToken();
		model.addAttribute("postage", postage);
		model.addAttribute("accessToken", accessToken);
		model.addAttribute("unionPostage", unionPostage);
		return ftl("mall_admin");
	}

	@RequestMapping("set_config")
	public void setPrice(Double postage, Double unionPostage, HttpServletResponse response) {
		if (postage != null) {
			DataConfig.setValue(POSTAGE, postage);
			DataConfig.setValue(UNION_POSTAGE, unionPostage);
			ResponseUtil.writeSuccessJSON(response);
		} else {
			ResponseUtil.writeFailedJSON(response, "error");
		}

	}

}

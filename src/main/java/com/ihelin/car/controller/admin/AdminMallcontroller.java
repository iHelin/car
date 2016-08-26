package com.ihelin.car.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.config.DataConfig;
import com.ihelin.car.utils.ResponseUtil;

@Controller
public class AdminMallcontroller extends BaseAdminController {

	@RequestMapping("mall_admin")
	public String mallAdmin(Model model) {
		Map<String, Object> config = DataConfig.getValue();
		double postage = (double) config.get(POSTAGE);
		double unionPostage = (double) config.get(UNION_POSTAGE);
		model.addAttribute("postage", postage);
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

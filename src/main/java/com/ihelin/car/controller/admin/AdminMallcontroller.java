package com.ihelin.car.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.config.MallConfig;
import com.ihelin.car.utils.ResponseUtil;

@Controller
public class AdminMallcontroller extends AdminBaseController {

	@RequestMapping("mall_admin")
	public String mallAdmin(Model model) {
		Map<String, Object> config = MallConfig.getValue();
		double postage = (double) config.get(POSTAGE);
		double unionPostage = (double) config.get(UNION_POSTAGE);
		model.addAttribute("postage", postage);
		model.addAttribute("unionPostage", unionPostage);
		return ftl("mall_admin");
	}

	@RequestMapping("set_config")
	public void setPrice(Double postage, Double unionPostage, HttpServletResponse response) {
		if (postage != null) {
			MallConfig.setValue(POSTAGE, postage);
			MallConfig.setValue(UNION_POSTAGE, unionPostage);
			ResponseUtil.writeSuccessJSON(response);
		} else {
			ResponseUtil.writeFailedJSON(response, "error");
		}

	}

}

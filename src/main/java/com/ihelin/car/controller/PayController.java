package com.ihelin.car.controller;

import java.text.ParseException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.controller.h5.H5BaseController;
import com.ihelin.car.utils.MessageUtil;
import com.ihelin.car.utils.ResponseUtil;

@Controller
public class PayController extends H5BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PayController.class);

	@RequestMapping("wx_pay_notify")
	public synchronized void wx_pay_notify(HttpServletRequest request, HttpServletResponse response)
			throws ParseException {
		Map<String, String> res = MessageUtil.xmlToMap(request);
		String returnCode = res.get("return_code");
		if ("SUCCESS".equalsIgnoreCase(returnCode)) {
			/*User user = getWeixinUser();
			user.setStatus(1);
			userManager.updateUser(user);
			setWeixinUser(user);*/
			String orderId = res.get("out_trade_no");
			LOGGER.info(orderId);
		}
		LOGGER.info("get_wx_notify,return success");
		String message = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		ResponseUtil.writeHtml(response, message);
	}

}

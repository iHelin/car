package com.ihelin.car.controller.admin;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.ihelin.car.utils.QRCode;
import com.ihelin.car.utils.ResponseUtil;

@Controller
public class AdminQRCodeController extends AdminBaseController {

	@RequestMapping("qrcode")
	public String qRCode() {
		return ftl("qrcode");
	}

	@RequestMapping("generate_img")
	public void generateQRCode(String content, HttpServletRequest request, HttpServletResponse response) {
		String path = "/img/qrcode/";
		ServletContext application = request.getSession().getServletContext();
		String saveRealFilePath = application.getRealPath(path);
		Map<String, Object> res = Maps.newHashMap();
		path = path + QRCode.generateQRCode(saveRealFilePath, content, "png", 300, 300);
		res.put("url", path);
		ResponseUtil.writeSuccessJSON(response, res);
	}

}

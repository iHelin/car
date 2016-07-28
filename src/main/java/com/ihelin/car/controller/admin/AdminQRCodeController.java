package com.ihelin.car.controller.admin;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
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
		int width = 300;
		int height = 300;
		String format = "png";
		String newFileName = new Random().nextInt(1000000) + "." + format;

		HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);

		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			Path file = new File(saveRealFilePath, newFileName).toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, file);
			path = request.getContextPath() + path + newFileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.put("url", path);
		ResponseUtil.writeSuccessJSON(response, res);
	}

}

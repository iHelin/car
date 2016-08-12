package com.ihelin.car.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.io.ByteStreams;
import com.ihelin.car.utils.JSON;

@Controller
public class ImgUploadController {

	@RequestMapping(value = "imgupload.do", method = RequestMethod.POST)
	@ResponseBody
	public String imgUpload(HttpServletRequest request, HttpSession session) {
		String res = null;
		ServletContext application = session.getServletContext();
		String saveRealFilePath = application.getRealPath("/upload");
		File fileDir = new File(saveRealFilePath);// 文件存放在webapp/upload目录
		if (!fileDir.exists()) { // 如果不存在 则创建
			fileDir.mkdirs();
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> item = multipartRequest.getFileNames();
		while (item.hasNext()) {
			String fileName = (String) item.next();
			MultipartFile file = multipartRequest.getFile(fileName);
			// 获取扩展名
			String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
					.toLowerCase();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String newFileName = sdf.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
			try {
				File uploadedFile = new File(saveRealFilePath, newFileName);
				ByteStreams.copy(file.getInputStream(), new FileOutputStream(uploadedFile));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				String file_Name = "/upload/" + newFileName;
				ResponseJSON resJson = new ResponseJSON();
				resJson.setSuccess(true);
				resJson.setFile_path(file_Name);
				res = JSON.toJson(resJson);
			}
		}
		return res;
	}

	class ResponseJSON {
		private boolean success;
		private String msg;
		private String file_path;

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getFile_path() {
			return file_path;
		}

		public void setFile_path(String file_path) {
			this.file_path = file_path;
		}
	}

}

package com.ihelin.car.controller.admin;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ihelin.car.controller.BaseImageAdminController;
import com.ihelin.car.utils.ImageUtil;
import com.ihelin.car.utils.ImageUtil.ImageResult;

@Controller
@RequestMapping("/admin/*")
public class UploadImageController extends BaseImageAdminController {

	protected ImageResult compressAndSaveImage(String category, MultipartFile imgFile) throws IOException {
		return ImageUtil.compressAndSave(imgFile, 1500, getSubPath(category), true);
	}

	@Override
	protected String getImageNamespace() {
		return "car";
	}
}

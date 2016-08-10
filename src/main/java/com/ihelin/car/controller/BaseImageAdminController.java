package com.ihelin.car.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.ihelin.car.utils.ImageUtil;
import com.ihelin.car.utils.ImageUtil.ImageResult;
import com.ihelin.car.utils.JSON;

public abstract class BaseImageAdminController {

	
	private static final String DEFAULT_CATEGORY = "default";
	private static final int IMAGE_MANAGER_LIST_SIZE = 20;
	
	@RequestMapping(value = "/upload_image.do")
	@ResponseBody
	public String uploadImage(String category,@RequestParam MultipartFile imgFile) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ImageResult imgResult = compressAndSaveImage(category, imgFile);
			
			result.put("state", "SUCCESS");		//ueditor使用的数据格式
			result.put("url", imgResult.getUrl());
			result.put("title", imgResult.getFileName());	
			result.put("original", imgResult.getFileName());
		} catch (IOException e) {
			result.put("state", e.getMessage());
		} 
		
		return JSON.toJson(result);
	}
	
	@RequestMapping(value = "/list_image.do")
	@ResponseBody
	public String listImage(String category, Integer start, Integer size, HttpServletRequest request) {
		List<String> imgList = ImageUtil.getImageFileList(request, getSubPath(category));
		List<Map<String, Object>> rlist = new ArrayList<Map<String,Object>>();
		for (String imgpath : imgList) {
			Map<String, Object> mm = Maps.newHashMap();
			mm.put("url", imgpath);
			mm.put("mtime", System.currentTimeMillis());
			rlist.add(mm);
		}
		rlist = rlist.subList(start, Math.min(rlist.size(), start + size));
		Map<String, Object> result = new HashMap<String, Object>();	//ueditor使用的数据格式
		result.put("state", "SUCCESS");
		result.put("list", rlist);
		result.put("start", start);
		result.put("total", imgList.size());	

		return JSON.toJson(result);
	}
	
	//专门用于ueditor的配置
	@RequestMapping(value = "/editor_config")	
	@ResponseBody
	public String getConfig(String action) {
		if ("config".equals(action)) {
			Map<String, Object> cfgmap = Maps.newConcurrentMap();
			cfgmap.put("imageActionName", "uploadimage");
			cfgmap.put("imageFieldName", "imgFile");
			cfgmap.put("imageUrlPrefix", "");
			List<String> filenameList = new ArrayList<String>();
			filenameList.add(".jpg");
			filenameList.add(".png");
			filenameList.add(".jpeg");
			cfgmap.put("imageAllowFiles", filenameList);
			
			cfgmap.put("imageManagerActionName", "listimage");
			cfgmap.put("imageManagerUrlPrefix", "");
			cfgmap.put("imageManagerListSize", IMAGE_MANAGER_LIST_SIZE);
			cfgmap.put("imageManagerAllowFiles", filenameList);
			return JSON.toJson(cfgmap);
		}
		return "";
	}
	
	protected String getSubPath(String category) {
		if (StringUtils.isBlank(category) || category.contains(".")) //不能包含.
			category = DEFAULT_CATEGORY;
		return (getImageNamespace() + "/" + category).toLowerCase();
	}
	
	protected ImageResult compressAndSaveImage(String category, MultipartFile imgFile) throws IOException {
		return ImageUtil.compressAndSave(imgFile, ImageUtil.DEFAULT_WIDTH, getSubPath(category), true);
	}
	
	protected abstract String getImageNamespace();
}

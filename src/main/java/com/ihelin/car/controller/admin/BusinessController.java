package com.ihelin.car.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.ihelin.car.db.entity.Business;
import com.ihelin.car.db.plugin.Pagination;
import com.ihelin.car.utils.ResponseUtil;

@Controller
public class BusinessController extends BaseAdminController {

	@RequestMapping("business_admin")
	public String businessAdmin(Model model, String keyword, Integer pageNum) {
		if (pageNum == null)
			pageNum = 1;
		List<Business> businesses = businessManager.listByCondition(keyword, (pageNum - 1) * PAGE_LENGTH, PAGE_LENGTH);
		int totalCount = businessManager.listCount(keyword);
		model.addAttribute("businesses", businesses);
		model.addAttribute("pagination", new Pagination(totalCount, pageNum, PAGE_LENGTH));
		model.addAttribute("keyword", keyword);
		return ftl("business_admin");
	}

	@RequestMapping("business_edit")
	public String businessEdit(Model model, Integer businessId) {
		Business business = businessManager.getObjectById(businessId);
		model.addAttribute("business", business);
		return ftl("business_edit");
	}

	@RequestMapping("add_business.do")
	public void addBusiness(Integer businessId, String businessName,String img,String detail,String zone, String address, String contactName,
			String phone, Float longitude, Float latitude, HttpServletResponse response) {
		Business business = null;
		if (businessId == null) {
			business = new Business();
			business.setName(businessName);
			business.setCreateTime(new Date());
			business.setZone(zone);
			business.setAddress(address);
			business.setImg(img);
			business.setDetail(detail);
			business.setContactName(contactName);
			business.setPhone(phone);
			business.setLongitude(longitude);
			business.setLatitude(latitude);
			businessManager.insert(business);
		} else {
			business = businessManager.getObjectById(businessId);
			business.setName(businessName);
			business.setZone(zone);
			business.setAddress(address);
			business.setContactName(contactName);
			business.setPhone(phone);
			business.setImg(img);
			business.setDetail(detail);
			business.setLongitude(longitude);
			business.setLatitude(latitude);
			businessManager.update(business);
		}
		ResponseUtil.writeSuccessJSON(response);
	}

	@RequestMapping("delete_business")
	public void deleteBusiness(Integer id, HttpServletResponse response) {
		businessManager.deleteById(id);
		ResponseUtil.writeSuccessJSON(response);
	}

	@RequestMapping("get_business")
	public void getBusiness(Integer id, HttpServletResponse response) {
		Business business = businessManager.getObjectById(id);
		if (business == null) {
			ResponseUtil.writeFailedJSON(response, "item_is_null");
			return;
		}
		Map<String, Object> res = Maps.newHashMap();
		res.put("business", business);
		ResponseUtil.writeSuccessJSON(response, res);
	}
}

package com.ihelin.car.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.ihelin.car.db.entity.ServiceMenu;
import com.ihelin.car.utils.ResponseUtil;

@Controller
public class AdminMenuController extends BaseAdminController {

	@RequestMapping("menu_admin")
	public String menuAdmin(Model model) {
		List<ServiceMenu> menus = serviceMenuMannger.getAllMenus();
		model.addAttribute("menus", menus);
		return ftl("menu_admin");
	}

	@RequestMapping(value = "service_menu_sync")
	public String syncMenu() {
		String token = accessTokenManager.getAccessToken().getToken();
		try {
			serviceMenuMannger.syncServiceMenuToWeiXin(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:menu_admin";
	}

	@RequestMapping(value = "service_menu_update")
	public String updateServiceMenu(Integer menuId, String menuName, String content, String url, Integer articleId,
			Integer menuType, Integer parentId, Integer sort) {
		if (parentId == null)
			parentId = 0;
		if (menuId == null) {
			ServiceMenu menu = new ServiceMenu();
			menu.setName(menuName);
			if (menuType == ServiceMenu.TEXT_MENU) {
				menu.setContent(content);
			} else if (menuType == ServiceMenu.LINK_MENU) {
				if (url != null && !url.startsWith("http")) {
					url = "http://" + url;
				}
				menu.setContent(url);
			} else if (menuType == ServiceMenu.PIC_MENU) {
				menu.setContent(String.valueOf(articleId));
			}
			menu.setContentType(menuType);
			menu.setParentId(parentId);
			if (sort == null)
				sort = 100;
			menu.setSort(sort);
			serviceMenuMannger.insertMenu(menu);
		} else {
			ServiceMenu menu = serviceMenuMannger.getMenuById(menuId);
			menu.setName(menuName);
			if (menuType == ServiceMenu.TEXT_MENU) {
				menu.setContent(content);
			} else if (menuType == ServiceMenu.LINK_MENU) {
				if (url != null && !url.startsWith("http")) {
					url = "http://" + url;
				}
				menu.setContent(url);
			} else if (menuType == ServiceMenu.PIC_MENU) {
				menu.setContent(String.valueOf(articleId));
			}
			menu.setContentType(menuType);
			menu.setParentId(parentId);
			if (sort == null)
				sort = 100;
			menu.setSort(sort);
			serviceMenuMannger.updateMenu(menu);
		}
		return "redirect:menu_admin";
	}

	@RequestMapping(value = "delete_menu")
	public void deleteMenu(Integer id, HttpServletResponse response) {
		ServiceMenu menu = serviceMenuMannger.getMenuById(id);
		if (menu.getParentId() == null) {
			List<ServiceMenu> subMenus = serviceMenuMannger.getMenusByParentId(id);
			for (ServiceMenu serviceMenu : subMenus) {
				serviceMenuMannger.deleteMenu(serviceMenu.getId());
			}
		}
		serviceMenuMannger.deleteMenu(id);
		ResponseUtil.writeSuccessJSON(response);
	}

	@RequestMapping("get_menu_by_id")
	public void getMenuById(Integer id, HttpServletResponse response) {
		Map<String, Object> res = Maps.newHashMap();
		ServiceMenu menu = serviceMenuMannger.getMenuById(id);
		res.put("menu", menu);
		ResponseUtil.writeSuccessJSON(response, res);
	}

}

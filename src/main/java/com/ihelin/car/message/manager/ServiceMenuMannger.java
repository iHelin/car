package com.ihelin.car.message.manager;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.ParseException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.ihelin.car.db.entity.ServiceMenu;
import com.ihelin.car.db.mapper.ServiceMenuMapper;
import com.ihelin.car.menu.Button;
import com.ihelin.car.menu.ClickButton;
import com.ihelin.car.menu.Menu;
import com.ihelin.car.menu.ViewButton;
import com.ihelin.car.message.entity.AccessToken;
import com.ihelin.car.utils.WechatUtil;

@Service
public class ServiceMenuMannger {
	@Resource
	private ServiceMenuMapper serviceMenuMapper;

	public int insertMenu(ServiceMenu menu) {
		return serviceMenuMapper.insert(menu);
	}
	
	public ServiceMenu getMenuById(Integer id){
		return serviceMenuMapper.selectByPrimaryKey(id);
	}

	public List<ServiceMenu> getAllMenus() {
		return serviceMenuMapper.getAllMenus();
	}

	public int syncServiceMenuToWeiXin() throws ParseException, IOException {
		AccessToken token = WechatUtil.getAccessToken();
		Menu menu = new Menu();
		List<ServiceMenu> sMenus = getAllMenus();
		List<Button> parBtns = Lists.newArrayList();
		for (ServiceMenu serviceMenu : sMenus) {
			if (serviceMenu.getParentId() == null) {
				if (serviceMenu.getContentType() == ServiceMenu.TEXT_MENU) {
					ClickButton button = new ClickButton();
					button.setName(serviceMenu.getName());
					button.setType("click");
					button.setKey(serviceMenu.getId() + "");
					parBtns.add(button);
				} else if (serviceMenu.getContentType() == ServiceMenu.LINK_MENU) {
					ViewButton button = new ViewButton();
					button.setName(serviceMenu.getName());
					button.setType("view");
					button.setUrl(serviceMenu.getContent());
					parBtns.add(button);
				} else if (serviceMenu.getContentType() == ServiceMenu.PIC_MENU) {

				}
			}
		}
		menu.setButton(parBtns);
		String menuStr = new JSONObject(menu).toString();
		int result = WechatUtil.createMenu(token.getToken(), menuStr);
		if (result == 0) {
			System.out.println("创建菜单成功");
		} else {
			System.out.println("错误码：" + result);
		}
		return result;
	}

}

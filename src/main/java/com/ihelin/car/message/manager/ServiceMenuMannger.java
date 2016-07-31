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
import com.ihelin.car.utils.WechatUtil;

@Service
public class ServiceMenuMannger {
	@Resource
	private ServiceMenuMapper serviceMenuMapper;

	public int insertMenu(ServiceMenu menu) {
		return serviceMenuMapper.insert(menu);
	}

	public int updateMenu(ServiceMenu menu) {
		return serviceMenuMapper.updateByPrimaryKey(menu);
	}

	public int deleteMenu(Integer id) {
		return serviceMenuMapper.deleteByPrimaryKey(id);
	}

	public ServiceMenu getMenuById(Integer id) {
		return serviceMenuMapper.selectByPrimaryKey(id);
	}

	public List<ServiceMenu> getAllMenus() {
		return serviceMenuMapper.getAllMenus();
	}

	// 同步微信菜单
	public int syncServiceMenuToWeiXin(String token) throws ParseException, IOException {
		Menu menu = new Menu();
		List<ServiceMenu> sMenus = getAllMenus();
		List<Button> parBtns = Lists.newArrayList();
		for (ServiceMenu serviceMenu : sMenus) {
			if (serviceMenu.getParentId() == null) {
				List<ServiceMenu> subMenus = getMenusByParentId(serviceMenu.getId());
				List<Button> subBtns = Lists.newArrayList();
				if (serviceMenu.getContentType() == ServiceMenu.TEXT_MENU) {
					ClickButton parBtn = new ClickButton();
					parBtn.setName(serviceMenu.getName());
					if (subMenus.size() > 0) {
						for (ServiceMenu subMenu : subMenus) {
							if (subMenu.getContentType() == ServiceMenu.TEXT_MENU) {
								ClickButton subBtn = new ClickButton();
								subBtn.setName(subMenu.getName());
								subBtn.setType("click");
								subBtn.setKey(subMenu.getId() + "");
								subBtns.add(subBtn);
							} else if (subMenu.getContentType() == ServiceMenu.LINK_MENU) {
								ViewButton subBtn = new ViewButton();
								subBtn.setName(subMenu.getName());
								subBtn.setType("view");
								subBtn.setUrl(subMenu.getContent());
								subBtns.add(subBtn);
							}
						}
						parBtn.setSub_button(subBtns);
					} else {
						parBtn.setType("click");
						parBtn.setKey(serviceMenu.getId() + "");
					}
					parBtns.add(parBtn);
				} else if (serviceMenu.getContentType() == ServiceMenu.LINK_MENU) {
					ViewButton patBtn = new ViewButton();
					patBtn.setName(serviceMenu.getName());
					if (subMenus.size() > 0) {
						for (ServiceMenu subMenu : subMenus) {
							if (subMenu.getContentType() == ServiceMenu.TEXT_MENU) {
								ClickButton subBtn = new ClickButton();
								subBtn.setName(subMenu.getName());
								subBtn.setType("click");
								subBtn.setKey(subMenu.getId() + "");
								subBtns.add(subBtn);
							} else if (subMenu.getContentType() == ServiceMenu.LINK_MENU) {
								ViewButton subBtn = new ViewButton();
								subBtn.setName(subMenu.getName());
								subBtn.setType("view");
								subBtn.setUrl(subMenu.getContent());
								subBtns.add(subBtn);
							}
						}
						patBtn.setSub_button(subBtns);
					} else {
						patBtn.setType("view");
						patBtn.setUrl(serviceMenu.getContent());
					}
					parBtns.add(patBtn);
				} else if (serviceMenu.getContentType() == ServiceMenu.PIC_MENU) {
					// 图文消息
				}
			}
		}
		menu.setButton(parBtns);
		String menuStr = new JSONObject(menu).toString();
		int result = WechatUtil.createMenu(token, menuStr);
		if (result == 0) {
			System.out.println("创建菜单成功");
		} else {
			System.out.println("错误码：" + result);
		}
		return result;
	}

	public List<ServiceMenu> getMenusByParentId(Integer parentId) {
		return serviceMenuMapper.getMenustByParentId(parentId);
	}

}

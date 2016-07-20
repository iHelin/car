package com.ihelin.car.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ParseException;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.db.entity.ServiceMenu;
import com.ihelin.car.message.entity.LocationMessage;
import com.ihelin.car.utils.CheckUtil;
import com.ihelin.car.utils.MessageUtil;
import com.ihelin.car.utils.WechatUtil;

@Controller
public class AccessWeChatController extends BaseController {
	private static final Log logger = LogFactory.getLog(AccessWeChatController.class);

	@RequestMapping(value = "access_wechat")
	public void test(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		if (isGet) {
			logger.info("进入验证access");
			if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
				response.getWriter().write(echostr);
				logger.info("验证成功，echostr：" + echostr);
			} else {
				logger.info("认证失败");
			}
		} else {
			try {
				Map<String, String> msgMap = MessageUtil.xmlToMap(request);
				String fromUserName = msgMap.get("FromUserName");
				String toUserName = msgMap.get("ToUserName");
				String msgType = msgMap.get("MsgType");
				String content = msgMap.get("Content");
				System.out.println("用户发送的消息类型为：" + msgType);
				String message = "";
				if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
					System.out.println("用户发送的消息是：" + content);
					message = textMessage(content, toUserName, fromUserName);
				} else if (MessageUtil.MESSAGE_IMAGE.equals(msgType)) {
					message = MessageUtil.initText(toUserName, fromUserName, "我已经收到了你的图片！");
				} else if (MessageUtil.MESSAGE_EVNET.equals(msgType)) {
					message = eventMessage(toUserName, fromUserName, msgMap);
				} else if (MessageUtil.MESSAGE_LOCATION.equals(msgType)) {
					LocationMessage locationMsg = WechatUtil.MapToLocation(msgMap);
					message = MessageUtil.initText(toUserName, fromUserName, locationMsg.getLabel());
				}
				System.out.println(message);
				response.getWriter().print(message);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String otherText(String content, String fromUserName) throws ParseException, IOException {
		String message = "你好";
		return message;
	}

	public static String textMessage(String content, String toUserName, String fromUserName)
			throws ParseException, IOException {
		String message = "";
		switch (content) {
		case "1":
			message = MessageUtil.initText(toUserName, fromUserName, "Java是一种简单的，跨平台的，面向对象的，分布式的，解释的，健壮的安全的，结构的中立的，可移植的，性能很优异的多线程的，动态的语言。");
			break;
		case "?":
		case "？":
			message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
			break;
		default:
			message = MessageUtil.initText(toUserName, fromUserName, otherText(content, fromUserName));
			break;
		}
		return message;
	}
	
	public String eventMessage(String toUserName, String fromUserName,Map<String, String> map){
		String eventType = map.get("Event");
		String message = "";
		if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
			message = MessageUtil.initText(toUserName, fromUserName, "欢迎关注");
		} else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
			Integer id = Integer.parseInt(map.get("EventKey"));
			ServiceMenu sMenu = serviceMenuMannger.getMenuById(id);
			message = MessageUtil.initText(toUserName, fromUserName, sMenu.getContent());
		} else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
			String url = map.get("EventKey");
			message = MessageUtil.initText(toUserName, fromUserName, url);
		} else if (MessageUtil.MESSAGE_SCANCODE.equals(eventType)) {
			String key = map.get("EventKey");
			message = MessageUtil.initText(toUserName, fromUserName, key);
		}
		return message;
	}

}

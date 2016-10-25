package com.ihelin.car.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ihelin.car.db.entity.ServiceMenu;
import com.ihelin.car.message.req.LocationMessage;
import com.ihelin.car.message.resp.Article;
import com.ihelin.car.message.resp.NewsMessage;
import com.ihelin.car.utils.CheckUtil;
import com.ihelin.car.utils.MessageUtil;
import com.ihelin.car.utils.ResponseUtil;
import com.ihelin.car.utils.WechatUtil;

/**
 * 微信消息接入
 * 
 * @author ihelin
 *
 */
@Controller
public class AccessWeChatController extends BaseController {
	private static final Log logger = LogFactory.getLog(AccessWeChatController.class);

	/**
	 * 处理get消息：消息验证
	 * 
	 */
	@RequestMapping(value = "access_wechat", method = RequestMethod.GET)
	public void doGet(String signature, String timestamp, String nonce, String echostr, HttpServletResponse response) {
		logger.info("验证access");
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			ResponseUtil.writeHtml(response, echostr);
			logger.info("验证成功");
		} else {
			logger.info("验证失败，echostr：" + echostr);
		}
	}

	/**
	 * 处理post消息
	 * 
	 * @throws IOException
	 * 
	 */
	@RequestMapping(value = "access_wechat", method = RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String> msgMap = MessageUtil.xmlToMap(request);
		String msgType = msgMap.get("MsgType");
		String respMessage = null;
		if (MessageUtil.MESSAGE_EVNET.equals(msgType)) {
			respMessage = processEvent(msgMap); // 进入事件处理
		} else {
			respMessage = processMessage(msgMap); // 进入普通消息处理
		}
		System.out.println(respMessage);
		response.getWriter().print(respMessage);
	}

	/**
	 * 普通消息处理
	 * 
	 */
	public String processMessage(Map<String, String> msgMap) {
		String message = "";
		String fromUserName = msgMap.get("FromUserName");
		String toUserName = msgMap.get("ToUserName");
		String msgType = msgMap.get("MsgType");
		String content = msgMap.get("Content");
		System.out.println("消息类型：" + msgType);
		switch (msgType) {
		case MessageUtil.MESSAGE_TEXT:
			// 处理文本消息
			System.out.println("用户发送的消息是：" + content);
			message = textMessage(content, toUserName, fromUserName);
			break;
		case MessageUtil.MESSAGE_IMAGE:
			// 图片消息处理
			message = MessageUtil.sendTextMsg(toUserName, fromUserName, "我已经收到了你的图片！");
			break;
		case MessageUtil.MESSAGE_LOCATION:
			// 处理地理位置消息
			LocationMessage locationMsg = WechatUtil.MapToLocation(msgMap);
			message = MessageUtil.sendTextMsg(toUserName, fromUserName, locationMsg.getLabel());
			break;
		case MessageUtil.MESSAGE_LINK:
			// 链接消息
			message = MessageUtil.sendTextMsg(toUserName, fromUserName, "我已经收到了你的链接！");
			break;
		case MessageUtil.MESSAGE_VIDEO:
			// 视频消息
			message = MessageUtil.sendTextMsg(toUserName, fromUserName, "我已经收到了你的视频！");
			break;
		case MessageUtil.MESSAGE_VOICE:
			// 音频消息
			message = MessageUtil.sendTextMsg(toUserName, fromUserName, "我已经收到了你的音频！");
			break;
		default:
			message = MessageUtil.sendTextMsg(toUserName, fromUserName, "未知的消息。");
			break;
		}
		return message;
	}

	/**
	 * 事件类消息处理
	 * 
	 * @param map
	 * @return
	 */
	public String processEvent(Map<String, String> msgMap) {
		System.out.println(msgMap);
		String eventType = msgMap.get("Event");
		String fromUserName = msgMap.get("FromUserName");
		String toUserName = msgMap.get("ToUserName");
		String message = "";
		if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
			message = MessageUtil.sendTextMsg(toUserName, fromUserName, "感谢您的关注，我会继续努力！");// 关注事件
		} else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
			// 点击菜单事件
			Integer id = Integer.parseInt(msgMap.get("EventKey"));
			ServiceMenu sMenu = serviceMenuMannger.getMenuById(id);
			message = MessageUtil.sendTextMsg(toUserName, fromUserName, sMenu.getContent());
		} else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
			// view类型事件，访问网页
			String url = msgMap.get("EventKey");
			message = MessageUtil.sendTextMsg(toUserName, fromUserName, url);
		} else if (MessageUtil.MESSAGE_SCANCODE.equals(eventType)) {
			// 扫码事件
			String key = msgMap.get("EventKey");
			message = MessageUtil.sendTextMsg(toUserName, fromUserName, key);
		}
		return message;
	}

	/**
	 * 文本消息处理
	 */
	public static String textMessage(String content, String toUserName, String fromUserName) {
		String message = "";
		switch (content) {
		case "1":
			message = MessageUtil.sendTextMsg(toUserName, fromUserName,
					"Java是一种简单的，跨平台的，面向对象的，分布式的，解释的，健壮的安全的，结构的中立的，可移植的，性能很优异的多线程的，动态的语言。");
			break;
		case "?":
		case "？":
			message = MessageUtil.sendTextMsg(toUserName, fromUserName, MessageUtil.menuText());
			break;
		case "0":
			// 创建图文消息
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.MESSAGE_NEWS);
			newsMessage.setFuncFlag(0);
			List<Article> articleList = new ArrayList<Article>();
			Article article = new Article();
			article.setTitle("欢迎关注车友1048");
			article.setDescription("Powered By iHelin");
			article.setPicUrl("");
			article.setUrl("http://car.520lyx.cn/h5/index");
			articleList.add(article);
			// 设置图文消息个数
			newsMessage.setArticleCount(articleList.size());
			// 设置图文消息包含的图文集合
			newsMessage.setArticles(articleList);
			message = MessageUtil.sendArticleMsg(toUserName, fromUserName, newsMessage);
			break;
		default:
			message = MessageUtil.sendTextMsg(toUserName, fromUserName, otherText(content, fromUserName));
			break;
		}
		return message;
	}

	public static String otherText(String content, String fromUserName) {
		String message = "[难过] /难过 /::(";
		return message;
	}

}

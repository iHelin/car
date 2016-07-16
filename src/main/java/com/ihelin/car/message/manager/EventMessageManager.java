package com.ihelin.car.message.manager;

import java.util.Map;

import com.ihelin.car.utils.MessageUtil;


public class EventMessageManager {
	
	public static String eventMessage(String toUserName, String fromUserName,Map<String, String> map){
		String eventType = map.get("Event");
		String message = "";
		if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
			message = MessageUtil.initText(toUserName, fromUserName, "欢迎关注");
		} else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
			message = MessageUtil.initText(toUserName, fromUserName, "click事件");
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

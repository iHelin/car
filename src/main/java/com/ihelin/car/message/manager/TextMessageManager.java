package com.ihelin.car.message.manager;

import java.io.IOException;

import org.apache.http.ParseException;

import com.ihelin.car.config.CommonConfig;
import com.ihelin.car.utils.MessageUtil;

public class TextMessageManager {
	public static String OtherText(String content, String fromUserName) throws ParseException, IOException {
		String message = "你好";
		return message;
	}

	public static String textMessage(String content, String toUserName, String fromUserName)
			throws ParseException, IOException {
		String message = "";
		switch (content) {
		case "1":
			message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
			break;
		case "2":
			message = MessageUtil.initText(toUserName, fromUserName, CommonConfig.getAppID());
			break;
		case "3":
			message = MessageUtil.initNewsMessage(toUserName, fromUserName);
			break;
		case "4":
			message = MessageUtil.initImageMessage(toUserName, fromUserName);
			break;
		case "6":
			message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.getWeather());
			break;
		case "?":
		case "？":
			message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
			break;
		default:
			message = MessageUtil.initText(toUserName, fromUserName, OtherText(content, fromUserName));
			break;
		}
		return message;
	}

}

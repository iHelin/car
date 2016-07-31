package com.ihelin.car.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ihelin.car.wechat.model.Article;
import com.ihelin.car.wechat.model.ArticleMessage;
import com.ihelin.car.wechat.model.ImageMessage;
import com.ihelin.car.wechat.model.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVNET = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE = "scancode_push";

	/**
	 * xml转为Map
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> msgMap = new HashMap<String, String>();
		InputStream inStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document doc = reader.read(inStream);
		Element root = doc.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			msgMap.put(e.getName(), e.getText());
		}
		inStream.close();
		return msgMap;
	}

	/**
	 * 主菜单
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎关注E品居，请按照菜单提示进行操作：\n\n");
		sb.append("1、E品居介绍\n\n");
		sb.append("回复？调出此菜单");
		return sb.toString();
	}

	/**
	 * 文本消息组装
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String sendTextMsg(String toUserName, String fromUserName, String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}

	/**
	 * 图文消息组装
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String sendArticleMsg(String toUserName, String fromUserName, ArticleMessage newsMessage) {
		String message = "";
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		message = newsMessageToXml(newsMessage);
		return message;
	}

	/**
	 * 图片消息组装
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String sendImageMsg(String toUserName, String fromUserName, ImageMessage imageMessage) {
		String message = null;
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		message = imageMessageToXml(imageMessage);
		return message;
	}

	/**
	 * 音乐消息组装
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	/*
	 * public static String initMusicMessage(String toUserName, String
	 * fromUserName) { String message = null; Music music = new Music();
	 * music.setThumbMediaId(
	 * "1iUQ5e0XvA3HajCgef6Q9mtUz0JKVyb-3cT7UNQMm07cArgxflOGFy61QWqYDXT4");
	 * music.setTitle("see you again"); music.setDescription("速度与激情");
	 * music.setMusicUrl(
	 * "http://ihelin.ngrok.natapp.cn/seven/resource/See You Again.mp3");
	 * music.setHQMusicUrl(
	 * "http://ihelin.ngrok.natapp.cn/seven/resource/See You Again.mp3");
	 * 
	 * MusicMessage musicMessage = new MusicMessage();
	 * musicMessage.setFromUserName(toUserName);
	 * musicMessage.setToUserName(fromUserName);
	 * musicMessage.setMsgType(MESSAGE_MUSIC); musicMessage.setCreateTime(new
	 * Date().getTime()); musicMessage.setMusic(music); message =
	 * musicMessageToXml(musicMessage); return message; }
	 */

	/**
	 * 文本消息转为xml
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 图文消息转为xml
	 * 
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(ArticleMessage newsMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 音乐消息转XML
	 * 
	 * @param musicMessage
	 * @return
	 */
	/*
	 * public static String musicMessageToXml(MusicMessage musicMessage) {
	 * XStream xstream = new XStream(); xstream.alias("xml",
	 * musicMessage.getClass()); return xstream.toXML(musicMessage); }
	 */

	/**
	 * 图片消息转XML
	 * 
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

}

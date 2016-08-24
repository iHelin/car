package com.ihelin.car.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ihelin.car.message.resp.Article;
import com.ihelin.car.message.resp.NewsMessage;
import com.ihelin.car.message.resp.ImageMessage;
import com.ihelin.car.message.resp.MusicMessage;
import com.ihelin.car.message.resp.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

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
	 * 解析微信发来的请求 xml转为Map
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> msgMap = new HashMap<String, String>();// 将解析结果存储在Map中
		InputStream inStream = request.getInputStream();// 从request中取得输入流
		SAXReader reader = new SAXReader();// 读取输入流
		Document doc = reader.read(inStream);
		Element root = doc.getRootElement();// 得到xml根元素
		List<Element> elementList = root.elements();// 得到根元素的所有子节点
		// 遍历所有子节点
		for (Element e : elementList) {
			msgMap.put(e.getName(), e.getText());
		}
		// 释放资源
		inStream.close();
		return msgMap;
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
		text.setCreateTime(System.currentTimeMillis());
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
	public static String sendArticleMsg(String toUserName, String fromUserName, NewsMessage newsMessage) {
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
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 图文消息转为xml
	 * 
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
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
	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图片消息转XML
	 * 
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
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
	 * 扩展xstream，使其支持CDATA块
	 * 
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (IsNumeric(text)) {
						writer.write(text);
					} else {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}
				}
			};
		}
	});

	public static boolean IsNumeric(String str) {
		try {
			Long.parseLong(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}

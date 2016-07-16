package com.ihelin.car.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.ParseException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;

import com.ihelin.car.message.entity.ImageMessage;
import com.ihelin.car.message.entity.Article;
import com.ihelin.car.message.entity.ArticleMessage;
import com.ihelin.car.message.entity.TextMessage;
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
		sb.append("1、介绍\n");
		sb.append("2、也是介绍\n");
		sb.append("回复？调出此菜单");
		return sb.toString();
	}

	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎关注此账号");
		return sb.toString();
	}

	public static String secondMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("再次感谢您的关注");
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
	public static String initText(String toUserName, String fromUserName, String content) {
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
	public static String initNewsMessage(String toUserName, String fromUserName) {
		String message = null;
		List<Article> newsList = new ArrayList<Article>();
		ArticleMessage newsMessage = new ArticleMessage();
		Article news = new Article();
		news.setTitle("xxx介绍");
		news.setDescription("这里是xxx的描述");
		news.setPicUrl("http://ihelin.ngrok.natapp.cn/seven/image/imooc.jpg");
		news.setUrl("www.imooc.com");
		newsList.add(news);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
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
	public static String initImageMessage(String toUserName, String fromUserName) {
		String message = null;
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setMediaId("cgTPHYq0HdIjI5aW9Jm_inxauWIsianFzOYJplmQ9mwyP2Q_U4P5JAAkMq38RGCy");
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
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
	/*public static String initMusicMessage(String toUserName, String fromUserName) {
		String message = null;
		Music music = new Music();
		music.setThumbMediaId("1iUQ5e0XvA3HajCgef6Q9mtUz0JKVyb-3cT7UNQMm07cArgxflOGFy61QWqYDXT4");
		music.setTitle("see you again");
		music.setDescription("速度与激情");
		music.setMusicUrl("http://ihelin.ngrok.natapp.cn/seven/resource/See You Again.mp3");
		music.setHQMusicUrl("http://ihelin.ngrok.natapp.cn/seven/resource/See You Again.mp3");

		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}*/

	public static String getWeather() throws ParseException, IOException {
		String res = WechatUtil
				.doGetStr("http://v.juhe.cn/weather/index?format=1&cityname=上海&key=80a107e67f0dbb1da04f939f479d6f26");
		JSONObject jsonObject = new JSONObject(res);
		String result = "这里是上海天气\n温度：" + ((JSONObject) ((JSONObject) jsonObject.get("result")).get("sk")).get("temp")
				+ "\n";
		result += "风向：" + ((JSONObject) ((JSONObject) jsonObject.get("result")).get("sk")).get("wind_direction") + "\n";
		result += "风力：" + ((JSONObject) ((JSONObject) jsonObject.get("result")).get("sk")).get("wind_strength") + "\n";
		result += "发布时间：" + ((JSONObject) ((JSONObject) jsonObject.get("result")).get("sk")).get("time");
		return result;
	}

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
	/*public static String musicMessageToXml(MusicMessage musicMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}*/

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

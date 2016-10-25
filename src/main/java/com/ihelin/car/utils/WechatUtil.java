package com.ihelin.car.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.ihelin.car.db.manager.MediaManager.PostData;
import com.ihelin.car.message.req.LocationMessage;
import com.ihelin.car.model.Button;
import com.ihelin.car.model.ClickButton;
import com.ihelin.car.model.Item;
import com.ihelin.car.model.Menu;
import com.ihelin.car.model.RootMedia;
import com.ihelin.car.model.ViewButton;
import com.ihelin.car.model.WXAccessToken;

public class WechatUtil {
	private static final String MEDIA_COUNT_URL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
	private static final String GET_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatUtil.class);

	// get请求，返回String
	public static String doGetStr(String url) {
		HttpGet httpGet = new HttpGet(url);
		String result = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpResponse = client.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			result = "";
		}
		return result;
	}

	// post请求
	public static String doPostStr(String url, String outStr) {
		HttpPost httpPost = new HttpPost(url);
		String result = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpEntity reqEntity = new StringEntity(outStr, ContentType.APPLICATION_JSON);
			httpPost.setEntity(reqEntity);
			HttpResponse response = client.execute(httpPost);
			HttpEntity respEntity = response.getEntity();
			result = EntityUtils.toString(respEntity, "UTF-8");
		} catch (Exception e) {
			result = "";
		}
		return result;
	}

	// 获取微信access_token
	public static WXAccessToken getAccessToken(String appid, String secret) {
		String url = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", secret);
		String res = doGetStr(url);
		WXAccessToken token = JSON.parseObject(res, WXAccessToken.class);
		LOGGER.info("从微信服务器获取token成功，有效期为" + token.getExpires_in() + "秒");
		return token;
	}

	public static List<Item> selectMediaByPage(String type,String accessToken, int offset, int size) {
		String url = GET_MEDIA_URL.replace("ACCESS_TOKEN", accessToken);
		PostData postDate = new PostData(type, offset, size);
		String res = WechatUtil.doPostStr(url, JSON.toJson(postDate));
		RootMedia rootMedia = JSON.parseObject(res, RootMedia.class);
		return rootMedia.getItem();
	}

	public static int selectMediaCount(String accessToken, String type) {
		String url = MEDIA_COUNT_URL.replace("ACCESS_TOKEN", accessToken);
		String res = WechatUtil.doGetStr(url);
		Map<String, Integer> resMap = JSON.parseMap(res, String.class, Integer.class);
		return resMap.get(type);
	}

	// 组装菜单
	public static Menu initMenu() {
		Menu menu = new Menu();
		List<Button> buttons = Lists.newArrayList();
		ClickButton button11 = new ClickButton();
		button11.setName("click菜单");
		button11.setType("click");
		button11.setKey("11");
		buttons.add(button11);

		ViewButton button2 = new ViewButton();
		button2.setName("官方网站");
		button2.setType("view");
		button2.setUrl("http://www.tcqcw.cn");
		buttons.add(button2);

		List<Button> subButtons = Lists.newArrayList();
		ClickButton button31 = new ClickButton();
		button31.setName("扫码");
		button31.setType("scancode_push");
		button31.setKey("31");
		subButtons.add(button31);

		ClickButton button32 = new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");
		subButtons.add(button32);

		Button button = new Button();
		button.setName("新菜单");
		button.setSub_button(subButtons);
		buttons.add(button);

		menu.setButton(buttons);
		return menu;
	}

	// 创建菜单
	public static String createMenu(String accessToken, String menu) {
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		return doPostStr(url, menu);
	}

	// 查询菜单
	public static String queryMenu(String token) {
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		return doGetStr(url);
	}

	// 删除菜单
	public static String deleteMenu(String token) {
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		return doGetStr(url);
	}

	// 组装地理位置消息
	public static LocationMessage MapToLocation(Map<String, String> map) {
		LocationMessage location = new LocationMessage();
		location.setLabel(map.get("Label"));
		location.setLocation_X(map.get("Location_X"));
		location.setLocation_Y(map.get("Location_Y"));
		location.setScale(Integer.parseInt(map.get("Scale")));
		location.setMsgId(Long.valueOf(map.get("MsgId")));
		return location;
	}

	// 微信文件上传
	public static String upload(String filePath, String accessToken, String type)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("lalalal");
		}
		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		URL urlObj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		OutputStream out = new DataOutputStream(con.getOutputStream());
		out.write(head);
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		JSONObject jsonObj = new JSONObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if (!"image".equals(type)) {
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
}

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
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.google.common.collect.Lists;
import com.ihelin.car.config.CommonConfig;
import com.ihelin.car.menu.Button;
import com.ihelin.car.menu.ClickButton;
import com.ihelin.car.menu.Menu;
import com.ihelin.car.menu.ViewButton;
import com.ihelin.car.message.entity.AccessToken;
import com.ihelin.car.message.entity.LocationMessage;

public class WechatUtil {
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	// get请求，返回String
	public static String doGetStr(String url) throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		String result = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			result = EntityUtils.toString(entity, "UTF-8");
		}
		return result;
	}

	// post请求
	public static String doPostStr(String url, String outStr) throws ParseException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
		HttpResponse response = client.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		return result;
	}

	// 获取access_token
	public static AccessToken getAccessToken() throws ParseException, IOException {
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", CommonConfig.getAppID()).replace("APPSECRET",
				CommonConfig.getAppSecret());
		JSONObject jsonObject = new JSONObject(doGetStr(url));
		if (jsonObject != null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
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
	public static int createMenu(String token, String menu) throws ParseException, IOException {
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = new JSONObject(doPostStr(url, menu));
		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}

	// 查询菜单
	public static JSONObject queryMenu(String token) throws ParseException, IOException {
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = new JSONObject(doGetStr(url));
		return jsonObject;
	}

	// 删除菜单
	public static int deleteMenu(String token) throws ParseException, IOException {
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = new JSONObject(doGetStr(url));
		int result = 0;
		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}

	// 组装地理位置消息
	public static LocationMessage MapToLocation(Map<String, String> map) {
		LocationMessage location = new LocationMessage();
		location.setLabel(map.get("Label"));
		location.setLocation_X(map.get("Location_X"));
		location.setLocation_Y(map.get("Location_Y"));
		location.setScale(Integer.parseInt(map.get("Scale")));
		location.setMsgId(map.get("MsgId"));
		return location;
	}

	// 文件上传
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

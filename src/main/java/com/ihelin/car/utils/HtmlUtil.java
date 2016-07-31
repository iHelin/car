package com.ihelin.car.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteStreams;


public class HtmlUtil {

	public static final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HtmlUtil.class);
	private static final int CONNECT_TIMEOUT = 5000; // in milliseconds
	private static final String DEFAULT_ENCODING = "UTF-8";

	public static String getUrlContent(String urlStr) {
		return getUrlContent(urlStr, CONNECT_TIMEOUT);
	}
	
	public static String getUrlContent(String urlStr, int readTimeOut) {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(readTimeOut);
			conn.setRequestProperty("User-Agent", USER_AGENT);

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			return sb.toString();
		} catch (IOException e) {
			LOGGER.debug("getUrlContent: Error connecting to " + urlStr + ": " + e.getMessage(), e);
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				LOGGER.debug("getUrlContent: Error close bufferedReader ", e);
			}
		}
		return null;
	}
	
	public static String postData(String urlStr, String data){
		return postData(urlStr, data, null);
	}
	
	public static String postData(String urlStr, String data, String contentType) {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(CONNECT_TIMEOUT * 10);
			if(StringUtils.isNotBlank(contentType))
				conn.setRequestProperty("content-type", contentType);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
			if(data == null)
				data = "";
	        writer.write(data); 
	        writer.flush();
	        writer.close();  

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			return sb.toString();
		} catch (IOException e) {
			LOGGER.debug("postData: Error connecting to " + urlStr + ": " + e.getMessage());
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				LOGGER.debug("postData: Error close bufferedReader ", e);
			}
		}
		return null;
	}
	
	/**
	 * 从url加载图片
	 * 
	 * @param imgUrl
	 * @param readTimeOut 超时时间
	 * @return
	 */
	public static byte[] getImageContent(String imgUrl, int readTimeOut) {
		ByteArrayOutputStream bout = null;
		try {
			URL url = new URL(imgUrl);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(readTimeOut);

			InputStream in = conn.getInputStream();
			return ByteStreams.toByteArray(in);
		} catch (IOException e) {
			LOGGER.info("getImageContent: Error connecting to " + imgUrl + ": " + e.getMessage(), e);
		} finally {
			try {
				if (bout != null)
					bout.close();
			} catch (IOException e) {
				LOGGER.info("getImageContent: Error close byteArrayOutputStream ", e);
			}
		}
		return null;
	}
}

package com.ihelin.car.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * 所有接口的基类
 */
public class CommonUtilPub {

	public String trimString(String value) {
		String ret = null;
		if (null != value) {
			ret = value;
			if (ret.length() == 0) {
				ret = null;
			}
		}
		return ret;
	}

	/**
	 * 作用：产生随机字符串，不长于32位
	 */
	public static String createNoncestr() {
		return createNoncestr(32);
	}

	public static String createNoncestr(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		char[] chars = str.toCharArray();
		int maxIndex = str.length() - 1;

		StringBuffer res = new StringBuffer();
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res.append(chars[rd.nextInt(maxIndex)]);
		}
		return res.toString();
	}

	/**
	 * 作用：格式化参数，签名过程需要使用
	 */
	public static String formatBizQueryParaMap(Map<String, String> paraMap, boolean urlencode) {
		String buff = "";
		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(paraMap.entrySet());

		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		for (int i = 0; i < infoIds.size(); i++) {
			Map.Entry<String, String> item = infoIds.get(i);
			if (item.getKey() != "") {
				String key = item.getKey();
				String val = item.getValue();
				if (urlencode) {
					try {
						val = URLEncoder.encode(val, "utf-8");
					} catch (UnsupportedEncodingException e) {
					}
				}
				buff = buff + key + "=" + val + "&";
			}
		}

		if (buff.isEmpty() == false) {
			buff = buff.substring(0, buff.length() - 1);
		}
		return buff;
	}

	/**
	 * 作用：生成签名
	 */
	public static String getSign(Map<String, String> payApiMap, String key) {
		// 生成字符串
		String string = formatBizQueryParaMap(payApiMap, false);
		// 连接商户key：
		string = string + "&key=" + key;
		// md5编码并转成大写
		string = CryptUtil.md5(string);
		String result_ = string.toUpperCase();
		return result_;
	}

	/**
	 * 作用：array转xml
	 */
	public static String mapToXml(HashMap<String, String> arr) {
		String xml = "<xml>";

		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (IsNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";

			} else
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
		}
		xml += "</xml>";
		return xml;
	}

	public static boolean IsNumeric(String str) {
		try {
			Integer.parseInt(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}

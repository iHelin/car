package com.ihelin.car.controller;

import com.ihelin.car.utils.JSON;
import com.ihelin.car.utils.WechatUtil;

public class Test {

	public static void main(String[] args) {
		String url = "http://www.one2six.cn/user/login";
		User user = new User();
		user.setUname("18726025079");
		user.setPasswd("123456");
		String outStr = JSON.toJson(user);
		//String outStr = "{\"account\":\"18726025079\",\"password\":\"123456\"}";
		System.out.println(outStr);
		String res = WechatUtil.doPostStr(url, outStr);
		System.out.println(res);
	}

	static class User {
		String uname;
		String passwd;

		public String getUname() {
			return uname;
		}

		public void setUname(String uname) {
			this.uname = uname;
		}

		public String getPasswd() {
			return passwd;
		}

		public void setPasswd(String passwd) {
			this.passwd = passwd;
		}
	}
}

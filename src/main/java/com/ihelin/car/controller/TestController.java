package com.ihelin.car.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.utils.WechatUtil;

@Controller
public class TestController extends BaseController {
	private static final String GET_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";

	@RequestMapping("test1")
	public void test() {
		String url = GET_MEDIA_URL.replace("ACCESS_TOKEN", accessTokenManager.getAccessToken().getToken());
		String data = "{\"type\":TYPE,\"offset\":OFFSET,\"count\":COUNT}";
		String res = WechatUtil.doPostStr(url, data);
		System.out.println(res);
	}
}

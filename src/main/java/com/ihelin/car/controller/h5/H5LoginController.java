package com.ihelin.car.controller.h5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.config.CommonConfig;
import com.ihelin.car.db.entity.User;
import com.ihelin.car.model.WXUser;
import com.ihelin.car.utils.JSON;
import com.ihelin.car.utils.WechatUtil;

@Controller
public class H5LoginController extends H5BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(H5LoginController.class);

	/**
	 * 第一步：用户同意授权，获取code
	 * 
	 * @param from
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/wx_login")
	public String loginVerify(String from, HttpServletRequest request) throws UnsupportedEncodingException {
		LOGGER.info("Browser's user-agent: " + request.getHeader("User-Agent"));
		String url = CommonConfig.getDomainUrl() + "/h5/weixin_login.do?from=" + from;
		String callbackUrl = CommonConfig.getDomainUrl() + "/h5/oauth_url_callback?" + "url="
				+ URLEncoder.encode(url, "UTF-8");
		String wxUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + CommonConfig.getAppID()
				+ "&redirect_uri=" + URLEncoder.encode(callbackUrl, "UTF-8")
				+ "&response_type=code&scope=snsapi_userinfo&state=#wechat_redirect";
		LOGGER.info("Oauth redirect url: " + wxUrl);
		return "redirect:" + wxUrl;
	}

	/**
	 * 第二步：通过code换取网页授权access_token
	 * 
	 * @param url
	 * @param code
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "oauth_url_callback")
	public String oauthUrlCallback(String url, String code, String state) {
		LOGGER.info("code: " + code + ", state: " + state);
		OauthModel oauthRes = null;
		int retryCount = 0;
		String api = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + CommonConfig.getAppID() + "&secret="
				+ CommonConfig.getAppSecret() + "&code=" + code + "&grant_type=authorization_code";
		while (retryCount < 3) {
			String res = WechatUtil.doGetStr(api);
			if (StringUtils.isEmpty(res)) {
				retryCount++;
			} else {
				oauthRes = JSON.parseObject(res, OauthModel.class);
				if (oauthRes != null)
					break;
			}
		}
		if (oauthRes != null && !StringUtils.isEmpty(url)) {
			if (url.endsWith("&") || url.endsWith("?")) {
				// 直接加openid
			} else if (url.contains("?")) {
				url += "&";
			} else {
				url += "?";
			}
			url += "openId=" + oauthRes.openid + "&accessToken=" + oauthRes.access_token + "&code=" + code + "&state="
					+ state;
			return "redirect:" + url;
		}
		return "redirect:404";
	}

	/**
	 * 第三步：获取用户信息
	 * 
	 * @param openId
	 * @param from
	 * @param accessToken
	 * @param code
	 * @param state
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "weixin_login.do")
	public String doWeixinLogin(String openId, String from, String accessToken, String code, String state) {
		LOGGER.info("请求微信登录，openid: " + openId);
		if (StringUtils.isNotBlank(openId)) {
			User user = userManager.selectUserByOpenId(openId);
			if (user == null) {
				WXUser wxUser = userManager.selectWXUserByOpenId(openId,
						accessTokenManager.getAccessToken().getToken());
				user = userManager.transWXUserToUser(wxUser);
				userManager.insertUser(user);
			}
			setWeixinUser(user);
		} else {
			LOGGER.error("openid is blank");
			return "redirect:/h5/index";
		}
		if (StringUtils.isNotBlank(from))
			return "redirect:" + from;
		return "redirect:/h5/index";
	}

	static class OauthModel {
		public String access_token;
		public String openid;
		public String scope;
	}

	@RequestMapping(value = "login")
	public String login() {
		return ftl("login");
	}

	@RequestMapping(value = "reg")
	public String reg() {
		return ftl("reg");
	}

}

package com.ihelin.car.controller.h5;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.ihelin.car.config.CommonConfig;
import com.ihelin.car.db.entity.User;
import com.ihelin.car.db.manager.OrderManager;
import com.ihelin.car.utils.CommonUtilPub;
import com.ihelin.car.utils.RequestUtil;
import com.ihelin.car.utils.ResponseUtil;
import com.ihelin.car.utils.WechatUtil;

@Controller
@RequestMapping("/h5/user/**")
public class UserController extends H5BaseController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String UNION_PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 提交订单
	 * 
	 * @param productId
	 * @param productNum
	 * @param siteId
	 * @param takeType
	 * @param receiptName
	 * @param receiptPhone
	 * @param receiptZone
	 * @param receiptAddress
	 */
	@RequestMapping("submit_order")
	@Transactional
	public synchronized void submitOrder(HttpServletResponse response, HttpServletRequest request) {
		String orderId = OrderManager.createOrderId();
		Map<String, Object> res = Maps.newConcurrentMap();
		User user = getWeixinUser();
		HashMap<String, String> paramMap = Maps.newHashMap();
		paramMap.put("openid", user.getOpenId());
		paramMap.put("trade_type", "JSAPI");
		String ip = RequestUtil.getRealIp(request);
		ip = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
		paramMap.put("spbill_create_ip", ip);
		paramMap.put("body", "润滑油商城");// 商家名称-销售商品类目 如：罗辑思维-图书
		paramMap.put("out_trade_no", orderId);
		paramMap.put("total_fee", "" + 1);
		paramMap.put("notify_url", CommonConfig.getDomainUrl() + "/h5/wx_pay_notify");
		paramMap.put("appid", CommonConfig.getAppID());
		paramMap.put("mch_id", CommonConfig.getMchId());
		paramMap.put("nonce_str", CommonUtilPub.createNoncestr(32));
		paramMap.put("sign", CommonUtilPub.getSign(paramMap, CommonConfig.getKey()));
		String xmlData = CommonUtilPub.mapToXml(paramMap);
		logger.info("before prePay: xmlData: " + xmlData);
		String resXml = WechatUtil.doPostStr(UNION_PAY_URL, xmlData);
		Document document = null;
		String prepayId = null;
		try {
			document = DocumentHelper.parseText(resXml);
		} catch (DocumentException e) {
			logger.info("parse resXml error: resXml" + resXml);
			ResponseUtil.writeFailedJSON(response, "xml解析出错，请稍后再试！");
			return;
		}
		if (document == null) {
			logger.info("Document is null");
			ResponseUtil.writeFailedJSON(response, "xml解析出错，请稍后再试！");
			return;
		}
		Element root = document.getRootElement();
		if (root == null) {
			logger.info("Root(parseText response xml) is null");
			ResponseUtil.writeFailedJSON(response, "xml解析出错，请稍后再试！");
			return;
		}
		Element piEle = root.element("prepay_id");
		if (piEle == null) {
			logger.info("prepay_id is null");
			ResponseUtil.writeFailedJSON(response, "预支付id不存在！");
			return;
		}
		prepayId = piEle.getText();
		logger.info("prepay_id:" + prepayId);
		Map<String, String> payApiMap = Maps.newHashMap();
		payApiMap.put("signType", "MD5");
		payApiMap.put("appId", CommonConfig.getAppID());
		payApiMap.put("package", "prepay_id=" + prepayId);
		payApiMap.put("nonceStr", CommonUtilPub.createNoncestr(32));
		payApiMap.put("timeStamp", Long.toString(System.currentTimeMillis() / 1000));
		payApiMap.put("paySign", CommonUtilPub.getSign(payApiMap, CommonConfig.getKey()));
		ResponseUtil.writeSuccessJSON(response, res);
	}

	/**
	 * 微信支付
	 * 
	 * @return
	 */
	@RequestMapping(value = "pay")
	public String pay(String orderId, Model model, HttpServletRequest request) {
		User user = getWeixinUser();
		HashMap<String, String> paramMap = Maps.newHashMap();
		paramMap.put("openid", user.getOpenId());
		paramMap.put("trade_type", "JSAPI");
		String ip = RequestUtil.getRealIp(request);
		ip = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
		paramMap.put("spbill_create_ip", ip);
		paramMap.put("body", "润滑油商城");// 商家名称-销售商品类目 如：罗辑思维-图书
		paramMap.put("out_trade_no", orderId);
		paramMap.put("total_fee", "" + 1);
		paramMap.put("notify_url", CommonConfig.getDomainUrl() + "/h5/wx_pay_notify");
		paramMap.put("appid", CommonConfig.getAppID());
		paramMap.put("mch_id", CommonConfig.getMchId());
		paramMap.put("nonce_str", CommonUtilPub.createNoncestr(32));
		paramMap.put("sign", CommonUtilPub.getSign(paramMap, CommonConfig.getKey()));
		String xmlData = CommonUtilPub.mapToXml(paramMap);
		logger.info("before prePay: xmlData: " + xmlData);
		String resXml = WechatUtil.doPostStr(UNION_PAY_URL, xmlData);
		Document document = null;
		String prepayId = null;
		try {
			document = DocumentHelper.parseText(resXml);
		} catch (DocumentException e) {
			logger.info("parse resXml error: resXml" + resXml);
			model.addAttribute("errMsg", "xml解析出错，请稍后再试！");
			return ftl("wx_pay");
		}
		if (document == null) {
			logger.info("Document is null");
			model.addAttribute("errMsg", "xml解析出错，请稍后再试！");
			return ftl("wx_pay");
		}
		Element root = document.getRootElement();
		if (root == null) {
			logger.info("Root(parseText response xml) is null");
			model.addAttribute("errMsg", "xml解析出错，请稍后再试！");
			return ftl("wx_pay");
		}
		Element piEle = root.element("prepay_id");
		if (piEle == null) {
			logger.info("prepay_id is null");
			model.addAttribute("errMsg", "预支付id不存在！");
			return ftl("wx_pay");
		}
		prepayId = piEle.getText();
		logger.info("prepay_id:" + prepayId);
		Map<String, String> payApiMap = Maps.newHashMap();
		payApiMap.put("signType", "MD5");
		payApiMap.put("appId", CommonConfig.getAppID());
		payApiMap.put("package", "prepay_id=" + prepayId);
		payApiMap.put("nonceStr", CommonUtilPub.createNoncestr(32));
		payApiMap.put("timeStamp", Long.toString(System.currentTimeMillis() / 1000));
		payApiMap.put("paySign", CommonUtilPub.getSign(payApiMap, CommonConfig.getKey()));
		model.addAttribute("payApiMap", payApiMap);
		return ftl("wx_pay");
	}
	
	@RequestMapping(value = "user_center")
	public String userCenter() {
		return ftl("user_center");
	}

}

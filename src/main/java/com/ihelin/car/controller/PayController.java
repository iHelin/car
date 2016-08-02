package com.ihelin.car.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beust.jcommander.internal.Lists;
import com.ihelin.car.config.CommonConfig;
import com.ihelin.car.utils.CheckUtil;
import com.ihelin.car.utils.GoodsDetail;
import com.ihelin.car.utils.GoodsModel;
import com.ihelin.car.utils.JSON;
import com.ihelin.car.utils.UnionOrderModel;
import com.thoughtworks.xstream.XStream;

@Controller
@RequestMapping("testpay")
public class PayController extends BaseController {

	public static final String payurl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERCHAR = "0123456789";
	public static final String key = "192006250b4c09247ec02edce69f6a2d";

	@RequestMapping("abc")
	public String abc() {
		String mch_id = "1354907702";
		String device_info = "";
		String body = "成为1048会员";
		UnionOrderModel order = new UnionOrderModel();
		GoodsDetail detail = new GoodsDetail();
		GoodsModel goods = new GoodsModel();
		goods.setGoods_id("1");
		goods.setWxpay_goods_id("10");
		goods.setGoods_name("1048会员");
		goods.setGoods_num(1);
		goods.setPrice(100 * 10);
		goods.setBody(body);
		List<GoodsModel> goods_detail = Lists.newArrayList();
		goods_detail.add(goods);
		detail.setGoods_detail(goods_detail);
		String goodsJson = JSON.toJson(detail);
		goodsJson = "<![CDATA[" + goodsJson + "]]";
		System.out.println(goodsJson);
		order.setAppid(CommonConfig.getAppID());
		order.setAttach("支付测试");
		order.setBody("JSAPI支付测试");
		order.setMch_id(mch_id);
		order.setDetail(goodsJson);
		String nonce = generateString(32);
		order.setNonce_str(nonce);
		order.setNotify_url("http://ihelin.vip.natapp.cn/notify");
		String openId = "";
		String out_trade_no = "";
		String spbill_create_ip = "";
		order.setOpenid(openId);
		order.setOut_trade_no(out_trade_no);
		order.setSpbill_create_ip(spbill_create_ip);
		order.setTotal_fee(10 * 100);
		order.setFee_type("CNY");
		order.setTrade_type("JSAPI");
		SortedMap<String, Object> parameters = new TreeMap<String, Object>();
		parameters.put("appid", CommonConfig.getAppID());
		parameters.put("mch_id", mch_id);
		parameters.put("device_info", device_info);
		parameters.put("body", body);
		parameters.put("nonce_str", nonce);
		String sign = createSign(parameters);
		order.setSign(sign);
		String xmlStr = "";
		XStream xstream = new XStream();
		xstream.alias("xml", order.getClass());
		xmlStr = xstream.toXML(order);
		System.out.println(xmlStr);
		return "index";
	}

	public static void main(String[] args) {
		System.out.println(">>>模拟微信支付<<<");
		System.out.println("==========华丽的分隔符==========");
		// 微信api提供的参数
		String appid = "wxd930ea5d5a258f4f";
		String mch_id = "10000100";
		String device_info = "1000";
		String body = "test";
		String nonce_str = "ibuaiVcKdpRxkhJA";

		SortedMap<String, Object> parameters = new TreeMap<String, Object>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("device_info", device_info);
		parameters.put("body", body);
		parameters.put("nonce_str", nonce_str);

		String weixinApiSign = "9A0A8659F005D6984697E2CA0A9CF3B7";
		System.out.println("微信的签名是：" + weixinApiSign);
		String mySign = createSign(parameters);
		System.out.println("我的签名是：" + mySign);

		if (weixinApiSign.equals(mySign)) {
			System.out.println("恭喜你成功了~");
		} else {
			System.out.println("注定了你是个失败者~");
		}
		String userAgent = "Mozilla/5.0(iphone;CPU iphone OS 5_1_1 like Mac OS X) AppleWebKit/534.46(KHTML,like Geocko) Mobile/9B206 MicroMessenger/5.0";
		char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
		System.out.println("微信的版本号：" + new String(new char[] { agent }));
	}

	public static String createSign(SortedMap<String, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, Object>> es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, Object>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = CheckUtil.GetMD5Code(sb.toString()).toUpperCase();
		return sign;
	}

	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

}

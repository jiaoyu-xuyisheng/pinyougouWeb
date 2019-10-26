package com.pinyougou.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pinyougou.pay.service.WeixinPayService;

import util.HttpClient;



@Service
public class WeixinPayServiceImpl implements WeixinPayService {
	
	//微信公众账号或开放平台APP的唯一标识
	@Value("${appid}")
	private String appid;
	
	//商户号  (配置文件中的partner)
	@Value("${partner}")
	private String partner;
		
	//商户密钥
	@Value("${partnerkey}")
	private String partnerkey;

	@Override
	public Map createNative(String out_trade_no, String total_fee) {
		Map<String,String> param=new HashMap<String,String>();
		param.put("appid", appid);
		param.put("mch_id", partner);
		param.put("nonce_str",WXPayUtil.generateNonceStr());//生成随机字符串
		param.put("body", "品优购");//商品描述
		param.put("out_trade_no", out_trade_no);// 订单号
		param.put("total_fee", total_fee); //金额
		param.put("spbill_create_ip", "127.0.0.1");
		param.put("notify_url",  "http://www.itcast.cn");
		param.put("trade_type", "NATIVE");
		try {
			String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
			System.out.println(xmlParam);
			HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
			client.setHttps(true);
			client.setXmlParam(xmlParam);
			client.post();	
			//3.获得结果 
			String result = client.getContent();
			System.out.println(result);
			Map<String, String> resultMap = WXPayUtil.xmlToMap(result);			
			Map<String, String> map=new HashMap<>();
			map.put("code_url", resultMap.get("code_url"));//支付地址
			map.put("total_fee", total_fee);//总金额
			map.put("out_trade_no",out_trade_no);//订单号
			return map;
		
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<>();
		}
		
	}

	@Override
	public Map queryPayStatus(String out_trade_no) {
		
		Map param=new HashMap();
		param.put("appid", appid);//公众账号ID
		param.put("mch_id", partner);//商户号
		param.put("out_trade_no", out_trade_no);//订单号
		param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
		String url="https://api.mch.weixin.qq.com/pay/orderquery";	
		try {
			String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);	
			HttpClient client=new HttpClient(url);
			client.setHttps(true);
			client.setXmlParam(xmlParam);
			client.post();
			String result = client.getContent();			
			Map<String, String> map = WXPayUtil.xmlToMap(result);
			System.out.println(map);
			return map;			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

	/**
	 * 关闭订单
	 */
	@Override
	public Map closePay(String out_trade_no) {
		Map param=new HashMap();
		param.put("appid", appid);//公众账号ID
		param.put("mch_id", partner);//商户号
		param.put("out_trade_no", out_trade_no);//订单号
		param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
		String url="https://api.mch.weixin.qq.com/pay/closeorder";
		try {
			String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
			HttpClient client=new HttpClient(url);
			client.setHttps(true);
			client.setXmlParam(xmlParam);
			client.post();
			String result = client.getContent();
			Map<String, String> map = WXPayUtil.xmlToMap(result);
			System.out.println(map);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

}

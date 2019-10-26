package com.pinyougou.cart.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.order.service.OrderService;
import com.pinyougou.pay.service.WeixinPayService;
import com.pinyougou.pojo.TbPayLog;

import entity.Result;
import util.IdWorker;

@RestController
@RequestMapping("/pay")
public class PayController {

	@Reference
	private WeixinPayService weixinPayService;
	
	@Reference
	private OrderService orderService;
	
	
	/**
	 * 生成二维码
	 */
	
	/**
	 * 生成二维码
	 * @return
	 */
	@RequestMapping("/createNative")
	public Map createNative(){
		//获取当前用户		
		String userId=SecurityContextHolder.getContext().getAuthentication().getName();
		//到redis查询支付日志
		TbPayLog payLog = orderService.searchPayLogFromRedis(userId);
		//判断支付日志存在
		if(payLog!=null){
			return weixinPayService.createNative(payLog.getOutTradeNo(),payLog.getTotalFee()+"");
		}else{
			return new HashMap();
		}		
	}
	
	
	@RequestMapping("/queryPayStatus")
	public Result queryPayStatus(String out_trade_no) {
		Result result=null;
		int x=0;
		while(true) {
			Map<String,String> map=weixinPayService.queryPayStatus(out_trade_no);
			if(map==null) {
				result=new Result(false,"支付出错");
				break;
			}
			
			if(map.get("trade_state").equals("SUCCESS")){//如果成功				
				result=new  Result(true, "支付成功");
				//修改订单状态
				orderService.updateOrderStatus(out_trade_no, map.get("transaction_id"));
				break;
			}		
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			x++;
			if(x>100) {
				result=new Result(false,"二维码超时");
				break;
			}
		}
		
	
		
		return result;
	}
	
	
	
	
	
}

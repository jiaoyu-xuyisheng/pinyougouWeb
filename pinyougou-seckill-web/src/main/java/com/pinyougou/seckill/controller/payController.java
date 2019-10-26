package com.pinyougou.seckill.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pay.service.WeixinPayService;
import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.seckill.service.SeckillOrderService;

import entity.Result;

@RestController
@RequestMapping("/pay")
public class payController {
	
	
	@Reference
	private WeixinPayService weixinPayService;
	
	
	@Reference
	private SeckillOrderService seckillorderService;
	
	
	/**
	 * 生成二维码
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/createNative")
	public Map createNative() {
		
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		TbSeckillOrder seckillOrder = seckillorderService.searchOrderFromRedisByUserId(userId);
		System.out.println(seckillOrder.getMoney()+"controller");
		if(seckillOrder!=null) {
			long fen=(long)(seckillOrder.getMoney().doubleValue()*100);
			return	weixinPayService.createNative(seckillOrder.getId()+"", fen+"");
		}else {
			return new HashMap();
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryPayStatus")
	public Result queryPayStatus(String out_trade_no) {
		
		String userId=SecurityContextHolder.getContext().getAuthentication().getName();
		Result result=null;
		int x=0;
		while(true) {
			Map<String,String> map = weixinPayService.queryPayStatus(out_trade_no);
			if(map==null) {
				result=new Result(false,"支付错误");
				break;
			}
			
			if(map.get("trade_state").equals("SUCCESS")) {
				result =new Result(true,"支付成功");
				seckillorderService.saveOrderFromRedisToDb(userId,Long.valueOf(out_trade_no) ,map.get("transaction_id"));
				break;
			
			}
			
			
			try {
				Thread.sleep(3000);
			}catch(InterruptedException e) {
				
				e.printStackTrace();	
			}
			x++;
			if(x>100) {
				result =new Result(false,"二维码超时");
				Map<String,String> payResult = weixinPayService.closePay(out_trade_no);
				//如果返回的结果是正常关闭
				if(!"SUCCESS".equals(payResult.get("result_code"))) {
					if("ORDERPAID".equals(payResult.get("err_code"))) {
						result=new Result(true,"支付成功");
						seckillorderService.saveOrderFromRedisToDb(userId,Long.valueOf(out_trade_no), map.get("transaction_id"));
					}
				}
				
				if(result.isSuccess()==false) {
					System.out.println("超时，取消定单");
					//调用删除
					seckillorderService.deleteOrderFromRedis(userId, Long.valueOf(out_trade_no));
				}
				break;
			}
		}
		
		
		return result;
	}
	

}

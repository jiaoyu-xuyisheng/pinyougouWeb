package com.pinyougou.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.pojo.TbSeckillGoodsExample;
import com.pinyougou.pojo.TbSeckillGoodsExample.Criteria;

@Component
public class SeckillTask {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	@Autowired
	private TbSeckillGoodsMapper seckillGoodsMapper;
	
	/**
	 * 每分钟增加一次，看下有没有要上架的商品
	 */
	@SuppressWarnings("unchecked")
	@Scheduled(cron="0 * * * * ?")
	public void refreshSeckillGoods() {
		System.out.println("执行了秒杀商品增量更新 任务调度"+new Date());
		//查询缓存中的秒杀商品ID集合
		List goodsIdList=new ArrayList(redisTemplate.boundHashOps("seckillGoods").keys());
		System.out.println(goodsIdList);
		TbSeckillGoodsExample example=new TbSeckillGoodsExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("1");// 审核通过的商品
		criteria.andStockCountGreaterThan(0);//库存数大于0
		criteria.andStartTimeLessThanOrEqualTo(new Date());//开始日期小于等于当前日期
		criteria.andEndTimeGreaterThanOrEqualTo(new Date());//截止日期大于等于当前日期
		
		if(goodsIdList.size()>0){
			criteria.andIdNotIn(goodsIdList);//排除缓存中已经存在的商品ID集合
		}
		List<TbSeckillGoods> list = seckillGoodsMapper.selectByExample(example);
		for (TbSeckillGoods seckillGoods : list) {
			redisTemplate.boundHashOps("seckillGoods").put(seckillGoods.getId(), seckillGoods);
			System.out.println("增量更新秒杀商品ID:"+seckillGoods.getId());
		}
		System.out.println("....end.........");
	}
	
	
	@Scheduled(cron="* * * * * ?")
	public void removeSeckillGoods() {
		List<TbSeckillGoods> seckillGoodsList=	redisTemplate.boundHashOps("seckillGoods").values();
		System.out.println("执行了清除秒杀商品的任务"+new Date());
		for (TbSeckillGoods seckillGoods : seckillGoodsList) {
			if(seckillGoods.getEndTime().getTime()<new Date().getTime()) {
				//同步到数据库
				seckillGoodsMapper.updateByPrimaryKey(seckillGoods);				
				//清除缓存
				redisTemplate.boundHashOps("seckillGoods").delete(seckillGoods.getId());
				System.out.println("秒杀商品"+seckillGoods.getId()+"已过期");
			}
		}
		System.out.println("执行了清除秒杀商品的任务...end");
	}
	

}

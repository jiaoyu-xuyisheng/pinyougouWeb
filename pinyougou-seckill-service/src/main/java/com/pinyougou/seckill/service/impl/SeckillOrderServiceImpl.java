package com.pinyougou.seckill.service.impl;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.mapper.TbSeckillOrderMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.pojo.TbSeckillOrder;
import com.pinyougou.pojo.TbSeckillOrderExample;
import com.pinyougou.pojo.TbSeckillOrderExample.Criteria;
import com.pinyougou.seckill.service.SeckillOrderService;

import entity.PageResult;
import util.IdWorker;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

	@Autowired
	private TbSeckillOrderMapper seckillOrderMapper;
	
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private IdWorker idWorker;
	
	@Autowired
	private TbSeckillGoodsMapper seckillGoodsMapper;
	@SuppressWarnings("unchecked")
	@Override
	public void submitOrder(Long seckillId, String userId) {
		//从缓存中查询秒杀商品		seckillGoods
		
		TbSeckillGoods seckillGoods =(TbSeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(seckillId);
	
		if(seckillGoods==null){
			throw new RuntimeException("商品不存在");
		}
		if(seckillGoods.getStockCount()<=0){
			throw new RuntimeException("商品已抢购一空");
		}	
		System.out.println("第二次："+seckillGoods.getCostPrice());
		//扣减（redis）库存		
		seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
		redisTemplate.boundHashOps("seckillGoods").put(seckillId, seckillGoods);//放回缓存
		if(seckillGoods.getStockCount()==0){//如果已经被秒光
			seckillGoodsMapper.updateByPrimaryKey(seckillGoods);//同步到数据库	
redisTemplate.boundHashOps("seckillGoods").delete(seckillId);		
		}
		
		System.out.println("第三次："+seckillGoods.getCostPrice());
		//保存（redis）订单
		long orderId = idWorker.nextId();
		TbSeckillOrder seckillOrder=new TbSeckillOrder();
		seckillOrder.setId(orderId);
		seckillOrder.setCreateTime(new Date());
		seckillOrder.setMoney(seckillGoods.getCostPrice());//秒杀价格
		seckillOrder.setSeckillId(seckillId);
		seckillOrder.setSellerId(seckillGoods.getSellerId());
		seckillOrder.setUserId(userId);//设置用户ID
		seckillOrder.setStatus("0");//状态
		System.out.println(seckillOrder);
		redisTemplate.boundHashOps("seckillOrder").put(userId, seckillOrder);
	}
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeckillOrder> findAll() {
		return seckillOrderMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSeckillOrder> page=   (Page<TbSeckillOrder>) seckillOrderMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSeckillOrder seckillOrder) {
		seckillOrderMapper.insert(seckillOrder);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeckillOrder seckillOrder){
		seckillOrderMapper.updateByPrimaryKey(seckillOrder);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbSeckillOrder findOne(Long id){
		return seckillOrderMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			seckillOrderMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSeckillOrder seckillOrder, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSeckillOrderExample example=new TbSeckillOrderExample();
		Criteria criteria = example.createCriteria();
		
		if(seckillOrder!=null){			
						if(seckillOrder.getUserId()!=null && seckillOrder.getUserId().length()>0){
				criteria.andUserIdLike("%"+seckillOrder.getUserId()+"%");
			}
			if(seckillOrder.getSellerId()!=null && seckillOrder.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+seckillOrder.getSellerId()+"%");
			}
			if(seckillOrder.getStatus()!=null && seckillOrder.getStatus().length()>0){
				criteria.andStatusLike("%"+seckillOrder.getStatus()+"%");
			}
			if(seckillOrder.getReceiverAddress()!=null && seckillOrder.getReceiverAddress().length()>0){
				criteria.andReceiverAddressLike("%"+seckillOrder.getReceiverAddress()+"%");
			}
			if(seckillOrder.getReceiverMobile()!=null && seckillOrder.getReceiverMobile().length()>0){
				criteria.andReceiverMobileLike("%"+seckillOrder.getReceiverMobile()+"%");
			}
			if(seckillOrder.getReceiver()!=null && seckillOrder.getReceiver().length()>0){
				criteria.andReceiverLike("%"+seckillOrder.getReceiver()+"%");
			}
			if(seckillOrder.getTransactionId()!=null && seckillOrder.getTransactionId().length()>0){
				criteria.andTransactionIdLike("%"+seckillOrder.getTransactionId()+"%");
			}
	
		}
		
		Page<TbSeckillOrder> page= (Page<TbSeckillOrder>)seckillOrderMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override
		public TbSeckillOrder searchOrderFromRedisByUserId(String userId) {
			
			 TbSeckillOrder order= (TbSeckillOrder) redisTemplate.boundHashOps("seckillOrder").get(userId);
			 System.out.println("第4次："+order.getMoney());
			 return order;
		}

		
		/**
		 * 保存支付订单
		 */
		@Override
		public void saveOrderFromRedisToDb(String userId, Long orderId, String transactionId) {
			
			//从redis中拿到订单数据;
			TbSeckillOrder seckillOrder=(TbSeckillOrder) redisTemplate.boundHashOps("seckillOrder").get(userId);
			if(seckillOrder==null) {
				throw new RuntimeException("订单不存在");
				
			}
			
			//将数据保存在数据库中;
			seckillOrder.setTransactionId(transactionId);
			seckillOrder.setPayTime(new Date());
			seckillOrder.setStatus("1");
			seckillOrderMapper.insert(seckillOrder);
			//删除redis
			redisTemplate.boundHashOps("seckilOrder").delete(userId);

			
			
			
		}

		@Override
		public void deleteOrderFromRedis(String userId, Long orderId) {
			
		TbSeckillOrder seckillOrder=	(TbSeckillOrder) redisTemplate.boundHashOps("seckilOrder").get(userId);
		
		if(seckillOrder!=null&&seckillOrder.getId().longValue()==orderId.longValue()) {
			redisTemplate.boundHashOps("seckilOrder").delete(userId);
			TbSeckillGoods seckillGoods=(TbSeckillGoods) redisTemplate.boundHashOps("seckilGoods").get(seckillOrder.getSeckillId());
			if(seckillGoods!=null) {
				seckillGoods.setStockCount(seckillGoods.getStockCount()+1);
				redisTemplate.boundHashOps("seckilGoods").put(seckillOrder.getSeckillId(),seckillGoods);//存入缓存

			}
		
		
		
		}
		
		
		
		
		
		}
	
}

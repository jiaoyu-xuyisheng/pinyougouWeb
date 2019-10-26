package com.pinyougou.cart.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojogroup.Cart;



@Service
public class CartServiceImpl implements CartService {
	
	
	@Autowired
	private TbItemMapper itemMapper;
	
	
	
	

	@Override
	public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
		
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		if(item==null) {
			throw new RuntimeException("商品不存在");
		}
		
		if(!item.getStatus().equals("1")) {
			throw new RuntimeException("商品状态无效！！");
		}
		
		String sellerId=item.getSellerId();
		//3.根据商家ID判断购物车列表中是否存在该商家的购物车	
		Cart cart = searchCartBySellerId(cartList, sellerId);
		if(cart==null) {
			cart=new Cart();
			cart.setSellerId(sellerId);
			cart.setSellerName(item.getSeller());
			//创建对象！！
			TbOrderItem tbOrderItem = createOrderItem(item ,num);
			List<TbOrderItem> orderItemList=new ArrayList<>();
			orderItemList.add(tbOrderItem);
			cart.setOrderItemList(orderItemList);
			cartList.add(cart);
			
		}else {
			//5.如果购物车列表中存在该商家的购物车			
			// 判断购物车明细列表中是否存在该商品
			TbOrderItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(), itemId);
			if(orderItem==null) {
				orderItem=createOrderItem(item ,num);
				cart.getOrderItemList().add(orderItem);
				
			}else {
				orderItem.setNum(orderItem.getNum()+num);
				orderItem.setTotalFee(new BigDecimal(orderItem.getNum()*orderItem.getPrice().doubleValue()));
				
				//如果数量操作后小于等于0，则移除
				if(orderItem.getNum()<=0) {
					cart.getOrderItemList().remove(orderItem);
				}
				
				if(cart.getOrderItemList().size()==0) {
					cartList.remove(cart);
				}
			}
			
		
		
		
		
		
		
		}
	
		
		return cartList;
	}
	
	
	/**
	 * 查看列表中是否存在该商家！！！
	 * @param cartList
	 * @param sellerId
	 * @return
	 */
	private Cart searchCartBySellerId(List<Cart> cartList,String sellerId) {
		
		for(Cart cart :cartList) {
			if(cart.getSellerId().equals(sellerId)) {
				return cart;
			}
		}
		
		
		return null;
		
	}
	
	
	/**
	 * 创造对象！！
	 * @param item
	 * @param num
	 * @return
	 */
	private TbOrderItem createOrderItem(TbItem item ,Integer num) {
		if(num<0) {
			throw new RuntimeException("数量非法");
		}
		
		TbOrderItem orderItem =new TbOrderItem();
		orderItem.setGoodsId(item.getGoodsId());
		orderItem.setItemId(item.getId());
		orderItem.setNum(num);
		orderItem.setPicPath(item.getImage());
		orderItem.setPrice(item.getPrice());
		orderItem.setSellerId(item.getSellerId());
		orderItem.setTitle(item.getTitle());
		orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*num));
		return orderItem;
		
		
	}
	
	
	
	
	/**
	 * 查询该商家是否存在该商品！！
	 * @param orderItemList
	 * @param itemId
	 * @return
	 */
	private TbOrderItem searchOrderItemByItemId(List<TbOrderItem> orderItemList ,Long itemId) {
		
		
		for (TbOrderItem Otm : orderItemList) {
			if(Otm.getItemId().longValue()==itemId.longValue()) {
				return Otm;
			}
		}
		
		return null;
	}


	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public List<Cart> findCartListFromRedis(String username) {
		
		System.out.println("从redis中提取购物车数据。。。"+username);
		List<Cart> cartList=(List<Cart>) redisTemplate.boundHashOps("cartList").get(username);
		if(cartList==null) {
			cartList=new ArrayList();
		}
		
		return cartList;
	}


	@Override
	public void saveCartListToRedis(String username, List<Cart> cartList) {
		
		System.out.println("向redis存入购物车数据。。。"+username);
		redisTemplate.boundHashOps("cartList").put(username, cartList);
		
	}


	@Override
	public List<Cart> mergeCartList(List<Cart> cartList1, List<Cart> cartList2) {
		System.out.println("合并购物车");
		for(Cart cart : cartList2) {
			for (TbOrderItem oitem : cart.getOrderItemList()) {
				cartList1=addGoodsToCartList(cartList1, oitem.getItemId(), oitem.getNum());
			}
		}
		return cartList1;
	}
	

}

package com.pinyougou.manager.controller;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.dubbo.config.annotation.Reference;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.sellergoods.service.GoodsService;

import entity.PageResult;
import entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
	
	
	

	@Reference
	private GoodsService goodsService;
	
	

	
	@Autowired
	private Destination queueSolrDestination;
	
	@Autowired
	private Destination topicPageDestination;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	
	@Autowired
	private Destination queueSolrDeleteDestination;//用户在索引库中删除记录
	
	
	
	@RequestMapping("/genHtml")
	public void genHtml(Long goodsId) {
		
		
		
		//jmsTemplate.send(messageCreator);
		//itemPageService.genItemHtml(goodsId);
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return goodsService.findPage(page, rows);
	}
	
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods){
		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	
	@Autowired
	private Destination topicPageDeleteDestination;//用于删除静态网页的消息
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(final Long [] ids){
		try {
			goodsService.delete(ids);
			
			
			//删除索引
			
			jmsTemplate.send(queueSolrDeleteDestination,new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					
					return session.createObjectMessage(ids);
				}
			});
			
			//删除页面
			jmsTemplate.send(topicPageDeleteDestination, new MessageCreator() {		
				@Override
				public Message createMessage(Session session) throws JMSException {	
					return session.createObjectMessage(ids);
				}
			});	
			//itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	//@Reference(timeout=500000)
	//private ItemSearchService itemSearchService;
		/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
		return goodsService.findPage(goods, page, rows);		
	}
	
	@RequestMapping("/updateStatus")
	public Result updateStatus(Long[] ids, String status) {
		try {
			goodsService.updateStatus(ids, status);
			if("1".equals(status)) {
				List<TbItem> list = goodsService.findItemListByGoodsIdandStatus(ids, status);
				
				//调用搜索接口实现数据批量导入
				if(list.size()>0) {
					
					final String jsonString=JSON.toJSONString(list);
					//添加索引
					jmsTemplate.send(queueSolrDestination,new MessageCreator() {
						
						@Override
						public Message createMessage(Session session) throws JMSException {
							return session.createTextMessage(jsonString);
						}
					});
					
					
					
					
					
					//itemSearchService.importList(list);
				}else {
					System.out.println("没有明细数据");
				}
				//静态页生成
				for(final Long goodsId:ids){
				
					jmsTemplate.send(topicPageDestination,new MessageCreator() {
						
						@Override
						public Message createMessage(Session session) throws JMSException {
							// TODO Auto-generated method stub
							return session.createTextMessage(goodsId+"");
						}
					});						
				}
			}
				
			return new Result(true, "审核成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "审核失败");
		}
		
	}
	
}

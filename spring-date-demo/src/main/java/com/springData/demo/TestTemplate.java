package com.springData.demo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.response.UpdateResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jiaoyu.teacher.service.KeService;
import com.springData.pojo.Ke;
import com.springData.pojo.TbItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-solr.xml")
public class TestTemplate {
	
	@Autowired
	private SolrTemplate solrTemplate;
	
	

	
	
	@Reference
	private KeService keService;
	
	/**
	 * 导入课程数据
	 */
	
	@Test
	public void importKeData() {
		List<com.jiaoyu.pojo.Ke> list = keService.findAll();
		System.out.println(list);
		solrTemplate.saveBeans(list);
		solrTemplate.commit();
		System.out.println("==导入成功==");
	}
	
	@Test
	public void testAdd() {
		TbItem item=new TbItem();
		item.setId(1L);
		item.setBrand("华为");
		item.setCategory("手机");
		item.setGoodsId(1L);
		item.setSeller("华为2号专卖店");
		item.setTitle("华为 META9");
		item.setPrice(new BigDecimal(2000));
		solrTemplate.saveBean(item);
		solrTemplate.commit();
	}
	
	
	@Test
	public void testAddKe() {
		Ke ke=new Ke();
		ke.setId(1);
		ke.setCompany("义生教育");
		ke.setPublisherId("xuyisheng");
		ke.setImgurl("http://www.baidu.com");
		ke.setPageView(200);
		ke.setCharge("免费！！");
		ke.setKeurl("http:///www.baidu.com");
		ke.setTitle("走进java哈哈");		
		solrTemplate.saveBean(ke);
		solrTemplate.commit();
	}
	
	
	/**
	 * 查一个元素！！
	 */
	@Test
	public void testFindOne() {
		TbItem tbItem = solrTemplate.getById(1L, TbItem.class);
		System.out.println(tbItem.getTitle());
	}
	
	/**
	 * 删除一个元素
	 */
	@Test 
	public void testDelete() {
		 solrTemplate.deleteById("1");
		 solrTemplate.commit();
	}
	
	
	/**
	 * 增加查询
	 */
	@Test
	public void testAddList() {
		List<TbItem> list=new ArrayList();
		
		for(int i=0;i<100;i++) {
			TbItem item=new TbItem();
			item.setId(1L+i);
			item.setBrand("华为");
			item.setGoodsId(1L);
			item.setSeller("华为2号专卖店");
			item.setTitle("华为Mate"+i);
			item.setPrice(new BigDecimal(2000+i));	
			list.add(item);	
		}
		
		solrTemplate.saveBeans(list);
		solrTemplate.commit();
	}
	
	/**
	 * 分页
	 */
	@Test
	public void testPageQuery() {
		Query query=new SimpleQuery("*:*");
		query.setOffset(10);
		query.setRows(20);
		ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
		long total = page.getTotalElements();
		int pages = page.getTotalPages();
		System.out.println("共有"+total+"条"+","+pages+"页");
		
		List<TbItem> content = page.getContent();
		showList(content);
	}
	
	
	public void showList(List<TbItem> list) {
		
		for (TbItem item : list) {
			System.out.println(item.getTitle()+"--"+item.getPrice());
		}
	} 
	
	/**
	 * 条件查询
	 */
	@Test
	public void testPageQueryMutil() {
		Query query=new SimpleQuery();
		Criteria critercia=new Criteria("item_title").contains("2");
		critercia.and("item_title").contains("5");
		query.addCriteria(critercia);
		ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
		List<TbItem> content = page.getContent();
		showList(content);
		
	}
	
	
	@Test
	public void testDeleteAll() {
		SolrDataQuery query=new SimpleQuery("*:*");
		solrTemplate.delete(query);
		solrTemplate.commit();
	}
	
	
}


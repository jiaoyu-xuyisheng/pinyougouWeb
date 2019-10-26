package com.pinyougou.search.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FilterQuery;
import org.springframework.data.solr.core.query.GroupOptions;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.data.solr.core.query.result.GroupEntry;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.GroupResult;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;

@Service(timeout=3000)
public class ItemSearchServiceImpl implements ItemSearchService{
	
	@Autowired
	private SolrTemplate solrTemplate;
	
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public Map<String, Object> search(Map searchMap) {
		Map<String,Object> map=new HashMap();
		
		
		String keywords=(String) searchMap.get("keywords");
		searchMap.put("keywords",keywords.replaceAll(" ",""));
	/*	Query query=new SimpleQuery("*:*");
		Criteria c=new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(c);
		ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
		map.put("rows", page.getContent());*/
		//1高亮显示
		Map putAll = putAll(searchMap);
		map.putAll(putAll);
		//2分类列表
		List<String> categoryList = searchCategory(searchMap);
		map.put("categoryList",categoryList);
		
		if(categoryList.size()>0) {
			map.putAll(searchBrandAndSpecList(categoryList.get(0)));
		}
		
		//3.查询品牌和规格列表
				String categoryName=(String)searchMap.get("category");
				if(!"".equals(categoryName)){//如果有分类名称
					map.putAll(searchBrandAndSpecList(categoryName));			
				}else{//如果没有分类名称，按照第一个查询
					if(categoryList.size()>0){
						map.putAll(searchBrandAndSpecList(categoryList.get(0)));
					}
				}
		
		return map;
	}
	
	private Map putAll(Map searchMap) {
		Map<String,Object> map=new HashMap();
		HighlightQuery query=new SimpleHighlightQuery();
		HighlightOptions highlightOptions=new HighlightOptions().addField("item_title");
		highlightOptions.setSimplePrefix("<em style='color:red'>");
		highlightOptions.setSimplePostfix("</em>");
		//设置高亮
		query.setHighlightOptions(highlightOptions);
		//1.1关键字查询
		Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);
		//*****************************************************************************
		//1.2按分类筛选
		if(!"".equals(searchMap.get("category"))) {
			Criteria filterCriteria=new Criteria("item_category").is(searchMap.get("category"));
			FilterQuery filterQuery=new SimpleFilterQuery(filterCriteria);
			query.addFilterQuery(filterQuery);
		
		
		}
		//1.3按品牌筛选
		if(!"".equals(searchMap.get("brand"))){			
			Criteria filterCriteria=new Criteria("item_brand").is(searchMap.get("brand"));
			FilterQuery filterQuery=new SimpleFilterQuery(filterCriteria);
			query.addFilterQuery(filterQuery);
		}
		//1.4过滤规格
		if(searchMap.get("spec")!=null){
		    Map<String,String> specMap= (Map) searchMap.get("spec");
		    for(String key:specMap.keySet() ){
			Criteria filterCriteria=new Criteria("item_spec_"+key).is( specMap.get(key) );
			FilterQuery filterQuery=new SimpleFilterQuery(filterCriteria);
			query.addFilterQuery(filterQuery);					
		    }
		}
		//1.5过滤价格
		if(!"".equals(searchMap.get("price"))) {
			String[] price =((String)searchMap.get("price")).split("-");
			if(!price[0].equals("0")) {//如果区间起点不等于0
				Criteria filterCriteria=new Criteria("item_price").greaterThanEqual(price[0]);
				FilterQuery filterQuery=new SimpleFilterQuery(filterCriteria);
				query.addFilterQuery(filterQuery);
			}
			if(!price[1].equals("*")) {
				Criteria filterCriteria=new  Criteria("item_price").lessThanEqual(price[1]);
				FilterQuery filterQuery=new SimpleFilterQuery(filterCriteria);
				query.addFilterQuery(filterQuery);
			}
			
		}
		
		
		//1.6分页查询 pageNo
		Integer pageNo=(Integer) searchMap.get("pageNo");
		if(pageNo==null) {
			pageNo=1;
		}
		
		Integer pageSize=(Integer) searchMap.get("pageSize");
		
		if(pageSize==null) {
			pageSize=20;
		}
		query.setOffset((pageNo-1)*pageSize);
		query.setRows(pageSize);
		
		
		//1.7排序
		String sortValue=(String)searchMap.get("sort");//ASC DESC
		String sortField=(String)searchMap.get("sortField");//排序字段
		if(sortValue!=null&&!sortValue.equals("")) {
			Sort sort=new Sort(Sort.Direction.ASC,"item_"+sortField);
			query.addSort(sort);
		}
		
		if(sortValue.equals("DESC")) {
			Sort sort=new Sort(Sort.Direction.DESC,"item_"+sortField);
			query.addSort(sort);
		}
		
		//**********************************************************************************
		HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);
		//高亮入口
		List<HighlightEntry<TbItem>> highlighted = page.getHighlighted();
		for (HighlightEntry<TbItem> h : highlighted) {
			TbItem item = h.getEntity();//获取原实体类
			if(h.getHighlights().size()>0&&h.getHighlights().get(0).getSnipplets().size()>0) {
				item.setTitle(h.getHighlights().get(0).getSnipplets().get(0));//设置高亮结果
			}
			
		}
		map.put("rows", page.getContent());
		map.put("totalPages", page.getTotalPages());//总页数
		map.put("total", page.getTotalElements());//总条数;
		
		return map;
	}
	
	/**
	 * 查询分类列表
	 */
	private List searchCategory(Map searchMap) {
		
		List<String> list=new ArrayList();
		Query query=new SimpleQuery();
		Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);
		//添加分组字段
		query.setGroupOptions(new GroupOptions().addGroupByField("item_category"));
		GroupPage<TbItem> page = solrTemplate.queryForGroupPage(query, TbItem.class);
		//得到以item_category的分组！！
		GroupResult<TbItem> groupResult = page.getGroupResult("item_category");
		//得到分组结果入口页
		Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();
		//得到分组入口集合
		List<GroupEntry<TbItem>> content = groupEntries.getContent();
		for (GroupEntry<TbItem> entry : content) {
			list.add(entry.getGroupValue());
		}
		return list;
	}
	
	private Map searchBrandAndSpecList(String category){
		Map map=new HashMap();
		//得到模板id
		Long typeId=(Long) redisTemplate.boundHashOps("itemCat").get(category);
		//得到规格列表
		if(typeId!=null) {
			List brandList =(List) redisTemplate.boundHashOps("brandList").get(typeId);
			map.put("brandList", brandList);
			List specList=(List)  redisTemplate.boundHashOps("specList").get(typeId);
			map.put("specList", specList);
		}
		
		return map;
	}
	
	@Override
	public void importList(List list) {
		solrTemplate.saveBeans(list);
		solrTemplate.commit();
	}

	

	@Override
	public void deleteByGoodsIds(List goodsIdList) {
		System.out.println("删除商品ID"+goodsIdList);
		Query query=new SimpleQuery();
		Criteria criteria=new Criteria("item_goodsid").in(goodsIdList);
		query.addCriteria(criteria);
		solrTemplate.delete(query);
		solrTemplate.commit();		
	}

}

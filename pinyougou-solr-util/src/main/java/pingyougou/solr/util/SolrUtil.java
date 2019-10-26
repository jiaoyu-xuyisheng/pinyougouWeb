package pingyougou.solr.util;

import java.util.List;
import java.util.Map;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.pojo.TbItemExample.Criteria;

//相当于一个bean
@Component
public class SolrUtil {
	
	@Autowired
	private TbItemMapper itemMapper;
	
	
	@Autowired
	private SolrTemplate solrTemplate;
	
	public void importItemData() {
		TbItemExample example=new TbItemExample();
		 Criteria criteria = example.createCriteria();
		 criteria.andStatusEqualTo("1");
		List<TbItem> list = itemMapper.selectByExample(example);
		System.out.println("word");
		for (TbItem item : list) { 
			Map specMap = JSON.parseObject(item.getSpec());
			item.setSpecMap(specMap);
			System.out.println(item.getTitle());
			
		}
		solrTemplate.saveBeans(list);
		solrTemplate.commit();
	}
	
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
		SolrUtil solrUtil = (SolrUtil) context.getBean("solrUtil");	
		solrUtil.importItemData()	;
	
	}

}

package com.pinyougou.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.pojo.TbBrandExample.Criteria;
import com.pinyougou.sellergoods.service.BrandService;

import entity.PageResult;

@Service(timeout=100000)
@Transactional
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private TbBrandMapper tbBrandMapper;

	@Override
	public List<TbBrand> findAll() {
		
		return  tbBrandMapper.selectByExample(null);	
	
	}

	//分页
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);//分页
		Page<TbBrand> page = (Page<TbBrand>) tbBrandMapper.selectByExample(null);		
		return new PageResult(page.getTotal(),page.getResult());
	}

	//增加
	@Override
	public void add(TbBrand brand) {
		
		tbBrandMapper.insert(brand);
	}

	@Override
	public TbBrand findOne(Long id) {
		
		return tbBrandMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(TbBrand brand) {		
		tbBrandMapper.updateByPrimaryKey(brand);
	}

	@Override
	public void delete(Long[] ids) {		
		for (Long id : ids) {
			tbBrandMapper.deleteByPrimaryKey(id);
		}		
	}

	@Override
	public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);//分页
		TbBrandExample example=new TbBrandExample();
		Criteria c = example.createCriteria();
		if(brand!=null) {
			if(brand.getName()!=null&&brand.getName().length()>0) {
				c.andNameLike("%"+brand.getName()+"%");
			}
			if(brand.getFirstChar()!=null&&brand.getFirstChar().length()>0) {
				c.andFirstCharLike("%"+brand.getFirstChar()+"%");
			}
		}		
		Page<TbBrand> page = (Page<TbBrand>) tbBrandMapper.selectByExample(example);		
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public List<Map> selectOptionList() {
		return tbBrandMapper.selectOptionList();
		 
	}
	
	

}


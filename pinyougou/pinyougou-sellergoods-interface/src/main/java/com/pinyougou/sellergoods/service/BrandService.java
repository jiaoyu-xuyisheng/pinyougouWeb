package com.pinyougou.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.pinyougou.pojo.TbBrand;

import entity.PageResult;

public interface BrandService {
	
	public List<TbBrand> findAll();
	
	public PageResult findPage(int pageNum,int pageSize);
	
	public void add(TbBrand brand);
	
	public TbBrand findOne(Long id);
	
	public void update(TbBrand brand);
	
	public void delete(Long[] ids);
	
	public PageResult findPage(TbBrand brand,int pageNum,int pageSize);
	
	//返回下拉列表
	public List<Map> selectOptionList();

}

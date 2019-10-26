package com.pinyougou.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;

/**
 * 
 * @author xuyisheng
 *规格组合实体类;
 */
public class Specification implements Serializable{

	private TbSpecification specification;
	
	private List<TbSpecificationOption> specificationOptionList;

	public TbSpecification getSpecification() {
		return specification;
	}

	public void setSpecification(TbSpecification specification) {
		this.specification = specification;
	}

	public List<TbSpecificationOption> getSpecificationOptionList() {
		return specificationOptionList;
	}

	public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
		this.specificationOptionList = specificationOptionList;
	}

	public Specification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Specification(TbSpecification specification, List<TbSpecificationOption> specificationOptionList) {
		super();
		this.specification = specification;
		this.specificationOptionList = specificationOptionList;
	}
	
	
}

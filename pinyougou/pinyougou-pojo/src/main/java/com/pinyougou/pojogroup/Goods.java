package com.pinyougou.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;

public class Goods implements Serializable {
	
	private TbGoods goods;
	private TbGoodsDesc tbGoodsDesc;
	private List<TbItem> itemList;
	
	
	public Goods(TbGoods goods, TbGoodsDesc tbGoodsDesc, List<TbItem> itemList) {
		super();
		this.goods = goods;
		this.tbGoodsDesc = tbGoodsDesc;
		this.itemList = itemList;
	}
	
	
	public Goods() {
		super();
	}


	public TbGoods getGoods() {
		return goods;
	}
	public void setGoods(TbGoods goods) {
		this.goods = goods;
	}
	public TbGoodsDesc getTbGoodsDesc() {
		return tbGoodsDesc;
	}
	public void setTbGoodsDesc(TbGoodsDesc tbGoodsDesc) {
		this.tbGoodsDesc = tbGoodsDesc;
	}
	public List<TbItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<TbItem> itemList) {
		this.itemList = itemList;
	}
	
	

}

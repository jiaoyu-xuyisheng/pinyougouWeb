package baseArray;

import java.util.Arrays;

public class ArrayClass {
	
	private Object[] arr;
	
	public ArrayClass(){
		arr=new Object[0];
	}
	
	/**
	 * 向后添加
	 */
	public void add(Object o) {	
		//创建一个新数组长度原数组长加一
		Object[]newArr=new Object[arr.length+1];
		for (int i =0;i<arr.length;i++) {
			newArr[i]=arr[i];			
		}
		newArr[arr.length]=o;
		arr=newArr;		
	}
	
	/**
	 * 删除指定位置元素
	 */
	public void delete(int index) {
		if(index<0||index>arr.length-1) {
			new RuntimeException("下标越界");
		}
		if(arr.length>0) {
			Object[] newArr=new Object[arr.length-1];
			for (int i =0;i<arr.length;i++) {
				if(i<index) {
					newArr[i]=arr[i];
				}else {
					newArr[i]=arr[i+1];
				}
			}			
			arr=newArr;
		}else {
			new RuntimeException("空指针错误");
		}
		
	}
	
	/**
	 * 查找指定元素
	 * @param o
	 * @return
	 */
	public int indexOf(Object o) {		
		if(o==null) {
			for (int i =0;i<arr.length;i++) {
				if(arr[i]==null) {
					return i;
				}
			}
		}else {
			for (int i =0;i<arr.length;i++) {
				if(arr[i].equals(o)) {
					return i;
				}
			}
		}
		
		return -1;
		
	}
	
	/**
	 * 从后面查找元素
	 */
	public int lastIndexOf(Object o) {
		if(o==null) {
			for(int i=arr.length-1;i>=0;i--) {
				if(arr[i]==null) {
					return i;
				}
			}
		}else {
			for(int i=arr.length-1;i>=0;i--) {
				if(o.equals(arr[i])) {//如果写成arr[i].equals(o)就有可能变成空指针错误
					return i;
				}
			}
			
		}
		return -1;
	}
	
	
	public Object get(int index) {
		if(index<0||index>arr.length-1) {
			new RuntimeException("下标越界");
		}
		return arr[index];
	}
	
	/**
	 * 在任意位置插入
	 * @param index
	 * @param o
	 */
	public void insert(int index,Object o) {
		Object[] objarr=new Object[arr.length+1];
		for(int i=0;i<arr.length;i++) {
			if(i<index) {
				objarr[i]=arr[i];
			}else {
				objarr[i+1]=arr[i+1];
			}
		}
		objarr[index]=o;
		arr=objarr;
		
	}
	
	public String show() {
		return Arrays.toString(arr);
	}
	
	public void set(int i,Object o) {
		if(i<0||i>arr.length-1)
		arr[i]=o;
	}
	
	public int size() {
		int i=arr.length;
		return i;
	}
	
	/**
	 * 插入数组
	 * @param objarr
	 * @param index
	 */
	public void addAll(Object[] objarr,int index) {
		Object[] newArr=new Object[size()+objarr.length];
		for(int i=0;i<size();i++) {
			if(i<index) {
				newArr[i]=arr[i];
			}else {
				newArr[i+(objarr.length)]=arr[i];
			}
		}
		
		for(int j=0;j<objarr.length;j++) {
			newArr[index+j]=objarr[j];
		}
		arr=newArr;
		
	}

}



package baseArray;

public class MyStack {
	
	private int[] arr;
	
	public MyStack() {
		arr=new int[0];
	}
	
	//压入元素
	public void push(int element) {
		int[] newArr=new int[arr.length+1];
		for (int i =0;i<arr.length;i++) {
			newArr[i]=arr[i];	
		}
		newArr[arr.length]=element;
		arr=newArr;
	}
	
	//取出栈的顶元素
	public int pop() {
		if(arr.length==0) {
			throw new RuntimeException("这是个空数组，没法取了");
		}
		int element=arr[arr.length-1];
		int[] newArr=new int[arr.length-1];
		for(int i=0;i<arr.length-1;i++) {
			newArr[i]=arr[i];
		}
		arr=newArr;
		return element;
	}
	
	//取出多个顶元素
	public int[] pop(int num) {
		if(num<=0) {
			throw new RuntimeException("取出的数量不双能小于等于0");
		}else if(num>arr.length){
			throw new RuntimeException("没有这么多元素给你弹，小样");
		}
		int[] newArr=new int[arr.length-num];
		int[] returnArr=new int[num];
		int cont=0;
		for(int i=0;i<arr.length;i++) {
			if(i<=(arr.length-num-1)) {
				newArr[i]=arr[i];
				cont++;
			}else {
				if(num==arr.length) {
					returnArr=null;
				}else {
					returnArr[i-cont]=arr[i];
				}
				
			}
			
		}
		arr=newArr;
		return  returnArr;
	}
	
	//查看栈元素
	public int pick() {
		if(arr.length==0) {
			throw new RuntimeException("没有元素了");
		}
		return arr[arr.length-1];
	}
	
	public boolean isnull() {
		return arr.length==0;
	}
	

}

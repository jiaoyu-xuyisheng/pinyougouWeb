package baseArray;


public class MyQueue {
	
	private int arr[];
	
	public MyQueue() {
		arr=new int[0];
	}
	
	//入队元素
	public void add(int element) {
		int[] newArr=new int[arr.length+1];
		for (int i =0;i<arr.length;i++) {
			newArr[i]=arr[i];	
		}
		newArr[arr.length]=element;
		arr=newArr;
	}
	
	public int pop() {
		if(arr.length==0) {
			throw new RuntimeException("已经没有元素了兄弟");
		}
		
		int ele=arr[0];
		int[] newArr=new int[arr.length-1];
		for (int i =0;i<arr.length-1;i++) {
			newArr[i]=arr[i+1]	;	
		}
		arr=newArr;
		return ele;		
	}
	
	public boolean isEmpty() {
		return arr.length==0;
	}

}

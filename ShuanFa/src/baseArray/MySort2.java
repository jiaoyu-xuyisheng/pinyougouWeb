package baseArray;

import java.util.Arrays;

public class MySort2 {

	public static void main(String[] args) {
		 
		int[] arr=new int[] {1,22,13,101,134,234,43,55,99,703};
		radixSort(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	public static void radixSort(int[] arr) {
		//用于存数组中最大数
		int max=Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			//通过多次改变max的值最后得到最大值！！
			if(arr[i]>=max) {
				max=arr[i];
			}
			
		}
		
		int maxlenght=	(max+"").length();
		
		//使用队列存储临时数据的数组
		MyQueue[] temp=new MyQueue[10];
		
		//为队列数组赋值
		for (int i = 0; i < temp.length; i++) {
			temp[i]=new MyQueue();
		}
		//第一次得到个位上的数字，然后放到临时数组相应的位置中
		//然后把临时数组的数组放到原数组中去
		//第二次得到十位上的数字，然后放到临时数组相应的位置中
		//然后把临时数组的数组放到原数组中去
		//第三次得到百位上的数字，然后放到临时数组相应的位置中
		//然后把临时数组的数组放到原数组中去
		//直到排完
		
		//得到最大数的长度，然后进行循环！！
		for (int i = 0,n=1; i < maxlenght; i++,n*=10) {
			//把每一个数字分别计算余数！！然后放到临时数组相应的位置中
			for (int j = 0; j < arr.length; j++) {
				//计算余数
				int ys=(arr[j]/n)%10;
				//把当前遍历的数据放入指定的数组中
				temp[ys].add(arr[j]);
			}
			
			int index=0;
			//把所有队列中的数据取出放入到原数组中
			for(int k=0;k<temp.length;k++) {
				//当前遍历的队列不为空时
				while(!temp[k].isEmpty()) {
					arr[index]=temp[k].pop();
					index++;
				}
			}
			
		}
	
	
	}

}

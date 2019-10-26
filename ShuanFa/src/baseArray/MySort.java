package baseArray;

import java.util.Arrays;


public class MySort {

	public static void main(String[] args) {
		int [] arr=new int[] {2,12,5,3,7,11,10};
		bulleSort(arr);
		System.out.println(Arrays.toString(arr));
		int [] arr2=new int[] {8,10,5,8,9,13,4};
		quickSort(arr2,0 ,arr.length-1);
		System.out.println(Arrays.toString(arr2));
		int[] arr3 =new int[] {2,8,4,3,22,11,3,22,44,66};
		shellSort(arr3);
		System.out.println(Arrays.toString(arr3));
		
		int[] arr4=new int[] {4,3,22,11,3,22,44,66};
		mergSort(arr4,0,arr4.length-1);
		System.out.println(Arrays.toString(arr4));
	}
	
	//冒泡排序
	public static void bulleSort(int[] arr) {
		
		//控制比较多少轮
		for (int i =0;i<arr.length-1;i++) {
			//比较的次数
			for(int j=0;j<arr.length-1-i;j++) {
				if(arr[j]>arr[j+1]) {
					int template=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=template;
				}
			}
			
		}		
	}
	
	//快速排序 就是以一个数为基准，比基准大的向后排，基准小向前排
	//前后各两个指针，分别向中间走，以第一个数为基准
	//如果后面有比基准小的数就替换前指针指到的数，然后前指针向后移，
	//如果没有后面的比基准小的数就直接向前移！！
	//如果前面有比基准大的数就替换后面指针指到的数！后指针向前移
	//如果前面没有的比基准大的数就直接向后移！！
	//然后递归
	public static void quickSort(int[] arr,int start ,int end) {
		if(start<end) {
		//以第一个数为基准
		int mystart=arr[start];
		//后面的指针
		int low=start;
		int high=end;
		
			while(low<high) {
				//如果后指针指到的数大于基准数
				while(low<high&&mystart<=arr[high]) {
					//后指针向前移动一位
					high=high-1;	
				}
				//如果后指针指到的数小于基准数，则指针不变，将后指针的数给前值针
				arr[low]=arr[high];
				//如果前指针指到的数小于基准数
				while(low<high&&arr[low]<=mystart) {
					//前指针向后移动一位
					low=low+1;
				}
				//如果前指针指到的数大于基准数，则指针不变，将前指针的数给后值针
				arr[high]=arr[low];
				
					
			}	
			//最后当low==high时就将arr[low]=基数
			arr[low]=mystart;
			//给小的数排序，
			quickSort(arr,start,low);
			//给大的数排序
			quickSort(arr,low+1,end);	
			}
	}
	
	//交替排序，前后两个数比，小的在前，大的在后，
	public static void insetSort(int[] arr) {
		//遍厉所有的数字
		for (int i = 1; i < arr.length; i++) {
			//如果当前数字比前一个数字小
			if(arr[i]<arr[i-1]) {			
				int temp=arr[i];
				//遍厉之前的项
				int j;
				for( j=i-1;j>=0&&temp<arr[j];j--) {
					//把前一个数字赋值给后一个数字
					arr[j+1]=arr[j];
				}
				//把临时变量赋给（外层循环的当前元素）
				arr[j+1]=temp;									
			}		
		}	
	}
	
	
	//希而排序
	public static void shellSort(int[] arr) {
		//遍历所有步常
		for (int d=arr.length/2;d>0;d/=2) {
			//遍厉所有元素
			for(int i=d;i<arr.length;i++) {
				//遍历本组是所有的元素
				for(int j=i-d;j>0;j-=d) {
					if(arr[j]>arr[j+d]) {
						int temp=arr[j];
						arr[j]=arr[j+d];
						arr[j+d]=temp;
					}
					
				}
				
			}		
		}
		
		
	}

	//选择算法
	public static void selectSort(int[] arr) {

		//遍历所有的数
		for (int i = 0; i < arr.length; i++) {
			int minIndex=i;
			//把当前遍历的数和后面的所有数比较，并记录下最小数的下标
			for(int j=i+1;j<arr.length;j++) {
				//如果后面的数比最小的数小
				if(arr[minIndex]>arr[j]) {
					//记录下最小的那个数的下标 
					minIndex=j;
				}			
			}
			//如果最小的数和当前遍历的数的下标不一致,说明下标为minIndex的数
			//比当前的数更小
			if(i!=minIndex) {
				int temp=arr[i];
				arr[i]=arr[minIndex];
				arr[minIndex]=temp;
			}
			
		}
		
	}
	
	//归并算法
	public static void merg(int[] arr,int low,int middle,int high) {
		//用于存储归并的临时数组
		int[] temparr =new int[high-low+1];
		//记录第一数组中需要遍历的下标
		int  i=low;
		//记录第二数组中需要遍历的下标
		int j=middle+1;
		//用于记录在临时数组中的下标！！
		int index=0;
		//遍历两个数组取出最小的数字，放入数组中
		while(i<=middle&&j<=high) {
			//如果第一个数组的数更小的话，
			if(arr[i]<=arr[j]) {
				temparr[index]=arr[i];			
				i++;
			}else {
				temparr[index]=arr[j];
				j++;
			}
			index++;
			
		}
		
		//处理多余的数据！！
		while(j<=high) {
			temparr[index]=arr[j];
			j++;
			index++;
		}
		
		while(i<=middle) {
			temparr[index]=arr[i];
			i++;
			index++;
		}
		
		//把临时的数据重新存入原数组中去！！
		for(int k=0;k<temparr.length;k++) {
			arr[k+low]=temparr[k];
		}
		
	}

	public static void mergSort(int[] arr,int low,int high) {
		int middle=(low+high)/2;
		if(low<high) {
			//处理左边,如果进行递归，middle+1就相当于low;中间数为int middle， 参数middle就是high
			mergSort( arr, low,middle);
			//处理右边，如果进行递归，middle+1就相当于low;中间数为 int middle，高位数就是high
			mergSort( arr, middle+1,high);
			//进行归并
			merg( arr, low,middle,high);
		}
		
	}

}

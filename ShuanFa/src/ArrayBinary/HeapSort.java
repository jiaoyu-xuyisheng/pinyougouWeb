package ArrayBinary;

import java.util.Arrays;


//堆排序
public class HeapSort {

	public static void main(String[] args) {	
		int[] arr=new int[] {5,2,6,8,3,9,33,0,3,1,22};	
		
		myHeapSort(arr);
		
		System.out.println(Arrays.toString(arr));
	}
	
	
	public static void myHeapSort(int[]arr) {
		int start=(arr.length-1)/2;//最后一个叶子节点的父节点
		//最后一个叶子节点的父节点都是有子节点的，并且在数组中是连续的
		//所以从最后一个叶子节点的父节点开始从前遍历
		for (int i =start;i>=0;i--) {//向前遍历
			maxHeap(arr,arr.length,i);
		}
		//将大顶堆的首尾交换,然后排序。		
		for (int i =arr.length-1;i>0;i--) {
			int temp=arr[0];
			arr[0]=arr[i];
			arr[i]=temp;	
			//为什么第三个参数写0;因为i一直变小，所以要找一个有子节点的节点，
			//0表示根节点位置，因为根节点的值，一直在变所以写根节点
			//在maxHeap中有自己的递归只要将根节点的最小值调到子节点后
			//maxHeap自己就能排成大顶堆！！
			maxHeap(arr,i,0);
		}
		
	}
	
	/**
	 * 大顶堆就是所有的根节点都大于子节点的完全二叉树
	 * 大顶堆的调整
	 * index:表示一个叶子节点的父节点;
	 * size:表示数组长度。用于控制边界;防止数组过界！！
	 * @param arr：传入的数组，可以看成完全二叉树的！！
	 * @param size
	 */
	public static void maxHeap(int[] arr,int size,int index) {
		
		//左子节点
		int leftNode=2*index+1;
		//右子节点
		int rightNode=2*index+2;
		int max=index;
		//和左节点比
		if(leftNode<size&&arr[leftNode]>arr[max]) {
			max=leftNode;
		}
		//和右节点比
		if(rightNode<size&&arr[rightNode]>arr[max]) {
			max=rightNode;
		}
		
		//进行交换
		if(max!=index) {
			int temp=arr[index];
			arr[index]=arr[max];
			arr[max]=temp;
			//交换位置以后，可能会破坏之前排好的椎，所以，之前排好的堆要重新调整！！所以就是递归！！
			maxHeap(arr,size,max);
		}
		
		
	}

}

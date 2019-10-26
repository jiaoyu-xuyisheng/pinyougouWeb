package ArrayBinary;

/**
 * 完全二叉树的遍历
 * @author xuyisheng
 *
 */
public class ArrayBinaryTree {
	
	//完全二叉树的层级存的数据
	int [] data;


	public ArrayBinaryTree(int[] data) {
		this.data = data;
	}
	
	
	public void fontSort() {
		fontSort(0);
	}
	
	//前序遍历
	public void fontSort(int index) {
		
		if(data==null||data.length==0) {
			return;
		}
		System.out.println(data[index]);
		//有可能没有左子节点,所以要在数组长度内
		if(2*index+1<data.length) {
			fontSort(2*index+1);
		}
		if(2*index+2<data.length) {
			fontSort(2*index+2);
		}
		
		
	}
	

}

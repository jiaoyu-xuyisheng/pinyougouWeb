package HuffMan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestHuffmanTree {

	public static void main(String[] args) {
		int[] arr= {3,4,8,11,23,53,31,10,33,6};
		createHuffmanTree(arr);
	}
	
	
	public static Node createHuffmanTree(int[] arr) {
		//先使用数组中所有的元素创建 若干个二叉树。（只有一个节点）
		List<Node> nodes=new ArrayList<Node>();
		for (int i = 0; i < arr.length; i++) {
			nodes.add(new Node(arr[i]));
		}
		
		while(nodes.size()>1) {
			//排序
			Collections.sort(nodes);
			//取出来权值 最小的两个二叉树
			Node leftNode=nodes.get(nodes.size()-1);
			Node rightNode=nodes.get(nodes.size()-2);
			//创建 一颗新的二叉树
			Node parent=new Node(leftNode.value+rightNode.value);
			//把取出来 的二叉树移除
			nodes.remove(leftNode);
			nodes.remove(rightNode);
			//放入原来的二叉树集合中
			nodes.add(parent);	
		}
		Node root=nodes.get(0);	
		return root;
		
	}

}

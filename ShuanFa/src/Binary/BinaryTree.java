package Binary;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTree {
	
	//根节点
	TreeNode root;

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}
	
	/**
	 * 广度优先遍历： 按照层次由低到高，先左后右的顺序 1 2 3 4 5 6 7
    编程实现的思想： 开启一个队列 queue（队列是数据结构中先进先出的实现，队列的朋友需要再看一看数据结构链表的知识）
        1 把 树根 1 放进队列     队列变成 1 
        2 重复执行：从队列取一个队头出队一个节点，如果它有左孩子，把左孩子放入队列，如果他有右孩子把右孩子放入队列。输出当前节点的数值
         一直到队列空了为止
          2.1 从队头取出1 把1输出，把1的左孩子和右孩子添加到队列  队列变成： 2 3
          2.2 从对头取出2 把2输出，把2的左孩子和右孩子添加到队列  队列变成： 3 4 5
          2.3 从对头取出3 把3输出， 把3的左孩子右孩子添加到队列， 队列变成： 4 5 6 7
          2.4 从对头取出4 把4输出，它没有左孩子和右孩子 不用入队列
          2.5 从对头取出5 把5输出， 它没有左孩子右孩子  不用入队列
          2.6 从对头取出6 把6输出， 它没有左孩子右孩子  不用入队列
          2.7 从对头取出7 把7输出， 它没有左孩子右孩子  不用入队列
          2.8 队列空了，结束
	 */
	public void breadthShow() {
		Queue<TreeNode> queueTree=new LinkedList<TreeNode>();
		List<Integer> list=new ArrayList<Integer>();
		queueTree.add(this.root);
		while(!queueTree.isEmpty()) {
			//弹出的树节点
			TreeNode cur=queueTree.poll();
			list.add(cur.value);
			if(cur.getLnode()!=null) {
				//添加到队列中去
				queueTree.add(cur.getLnode());
			}
			if(cur.getRnode()!=null) {
				//添加到队列中去
				queueTree.add(cur.getRnode());
			}		
		}
		System.out.println(list.toString());
		
}
	
	//添加子节点，向第一个为空的子节点添加节点
	public void addTreeNode(int value) {
		TreeNode node=new TreeNode(value);
		if(this.root==null) {
			this.root=node;
		}else {
			if(root.getLnode()!=null) {
				Queue<TreeNode> queue = new LinkedList<TreeNode>();
				            queue.offer(this.root);
				             while( ! queue.isEmpty() ){
				                 TreeNode cur = queue.poll();
				                  if( cur.lnode == null ){
				                      cur.lnode=node;
				                     break;
				                  }else{
				                      queue.add(cur.getLnode());
				                  }
				                  if( cur.rnode== null ){
				                      cur.rnode=node;
				                      break;
				                  }else{
				                      queue.add(cur.getRnode());
				                  }
				             }
				  }
		}
	}
	
	public void frontShow() {
		if(root!=null) {
		root.frontShow();
		}
	}
	
	public void midShow() {
		if(root!=null) {
			root.midShow();
		}
		
	}

	public void afterShow() {
		if(root!=null) {
			root.afterShow();
		}	
	}

	public TreeNode findFrontShow(int value) {
		
		return root.findFrontShow(value) ;
	}

	
	public void deleteNode(int i) {
		if(root.value==i) {
			root=null;
		}else {
			root.deleteNode(i);
		}	
	}

}

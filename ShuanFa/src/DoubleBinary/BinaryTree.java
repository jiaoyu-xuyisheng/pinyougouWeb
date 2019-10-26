package DoubleBinary;


public class BinaryTree {
	
	//根节点
	ThreadedNode root;
	
	//前驱节点
	ThreadedNode pre;
	
	//遍历线索二叉树
	public void threadIterate() {
		//用于临时存储当前遍历的节点
		ThreadedNode node=root;
		while(node!=null) {
			//循环找到最开始的节点,如果不是叶子节点找到当前节点的左节点
			while(node.leftType==0) {
				node=node.lnode;
			}
			//打印当前的值！这个是最下左的叶子节点
			System.out.println(node.value);
			//如果当前节点的右指针指向的是后继节点，可能后继节点还有后继节点
			while(node.rightType==1) {
				node=node.rnode;
				System.out.println(node.value);
			}
			//替换遍历的节点，这个是用来拐弯的。
			node=node.rnode;
		}
		
	}
	
	
	//中序线索化二叉树   node为当前节点
	public void threadNodes(ThreadedNode node) {
		//当前节点为null直接返回
		if(node==null) {
			return;
		}	
		//先处理左子树！！
		threadNodes(node.lnode) ;
		//处理当前节点的前驱节点
		if(node.lnode==null) {
			//让当前节点的左指针指向前驱节点
			node.lnode=pre;
			//改变当前节点的左指针的类型
			node.leftType=1;
		}
		//处理前驱的右指针，如果前驱节点是null(没有指下右子树）
		if(pre!=null&&pre.rnode==null) {
			//让前驱节点的右指针指向当前节点
			pre.rnode=node;
			//改变 前驱节点的右指针类型 
			pre.rightType=1;
			
		}//以上两个if只可能走一个if,因为当前node不是同一个node
		//每处理一个节点，当前节点是下一个节点的前节点
		pre=node;	
		//处理右子树
		threadNodes(node.rnode) ;	
	}
	
	
	
	//中序线索化二叉树
	public void threadNodes() {
		threadNodes(root);
	}

	public ThreadedNode getRoot() {
		return root;
	}

	public void setRoot(ThreadedNode root) {
		this.root = root;
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

	public ThreadedNode findFrontShow(int value) {
		
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

package Binary;

public class TestBinary {
	
	public  static void  main(String[] args) {
		
		//创建一颗树
		BinaryTree bt=new BinaryTree();
		TreeNode tn=new TreeNode(1);
		//为树添加根节点
		bt.setRoot(tn);
		//创建一个左节点
		TreeNode leftNode=new TreeNode(2);
		//为根节点设置左节点
		tn.setLnode(leftNode);
		//创建一个右节点
		TreeNode rightNode=new TreeNode(3);
		//为根节点设置右节点
		tn.setRnode(rightNode);
		//分别为二层的左右节点设置，左右子节点	
		leftNode.setLnode(new TreeNode(4));
		leftNode.setRnode(new TreeNode(5));
		rightNode.setLnode(new TreeNode(6));
		rightNode.setRnode(new TreeNode(7));	
		System.out.println("-------层级遍历--------");
		bt.breadthShow();
		System.out.println("-------前序遍历--------");
		bt.frontShow();//1,2,4,5,3,6,7
		System.out.println();
		System.out.println("-------中序遍历--------");
		bt.midShow();//4,2,5,1,6,3,7
		System.out.println();
		System.out.println("-------后序遍历--------");
		bt.afterShow();//4,5,2,6,7,3,1
		System.out.println();
		System.out.println("-----------前序查找---------");
		TreeNode findnode=bt.findFrontShow(5);
		findnode.showValue();	//5
		bt.deleteNode(3);//1,2,4,5
		bt.frontShow();	
	}
	

	

}

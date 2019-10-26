package DoubleBinary;



public class TestMain {

	public static void main(String[] args) {
		//创建一颗树
				BinaryTree bt=new BinaryTree();
				ThreadedNode tn=new ThreadedNode(1);
				//为树添加根节点
				bt.setRoot(tn);
				//创建一个左节点
				ThreadedNode leftNode=new ThreadedNode(2);
				//为根节点设置左节点
				tn.setLnode(leftNode);
				//创建一个右节点
				ThreadedNode rightNode=new ThreadedNode(3);
				//为根节点设置右节点
				tn.setRnode(rightNode);
				//分别为二层的左右节点设置，左右子节点	
				leftNode.setLnode(new ThreadedNode(4));
				ThreadedNode fiveNode=	new ThreadedNode(5);
				leftNode.setRnode(fiveNode);
				rightNode.setLnode(new ThreadedNode(6));
				rightNode.setRnode(new ThreadedNode(7));			
			
				System.out.println("-------中序遍历--------");
				bt.midShow();//4,2,5,1,6,3,7
				System.out.println();
				bt.threadNodes();
				/*System.out.println(fiveNode.value);
				ThreadedNode fnode=	fiveNode.rnode;
				System.out.println(fnode.value);*/
				bt.threadIterate();
			

	}

}

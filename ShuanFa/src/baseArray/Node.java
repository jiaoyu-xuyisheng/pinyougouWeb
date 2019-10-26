package baseArray;

public class Node {
	
	private	int data;
	private  Node next;
	 
	 public Node(int data) {
		 this.data=data;
	 }
	 
	 public int getData() {
		 return this.data;
	 }
	 
	 //为节点追加节点
	 public Node append(Node node) {
		 //当前节点
		this.next=node;  //3
		Node currentNode=this;  //2
		currentNode=this.next;  //3
		return currentNode;   //3
	 }
	 
	 public Node next() {
		 return this.next;
	 }
	 
	 public boolean isLast() {
		return this.next==null;
	 }
	 
	 //删除下一个节点
	 public void removeNext() {
		 Node newNode=next.next;
		 this.next=newNode;
	 }
	 
	 public void showAllNode() {
		 Node currentNode=this;
		 while(true) {
			 System.out.print(currentNode.data+",");
			 currentNode=currentNode.next;
			 if(currentNode==null) {
				 break;
			 }
		 }
	 }
	 
	 
	 //插入一个节点作为当前节点的下一个节点
	 public void insert(Node node) {
		 //取出下一个节点，作为下下一个节点
		Node nextNext=next;
		//将所传入的节点作为下一个节点
		this.next=node;
		//为node设置下一个节点也就是将下下个世点设置成新节点的下一个节点
		node.next=nextNext;				
	 }
	 
	
}

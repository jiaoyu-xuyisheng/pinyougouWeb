package baseArray;

public class LoopNode {
	
	private	int data;
	private  LoopNode next=this;
	 
	 public LoopNode(int data) {
		 this.data=data;
	 }
	 
	 public int getData() {
		 return this.data;
	 }
	 
	 //为节点追加节点
	 public void append(LoopNode node) {
		 //取出下一个节点的值
		 LoopNode nextNode=next;
		 //改变下一个节点的值，也就是指针指向下一个节点
		this.next=node;  //3
		//设置下一个节点的next的指针指向当前节点
		node.next=nextNode;
	 }
	 
	 public LoopNode next() {
		 return this.next;
	 }
	 
	 
	 //删除下一个节点
	 public void removeNext() {
		LoopNode newNode=next.next;
		 this.next=newNode;
	 }
	 
	 
	 //插入一个节点作为当前节点的下一个节点
	 public void insert(LoopNode node) {
		 //取出下一个节点，作为下下一个节点
		LoopNode nextNext=next;
		//将所传入的节点作为下一个节点
		this.next=node;
		//为node设置下一个节点也就是将下下个世点设置成新节点的下一个节点
		node.next=nextNext;				
	 }
	 

}

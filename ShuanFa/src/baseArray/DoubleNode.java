package baseArray;

/*8
 * 双向循环列表
 */
public class DoubleNode {
	
	DoubleNode pre=this;
	DoubleNode next=this;
	int data;
	public DoubleNode(int data) {
		this.data=data;
	}
	
	public void append(DoubleNode dn) {
		//保存原来的下个节点，用来做当前节点的前节点
		DoubleNode nextNext=next;
		//新节点做为做为当前节点的下一个节点
		this.next=dn;
		//把当前节点做为下一个节点的前节点
		dn.pre=this;
		//让原节点的下一个节点做为新节点的下一个节点
		dn.next=nextNext;
		//让原来的下一个的上一个节点为新节点
		nextNext.pre=dn;	
	}
	
	public DoubleNode next() {
		return this.next;
	}
	
	public DoubleNode pre() {
		return this.pre;
	}
	
	public int getData() {
		return this.data;
	}

}

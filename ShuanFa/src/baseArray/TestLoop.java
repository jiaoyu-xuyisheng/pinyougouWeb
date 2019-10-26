package baseArray;

public class TestLoop {

	public static void main(String[] args) {
		LoopNode ln=new LoopNode(1);
		LoopNode ln2=new LoopNode(2);
		LoopNode ln3=new LoopNode(3);
		LoopNode ln4=new LoopNode(4);
		LoopNode ln5=new LoopNode(5);
		LoopNode ln6=new LoopNode(6);
		//增加节点
		ln.append(ln2);
		ln2.append(ln3);
		ln3.append(ln4);
		ln4.append(ln5);
		ln5.append(ln6);
		System.out.println(ln.next().getData());
		System.out.println(ln2.next().getData());
		System.out.println(ln6.next().getData());
	}

}

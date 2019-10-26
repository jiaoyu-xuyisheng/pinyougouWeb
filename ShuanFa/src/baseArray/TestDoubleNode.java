package baseArray;

public class TestDoubleNode {

	public static void main(String[] args) {
		
		DoubleNode dn=new DoubleNode(1);
		DoubleNode dn2=new DoubleNode(2);
		DoubleNode dn3=new DoubleNode(3);
		System.out.println(dn.pre().getData()); //1
		System.out.println(dn.getData());  //1
		System.out.println(dn.next().getData());  //1
		
		dn.append(dn2);
		dn2.append(dn3);
		
		System.out.println(dn.pre().getData());  //3
		System.out.println(dn.getData());  //1
		System.out.println(dn.next().getData());  //2
		
		
	}

}

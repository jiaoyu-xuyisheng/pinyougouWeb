package baseArray;

public class TestNode {

	public static void main(String[] args) {
		
		Node no=	new Node(10);
		Node no2=new Node(11);
		Node no3=new Node(13);
		Node no4=new Node(14);
		//将node节点连起来
		Node no41=no.append(no2).append(no3).append(no4);
		//通过链找到no4
		System.out.println(no2.next().next().getData());//14
		System.out.println(no41.getData());//14
		//证明no41就是no4
		System.out.println(no41==no4);//true
		//证明no4是最后一个节点
		System.out.println(no4.isLast());//true
		//证明no3不是最后一个节点
		System.out.println(no3.isLast());//false
		//查看所有节点     10,11,13,14
		no.showAllNode();
		//删除一个节点
		no.removeNext();
		System.out.println("删除后的结果    10，13，14");	
		no.showAllNode();	
		no.next().insert(new Node(12));
		System.out.println("");
		System.out.println("-----------------");
		no.showAllNode();
		
		
		
		

	}

}

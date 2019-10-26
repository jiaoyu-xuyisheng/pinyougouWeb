package HuffMan2;

import java.util.Collection;
import java.util.Iterator;

public class Node2 implements  Comparable<Node2>{
	//字符标记
	Byte data;
	//权值
	int weight;
	//左节点
	Node2 left;
	//右节点
	Node2 right;
	
	public Node2(Byte data , int weight) {
		this.weight=weight;
		this.data=data;
	}

	@Override
	public int compareTo(Node2 o) {
		
		return o.weight-this.weight;
	}

	@Override
	public String toString() {
		return this.weight+"";
	}

	

}

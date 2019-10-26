package HuffMan;

public class Node implements Comparable<Node>{
	
	int value;
	Node left;
	Node right;
	
	public  Node(int value) {
		this.value=value;
	}

	@Override
	public int compareTo(Node o) {
		return  o.value-this.value;
	}

	@Override
	public String toString() {
		return this.value+"";
	}

	
}

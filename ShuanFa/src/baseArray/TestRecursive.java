package baseArray;

public class TestRecursive {

	public static void main(String[] args) {
		
		//斐波那契数列： 1 1 2 3 5 8 13 21
		System.out.println(print(8));	
		//汉诺塔问题
		hanoi(3,'A','B','C');
	}
	
	//斐波那契数列
	public static int print(int n) {
		if(n==1||n==2) {
			return 1;
		}else {
			return print(n-2)+print(n-1);
		
		}		
		
	}
	
	/**
	 * //汉诺塔问题
	 * @param n  共有n个盘子
	 * @param from  开始的柱子
	 * @param in  中间的柱子
	 * @param to 最后的柱子
	 */
	public static void hanoi(int n,char from,char in, char to) {
		
		if(n==1) {
			System.out.println("第一个盘子从"+from+"移到"+to);
		}else {
			hanoi(n-1,from,in,to);
			System.out.println("第"+n+"个盘子从"+from+"移到"+to);
			hanoi(n-1,in,from,to);
			
		}
		
	}

}

package baseArray;

import java.util.Arrays;

public class testMain {

	public static void main(String[] args) {
		ArrayClass ac=new ArrayClass();
		System.out.println(ac.size());
		ac.add(31);
		ac.add(71);
		ac.add(91);
		System.out.println(ac.show());
		Integer[] objarr=new Integer[]{34,43,55,56,77};
		ac.addAll(objarr, 1);
		System.out.println(ac.show());
		System.out.println(twoCheck(objarr,140));
		
		MyStack ms=new MyStack();
		ms.push(4);
		ms.push(6);
		ms.push(99);
		ms.push(22);
		ms.push(11);
		int pop = ms.pop();
		System.out.println(pop);
		System.out.println(ms.pick());
		System.out.println(Arrays.toString(ms.pop(3)));
		System.out.println(ms.pick());		
	}
	
	

	/**
	 * 二分法
	 * @param objarr
	 * @param index
	 * @return
	 */
	public static int twoCheck(Integer[] objarr,int index) {
		
		int start=0;
		int end=objarr.length-1;
		int middle=(end+start)/2;
		
		while (true) {		
			
			if(start>=end) {
				middle=-2;
				break;
			}
			
			if(objarr[middle]==index) {
				return middle;
			}else {
				if(objarr[middle]>index) {
					end=middle-1;
				}else if(objarr[middle]<index) {
					start=middle+1;
				}				
			}
			middle=(start+end)/2;		
		}
		
		return middle+1;
	}

}

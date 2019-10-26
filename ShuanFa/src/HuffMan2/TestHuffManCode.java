package HuffMan2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class TestHuffManCode {

	public static void main(String[] args) {
		
	String msg="can you can a can as a can canner can a can.";
		byte[] bytes=msg.getBytes();
	//进行赫夫曼压缩
	byte[] b=huffManZip(bytes);
	//进行赫夫曼解码
		byte[] newByte=decode(haffCode,b);
	System.out.println(new String(newByte));
		

		String src1="2.zip";
		String dst1="4.bmp";
		
		//String src1="1.bmp";
		//String dst1="2.zip";
		try {	
			//zipFile(src1,dst1);
			unZip(src1,dst1);
			System.out.println("hello");
		} catch (Exception e) {
			System.out.println(e);
		}
	
	}
	
	/**
	 * 解压文件
	 * @param src
	 * @param dst
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public static void unZip(String src,String dst) throws IOException, ClassNotFoundException{
				//创建一个输入流
				InputStream is=new FileInputStream(src);
				//使用对象流读
				ObjectInputStream ois=new ObjectInputStream(is);
				byte[] b = (byte[]) ois.readObject();
				@SuppressWarnings("unchecked")
				Map<Byte,String> codes=(Map<Byte, String>) ois.readObject();
				ois.close();
				is.close();
				byte[] bytes=decode(codes,b);
				FileOutputStream osss=new FileOutputStream(dst);
				osss.write(bytes);
				osss.close();
				
			
	}
	
	/**
	 * 文件压缩
	 * @param src:输入文件路径
	 * @param dst：输出文件路径
	 * @throws IOException
	 */
	public static void zipFile(String src,String dst) throws IOException{
		//创建一个输入流
		InputStream read=new FileInputStream(src);
		//创建一个和输入流文件大小一样的数组;
		byte[] b=new byte[read.available()];
		//读取文件内容
		read.read(b);
		read.close();
		//使用赫夫曼编号进行编码
		byte[] byteZip=huffManZip(b);
		System.out.println(byteZip.length);
		System.out.println(b.length);
		//输出流
		OutputStream out=new FileOutputStream(dst);
		ObjectOutputStream oos=new ObjectOutputStream(out);
		//把压缩后的byte数组写入文件
		oos.writeObject(byteZip);
		//把编码表也写入文件
		oos.writeObject(haffCode);
		oos.close();
		out.close();
		
	}

	/**
	 * 进行赫夫曼解码
	 * @param haffCode2
	 * @param b
	 * @return
	 */
	private static byte[] decode(Map<Byte, String> haffCode2, byte[] bytes) {
		//把byte数组转成二进制字符串
		StringBuilder sb=new StringBuilder();
		for (int i=0;i<bytes.length;i++) {
			if(i==bytes.length-1) {
				sb.append(byteToBitStr(false,bytes[i]));
			}else {
				sb.append(byteToBitStr(true,bytes[i]));
			}	
		}
	
		//把字符串按照指定的赫夫曼编码进行解码
		//把赫夫曼的值进行调换！！
		Map<String,Byte> map=new HashMap<String,Byte>();
		for(Map.Entry<Byte, String> entry: haffCode2.entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}
		List<Byte> list=new ArrayList<Byte>();
		//处理字符串
		for(int i=0;i<sb.length();) {//每次是否前进一步！！
		
			int count=1;
			boolean flag=true;
			//截取出一个key
			Byte b=null;
			while(flag) {
				//切割字符串，
				//存在数组过界风险！！
				String key=sb.substring(i,i+count);
			
				 b = map.get(key);
				if(b==null) {
					count++;
				}else {
					flag=false;
				}
			}
			list.add(b);
			i+=count;
			//防止数组过界
			if(i+count>sb.length()-1) {
				break;
			}
		}
		byte[] listb=new byte[list.size()];
		for (int i = 0; i < listb.length; i++) {
			 listb[i]=list.get(i);	
		}
		return listb;
	}
	
	private static String byteToBitStr(boolean flag,byte b) {
		int temp=b;
		if(flag) {
			temp|=256;
		}
		String str = Integer.toBinaryString(temp);
		if(flag) { 
			return str.substring(str.length()-8);
			}else {
				return str;
			}
	}

	/**
	 * 进行赫夫曼编码
	 * @param bytes
	 * @return
	 */
	private static byte[] huffManZip(byte[] bytes) {
		//统计每一个出现的次数,并放到集合中	
		List<Node2> nodes=getNodes(bytes);
		//创建一棵赫夫曼编码
		Node2 tree=createHuffManTree(nodes);
		//创建一个赫夫曼编码表！！
		Map<Byte,String> huffCodes=getCodes(tree);
		//编码
		byte[] b=Zip(bytes,huffCodes);
	
		return b;
	}

	/**
	 * 
	 * @param bytes
	 * @param huffCodes
	 * @return
	 */
	private static byte[] Zip(byte[] bytes, Map<Byte, String> huffCodes) {
		StringBuilder sb=new StringBuilder();
		for(byte b:bytes) {
			sb.append(huffCodes.get(b));
		}
		//用于存储压缩后的byte;
		byte[] by=new byte[(sb.length()+7)/8];
		//记录byte的位置;
		int index=0;
		for(int i=0;i<sb.length();i+=8) {
			//防止下标过界！！
			String strByte;
			if(i+8>sb.length()) {
				strByte = sb.substring(i);
			}else {
				 strByte = sb.substring(i, i+8);
			}
			//将2进制转成十进制,然后将十进制转成byte;
			byte byt=(byte) Integer.parseInt(strByte,2);
	
			by[index]=byt;
			index++;
		}
		return by;
	}

	//用于临时存储路径！！
	public static StringBuilder sb=new StringBuilder();
	//用于存储赫夫曼编码
	public static Map<Byte,String> haffCode=new HashMap<Byte,String>();
	/**
	 * 根据赫夫曼树获取赫夫曼表
	 * @param tree
	 * @return
	 */
	private static Map<Byte, String> getCodes(Node2 tree) {
		
		if(tree==null) {
			return null;
		}
		getCodes(tree.left,"0",sb);
		getCodes(tree.right,"1",sb);
		return haffCode;
	}

	
	private static void getCodes(Node2 node, String code, StringBuilder sb) {
		
		 StringBuilder sb2=new StringBuilder(sb);
		 sb2.append(code);
		 //如果不是叶子节点
		 if(node.data==null) {
			 getCodes(node.left,"0",sb2);
			 getCodes(node.right,"1",sb2);
		 }else {
			 haffCode.put(node.data, sb2.toString());
		 }
		
	}

	/**
	 * 创建一棵赫夫曼编码
	 * @param nodes
	 * @return
	 */
	private static Node2 createHuffManTree(List<Node2> nodes) {
		while(nodes.size()>1) {
			//排序
			Collections.sort(nodes);
			//取出来权值 最小的两个二叉树
			Node2 leftNode=nodes.get(nodes.size()-1);
			Node2 rightNode=nodes.get(nodes.size()-2);
			//创建 一颗新的二叉树
			
			Node2 parent=new Node2(null,leftNode.weight+rightNode.weight);
			parent.left=leftNode;
			parent.right=rightNode;
			//把取出来 的二叉树移除
			nodes.remove(leftNode);
			nodes.remove(rightNode);
			//放入原来的二叉树集合中
			nodes.add(parent);	
		}
		Node2 root=nodes.get(0);	
		return root;
	}

	/**
	 * 把byte数组转成Node2集合
	 * @param bytes
	 * @return
	 */
	private static List<Node2> getNodes(byte[] bytes) {
		List<Node2> nodes=new ArrayList<Node2>();
		Map<Byte,Integer> counts=new HashMap<Byte,Integer>();
		//统计每一个出现的次数
		for (byte b: bytes) {
			Integer count = counts.get(b);	
			if(count==null) {
				counts.put(b, 1);
			}else {
				counts.put(b,count+1);
			}
		}
		//把每一个键值对转为一个node对象！！
		//遍历map;
		for (Map.Entry<Byte, Integer>  entry:counts.entrySet()) {
			nodes.add(new Node2( entry.getKey(),entry.getValue()));
		}
		return nodes;
	}

}

package DoubleBinary;


public class ThreadedNode {
	//节点的权
	public int value;
	//左节点
	ThreadedNode lnode;
	//右节点
	 ThreadedNode rnode;
	 //用来标识指针类型
	 int leftType;
	 int rightType;
	
	public ThreadedNode(int value) {
		this.value=value;
	}
	
	public void showValue() {
		System.out.println(value);
	}
	
	public ThreadedNode getLnode() {
		return lnode;
	}

	public void setLnode(ThreadedNode lnode) {
		this.lnode = lnode;
	}

	public ThreadedNode getRnode() {
		return rnode;
	}

	public void setRnode(ThreadedNode rnode) {
		this.rnode = rnode;
	}
	
	


	//前序遍历
	public void frontShow() {
		//先遍历当前节点的内容，
		System.out.print(value+",");
		//左节点
		if(lnode!=null) {
			lnode.frontShow();
		}
		//右节点
		if(rnode!=null) {
			rnode.frontShow();
		}
		
	}

	//中序遍历
	public void midShow() {
		if(lnode!=null) {
			lnode.midShow();
		}
		System.out.print(value+",");
		if(rnode!=null) {
			rnode.midShow();
		}
		
		
	}

	//后序遍历
	public void afterShow() {
		if(lnode!=null) {
			lnode.afterShow();
		}		
		if(rnode!=null) {
			rnode.afterShow();
		}
		System.out.print(value+",");
	}

	//前序查找
	public ThreadedNode findFrontShow(int value2) {
		ThreadedNode target=null;
		if(this.value==value2) {
			return this;
		}else {
			//如果左节点不为空
			if(this.lnode!=null) {
				//有可能查的到，也有可能查不到！！
				target=lnode.findFrontShow(value2);
			}
			if(target!=null) {
				//如果结果不为空，就是找到了
				return target;
			}
			if(rnode!=null) {
				target=rnode.findFrontShow(value2);
			}
		}
		//返回结果
		return target;
		
	}

	
	//删除子树
	public void deleteNode(int i) {
		
		ThreadedNode partent=this;
		//左节点
		if(partent.lnode!=null&&partent.lnode.value==i) {
			partent.lnode=null;
			return;
		}
		//右节点
		if(partent.rnode!=null&&partent.rnode.value==i) {
			partent.rnode=null;
			return;
		}
		//如果都不是,就递归检查左节点是否可以继续操作，可以的话就进行删除！！
		partent=lnode;
		if(partent!=null) {
			partent.deleteNode(i);
		}
		
		partent=rnode;
		
		if(partent!=null) {
			partent.deleteNode(i);
		}
	
	}



}

package Binary;


public class TreeNode {
	//节点的权
	public int value;
	//左节点
	TreeNode lnode;
	//右节点
	 TreeNode rnode;
	
	public TreeNode(int value) {
		this.value=value;
	}
	
	public void showValue() {
		System.out.println(value);
	}
	
	public TreeNode getLnode() {
		return lnode;
	}

	public void setLnode(TreeNode lnode) {
		this.lnode = lnode;
	}

	public TreeNode getRnode() {
		return rnode;
	}

	public void setRnode(TreeNode rnode) {
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
	public TreeNode findFrontShow(int value2) {
		TreeNode target=null;
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
		
		TreeNode partent=this;
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

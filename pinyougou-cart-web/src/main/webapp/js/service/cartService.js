app.service('cartService',function($http){
	
	this.findCartList=function(){
		return $http.get('cart/findCartList.do');
	}
	
	this.addGoodsToCartList=function(itemId,num){
		return $http.get('cart/addGoodsToCartList.do?itemId='+itemId+'&num='+num);
	}
	
	/**
	 * 计算总数！！！
	 */
	this.sum=function(cartList){
		var totalValue={totalNum:0,totalMoney:0.00}//合计实体;
		for(var i=0;i<cartList.length;i++){
			var cart=cartList[i];
			for(var j=0;j<cart.orderItemList.length;j++){
				var orderItem=cart.orderItemList[j];//购物明细;也就是每一条数据
				totalValue.totalNum+=orderItem.num;
				totalValue.totalMoney+=orderItem.totalFee;
			}
		}
		
		return totalValue;
	}
	
	this.findAddressList=function(){
		return $http.get('address/findListByLoginUser.do');
	}
	
	this.submitOrder=function(order){
		return $http.post('order/add.do',order);
	}
	
})

//服务层
app.service('seckillService',function($http){
	
	this.findList=function(){
		return $http.get('seckillGoods/findAll.do');
	}
	
	this.findOne=function(id){
		return $http.get('seckillGoods/findOneFromRedis.do?id='+id);
	}
	
	this.submitOrder=function(seckillId){
		return $http.get('seckillOrder/submitOrder.do?seckillId='+seckillId);
	}
	
	
	
});

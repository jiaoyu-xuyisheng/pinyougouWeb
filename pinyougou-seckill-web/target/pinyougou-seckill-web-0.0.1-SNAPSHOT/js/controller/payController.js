app.controller('payController',function($scope,payService,$location){
	
	$scope.createNative=function(){
		
		payService.createNative().success(function(response){
	
			$scope.result=response;
			$scope.money=(result.total_fee/100).toFixed(2);
		  	var qr = new QRious({
		 		   element:document.getElementById('qrious'),
		 		   size:250,
		 		   level:'H',
		 		   value:'https://www.baidu.com'
		 		});	
		  	
		  	queryPayStatus(response.out_trade_no);//查询支付状态	
			
			
			
		})	
		
	}
	
	
//查询支付状态 
	queryPayStatus=function(out_trade_no){
		payService.queryPayStatus(out_trade_no).success(
			function(response){
				if(response.success){
					location.href="paysuccess.html#?money="+$scope.money;
				}else{					
					if(response.message=='二维码超时'){
						location.href="payTimeOut.html";				
					}else{
						location.href="payfail.html";
					}												
				}				
			}
		);
	}
	
	
	$scope.getMoney=function(){
		
		return $location.search()['money'];
	}
	
	
	
	
	
})
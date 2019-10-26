 //控制层 
app.controller('seckillController' ,function($scope,$location ,seckillService,$interval){	
	
	
	$scope.findList=function(){
		seckillService.findList().success(function(response){
			
			$scope.seckillList=response;
			
		})
	}
	
	
	$scope.findOne=function(){
		seckillService.findOne($location.search()['id']).success(function(response){
			
			$scope.entity=response;
			//总秒数！！
			allsecond=Math.floor((new Date($scope.entity.endTime).getTime()-(new Date().getTime()))/1000)
			time=$interval(function(){
				if(allsecond>0){
					allsecond=allsecond-1;
					//转换时间字符串
					$scope.timeString=convertTimeString(allsecond);
				}else{
					$interval.cancel(time);
					alert("秒杀服务已结束！！")
				}
			},1000);
			
		})
		
	}//end
	
	convertTimeString=function(allsecond){
		//天数
		var days=Math.floor(allsecond/(60*60*24));
		//小时
		var hours=Math.floor((allsecond-(days*60*60*24))/(60*60));
		
		var minutes=Math.floor((allsecond-(days*60*60*24)-(hours*60*60))/60);
		
		var seconds=allsecond -days*60*60*24 - hours*60*60 -minutes*60; //秒数
		var timeString="";
		var h="";
		var m="";
		var s="";
		if(days>0){
			timeString=days+"天";
		}
		if(hours<10){
			h="0";
		}
		if(minutes<10){
			m="0";
		}
		if(seconds<10){
			s="0";
		}
		
		return timeString+h+hours+"时:"+m+minutes+"分:"+s+seconds+"秒";
		
	}
	
	
	$scope.submitOrder=function(){
		seckillService.submitOrder($scope.entity.id).success(
			function(response){
				if(response.success){
					alert("下单成功，请在1分钟内完成支付");
					location.href="pay.html";
				}else{
					alert(response.message);
				}
			}
		);		
	}
	
	
	
	
	
		
});	

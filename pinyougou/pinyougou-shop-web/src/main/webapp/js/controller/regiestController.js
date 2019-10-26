app.controller('myindexController' ,function($scope,$controller,myindexService){
	
	$scope.reg=function(){
		
		if($scope.entity.jyPassword!=$scope.password){
			alert("两次输入的密码不一致，请重新输入！！");
			return ;
		}
		
		myindexService.regiestJYUser($scope.entity).success(
				function(response){
					if(response.success){
						location.href="http://localhost:8092/login.html"	
					}else{
						alert(response.message);
					}
				}
		
		);
		
		
	}
	
	
	
	
	
})
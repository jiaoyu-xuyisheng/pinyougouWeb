	app.controller('brandController',function($scope,$http,$controller,brandService){
		
		$controller("baseController",{$scope:$scope});
		
		//查找所有
		brandService.findAll().then(function successCallback(response) {
		      	$scope.list=response.data;
		   });
		
		//分页 
		$scope.findPage=function(page,size){
			
			brandService.findPage().then(function successCallback(response) {
				$scope.list=response.data.rows;
				$scope.paginationConf.totalItems=response.data.total;
			    }, function errorCallback(response) {
			        // 请求失败执行代码
			});			
		}
		
		//add and save
		$scope.save=function(){
			var object=null;
			if($scope.entity.id!=null){
				object=brandService.update($scope.entity);
			}else{
				Object=brandService.add($scope.entity);
			}
				
			object.then(function successCallback(response) {
				$scope.reloadList()
			    });
		}
		
		//查找一个
		$scope.findOne=function(id){
			brandService.findOne().then(function successCallback(response) {
				$scope.entity=response.data;
			    });
		}
		
		
		
		$scope.dele=function(){
			brandService.dele($scope.selectIds).then(function successCallback(response) {
				if(response.data.success){
					$scope.reloadList();
				}else{
					alert(reponse.data.message)
				}
				
			    });
		}
		
		$scope.searchEntity={};
		
		$scope.search=function(page,size){
			brandService.search(page,size,$scope.searchEntity).then(function successCallback(response) {
				$scope.list=response.data.rows;
				$scope.paginationConf.totalItems=response.data.total;
			    });
		}
		
		
	
	})
app.controller("baseController",function($scope){

	//分页控件配置currentPage:当前页   totalItems :总记录数  itemsPerPage:每页记录数  perPageOptions :分页选项  onChange:当页码变更后自动触发的方法 
	$scope.paginationConf = {
		currentPage: 1,
		totalItems: 100,
		itemsPerPage: 10,
		perPageOptions: [10, 20, 30, 40, 50],
		onChange: function(){
			$scope.reloadList()
		}
	};
	
	$scope.reloadList=function(){
		$scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
	}
	
	$scope.selectIds=[];//用户勾选的id集合
	
	$scope.updateSelection=function($event,id){
		if($event.target.checked){
			$scope.selectIds.push(id);
		}else{
			var index=$scope.selectIds.indexOf(id);//查找值的位置
			$scope.selectIds.splice(index,1); //从数组索引deletePos位置开始删除deletCount个元素，				
		}
		
	}
	
	$scope.jsonToString=function(jsonString,key){
		var json=JSON.parse(jsonString);
		var value="";
		for(var i=0;i<json.length;i++){
			if(i>0){
				value+=","
			}
			value+=json[i][key];
		}
		return value;
	}
	
	//从集合中按照key查对象！！从而决定是否增加list
	$scope.seachObjectByKey=function(list,key,keyValue){
		for(var i=0;i<list.length;i++){
			if(list[i][key]==keyValue){
				return list[i];
			}
		}
		return null;
	}
	
})
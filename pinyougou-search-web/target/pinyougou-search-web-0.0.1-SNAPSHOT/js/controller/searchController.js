app.controller('searchController',function($scope,$location,searchService){

	$scope.search=function(){
		$scope.searchMap.pageNo= parseInt($scope.searchMap.pageNo) ;
		searchService.search($scope.searchMap).success(
		
				function(response){
					$scope.resultMap=response; //搜索返回的结果
					bindPageLabel();
				}
		
		)
	}
	
	$scope.searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'','pageNo':1,'pageSize':20,'sortField':'','sort':''}//搜索对象
	
	$scope.addSearchItem=function(key,value){
		if(key=='category' || key=='brand' || key=='price'){//如果是分类或者是品牌
			$scope.searchMap[key]=value;
		}else{
			$scope.searchMap.spec[key]=value;
		}
		$scope.search();
	}
	
	$scope.removeSearchItem=function(key){
		if(key=="category" || key== "brand" || key=='price'){
			
			$scope.searchMap[key]="";
		}else{
			delete $scope.searchMap.spec[key];
		}
		
		$scope.search();
		
	}
	
	//构建分页标签（totalPages为总页数）;主要是设置起始页和截止页码;然后给分页页码的一个集合添加数字
	bindPageLabel=function(){
		
			$scope.pageLabel=[];
			var maxPageNo=$scope.resultMap.totalPages;//得到最后的页码
			var fistPage=1;//起始页，先给定为1;
			var lastPage=maxPageNo;//截止页码.先给最后显示页数定为总页数;
			$scope.firstDot=true;//前面有点
			$scope.lastDot=true;//后边有点	
			
			
			if($scope.resultMap.totalPages>5){ //如果总页数大于5页,显示部分页码
				if($scope.searchMap.pageNo<=3){//如果当前页小于等于3，这是最前面的情况
					lastPage=5; 
					$scope.firstDot=false;//前面没点
				}else if($scope.searchMap.pageNo>lastPage-2){//如果当前页大于等于最大页码-2，这是最后的情况
					fistPage=maxPageNo-4;//后5页
					$scope.lastDot=false;//后边没点
				}else{
					fistPage=$scope.searchMap.pageNo-2;
					lastPage=$scope.searchMap.pageNo+2;
				}
					
				
			}else{
				$scope.firstDot=false;//前面无点
				$scope.lastDot=false;//后边无点
			}
			
			for(var i=fistPage;i<=lastPage;i++){
				$scope.pageLabel.push(i);
			}
		
	}//end;
	
	$scope.queryByPage=function(pageNo){
		if(pageNo<1 || pageNo>$scope.resultMap.totalPages){
			return ;
		}
		$scope.searchMap.pageNo=pageNo;
		$scope.search();
	}
	
	
	$scope.isToPage=function(){
		if($scope.searchMap.pageNo==1){
			return ;
		}else{
			return false;
		}
	}
	
	$scope.isEndPage=function(){
		if($scope.searchMap.pageNo==$scope.resultMap.totalPages){
			return true;
		}else{
			return false;
		}
	}
	
	
	//设置排序规则  sortField 排序字段，SORT排序方式ASC DESC
	$scope.sortSearch=function(sortField,sort){
		$scope.searchMap.sortField=sortField;
		$scope.searchMap.sort=sort;
		$scope.search();
	}
	
	//判断关键字是不是品牌
	$scope.keywordIsBrand=function(){
		for(var i=0;i<$scope.resultMap.brandList.length;i++){
			if($scope.searchMap.keywords.indexOf($scope.resultMap.brandList[i].text)>=0){
				return true;
			}
		};
		return false;
	}//end;
	
	//加载查询字符串
 $scope.loadkeywords=function(){
	 $scope.searchMap.keywords=$location.search()['keywords'];
	 $scope.search();
 }

	
})

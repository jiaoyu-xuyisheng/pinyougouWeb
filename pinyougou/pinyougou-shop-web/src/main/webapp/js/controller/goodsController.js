 //控制层 
app.controller('goodsController' ,function($scope,$controller,goodsService,uploadService,itemCatService,typeTemplateService,$location){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(){	
	
		var id=$location.search()['id'];
		if(id==null){
			return ;		
		}
		goodsService.findOne(id).success(
				function(response){					
					$scope.entity= response;	
					editor.html($scope.entity.tbGoodsDesc.introduction);
					$scope.entity.tbGoodsDesc.itemImages=JSON.parse($scope.entity.tbGoodsDesc.itemImages);
					
					$scope.entity.tbGoodsDesc.customAttributeItems=JSON.parse($scope.entity.tbGoodsDesc.customAttributeItems);
					$scope.entity.tbGoodsDesc.specificationItems=JSON.parse($scope.entity.tbGoodsDesc.specificationItems);						
					for(var i=0;i<$scope.entity.itemList.length;i++){
						$scope.entity.itemList[i].spec=JSON.parse($scope.entity.itemList[i].spec);
					}
				}
			);
				
	}
	
	//保存 
	$scope.save=function(){	
		//提取文本编辑器的值
		$scope.entity.tbGoodsDesc.introduction=editor.html();
		var serviceObject;//服务层对象  				
		if($scope.entity.goods.id!=null){//如果有ID
			serviceObject=goodsService.update($scope.entity); //修改  
		}else{
			serviceObject=goodsService.add($scope.entity);//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					alert("保存成功");
					$scope.entity={};
					editor.html("");
					location.href="goods.html";
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	$scope.entity={ goods:{}, tbGoodsDesc:{itemImages:[],specificationItems:[]} };
	$scope.add_image_entity=function(){
		$scope.entity.tbGoodsDesc.itemImages.push($scope.image_entity)
	}
	
	//保存 
	$scope.add=function(){
		
		$scope.entity.tbGoodsDesc.introduction=editor.html();
		goodsService.add($scope.entity).success(
			
			function(response){
				if(response.success){
					alert("保存成功");
					$scope.entity={}
					editor.html("");//清空文本编辑器
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	$scope.uploadFile=function(){	  
		uploadService.uploadFile().success(function(response) {        	
        	if(response.success){//如果上传成功，取出url
        		$scope.image_entity.url=response.message;//设置文件地址
        	}else{
        		alert(response.message);
        	}
        }).error(function() {           
        	     alert("上传发生错误");
        });        
    }; 
    
    $scope.remove_image_entity=function(){
    	$scope.entity.tbGoodsDesc.itemImages.splice(index,1);
    }
	
    
    $scope.selectItemCat1List=function(){
    	itemCatService.findByParentById(0).success(
    	function(response){
    		$scope.itemCat1List=response;
    	}
    	
    	)
    }
    
    $scope.$watch('entity.goods.category1Id',function(newValue,oldValue){
    
    	
    	itemCatService.findByParentById(newValue).success(
    			function(response){
    				$scope.itemCat2List=response;
    			}
    	)
    	
    })
    
    $scope.$watch('entity.goods.category2Id',function(newValue,oldValue){
    	
    	itemCatService.findByParentById(newValue).success(
    			function(response){
    				$scope.itemCat3List=response;
    			}
    	)
    	
    })
    
  $scope.$watch('entity.goods.category3Id',function(newValue,oldValue){
    	
    	itemCatService.findOne(newValue).success(
    			function(response){
    				$scope.entity.goods.typeTemplateId=response.typeId;//更新模板id
    			}
    	)
    	
    })

     $scope.$watch('entity.goods.typeTemplateId',function(newValue,oldValue){
    	
    	typeTemplateService.findOne(newValue).success(
    			function(response){
    				$scope.typeTemplate=response;
    				$scope.typeTemplate.brandIds=JSON.parse($scope.typeTemplate.brandIds);
    				if($location.search()['id']==null){
        			$scope.entity.tbGoodsDesc.customAttributeItems=JSON.parse( $scope.typeTemplate.customAttributeItems);//扩展属性
    				}
    				
    			}
    	)//brandList
    	
    	//查询规格列表
    	typeTemplateService.findSpecList(newValue).success(
    			
    			  function(response){
        			  $scope.specList=response;
        		  }
    	)
    	
    	
    	
    	
    })
    
    
    $scope.updateSpecAttribute=function($event,name,value){
    	//这个是查找包含某元素的集合！！
    	
    	var object=$scope.seachObjectByKey(
    			//三个参数
    			$scope.entity.tbGoodsDesc.specificationItems,'attributeName', name
    	
    	)
    	
    	if(object!=null){
    		//$event.target.checked表示选中状态，
    		if($event.target.checked){
    			object.attributeValue.push(value);
    		}else{
    			//取消选择
    			object.attributeValue.splice( object.attributeValue.indexOf(value ) ,1); 
    			//如果选项都取消了，将此条记录移除
    			if(object.attributeValue.length==0){
    				$scope.entity.tbGoodsDesc.specificationItems.splice(
    				$scope.entity.tbGoodsDesc.specificationItems.indexOf(object),1);
    			}
    			
    		}
    	}else{
    		$scope.entity.tbGoodsDesc.specificationItems.push(
    				{"attributeName":name,"attributeValue":[value]});
    	}
    }
    
    
    
    $scope.createItemList=function(){
    	//初使化数据
    	$scope.entity.itemList=[{spec:{},price:0,num:99999,status:'0',isDefault:'0' }];
    	var items=$scope.entity.tbGoodsDesc.specificationItems;
    	//增加列
    	for(var i=0;i<items.length;i++){
    		$scope.entity.itemList=addColumn( $scope.entity.itemList,items[i].attributeName,items[i].attributeValue);
    	}
    	
    }
    
    //添加列值
    addColumn=function(list,columnName,conlumnValues){
    	var newList=[];//this is a new List
    	//list这个就是上一层的集合，也就是对行进行循环！！！
    	for(var i=0;i<list.length;i++){
    		//原有的计录的每一行
    		var oldRow=list[i];
    		//对规格值进行遍历
    		for(var j=0;j<conlumnValues.length;j++){
    			var newRow=JSON.parse(JSON.stringify(oldRow));
    			//向新行中添加内容
    			newRow.spec[columnName]=conlumnValues[j];
    			newList.push(newRow);
    		}
    	}
    	return newList;
    }
    
    $scope.status=['未审核','已审核','审核未通过','关闭'];//商品状态
    $scope.itemCatList=[];
    $scope.findItemCatList=function(){
    	itemCatService.findAll().success(
    			function(response){
    				for(var i=0;i<response.length;i++){
    					$scope.itemCatList[response[i].id]=response[i].name;
    				}
    			}
    	)
    }
    
    $scope.checkAttributeValue=function(specName,optionName){   	
    	var items= $scope.entity.tbGoodsDesc.specificationItems;
    	var object=$scope.seachObjectByKey(items,'attributeName',specName);
    	if(object==null){
    		return false;
    	}else{
    		if(object.attributeValue.indexOf(optionName)>0){
    			return true;
    		}else{
    			return false;
    		}
    		
    	}
    }
    
    
});	

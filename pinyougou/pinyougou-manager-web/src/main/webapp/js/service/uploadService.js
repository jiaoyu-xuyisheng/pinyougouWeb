app.service("uploadService",function($http){
	this.uploadFile=function(){
		var formData=new FormData();//表单数据！！
	    formData.append("file",file.files[0]);   //file文件上传框的name,files[0]第一个文件上传框;
		return $http({
            method:'POST',
            url:"../upload.do",
            data: formData,
            headers: {'Content-Type':undefined},
            transformRequest: angular.identity //对表单二进制序列化！！
        });		
	}	
});
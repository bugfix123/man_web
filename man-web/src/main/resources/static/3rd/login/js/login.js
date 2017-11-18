
jQuery(document).ready(function() {

    $('#submitBtn').click(function(){
//        var username = $(this).find('.username').val();
//        var password = $(this).find('.password').val();
//        if(username == '') {
//            $(this).find('.error').fadeOut('fast', function(){
//                $(this).css('top', '27px');
//            });
//            $(this).find('.error').fadeIn('fast', function(){
//                $(this).parent().find('.username').focus();
//            });
//            return false;
//        }
//        if(password == '') {
//            $(this).find('.error').fadeOut('fast', function(){
//                $(this).css('top', '96px');
//            });
//            $(this).find('.error').fadeIn('fast', function(){
//                $(this).parent().find('.password').focus();
//            });
//            return false;
//        }
    	$("#wrong").empty();
        var arr = ['userName','password','check'];
		for(var i = 0;i < arr.length;i++){
			var node = document.getElementsByName(arr[i])[0];
			if(node && (!node.value || node.value == "")){
				document.getElementById("wrong").innerHTML = "<span style='color: red;'>"+node.alt + "不能为空"+"<span>";
				node.focus();
				return false;
			}
		
		}
		//对密码加密
//		var pwdNode = document.getElementsByName("password")[0];
//		pwdNode.value = hex_md5(pwdNode.value);
		var userName = $.trim($(".username").val());
		var password = $.trim($(".password").val());
		var enPwd =  hex_md5(password);
		
		 $.ajax({
		        url: "loginValidate",
		        type: 'post',
		        data:{"userName": userName, "password" : enPwd},
		        async:false,
		        dataType:"json",
		        success: function (data) {
		           if(data.result == "success"){
		        	   var link = /*[[@{/edit.html}]]*/ 'test';
		        	   window.location.href = basePath + "/main";
		        	  //window.location.href="http://localhost:8080/main";
		           }else{
		        	   document.getElementById("wrong").innerHTML = "<span style='color: red;'>"+data.msg+"<span>";
		           }
		        },
		        error: function(data){
		        	console.log(data);
		        }
		    });
		
    });

    $('.page-container form .username, .page-container form .password').keyup(function(){
        $(this).parent().find('.error').fadeOut('fast');
    });
    $(document).keydown(function(event){ 
    	if(event.keyCode==13){ 
    		$('#submitBtn').click();
    	} 
    	}); 

});


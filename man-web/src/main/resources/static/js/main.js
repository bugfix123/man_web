      
function showRight(url){
	if(url == ""){
		return;
	}
	$('#mainContent').load(basePath + url);
//    $.ajax({
//        url: basePath + url,
//        type: 'post',
//        async:true,
//        success: function (responseText) {
//            $('#mainContent').html(responseText);
//        }
//    });

}

generateMenu = function(){
	$.ajax({
		url: "system/getMenu",//地址
        data: {},//参数
    	type: 'post',//提交方式 可以选择post/get 推荐post
    	async: false,//同步异步
    	dataType: 'json',//返回数据类型
    	success:function(data) {
			if (null!=data && {}!=data && ''!=data && data.length>0) {
				$("#demo-list").empty();
				var grade2Array = new Array();
				// 生成一级菜单，每个一级菜单为 div  (a标签有.dropdown-toggle)
				for (i in data){
					
    					 if(data[i].pid&&'2'==data[i].grade){
    						 grade2Array.push(data[i]);
    					 }else{
    						 //$('#demo-list').append('<div class="btn-group pull-left firstlevelmenu" id="'+data[menu].id+'"><a class="btn btn-brand dropdown-toggle firstlevelmenua" data-toggle="dropdown" data-hover="dropdown" data-delay="0" data-close-others="false" href="#" '+('#'==data[menu].url?'':'onclick="window.location.href=\''+basePath+data[menu].url+'\'')+'" aria-expanded="false"><span>'+data[menu].name+'</span></a></div>');
    						 $("#demo-list").append("<li id='" + data[i].id + "'><a href='#' onclick=showRight('" + data[i].url + "') ><i class='"+ data[i].css +"'></i>"+ data[i].name +"</a></li>");
    					 }		    						
					
				}
				
				if (null!=grade2Array && {}!=grade2Array && ''!=grade2Array && grade2Array.length>0) {
					//给有二级菜单的一级菜单右侧添加倒三角图标和二级菜单容器元素 ul
					for(menu in grade2Array){
						$('#'+grade2Array[menu].pid+' ul.submenu').remove();
    					//$('#'+grade2Array[menu].pId+' a').append('<span class="caret" style="margin-left:5px;"></span>');
    					$('#'+grade2Array[menu].pid).append('<ul class="submenu"></ul>');
    					$('#'+grade2Array[menu].pid+' a:first').removeAttr('onclick');
					}
					//填充二级菜单到 ul中       (a标签有.dropdown-toggle)
					for(menu in grade2Array){
						$('#'+grade2Array[menu].pid+' ul.submenu').append('<li><a class=""  grade="'+grade2Array[menu].grade+'" href="#" onclick="showRight(\''+grade2Array[menu].url+'\')" ><span class="secondlevelmenu">'+grade2Array[menu].name+'</span></a></li>');
    				}		    					
				}
			}
//			$('.dropdown-toggle').dropdownHover();
		}
	});
}
generateMenu();
$('#mainContent').load(basePath + "/home");
function bindEvent(){
	$("#logout").bind("click", function(e){
		 $.ajax({
		        url: "/man/logout",
		        type: 'post',
		        async:true,
		        cache:false,
		        success: function (responseText) {
		          window.location.href="/man/login"
		        }
		    });
	})
}
//bindEvent();
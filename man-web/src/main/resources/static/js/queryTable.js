/**
 * 查询表格数据
 */
var queryTable = (function(){
	return {
		initTable: function(){
			  $('#tb_departments').bootstrapTable({
		            url: '/query/queryTableData',         //请求后台的URL（*）
		            method: 'get',                      //请求方式（*）
		            toolbar: '#toolbar',                //工具按钮用哪个容器
		            striped: true,                      //是否显示行间隔色
		            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		            pagination: true,                   //是否显示分页（*）
		            sortable: false,                     //是否启用排序
		            sortOrder: "asc",                   //排序方式
		            queryParams: queryTable.queryParams,//传递参数（*）
		            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
		            pageNumber:1,                       //初始化加载第一页，默认第一页
		            pageSize: 10,                       //每页的记录行数（*）
		            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
		            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
		            strictSearch: true,
		            showColumns: true,                  //是否显示所有的列
		            showRefresh: true,                  //是否显示刷新按钮
		            minimumCountColumns: 2,             //最少允许的列数
		            clickToSelect: true,                //是否启用点击选中行
		            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
		            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
		            cardView: false,                    //是否显示详细视图
		            detailView: false,                   //是否显示父子表
		            columns: [{
		                checkbox: true
		            }, {
		                field: 'name',
		                title: '姓名'
		            }, {
		                field: 'sex',
		                title: '性别'
		            }, {
		                field: 'age',
		                title: '年龄'
		            }, {
		                field: 'address',
		                title: '地址'
		            },{
		                field: 'remark',
		                title: '描述'
		            } ]
		        });
		},
		queryParams: function(params){
			 var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, // 页面大小
				offset : params.offset, // 页码
				departmentname : $("#txt_search_departmentname").val(),
				statu : $("#txt_search_statu").val()
			};
			return temp;
		},
		bindEvent: function(){
			$("#btn_add").bind("click", function(){
				$("#addUserModal").modal("show");
			});
			$(".submit").bind("click", function(){
				queryTable.addStudent();
			});
		},
		addStudent: function(){
			$.ajax({
				url: "/student/add",
				data:$("#stuForm").serialize(),
				type:"post",
				dataType:"text",
				success: function(data){
					swal("Good!", "成功", "success"); 
					$("#addUserModal").modal("hide");
				},
				error: function(data){
					console.log(data);
				}
			});
		}
	};
})();
$(document).ready(function(){
	queryTable.initTable();
	queryTable.bindEvent();
});
//@ sourceURL=userList.js
/**
 * 查询表格数据
 */
var queryTable = (function() {
	return {
		initTable : function() {
			$('#userList').bootstrapTable({
				url : 'sys/user/query', // 请求后台的URL（*）
				method : 'get', // 请求方式（*）
				toolbar : '#toolbarForUserList', // 工具按钮用哪个容器
				striped : true, // 是否显示行间隔色
				cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, // 是否显示分页（*）
				sortable : false, // 是否启用排序
				sortOrder : "asc", // 排序方式
				queryParams : queryTable.queryParams,// 传递参数（*）
				sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
				pageNumber : 1, // 初始化加载第一页，默认第一页
				pageSize : 6, // 每页的记录行数（*）
				pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
				search : true, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
				strictSearch : true,
				showColumns : true, // 是否显示所有的列
				showRefresh : true, // 是否显示刷新按钮
				minimumCountColumns : 2, // 最少允许的列数
				clickToSelect : true, // 是否启用点击选中行
				height : 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				uniqueId : "ID", // 每一行的唯一标识，一般为主键列
				showToggle : true, // 是否显示详细视图和列表视图的切换按钮
				cardView : false, // 是否显示详细视图
				detailView : false, // 是否显示父子表
				columns : [ {
					checkbox : true
				}, {
					field : 'userName',
					title : '用户名'
				}, {
					field : 'realName',
					title : '姓名'
				}, {
					field : 'age',
					title : '年龄'
				}, {
					field : 'address',
					title : '地址'
				}, {
					field : 'job',
					title : '职业'
				}, {
					field : 'operate',
					title : '操作',
					width : '110px',
					events : window.operateEvents,
					formatter : queryTable.operateFormatter
				} ]
			});

		},
		initEvent : function() {
			window.operateEvents = {
				'click .edit' : function(e, value, row, index) {
					// alert('You click like action, row: ' +
					// JSON.stringify(row));
					$("#userInfoModal").modal("show");
					$("#userInfoForm").setForm(row)
					queryTable.initFormValidation();
					$(".pwd").hide();
					$("#userName").attr("readonly", "readonly");
					$("#userInfoForm").data('bootstrapValidator')
							.enableFieldValidators('userName', false, "remote");
					$("#userInfoModal .submit").bind("click", function() {
						queryTable.modifyUser();
					});
				},
				'click .remove' : function(e, value, row, index) {
//					$table.bootstrapTable('remove', {
//						field : 'id',
//						values : [ row.id ]
//					});
					queryTable.deleteUser(row.id);
				}
			};
			$('#userInfoModal').on('hide.bs.modal', function() {
				$("#userInfoForm").data('bootstrapValidator').resetForm();
				$("#userInfoForm").resetForm();
			});

		},
		operateFormatter : function(value, row, index) {
			return [
					'<a class="edit btn btn-success" href="javascript:void(0)" title="Like">',
					'<span class="glyphicon glyphicon-pencil"></span>',
					'</a>  ',
					'<a class="remove btn btn-danger" href="javascript:void(0)" title="Remove">',
					'<span class="glyphicon glyphicon-remove"></span>', '</a>' ]
					.join('');
		},
		queryParams : function(params) {
			var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, // 页面大小
				offset : params.offset, // 页码
				departmentname : $("#txt_search_departmentname").val(),
				statu : $("#txt_search_statu").val()
			};
			return temp;
		},
		bindEvent : function() {
			$("#btn_add").bind("click", function() {
				$("#userInfoModal").modal("show");
				$(".pwd").show();
				$("#userName").removeAttr("readonly");
				queryTable.initFormValidation();
				$("#userInfoModal .submit").bind("click", function() {
					queryTable.addUser();
				});
			});
			$("#userInfoModal").on('hide.bs.modal', function(e) {
				$("#userInfoModal .submit").unbind("click");
			});

		},
		initFormValidation : function() {
			$("#userInfoForm")
					.bootstrapValidator(
							{
								message : 'This value is not valid',
								feedbackIcons : {
									valid : 'glyphicon glyphicon-ok',
									invalid : 'glyphicon glyphicon-remove',
									validating : 'glyphicon glyphicon-refresh'
								},
								fields : {
									userName : {
										validators : {
											notEmpty : {
												message : '用户名不能为空'
											},
											stringLength : {
												min : 3,
												max : 20,
												message : '用户名长度在6和20之间'
											},
											regexp : {
												regexp : /^[a-zA-Z0-9_\.]+$/,
												message : 'The username can only consist of alphabetical, number, dot and underscore'
											},
											remote : {
												url : 'sys/user/userNameValid',
												delay : 1500,
												message : '用户名已存在'
											},
											different : {
												field : 'password',
												message : '用户名不能喝密码相同'
											}
										}
									},
									realName : {
										validators : {
											notEmpty : {
												message : '用户名不能为空'
											},
											stringLength : {
												min : 1,
												max : 20,
												message : '用户名长度在6和20之间'
											}
										}
									},
									password : {
										validators : {
											notEmpty : {
												message : '密码不能为空'
											},
											identical : {
												field : 'confirmPassword',
												message : '两次输入的密码不一致'
											},
											different : {
												field : 'userName',
												message : '密码不能和用户名相同'
											}
										}
									},
									confirmPassword : {
										validators : {
											notEmpty : {
												message : '确认密码不能为空'
											},
											identical : {
												field : 'password',
												message : '两次输入的密码不一致'
											},
											different : {
												field : 'username',
												message : '确认密码不能和用户名相同'
											}
										}
									},
									age : {
										validators : {
											integer : {
												message : '必须为整数'
											},
											between : {
												min : 1,
												max : 120,
												message : '请输入1到120之间',
											}
										}
									}
								}
							});
		},
		addUser : function() {
			$("#userInfoForm").ajaxSubmit(
					{
						beforeSubmit : queryTable.validateForm,
						success : function(data) {
							console.log(data);
							$('#userInfoModal').modal('hide');
							swal({
								title : "新增成功",
								text : "",
								type : "success",
								showCancelButton : false,
								closeOnConfirm : false,
								confirmButtonText : "OK",
								confirmButtonColor : "#ec6c62"
							});
							$("#userList").bootstrapTable("refresh");
						},
						url : "sys/user/add",
						type : "post",
						dataType : "json",
						clearForm : true,
						resetForm : true,
						timeout : 3000
					});
		},
		modifyUser: function(){
			$("#userInfoForm").ajaxSubmit(
					{
						beforeSubmit : queryTable.validateForm,
						success : function(data) {
							console.log(data);
							$('#userInfoModal').modal('hide');
							swal({
								title : "修改成功",
								text : "",
								type : "success",
								showCancelButton : false,
								closeOnConfirm : false,
								confirmButtonText : "OK",
								confirmButtonColor : "#ec6c62"
							});
							$("#userList").bootstrapTable("refresh");
						},
						url : "sys/user/edit",
						type : "post",
						dataType : "json",
						clearForm : true,
						resetForm : true,
						timeout : 3000
					});
		},
		validateForm: function(){
			var bootstrapValidator = $("#userInfoForm").data(
			'bootstrapValidator');
			bootstrapValidator.validate();
			return bootstrapValidator.isValid()
		},
		deleteUser: function(id){
			swal({ 
			    title: "您确定要删除吗？", 
			    type: "warning", 
			    showCancelButton: true, 
			    closeOnConfirm: false, 
			    confirmButtonText: "YES", 
			    confirmButtonColor: "#ec6c62" 
			}, function() { 
			    $.post("sys/user/del", {id: id}, function(data) { 
			    	var title;
			    	var type;
			    	if(data.result == true){
			    		title = "删除成功";
			    		type="success";
			    	}else{
			    		title = "删除失败";
			    		type = "error";
			    	}
			    	swal({
						title : title,
						text : "",
						type : type,
						showCancelButton : false,
						closeOnConfirm : false,
						confirmButtonText : "OK",
						confirmButtonColor : "#ec6c62"
					});
			    	$("#userList").bootstrapTable("refresh");
			    }) 
			});
		}
	};
})();
$(document).ready(function() {
	queryTable.initEvent();
	queryTable.initTable();
	queryTable.bindEvent();
});

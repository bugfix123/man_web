//@ sourceURL=userList.js
/**
 * 查询表格数据
 */
var queryTable = (function() {
	return {
		initTable : function() {
			$('#roleList').bootstrapTable({
				url : 'sys/role/query', // 请求后台的URL（*）
				method : 'get', // 请求方式（*）
				toolbar : '#toolbarForRoleList', // 工具按钮用哪个容器
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
					field : 'roleName',
					title : '角色名'
				}, {
					field : 'roleCode',
					title : '角色编码'
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
					$("#roleInfoModal").modal("show");
					$("#roleInfoForm").setForm(row)
					queryTable.initFormValidation();
					$("#roleCode").attr("readonly", "readonly");
					$("#roleInfoForm").data('bootstrapValidator')
							.enableFieldValidators('roleCode', false, "remote");
					$("#roleInfoModal .submit").bind("click", function() {
						queryTable.modifyRole();
					});
				},
				'click .remove' : function(e, value, row, index) {
//					$table.bootstrapTable('remove', {
//						field : 'id',
//						values : [ row.id ]
//					});
					queryTable.deleteRole(row.id);
				}
			};
			$('#roleInfoModal').on('hide.bs.modal', function() {
				$("#roleInfoForm").data('bootstrapValidator').resetForm();
				$("#roleInfoForm").resetForm();
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
				$("#roleInfoModal").modal("show");
				$("#roleCode").removeAttr("readonly");
				queryTable.initFormValidation();
				$("#userInfoModal .submit").bind("click", function() {
					queryTable.addRole();
				});
			});
			$("#userInfoModal").on('hide.bs.modal', function(e) {
				$("#roleInfoModal .submit").unbind("click");
			});

		},
		initFormValidation : function() {
			$("#roleInfoForm")
					.bootstrapValidator(
							{
								message : 'This value is not valid',
								feedbackIcons : {
									valid : 'glyphicon glyphicon-ok',
									invalid : 'glyphicon glyphicon-remove',
									validating : 'glyphicon glyphicon-refresh'
								},
								fields : {
									roleName : {
										validators : {
											notEmpty : {
												message : '角色名不能为空'
											},
											stringLength : {
												min : 3,
												max : 20,
												message : '角色名长度在3和20之间'
											}
										}
									},
									roleCode : {
										validators : {
											notEmpty : {
												message : '角色编码不能为空'
											},
											stringLength : {
												min : 1,
												max : 20,
												message : '角色编码长度在6和20之间'
											},
											regexp : {
												regexp : /^[a-zA-Z0-9_\.]+$/,
												message : 'The username can only consist of alphabetical, number, dot and underscore'
											},
											remote : {
												url : 'sys/role/roleCodeValid',
												delay : 1500,
												message : '角色编码已存在'
											}
										}
									},
									
								}
							});
		},
		addRole : function() {
			$("#roleInfoForm").ajaxSubmit(
					{
						beforeSubmit : queryTable.validateForm,
						success : function(data) {
							console.log(data);
							$('#roleInfoModal').modal('hide');
							swal({
								title : "新增成功",
								text : "",
								type : "success",
								showCancelButton : false,
								closeOnConfirm : false,
								confirmButtonText : "OK",
								confirmButtonColor : "#ec6c62"
							});
							$("#roleList").bootstrapTable("refresh");
						},
						url : "sys/role/add",
						type : "post",
						dataType : "json",
						clearForm : true,
						resetForm : true,
						timeout : 3000
					});
		},
		modifyRole: function(){
			$("#roleInfoForm").ajaxSubmit(
					{
						beforeSubmit : queryTable.validateForm,
						success : function(data) {
							console.log(data);
							$('#roleInfoModal').modal('hide');
							swal({
								title : "修改成功",
								text : "",
								type : "success",
								showCancelButton : false,
								closeOnConfirm : false,
								confirmButtonText : "OK",
								confirmButtonColor : "#ec6c62"
							});
							$("#roleList").bootstrapTable("refresh");
						},
						url : "sys/role/edit",
						type : "post",
						dataType : "json",
						clearForm : true,
						resetForm : true,
						timeout : 3000
					});
		},
		validateForm: function(){
			var bootstrapValidator = $("#roleInfoForm").data(
			'bootstrapValidator');
			bootstrapValidator.validate();
			return bootstrapValidator.isValid()
		},
		deleteRole: function(id){
			swal({ 
			    title: "您确定要删除吗？", 
			    type: "warning", 
			    showCancelButton: true, 
			    closeOnConfirm: false, 
			    confirmButtonText: "YES", 
			    confirmButtonColor: "#ec6c62" 
			}, function() { 
			    $.post("sys/role/del", {id: id}, function(data) { 
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
			    	$("#roleList").bootstrapTable("refresh");
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

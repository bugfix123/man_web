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
					width : '200px',
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
					$("#roleInfoForm").data('bootstrapValidator').enableFieldValidators('roleCode', false, "remote");
					$("#roleInfoForm").data('bootstrapValidator').enableFieldValidators('roleName', false, "remote");
					$("#roleInfoModal .submit").bind("click", function() {
						queryTable.modifyRole();
					});
				},
				'click .setting' : function(e, value, row, index) {
					$("#bindPermModal").modal("show");
					queryTable.initPermTree();
					
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
					'<a class="edit btn btn-success btn-circle" href="javascript:void(0)" title="修改角色">',
					'<span class="glyphicon glyphicon-pencil"></span>',
					'</a>  ',
					'<a class="setting btn btn-info btn-circle " href="javascript:void(0)" title="分配权限">',
					'<i class="fa fa-link"></i>',
					'</a>  ',
					'<a class="remove btn btn-danger btn-circle" href="javascript:void(0)" title="删除角色">',
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
		initPermTree: function(){
			var setting = {
				    view: {
				        addHoverDom: queryTable.addHoverDom,
				        removeHoverDom: queryTable.removeHoverDom,
				        selectedMulti: false,
				        showIcon:true,
						showLine:false,
				    },
				    check: {
				        enable: true
				    },
				    data: {
				        simpleData: {
				            enable: true
				        }
				    },
				    edit: {
				        enable: false
				    }
				};
			var zNodes = [
			              { id: 1, pId: 0, name: "父节点1", open: false },
			              { id: 11, pId: 1, name: "父节点11" },
			              { id: 111, pId: 11, name: "叶子节点111" },
			              { id: 112, pId: 11, name: "叶子节点112" },
			              { id: 113, pId: 11, name: "叶子节点113" },
			              { id: 114, pId: 11, name: "叶子节点114" },
			              { id: 12, pId: 1, name: "父节点12" },
			              { id: 121, pId: 12, name: "叶子节点121" },
			              { id: 122, pId: 12, name: "叶子节点122" },
			              { id: 123, pId: 12, name: "叶子节点123" },
			              { id: 124, pId: 12, name: "叶子节点124" },
			              { id: 13, pId: 1, name: "父节点13", isParent: true },
			              { id: 2, pId: 0, name: "父节点2" },
			              { id: 21, pId: 2, name: "父节点21", open: true },
			              { id: 211, pId: 21, name: "叶子节点211" },
			              { id: 212, pId: 21, name: "叶子节点212" },
			              { id: 213, pId: 21, name: "叶子节点213" },
			              { id: 214, pId: 21, name: "叶子节点214" },
			              { id: 22, pId: 2, name: "父节点22" },
			              { id: 221, pId: 22, name: "叶子节点221" },
			              { id: 222, pId: 22, name: "叶子节点222" },
			              { id: 223, pId: 22, name: "叶子节点223" },
			              { id: 224, pId: 22, name: "叶子节点224" },
			              { id: 23, pId: 2, name: "父节点23" },
			              { id: 231, pId: 23, name: "叶子节点231" },
			              { id: 232, pId: 23, name: "叶子节点232" },
			              { id: 233, pId: 23, name: "叶子节点233" },
			              { id: 234, pId: 23, name: "叶子节点234" },
			              { id: 3, pId: 0, name: "父节点3", isParent: false }
			          ];
			 $.fn.zTree.init($("#permTree"), setting, zNodes);
		},
		addHoverDom: function(treeId, treeNode) {
//		    var sObj = $("#" + treeNode.tId + "_span");
//		    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
//		    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
//		        + "' title='add node' onfocus='this.blur();'></span>";
//		    sObj.after(addStr);
//		    var btn = $("#addBtn_" + treeNode.tId);
//		    if (btn) btn.bind("click", function () {
//		        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//		        zTree.addNodes(treeNode, { id: (100 + newCount), pId: treeNode.id, name: "new node" + (newCount++) });
//		        return false;
//		    });
		},
		removeHoverDom: function (treeId, treeNode) {
		  //  $("#addBtn_" + treeNode.tId).unbind().remove();
		},
		bindEvent : function() {
			$("#btn_add").bind("click", function() {
				$("#roleInfoModal").modal("show");
				$("#roleCode").removeAttr("readonly");
				queryTable.initFormValidation();
				$("#roleInfoModal .submit").bind("click", function() {
					queryTable.addRole();
				});
			});
			$("#roleInfoModal").on('hide.bs.modal', function(e) {
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
												min : 2,
												max : 20,
												message : '角色名长度在3和20之间'
											},
											remote : {
												url : 'sys/role/roleNameValid',
												delay : 1500,
												message : '角色名称已存在'
											}
										}
									},
									roleCode : {
										validators : {
											notEmpty : {
												message : '角色编码不能为空'
											},
											stringLength : {
												min : 2,
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

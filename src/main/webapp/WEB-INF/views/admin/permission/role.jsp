<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CarIt Market</title>
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js" ></script>
		<script type="text/javascript">
		$(function(){
			$('#tree').tree({
				method:'get',
				checkbox:true,
				url: '${ctx}/back/module/query/all?t='+(new Date().getTime()),
				onClick: function(node){
					if(node.attributes.url&&node.attributes.url!=null){
						addTab(node.text, '${ctx}/'+node.attributes.url);
					}
				}
			});		
			checkEditControl('admin/permission/role');
			$('#edit_submit_r').bind('click',function(){
				$('#editForm').form({
					onSubmit:function(){
						var arr = [];
						$.each($('#tree').tree('getChecked'), function(i, n){
							arr.push(n.id);
						}); 
						var nodes = [];//获取实心节点
						$("#tree").find('.tree-checkbox2').each(function(){  
							var node = $(this).parent();  
							nodes.push($.extend({}, $.data(node[0], 'tree-node'), {  						 
								target: node[0],  
								checked: node.find('.tree-checkbox').hasClass('tree-checkbox2') 						 
							}));  
						 });
						 //增加实心节点Id
						 $.each(nodes,function(i,n){
							arr.push(n.id);
						 });
						$('#modules').val(arr.join());
					},
			    	success:function(data){
			    		if(data==-1){
							$.messager.alert('错误', "编辑失败", 'error');
			    		} else if(data>0){
							$.messager.alert('成功', "编辑成功", 'info');
				        	$('#editWin').window('close');
				        	// clear form
				        	// update rows
				        	$('#tt').datagrid('reload');
						}else{
			    			$.messager.alert('异常', "后台系统异常", 'error');
						}
				    }
				}).submit();
			});
		});
		function edit() {
			var m = $('#tt').datagrid('getSelected');
			if (m) {
				$('#editWin').window('open');
				// init data
				$('#editForm input[name=roleName]').val(m.roleName);
				$('#editForm input[name=roleDesc]').val(m.roleDesc);
				$('#id').val(m.id);
				// 查询拥有哪些模块
				var arr = new Array();
				$.getJSON('${ctx}/back/module/query/role/'+m.id, function(data){
				  	$.each(data, function(i,item){
				  		var node=$('#tree').tree('find',item.id);
				  		node.checked=true;
				  		$('#tree').tree('update', node);
				  		arr.push(item.id);
				  	});
				});
				$('#modules').val(arr.join());//设置值
				$('#editWin').show();
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选择要修改的记录。'
				});
			}
		}

		function del() {
			var user = $('#tt').datagrid('getSelected');
			if (user) {
				$.messager.confirm('警告','删除的同时会删除用户角色关联，您确认要删除吗?',function(data) {
					if (data) {
						$.ajax({
							url : '${ctx}/admin/permission/role/delete/'+ user.id,
							type : 'GET',
							timeout : 1000,
							error : function() {
								$.messager.alert('错误','删除失败!','error');
							},
							success : function(data) {
								if (data == -1) {
									$.messager.alert('错误','删除失败!','error');
								} else if (data > 0) {
									$.messager.alert('成功','删除成功','info');
									// update rows
									$('#tt').datagrid('reload');
								} else {
									$.messager.alert('异常','后台系统异常','error');
								}
							}
						});
					}
				});
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选择要删除的记录。'
				});
			}
		}
		</script>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="baseRole"
			action="${ctx}/admin/permission/role/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td>
						<form:label for="roleName" path="roleName">名称 ：</form:label>
					</td>
					<td>
						<form:input path="roleName" cssClass="easyui-validatebox" />
					</td>
					<td>
						<form:label for="roleDesc" path="roleDesc">描述：</form:label>
					</td>
					<td>
						<form:input path="roleDesc" cssClass="easyui-validatebox" />
					</td>
				</tr>
			</table>
		</form:form>
		<div style="text-align: center; padding: 5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" id="submit"
					iconCls="icon-search">查 询</a>
				<a href="javascript:void();" class="easyui-linkbutton" id="reset"
					iconCls="icon-undo">重 置</a>
			</div>
			<table id="tt" style="height: auto" iconCls="icon-blank" title="角色列表" singleSelect="true" 
			idField="id" url="${ctx}/admin/permission/role/query"" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10, 30 ]" sortName="updateTime" sortOrder="desc">
				<thead>
					<tr>
						<th field="roleName" width="150" align="center">名称</th>
						<th field="roleDesc" width="200" align="center">描述</th>
						<th field="createTime" width="80" align="center">创建时间</th>
						<th field="updateTime" width="80" align="center">更新时间</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="编辑角色" closed="true" style="width:500px;height:500px;padding:5px;" maximizable="true" modal="true">
			<form:form modelAttribute="baseRole" id="editForm" action="${ctx}/admin/permission/role/save" method="post" cssStyle="padding:10px 20px;">
				<table>
					<tr>
						<td><form:label	for="roleName" path="roleName"  cssClass="mustInput">名称：</form:label></td>
						<td><form:input path="roleName" cssClass="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td><form:label	for="roleDesc" path="roleDesc" >描述：</form:label></td>
						<td><form:input path="roleDesc" required="true" cssClass="easyui-validatebox"/></td>
					</tr>
					<tr><td><form:label	for="modules" path="modules" >权限：</form:label></td></tr>
					<tr>
						<td colspan="2"><ul id="tree" class="easyui-tree"></ul></td>
					</tr>
				</table>
				<form:hidden path="modules" />
				<form:hidden path="id"/>
				<div style="text-align: center; padding: 5px;">
					<a href="javascript:void(0);" class="easyui-linkbutton" id="edit_submit_r"
						iconCls="icon-save">保 存</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" id="edit_reset"
						iconCls="icon-undo">重 置</a>
				</div>
			</form:form>
		</div>
	</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="${ctx}"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js" ></script>
		<script type="text/javascript">
		$(function(){
			// 初始化
			/*$('#ttt').datagrid({
				width:'100%',
				method:'get',
				toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:function() {
						$('#editWin').window('open');
						$('#editWin').show();
					}
				}, '-', {
					text:'修改',
					iconCls:'icon-edit',
					handler:edit
				}, '-', {
					text :'删除',
					iconCls:'icon-remove',
					handler:del
				}, '-', {
					text :'版本',
					iconCls:'icon-add',
					handler:function() {
					}
				}, '-', {
					text :'版本',
					iconCls:'icon-search',
					handler:searchVersion
				} ]
			});
			// edit form
			$('#edit_submit_app').bind('click',function(){
				$('#editForm').form({
			    	success:function(data){
			    		if(data==-1){
							$.messager.alert('错误', "编辑失败", 'error');
			    		} else if(data>0){
							$.messager.alert('成功', "编辑成功", 'info');
				        	$('#editWin').window('close');
				        	// clear form
				        	$('#editForm').form('clear');
				        	// update rows
				        	$('#tt').datagrid('reload');
						}else{
			    			$.messager.alert('异常', "后台系统异常", 'error');
						}
				    }
				}).submit();
			});*/
			checkEditControl('${ctx}/admin/app/application');
		});
		function edit() {
			var m = $('#tt').datagrid('getSelected');
			if (m) {
				$('#editWin').window('open');
				// init data
				$('#editForm input[name=appName]').val(m.appName);
				$('#editForm input[name=displayName]').val(m.displayName);
				$('#editForm input[name=version]').val(m.version);
				$('#editForm input[name=catalogId]').val(m.catalogId);
				$('#editForm input[name=platform]').val(m.platform);
				$('#editForm input[name=supportLanguages]').val(m.supportLanguages);
				$('#editForm input[name=appLevel]').val(m.appLevel);
				$('#editForm input[name=price]').val(m.price);
				$('#editForm input[name=status]').val(m.status);
				$('#permissionDesc').val(m.permissionDesc);
				$('#description').val(m.description);
				$('#id').val(m.id);
				$('#editWin').show();
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选择要修改的记录。'
				});
			}
		}

		function del() {
			var m = $('#tt').datagrid('getSelected');
			if (m) {
				$.messager.confirm('警告','您确认要删除吗?',function(data) {
					if (data) {
						$.ajax({
							url : '${ctx}/admin/app/application/delete/'+ m.id,
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
		function lanFormatter(v){
			if(v==0){
				return '简体中文';
			}
			if(v==0){
				return '繁体中文';
			}
			if(v==0){
				return '英文';
			}
			return v;
		}
		
		function searchVersion() {
			var m = $('#tt').datagrid('getSelected');
			if (m) {
				location.href='${ctx}/admin/app/version/?appId='+m.id;
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选泽应用记录。'
				});
			}
		}
		</script>
		<style>
		#editWin label {width: 115px;}
		#editWin input {width: 180px;}
		#editWin select {width: 185px;}
		#editWin textarea {width: 485px;height: 40px;}
		</style>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="application"
			action="${ctx}/admin/app/application/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td>
						<form:label for="appName" path="appName">名称：</form:label>
					</td>
					<td>
						<form:input path="appName" cssClass="easyui-validatebox" />
					</td>
					<td>
						<form:label for="displayName" path="displayName">显示名称：</form:label>
					</td>
					<td>
						<form:input path="displayName" cssClass="easyui-validatebox" />
					</td>
				</tr>
				<tr>
					<td>
						<form:label for="catalogId" path="catalogId">分类：</form:label>
					</td>
					<td>
						<form:select path="catalogId" cssClass="easyui-combobox">
							<form:option value="">-请选择-</form:option>
							<form:options items="${catalogList}"  itemValue="id"  itemLabel="name" />
						</form:select>
					</td>
					<td>
						<form:label for="status" path="status">状态：</form:label>
					</td>
					<td>
						<form:select path="status" cssClass="easyui-combobox">
							<form:option value="">请选择</form:option>
							<form:option value="0">停用</form:option>
							<form:option value="1">启用</form:option>
						</form:select>
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
			<table id="tt" style="height: auto;" iconCls="icon-blank" title="应用列表" align="left" singleSelect="true" 
			idField="id" url="${ctx}/admin/app/application/query" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="downCount" sortOrder="desc">
				<thead>
					<tr>
						<th field="appName" width="100" align="center">名称</th>
						<th field="displayName" width="100" align="center">显示名称</th>
						<th field="version" width="60" align="center">版本</th>
						<th field="icon" width="60" align="center" hidden="true">图标路径</th>
						<th field="catalogId" width="80" align="center" formmatter="catalogFormatter">分类</th>
						<th field="appFilePath" width="100" align="center"  hidden="true">应用文件路径</th>
						<th field="platform" width="100" align="center">适用平台</th>
						<th field="supportLanguages" width="80" align="center" formatter="lanFormatter">支持语言</th>
						<th field="price" width="60" align="center">价格</th>
						<th field="appLevel" width="80" align="center">应用分级</th>
						<th field="description" width="80" align="center"  hidden="true">描述</th>
						<th field="permissionDesc" width="80" hidden="true">权限描述</th>
						<th field="status" width="60" align="center" formatter="statusFormatter">状态</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="编辑应用" closed="true" style="width:700px;height:480px;padding:5px;" modal="true">
			<form:form modelAttribute="application" id="editForm" action="${ctx}/admin/app/application/save" method="post" cssStyle="padding:10px 20px;"  enctype="multipart/form-data">
				<table>
					<tr>
						<td><form:label	for="appName" path="appName"  cssClass="mustInput">应用名称：</form:label></td>
						<td><form:input path="appName" cssClass="easyui-validatebox" required="true" /></td>
						<td><form:label	for="displayName" path="displayName"  cssClass="mustInput">显示名称：</form:label></td>
						<td><form:input path="displayName" required="true" cssClass="easyui-validatebox"/></td>
				</tr>
				<tr>
						<td><form:label	for="version" path="version"  cssClass="mustInput">版本：</form:label></td>
						<td><form:input path="version" required="true" cssClass="easyui-validatebox"/></td>
						<td><form:label	for="version" path="version"  cssClass="mustInput">分类：</form:label></td>
						<td>
						<form:select path="catalogId" cssClass="easyui-validatebox" required="true" >
							<form:option value="">-请选择-</form:option>
							<form:options items="${catalogList}"  itemValue="id"  itemLabel="name" />
						</form:select>
						</td>
				</tr>
				<tr>
						<td><form:label	for="icon" path="icon">图标：</form:label></td>
						<td><input type="file"  name="file"  id="file" cssClass="easyui-validatebox"/></td>
						<td><form:label	for="platform" path="platform">适用平台：</form:label></td>
						<td><form:input path="platform" required="true" cssClass="easyui-validatebox"/></td>
				</tr>
				<tr>
						<td><form:label	for="supportLanguages" path="supportLanguages">支持语言：</form:label></td>
						<td>
							<form:select path="supportLanguages" cssClass="easyui-combobox" required="true" validType="notempty">
							<form:option value="">请选择</form:option>
							<form:option value="0">简体中文</form:option>
							<form:option value="1">繁体中文</form:option>
							<form:option value="2">英语</form:option>
						</form:select></td>
						<td><form:label	for="appLevel" path="appLevel">应用分级：</form:label></td>
						<td><form:input path="appLevel"  cssClass="easyui-numberspinner"  value="1"  min="1" max="10" required="true" validType="number"/></td>
					</tr>
					<tr>
						<td><form:label	for="price" path="price">价格：</form:label></td>
						<td><form:input path="price" cssClass="easyui-validatebox"/></td>
						<td><form:label	for="status" path="status" cssClass="easyui-validatebox">状态：</form:label></td>
						<td>
							<form:select path="status" cssClass="easyui-combobox">
								<form:option value="1">启用</form:option>
								<form:option value="0">停用</form:option>
							</form:select>
						</td>
					</tr>
					<tr>
						<td><form:label for="permissionDesc" path="permissionDesc" cssClass="easyui-validatebox">权限描述：</form:label></td>
						<td colspan="3">
							<form:textarea path="permissionDesc" />
						</td>
					</tr>
					<tr>
						<td><form:label for="description" path="description" cssClass="easyui-validatebox">描述：</form:label></td>
						<td colspan="3">
							<form:textarea path="description" />
						</td>
					</tr>
					<tr>
						<td><label>截图1：</label></td><td><input type="file" name="image" /></td>
						<td><label>截图2：</label></td><td><input type="file" name="image" /></td>
					</tr>
					<tr>
						<td><label>截图3：</label></td><td><input type="file" name="image" /></td>
						<td><label>截图4：</label></td><td><input type="file" name="image" /></td>
					</tr>
					<tr><td><label>截图5：</label></td><td><input type="file" name="image" /></td></tr>
				</table>
				<form:hidden path="id"/>
				<div style="text-align: center; padding: 5px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_submit_app"
						iconCls="icon-save">保 存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_reset"
						iconCls="icon-undo">重 置</a>
				</div>
			</form:form>
		</div>
	</body>
</html>
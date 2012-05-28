<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js" ></script>
		<script type="text/javascript">
		var catalogs;
		$(function(){
			$.ajaxSettings.async = false;
			$.getJSON('${ctx}/portal/catalog/all/cn', function(data) {
				if(data){
					catalogs=data;
				}
			});
			$('#catalogId').combobox({  
			   	data:catalogs,
			    valueField:'id',  
			    textField:'name'  
			}); 
			$('#catalogId_edit').combobox({  
				data:catalogs,
			    valueField:'id',  
			    textField:'name'  
			}); 
			$('#perTabs').tabs({onSelect:function(title){
				$(this).tabs('getSelected').show()
			}});
			$('#descTabs').tabs({onSelect:function(title){
				$(this).tabs('getSelected').show()
			}});
			// 初始化
			$('#ttt').datagrid({
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
						var m = $('#ttt').datagrid('getSelected');
						if (m) {
							$('#appId').val(m.id);
							$('#editVersionWin').window('open');
							$('#editVersionWin').show();
						} else {
							$.messager.show({
								title : '警告',
								msg : '请先选泽应用记录。'
							});
						}
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
				        	$('#ttt').datagrid('reload');
						}else{
			    			$.messager.alert('异常', "后台系统异常", 'error');
						}
				    }
				}).submit();
			});
			$('#edit_submit_version').bind('click',function(){
				$('#editVersionForm').form({
					success:function(data){
			    		if(data==-1){
							$.messager.alert('错误', "编辑失败", 'error');
			    		} else if(data>0){
							$.messager.alert('成功', "编辑成功", 'info');
				        	$('#editVersionWin').window('close');
				        	// clear form
				        	$('#editVersionWin').form('clear');
				        	// update rows
				        	$('#ttt').datagrid('reload');
						}else{
			    			$.messager.alert('异常', "后台系统异常", 'error');
						}
				    }
				}).submit();
			});
			checkEditControl();
		});
		function edit() {
			var m = $('#ttt').datagrid('getSelected');
			if (m) {
				$('#editWin').window('open');
				// init data
				$('#editForm input[name=appName]').val(m.appName);
				$('#editForm input[name=enName]').val(m.enName);
				//$('#editForm input[name=version]').val(m.version);
				$('#catalogId_edit').combobox('setValue',m.catalogId);
				$('#editForm input[name=platform]').val(m.platform);
				$('#supportLanguages_edit').combobox('setValue',m.supportLanguages);
				$('#appLevel_edit').numberspinner('setValue',m.appLevel);
				$('#editForm input[name=price]').val(m.price);
				$('#status_edit').combobox('setValue',m.status);
				$('#permissionDesc').val(m.permissionDesc);
				$('#enPermissionDesc').val(m.enPermissionDesc);
				$('#description').val(m.description);
				$('#enDescription').val(m.enDescription);
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
			var m = $('#ttt').datagrid('getSelected');
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
									$('#ttt').datagrid('reload');
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
				return '中文';
			}
			if(v==1){
				return '英文';
			}
			if(v==2){
				return '中英双语';
			}
			return v;
		}
		
		function catalogFormatter(v){
			var result='-';
			$.each(catalogs, function(key,val) {
				if(v==val.id){
					result=val.name;
					return false;
				}
			});
			return result;
		}
		function searchVersion() {
			var m = $('#ttt').datagrid('getSelected');
			if (m) {
				location.href='${ctx}/admin/app/version/?appId='+m.id;
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选泽应用记录。'
				});
			}
		}
		function checkEditControl(){
			$.getJSON('${ctx}/back/permission/app', function(data) {
				if(data.save==0&&data.del==0&&data.searchVersion==0&&data.addVersion){
					$('.datagrid-toolbar').hide();
				}
				$.each($('.datagrid-toolbar a'),function(i,m){
					if(data.save==0&&i<2){
						$(this).hide();
					}
					if(data.del==0&&i==2){
						$(this).hide();
					}
					if(data.searchVersion==0&&i==3){
						$(this).hide();
					}
					if(data.addVersion==0&&i==3){
						$(this).hide();
					}
				});
			});
		}
		</script>
		<style>
		#editWin label {width: 115px;}
		#editWin input {width: 150px;}
		#editWin textarea {width: 525px;height: 40px;}
		#editVersionWin textarea {width: 400px;height: 100px;}
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
						<form:label for="appName" path="appName">应用名称：</form:label>
					</td>
					<td>
						<form:input path="appName" cssClass="easyui-validatebox" />
					</td>
					<td>
						<form:label for="enName" path="enName">英文名称：</form:label>
					</td>
					<td>
						<form:input path="enName" cssClass="easyui-validatebox" />
					</td>
				</tr>
				<tr>
					<td>
						<form:label for="catalogId" path="catalogId">分类：</form:label>
					</td>
					<td>
						<form:input path="catalogId" cssClass="easyui-validatebox" />
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
			<table id="ttt" style="height: auto;" iconCls="icon-blank" title="应用列表" align="left" singleSelect="true" 
			idField="id" url="${ctx}/admin/app/application/query" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="downCount" sortOrder="desc">
				<thead>
					<tr>
						<th field="appName" width="100" align="center">应用名称</th>
						<th field="enName" width="100" align="center">英文名称</th>
						<th field="version" width="60" align="center">版本</th>
						<th field="icon" width="60" align="center" hidden="true">图标路径</th>
						<th field="catalogId" width="80" align="center" formatter="catalogFormatter">分类</th>
						<th field="appFilePath" width="100" align="center"  hidden="true">应用文件路径</th>
						<th field="platform" width="100" align="center">适用平台</th>
						<th field="supportLanguages" width="80" align="center" formatter="lanFormatter">支持语言</th>
						<th field="price" width="60" align="center">价格</th>
						<th field="appLevel" width="80" align="center">应用分级</th>
						<th field="description" width="80" align="center"  hidden="true">描述</th>
						<th field="enDescription" width="80" align="center"  hidden="true">描述</th>
						<th field="permissionDesc" width="80" hidden="true">权限描述</th>
						<th field="enPermissionDesc" width="80" hidden="true">权限描述</th>
						<th field="status" width="60" align="center" formatter="statusFormatter">状态</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="编辑应用" closed="true" style="width:740px;height:500px;padding:5px;" modal="true">
			<form:form modelAttribute="application" id="editForm" action="${ctx}/admin/app/application/save" method="post" cssStyle="padding:10px 20px;"  enctype="multipart/form-data">
				<table>
					<tr>
						<td><form:label	for="appName" path="appName"  cssClass="mustInput">应用名称：</form:label></td>
						<td><form:input path="appName" cssClass="easyui-validatebox"  required="true"/></td>
						<td><form:label	for="enName" path="enName"  cssClass="mustInput">英文名称：</form:label></td>
						<td><form:input path="enName" cssClass="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td><form:label	for="catalogId" path="catalogId">分类：</form:label></td>
						<td><form:input path="catalogId" id="catalogId_edit" cssClass="easyui-validatebox"/></td>
						<td><form:label	for="platform" path="platform">适用平台：</form:label></td>
						<td><form:input path="platform" cssClass="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td><form:label	for="icon" path="icon">图标：</form:label></td>
						<td><input type="file"  name="file"  id="file" cssClass="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td><form:label	for="supportLanguages" path="supportLanguages">支持语言：</form:label></td>
						<td>
							<form:select path="supportLanguages" id="supportLanguages_edit" cssClass="easyui-combobox">
							<form:option value="0">中文</form:option>
							<form:option value="1">英文</form:option>
							<form:option value="2">中英双语</form:option>
						</form:select></td>
						<td><form:label	for="appLevel" path="appLevel">应用分级：</form:label></td>
						<td><form:input path="appLevel" id="appLevel_edit" cssClass="easyui-numberspinner" value="1"  min="1" max="10" required="true"/></td>
					</tr>
					<tr>
						<td><form:label	for="price" path="price">价格：</form:label></td>
						<td><form:input path="price" cssClass="easyui-validatebox"/></td>
						<td><form:label	for="status" path="status" cssClass="easyui-validatebox">状态：</form:label></td>
						<td>
							<form:select path="status" id="status_edit" cssClass="easyui-combobox">
								<form:option value="1">启用</form:option>
								<form:option value="0">停用</form:option>
							</form:select>
						</td>
					</tr>
					<tr>
						<td><form:label for="permissionDesc" path="permissionDesc" cssClass="easyui-validatebox">权限描述</form:label></td>
						<td colspan="3">
							<div id="perTabs" class="easyui-tabs" style="width:540px;height:78px;">  
								<div title="中文" style="padding:3px;">  
									<form:textarea path="permissionDesc" />
								</div>  
								<div title="英文" style="overflow:auto;padding:3px;display:none;">  
									<form:textarea path="enPermissionDesc" />
								</div> 
							</div>  
						</td>
					</tr>
					<tr>
						<td><form:label for="description" path="description" cssClass="easyui-validatebox">描述：</form:label></td>
						<td colspan="3">
							<div id="descTabs" class="easyui-tabs" style="width:540px;height:78px;">  
								<div title="中文" style="padding:3px;">  
									<form:textarea path="description" />
								</div>  
								<div title="英文" style="overflow:auto;padding:3px;display:none;">  
									<form:textarea path="enDescription" />
								</div> 
							</div>  
						</td>
					</tr>
					<tr>
						<td rowspan="2"><label>截图</label></td><td><input type="file" name="image" /></td><td><input type="file" name="image" /></td><td><input type="file" name="image" /></td>
					</tr>
					<tr>
					<td><input type="file" name="image" /></td></td><td><input type="file" name="image" /></tr>
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
		
		<div id="editVersionWin" class="easyui-window" title="编辑应用版本" closed="true" style="width:575px;height:300px;padding:5px;" modal="true">
			<form id="editVersionForm" action="${ctx}/admin/app/version/save" method="post" cssStyle="padding:10px 20px;"  enctype="multipart/form-data">
				<table>
					<tr>
						<td><label for="filePath" class="mustInput">应用文件：</label></td>
						<td><input type="file"  name="file"  id="file" class="easyui-validatebox"/></td>
						<td><label	for="size" class="mustInput">文件夹大小：</label></td>
						<td>
						<input type="text" name="size" id="size" class="easyui-validatebox" required="true"/>
						</td>
				</tr>
				<tr>
						<td><label	for="version" class="mustInput">版本：</label></td>
						<td><input type="text" name="version" id="version" required="true" class="easyui-validatebox"/></td>
					<td><label	for="status" class="easyui-validatebox">状态：</label></td>
					<td>
						<select name="status" class="easyui-combobox">
							<option value="1">启用</option>
							<option value="0">停用</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><label for="newFeatures" class="easyui-validatebox">新特性：</label></td>
					<td colspan="3">
						<textarea name="newFeatures"></textarea>
					</td>
				</tr>
				</table>
				<input type="hidden" name="appId" id="appId"/>
				<div style="text-align: center; padding: 5px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_submit_version"
						iconCls="icon-save">保 存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_reset_version"
						iconCls="icon-undo">重 置</a>
				</div>
			</form>
	</body>
</html>
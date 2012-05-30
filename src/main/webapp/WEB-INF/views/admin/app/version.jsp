<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js" ></script>
		<script type="text/javascript">
		var apps=[];
		$(function(){
			checkEditControl('${ctx}/admin/app/version');
			$.ajaxSettings.async = false;
			$.getJSON('${ctx}/admin/app/application/all', function(data) {
				if(data){
					apps=data;
				}
			});
			$('#appId').combobox({  
			    data:apps,
			    valueField:'id',  
			    textField:'appName'  
			}); 
			$('#appId_edit').combobox({  
			    data:apps,
			    valueField:'id',  
			    textField:'appName'   
			});
			$('#descTabs').tabs({onSelect:function(title){
				$(this).tabs('getSelected').show()
			}});
		});
		function edit() {
			var m = $('#tt').datagrid('getSelected');
			if (m) {
				$('#editWin').window('open');
				// init data
				//$('#editForm input[id=appId]').attr('disabled',true);
				$('#appId_edit').combobox('setValue',m.appId);
				$('#editForm input[name=version]').val(m.version);
				$('#editForm input[name=size]').val(m.size);
				$('#status_edit').combobox('setValue',m.status);
				$('#newFeatures').val(m.newFeatures);
				$('#enNewFeatures').val(m.enNewFeatures);
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
							url : '${ctx}/admin/app/version/delete/'+ m.id,
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
		
		function appFormatter(v){
			var result='-';
			$.each(apps, function(key,val) {
				if(v==val.id){
					result=val.appName;
					return false;
				}
			});
			return result;
		}
		</script>
		<style>
		#editWin label {width: 115px;}
		#editWin input {width: 180px;}
		#editWin select {width: 185px;}
		#editWin textarea {width: 460px;height: 80px;}
		</style>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="appVersionFile"
			action="${ctx}/admin/app/version/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td>
						<form:label for="appId" path="appId">应用名称：</form:label>
					</td>
					<td>
						<form:input path="appId" cssClass="easyui-validatebox" />
					</td>
					<td>
						<form:label for="version" path="version">版本：</form:label>
					</td>
					<td>
						<form:input path="version" cssClass="easyui-validatebox" />
					</td>
				</tr>
				<tr>
					<td>
						<form:label for="size" path="size">文件大小：</form:label>
					</td>
					<td>
						<form:input path="size" cssClass="easyui-validatebox" />
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
			<table id="tt" style="height: auto;" iconCls="icon-blank" title="应用版本列表" align="left" singleSelect="true" 
			idField="id" url="${ctx}/admin/app/version/query?appId=${param.appId}" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="updateTime" sortOrder="desc">
				<thead>
					<tr>
						<th field="appId" width="100" align="center" formatter="appFormatter">应用名称</th>
						<th field="version" width="60" align="center">版本</th>
						<th field="filePath" width="100" align="center">文件路径</th>
						<th field="size" width="60" align="center">文件大小</th>
						<th field="newFeatures" width="150" hidden="true">新特效</th>
						<th field="status" width="60" align="center" formatter="statusFormatter">状态</th>
						<th field="updateTime" width="100" align="center">更新时间</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="编辑应用版本" closed="true" style="width:650px;height:350px;padding:5px;" modal="true">
			<form:form modelAttribute="appVersionFile" id="editForm" action="${ctx}/admin/app/version/save" method="post" cssStyle="padding:10px 20px;"  enctype="multipart/form-data">
				<table>
					<tr>
						<td><form:label	for="appId" path="appId"  cssClass="mustInput">应用名称：</form:label></td>
						<td>
						<form:input path="appId" id="appId_edit" cssClass="easyui-validatebox" />
						</td>
						<td><form:label	for="version" path="version"  cssClass="mustInput">版本：</form:label></td>
						<td><form:input path="version" required="true" cssClass="easyui-validatebox"/></td>
				</tr>
				<tr>
						<td><form:label	for="filePath" path="filePath"  cssClass="mustInput">应用文件：</form:label></td>
						<td><input type="file"  name="file"  id="file" cssClass="easyui-validatebox"/></td>
						<td><form:label	for="size" path="size"  cssClass="mustInput">文件夹大小：</form:label></td>
						<td>
						<form:input path="size" cssClass="easyui-validatebox" required="true"/>
						</td>
				</tr>
				<tr>
					<td><form:label	for="status" path="status" cssClass="easyui-validatebox">状态：</form:label></td>
					<td>
						<form:select path="status" id="status_edit" cssClass="easyui-combobox">
							<form:option value="1">启用</form:option>
							<form:option value="0">停用</form:option>
						</form:select>
					</td>
				</tr>
				<tr>
					<td><form:label for="newFeatures" path="newFeatures" cssClass="easyui-validatebox">新特效：</form:label></td>
					<td colspan="3">
						<div id="descTabs" class="easyui-tabs" style="width:470px;height:120px;">  
							<div title="中文" style="padding:3px;">  
								<form:textarea path="newFeatures" />
							</div>  
							<div title="英文" style="overflow:auto;padding:3px;display:none;">  
								<form:textarea path="enNewFeatures" />
							</div> 
						</div>  
					</td>
				</tr>
				</table>
				<form:hidden path="id"/>
				<div style="text-align: center; padding: 5px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_submit"
						iconCls="icon-save">保 存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_reset"
						iconCls="icon-undo">重 置</a>
				</div>
			</form:form>
		</div>
	</body>
</html>
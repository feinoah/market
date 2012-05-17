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
		var modules;
		$(function(){
			$.getJSON('${ctx}/back/module/query/all', function(data) {
				modules=data
			});
			checkEditControl('admin/permission/module');
		});
		function edit() {
			var m = $('#tt').datagrid('getSelected');
			if (m) {
				$('#editWin').window('open');
				// init data
				$('#editForm input[name=moduleName]').val(m.moduleName);
				$('#editForm input[name=moduleUrl]').val(m.moduleUrl);
				$('#editForm input[name=parentId]').val(m.parentId);
				$('#editForm input[name=level]').val(m.level);
				$('#editForm input[name=expanded]').val(m.expanded);
				$('#editForm input[name=display]').val(m.display==true?'1':'0');
				$('#editForm input[name=displayIndex]').val(m.displayIndex);
				$('#editForm input[name=information]').val(m.information);
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
				$.messager.confirm('警告','删除时将会删除角色模块关联信息，您确认要删除吗?',function(data) {
					if (data) {
						$.ajax({
							url : '${ctx}/admin/permission/module/delete/'+ m.id,
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
		function moudleFormatter(v){
			var result='未知模块';
			$.each(modules, function(key,val) {
				if(v==val.id){
					result=val.moduleName;
				}
			});
			return result;
		}
		function displayFormatter(v){
			if(v==1){return '显示'}
			return '隐藏';
		}
		</script>
		<style>
		#editWin label {width: 115px;}
		#editWin input {width: 180px;}
		#editWin select {width: 185px;}
		</style>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="baseModule"
			action="${ctx}/admin/permission/module/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td>
						<form:label for="moduleName" path="moduleName">名称：</form:label>
					</td>
					<td>
						<form:input path="moduleName" cssClass="easyui-validatebox" />
					</td>
					<td>
						<form:label for="moduleUrl" path="moduleUrl">路径：</form:label>
					</td>
					<td>
						<form:input path="moduleUrl" cssClass="easyui-validatebox" />
					</td>
				</tr>
				<tr>
					<td>
						<form:label for="parentId" path="parentId">父模块：</form:label>
					</td>
					<td>
						<form:select path="parentId" cssClass="easyui-combobox">
							<form:option value="">-请选择-</form:option>
							<form:options items="${allModule}"  itemValue="id"  itemLabel="moduleName" />
						</form:select>
					</td>
					<td>
						<form:label for="level" path="level">层级大于等于：</form:label>
					</td>
					<td>
						<form:input path="level"  cssClass="easyui-numberspinner"  value="0" min="0" max='10'/>
					</td>
					<td>
						<form:label for="display" path="display">是否显示：</form:label>
					</td>
					<td>
						<form:select path="display" cssClass="easyui-combobox">
							<form:option value="">-请选择-</form:option>
							<form:option value="0">-隐藏-</form:option>
							<form:option value="1">-显示-</form:option>
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
			<table id="tt" style="height: auto;" iconCls="icon-blank" title="模块列表" align="left" singleSelect="true" 
			idField="id" url="${ctx}/admin/permission/module/query" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="level" sortOrder="asc">
				<thead>
					<tr>
						<th field="moduleName" width="100" align="center">模块名称</th>
						<th field="moduleUrl" width="100" align="center">模块路径</th>
						<th field="parentId" width="60" align="center" formatter="moudleFormatter">父模块</th>
						<th field="level" width="20" align="center" >层级</th>
						<th field="displayIndex" width="20" align="center">顺序</th>
						<th field="display" width="60" align="center" formatter="displayFormatter">是否显示</th>
						<th field="information" width="160" align="center">描述</th>
						<th field="createTime" width="90" align="center">创建时间</th>
						<th field="updateTime" width="90" align="center">更新时间</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="编辑模块" closed="true" style="width:700px;height:380px;padding:5px;" modal="true">
			<form:form modelAttribute="baseModule" id="editForm" action="${ctx}/admin/permission/module/save" method="post" cssStyle="padding:10px 20px;">
				<table>
					<tr>
						<td><form:label	for="moduleName" path="moduleName"  cssClass="mustInput">名称：</form:label></td>
						<td><form:input path="moduleName" cssClass="easyui-validatebox" required="true" /></td>
						<td><form:label	for="moduleUrl" path="moduleUrl"  cssClass="mustInput">路径：</form:label></td>
						<td><form:input path="moduleUrl" cssClass="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td><form:label	for="parentId" path="parentId" >父模块：</form:label></td>
						<td>
						<form:select path="parentId" cssClass="easyui-combobox" required="true" >
							<form:option value="">-请选择-</form:option>
							<form:options items="${allModule}"  itemValue="id"  itemLabel="moduleName" />
						</form:select>
						</td>
						<td><form:label	for="level" path="level" cssClass="easyui-validatebox">层级：</form:label></td>
						<td>
							<form:input path="level" cssClass="easyui-numberspinner" value="1" min="1" max="10" required="true"/>
						</td>
					</tr>
					<tr>
						<td><form:label	for="expanded" path="expanded" cssClass="easyui-validatebox">展开：</form:label></td>
						<td>
							<form:select path="expanded" cssClass="easyui-combobox" required="true">
								<form:option value="0">-收起-</form:option>
								<form:option value="1">-展开-</form:option>
							</form:select>
						</td>
						<td><form:label for="display" path="display" cssClass="easyui-validatebox" required="true">是否隐藏：</form:label></td>
						<td>
							<form:select path="display" cssClass="easyui-combobox" required="true">
								<form:option value="0">-隐藏-</form:option>
								<form:option value="1">-显示-</form:option>
							</form:select>
						</td>
					</tr>
					<tr>
						<td><form:label for="displayIndex" path="displayIndex" cssClass="easyui-validatebox">顺序：</form:label></td>
						<td><form:input path="displayIndex" cssClass="easyui-numberspinner"  value="1"  min="1" max="1000" required="true" validType="number"/></td>
					</tr>
					<tr><td><form:label for="information" path="information" cssClass="easyui-validatebox">描述：</form:label></td></tr>
					<tr><td colspan="4"><form:textarea path="information" cssStyle="width:605px;height:100px;" /></td></tr>
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
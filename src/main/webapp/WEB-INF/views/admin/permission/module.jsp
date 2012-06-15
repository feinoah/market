<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js?v=1.2" ></script>
		<script type="text/javascript">
		var modules;
		$(function(){
			$.ajaxSettings.async = false;
			// 查询所有模块 page=1起始页，rows=10000总记录树，默认是10的
			$.getJSON('${ctx}/back/module/all?t='+(new Date().getTime()), function(data) {
				modules=data
			});
			$('#parentId').combobox({  
			    data:modules,
			    valueField:'id',  
			    textField:'moduleName'  
			}); 
			$('#parentId_edit').combobox({  
				data:modules,
			    valueField:'id',  
			    textField:'moduleName'  
			}); 
			checkEditControl('${ctx}/back/permission/account?baseUri=/admin/permission/module');
			checkExisted($('#nickName_edit'),"${ctx}/back/permission/module?name=");
			$('.combobox-f').each(function(){
				$(this).combobox('clear');
			});
		});
		function edit() {
			var m = $('#tt').datagrid('getSelected');
			if (m) {
				$('#editForm input').each(function(){
					$(this).removeClass('validatebox-invalid');
				});
				$('#editWin').window({title:'修改'+winTitle,iconCls:'icon-edit'});
				$('#editWin').window('open');
				// init data
				$('#editForm input[name=moduleName]').val(m.moduleName);
				$('#editForm input[name=moduleUrl]').val(m.moduleUrl);
				$('#parentId_edit').combobox('setValue',m.parentId);
				$('#level_edit').numberspinner('setValue',m.level);
				$('#expanded_edit').combobox('setValue',m.expanded);
				$('#display_edit').combobox('setValue',m.display);
				$('#editForm input[name=display]').val(m.display==true?'1':'0');
				$('#displayIndex_edit').numberspinner('setValue',m.displayIndex);
				$('#information').val(m.information);
				$('#id').val(m.id);
				$('#editWin').show();
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选择要修改的记录。'
				});
			}
		}

		function moudleFormatter(v){
			var result='-';
			$.each(modules, function(key,val) {
				if(v==val.id){
					result=val.moduleName;
					return false;
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
		#editWin label {width: 85px;}
		#editWin input {width: 150px;}
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
						<form:input path="parentId" cssClass="easyui-validatebox" />
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
		<div id="editWin" class="easyui-window" title="模块" closed="true" style="width:580px;height:380px;padding:5px;" modal="true">
			<form:form modelAttribute="baseModule" id="editForm" action="${ctx}/admin/permission/module/save" method="post" cssStyle="padding:10px 20px;">
				<table>
					<tr>
						<td><form:label	for="moduleName" path="moduleName"  cssClass="mustInput">名称：</form:label></td>
						<td><form:input path="moduleName" id="moduleName_edit" cssClass="easyui-validatebox" required="true"/></td>
						<td><form:label	for="moduleUrl" path="moduleUrl">路径：</form:label></td>
						<td><form:input path="moduleUrl"/></td>
					</tr>
					<tr>
						<td><form:label	for="parentId" path="parentId" >父模块：</form:label></td>
						<td>
						<form:input path="parentId" id="parentId_edit" cssClass="easyui-validatebox"/>
						</td>
						<td><form:label	for="level" path="level" cssClass="emustInput">层级：</form:label></td>
						<td>
							<form:input path="level" id="level_edit" cssClass="easyui-numberspinner" value="1" min="1" max="10" required="true"/>
						</td>
					</tr>
					<tr>
						<td><form:label	for="expanded" path="expanded" cssClass="mustInput">展开：</form:label></td>
						<td>
							<form:select path="expanded" id="expanded_edit" cssClass="easyui-combobox" required="true">
								<form:option value="0">-收起-</form:option>
								<form:option value="1">-展开-</form:option>
							</form:select>
						</td>
						<td><form:label for="display" path="display" cssClass="mustInput">是否隐藏：</form:label></td>
						<td>
							<form:select path="display" id="display_edit" cssClass="easyui-combobox" required="true">
								<form:option value="0">-隐藏-</form:option>
								<form:option value="1">-显示-</form:option>
							</form:select>
						</td>
					</tr>
					<tr>
						<td><form:label for="displayIndex" path="displayIndex" cssClass="easyui-validatebox">顺序：</form:label></td>
						<td><form:input path="displayIndex" id="displayIndex_edit" cssClass="easyui-numberspinner"  min="1" max="1000" required="true" validType="number"/></td>
					</tr>
					<tr><td><form:label for="information" path="information" cssClass="easyui-validatebox">描述：</form:label></td></tr>
					<tr><td colspan="4"><form:textarea path="information" cssClass="easyui-validatebox" cssStyle="width:480px;height:100px;" validType="maxLength[100]" maxLen="100"/></td></tr>
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
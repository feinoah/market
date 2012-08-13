<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js?v=1.0" ></script>
		<script type="text/javascript">
		$(function(){
			checkEditControl('${ctx}/back/permission/account?baseUri=/admin/permission/field');
			$('#field_edit').combobox({
				url:'${ctx}/back/query/groupby/field',
				method:'get',
				valueField:'field',
				textField:'field',
				onSelect:function(record){
					$('#fieldName_edit').val(record.field_name);
				}
			});
			$('#enabled').combobox({
				data:statusList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
			});
			$('#enabled_edit').combobox({
				data:statusList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
			});
			$('.combobox-f').each(function(){
				$(this).combobox('clear');
			});
		});
		function edit(index) {
			if(index>-1){//双击
				// clear selected
				$('#tt').datagrid('clearSelections');
				$('#tt').datagrid('selectRow',index); //让双击行选定
			}
			var m = $('#tt').datagrid('getSelected');
			if (m) {
				$('#editForm input').each(function(){
					$(this).removeClass('validatebox-invalid');
				});
				$('#editWin').window({title:'修改'+winTitle,iconCls:'icon-edit'});
				$('#editWin').window('open');
				// init data
				//$('#field_edit').val(m.field);
				$('#field_edit').combobox('setValue',m.field);
				$('#fieldName_edit').val(m.fieldName);
				$('#fieldValue_edit').val(m.fieldValue);
				$('#displayValue_edit').val(m.level);
				$('#enabled_edit').combobox('setValue',m.enabled);
				$('#sort_edit').numberspinner('setValue',m.sort);
				$('#id').val(m.id);
				$('#editWin').show();
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选择要修改的记录。'
				});
			}
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
		<form:form modelAttribute="baseField"
			action="${ctx}/admin/permission/field/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td>
						<form:label for="field" path="field">字段名称：</form:label>
					</td>
					<td>
						<form:input path="field"/>
					</td>
					<td>
						<form:label for="fieldName" path="fieldName">字段描述：</form:label>
					</td>
					<td>
						<form:input path="fieldName"/>
					</td>
					<td>
						<form:label for="enabled" path="enabled">是否启用：</form:label>
					</td>
					<td>
						<form:input path="enabled" />
					</td>
				</tr>
				<tr>
					<td>
						<form:label for="fieldValue" path="fieldValue">字段值：</form:label>
					</td>
					<td>
						<form:input path="fieldValue" />
					</td>
					<td>
						<form:label for="displayValue" path="displayValue">显示值：</form:label>
					</td>
					<td>
						<form:input path="displayValue"/>
					</td>
					<td>
						<form:label for="sort" path="sort">显示顺序：</form:label>
					</td>
					<td>
						<form:input path="sort"  cssClass="easyui-numberspinner" value="0" min="1" max='100000'/>
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
			<table id="tt" style="height: auto;" iconCls="icon-blank" title="系统字典列表" align="left"  
			idField="id" url="${ctx}/admin/permission/field/query" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="sort" sortOrder="asc">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th field="field" width="100" align="center">字段名称</th>
						<th field="fieldName" width="100" align="center">字段描述</th>
						<th field="fieldValue" width="40" align="center" >字段值</th>
						<th field="displayValue" width="80" align="center">显示值</th>
						<th field="enabled" width="60" align="center" formatter="statusFormatter">是否启用</th>
						<th field="sort" width="60" align="center">顺序</th>
						<th field="createTime" width="90" align="center">创建时间</th>
						<th field="updateTime" width="90" align="center">更新时间</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="系统字典" closed="true" style="width:380px;height:380px;padding:5px;" modal="true">
			<form:form modelAttribute="baseField" id="editForm" action="${ctx}/admin/permission/field/save" method="post" cssStyle="padding:10px 20px;">
				<table>
					<tr>
						<td><form:label	for="field" path="field"  cssClass="mustInput">字段名：</form:label></td>
						<td><form:input path="field" id="field_edit" required="true" validType="maxLength[20]" maxLen="20" msg="字段名"/></td>
					</tr>
					<tr>
						<td><form:label	for="fieldName" path="fieldName">字段描述：</form:label></td>
						<td><form:input path="fieldName" id="fieldName_edit" cssClass="easyui-validatebox" required="true" validType="maxLength[20]" maxLen="20" msg="字段描述"/></td>
					</tr>
					<tr>
						<td><form:label	for="fieldValue" path="fieldValue" >字段值：</form:label></td>
						<td>
						<form:input path="fieldValue" id="fieldValue_edit" cssClass="easyui-validatebox" required="true" validType="number"/>
						</td>
					</tr>
					<tr>
						<td><form:label	for="displayValue" path="displayValue" cssClass="emustInput">显示值：</form:label></td>
						<td>
							<form:input path="displayValue" id="displayValue_edit" cssClass="easyui-validatebox" required="true" validType="maxLength[20]" maxLen="20" msg="显示值"/>
						</td>
					</tr>
					<tr>
						<td><form:label	for="enabled" path="enabled" cssClass="mustInput">是否启用：</form:label></td>
						<td>
							<form:input path="enabled" id="enabled_edit" required="true"/>
						</td>
					</tr>
					<tr>
						<td><form:label for="sort" path="sort" cssClass="mustInput">顺序：</form:label></td>
						<td><form:input path="sort" id="sort_edit" cssClass="easyui-numberspinner" min="1" max="100000" required="true"/></td>
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
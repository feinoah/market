<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js?v1.2" ></script>
		<script type="text/javascript">
		$(function(){
			checkEditControl('${ctx}/back/permission/account?baseUri=/admin/app/developer');
			$('#status').combobox({
				data:statusList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
			});
			$('#status_edit').combobox({
				data:statusList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
			});
			checkExisted($('#name_edit'),'${ctx}/portal/check/developer?local=cn&name=');
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
				$('#name_edit').val(m.name);
				$('#email_edit').val(m.email);
				$('#website_edit').val(m.website);
				$('#status_edit').combobox('setValue',m.status);
				$('#remark').val(m.remark);
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
		#editeWin label{width:120px;}
		#editWin textarea {width: 300px;height: 80px;}
		</style>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="appDeveloper"
			action="${ctx}/admin/app/developer/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td>
						<form:label for="name" path="name">开发者名称：</form:label>
					</td>
					<td>
						<form:input path="name"/>
					</td>
					<td>
						<form:label for="email" path="email">联系邮箱：</form:label>
					</td>
					<td>
						<form:input path="email"/>
					</td>
				</tr>
				<tr>
					<td>
						<form:label for="website" path="website">开发者网站：</form:label>
					</td>
					<td>
						<form:input path="website"/>
					</td>
					<td>
						<form:label for="status" path="status">状态：</form:label>
					</td>
					<td>
						<form:input path="status" />
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
			<table id="tt" style="height: auto;" iconCls="icon-blank" title="应用分类列表" align="left"   
			idField="id" url="${ctx}/admin/app/developer/query" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="updateTime" sortOrder="desc">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th field="name" width="150" align="center">开发者名称</th>
						<th field="website" width="150" align="center">开发者网站</th>
						<th field="email" width="200" align="center">联系邮箱</th>
						<th field="remark" width="200" align="center">备注</th>
						<th field="status" width="60" align="center" formatter="statusFormatter">状态</th>
						<th field="createTime" width="100" align="center">创建时间</th>
						<th field="updateTime" width="100" align="center">更新时间</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="开发者" closed="true" style="width:550px;height:350px;padding:5px;" modal="true">
			<form:form modelAttribute="appDeveloper" id="editForm" action="${ctx}/admin/app/developer/save" method="post" cssStyle="padding:10px 20px;">
				<table>
					<tr>
						<td><form:label	for="name" path="name"  cssClass="mustInput">开发者名称：</form:label></td>
						<td><form:input path="name" id="name_edit" cssClass="easyui-validatebox" required="true" validType="maxLength[100]" maxLen="100" msg="名称" cssStyle="width:300px;"/></td>
					</tr>
					<tr>
						<td><form:label	for="status" path="status" cssClass="mustInput">状态：</form:label></td>
						<td>
							<form:input path="status" id="status_edit" required="true" cssStyle="width:300px;"/>
						</td>
					</tr>
					<tr>
						<td><form:label for="email" path="email">联系邮箱：</form:label></td>
						<td><form:input path="email" id="email_edit" cssClass="easyui-validatebox" required="true" validType="email" cssStyle="width:300px;"/></td>
					</tr>
					<tr>
						<td><form:label	for="website" path="website">开发者网站：</form:label></td>
						<td><form:input path="website" id="website_edit" cssClass="easyui-validatebox" validType="url" cssStyle="width:300px;"/></td>
					</tr>
					<tr>
						<td><form:label	for="remark" path="remark" cssClass="easyui-validatebox">备注：</form:label></td>
						<td>
							<form:textarea path="remark"  class="easyui-validatebox" validType="maxLength[200]" maxLen="200" msg="备注"/>
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
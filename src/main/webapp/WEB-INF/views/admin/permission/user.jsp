<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="${ctx}"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CarIt Market</title>
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js" ></script>
		<script type="text/javascript">
		$(function(){
			$('#roles').combobox({
				method:'get',
				url:'${ctx}/back/role/query/all',
				valueField:'id',
				textField:'roleName'
			});
			checkEditControl('admin/permission/user');
		});
		function edit() {
			var m = $('#tt').datagrid('getSelected');
			if (m) {
				$('#editWin').window('open');
				// init data
				$('#editForm input[name=email]').val(m.email);
				$('#editForm input[name=nickName]').val(m.nickName);
				$('#editForm input[name=realName]').val(m.realName);
				$('#editForm input[name=officePhone]').val(m.officePhone);
				$('#editForm input[name=mobile]').val(m.mobile);
				$('#editForm input[name=status]').val(m.status);
				$('#editForm input[name=gender]').val(m.gender);
				$('#remark').val(m.remark);
				$('#password').attr('disabled',true);//密码不能在这里修改
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
			var user = $('#tt').datagrid('getSelected');
			if (user) {
				$.messager.confirm('提示信息','您确认要删除吗?',function(data) {
					if (data) {
						$.ajax({
							url : '${ctx}/admin/permission/user/delete/'+ user.id,
							type : 'GET',
							timeout : 1000,
							error : function() {
								$.messager.alert('错误','删除失败!','error');
							},
							success : function(data) {
								if(data==-2){
									$.messager.alert('错误','不能删除当前登录用户!','error');
								}else if (data == -1) {
									$.messager.alert('错误','非法参数!','error');
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
		<style>
		#editWin label {width: 115px;}
		#editWin input {width: 180px;}
		#editWin select {width: 185px;}
		</style>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="baseUser"
			action="${ctx}/admin/permission/user/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td>
						<form:label for="email" path="email">邮箱：</form:label>
					</td>
					<td>
						<form:input path="email" cssClass="easyui-validatebox" />
					</td>
					<td>
						<form:label for="nickName" path="nickName">昵称：</form:label>
					</td>
					<td>
						<form:input path="nickName" cssClass="easyui-validatebox" />
					</td>
					<td>
						<form:label for="realName" path="realName">真实姓名：</form:label>
					</td>
					<td>
						<form:input path="realName" cssClass="easyui-validatebox" />
					</td>
				</tr>
				<tr>
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
					<td>
						<form:label for="gender" path="gender">性别：</form:label>
					</td>
					<td>
						<form:select path="gender" cssClass="easyui-combobox">
							<form:option value="">请选择</form:option>
							<form:option value="0">女</form:option>
							<form:option value="1">男</form:option>
							<form:option value="2">保密</form:option>
						</form:select>
					</td>
					<td>
						<form:label for="mobile" path="mobile">手机号码：</form:label>
					</td>
					<td>
						<form:input path="gender" cssClass="easyui-combobox"/>
					</td>
				</tr>
			</table>
		</form:form>
		<div style="text-align: center; padding: 5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" id="submit"
					iconCls="icon-search">查 询</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" id="reset"
					iconCls="icon-undo">重 置</a>
			</div>
			<table id="tt" style="height: auto" iconCls="icon-blank" title="用户列表" singleSelect="true" nowrap="false"
			idField="id" url="${ctx}/admin/permission/user/query" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10, 30 ]" sortName="updateTime" sortOrder="desc">
				<thead>
					<tr>
						<th field="email" width="150" align="center">邮箱</th>
						<th field="nickName" width="100" align="center">昵称</th>
						<th field="realName" width="100" align="center">真实姓名</th>
						<th field="gender" width="150" align="center" formatter="genderFormatter">性别</th>
						<th field="mobile" width="100" align="center">手机</th>
						<th field="officePhone" width="100" align="center">办公电话</th>
						<th field="lastLoginTime" width="100" align="center">最后登录时间</th>
						<th field="lastLoginIp" width="100" align="center">最后登录地址</th>
						<th field="remark" width="60" align="remark">备注</th>
						<th field="status" width="60" align="center" formatter="statusFormatter">状态</th>
						<!-- 
						<th field="createTime" width="80" align="center">创建时间</th>
						 -->
						<th field="updateTime" width="80" align="center">更新时间</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="编辑用户" closed="true" style="width:650px;height:400px;padding:5px;" modal="true">
			<form:form modelAttribute="baseUser" id="editForm" action="${ctx}/admin/permission/user/save" method="post" cssStyle="padding:10px 20px;">
				<table>
					<tr>
						<td><form:label	for="email" path="email"  cssClass="mustInput">邮箱：</form:label></td>
						<td><form:input path="email" cssClass="easyui-validatebox" required="true" validType="email"/></td>
						<td><form:label	for="nickName" path="nickName"  cssClass="mustInput">昵称：</form:label></td>
						<td><form:input path="nickName" required="true" cssClass="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td><form:label	for="realName" path="realName">真实姓名：</form:label></td>
						<td><form:input path="realName" cssClass="easyui-validatebox"/></td>
						<td><form:label	for="password" path="password"  cssClass="mustInput">密码：</form:label></td>
						<td><form:password path="password" cssClass="easyui-validatebox" required="true"  validType="safepass"/></td>
					</tr>
					<tr>
						<td><form:label	for="mobile" path="mobile">手机号码：</form:label></td>
						<td><form:input path="mobile" cssClass="easyui-validatebox" validType="mobile"/></td>
						<td><form:label	for="officePhone" path="officePhone">办公电话：</form:label></td>
						<td><form:input path="officePhone" cssClass="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td><form:label	for="status" path="status" cssClass="easyui-validatebox">状态：</form:label></td>
						<td>
							<form:select path="status" cssClass="easyui-combobox">
								<form:option value="1">启用</form:option>
								<form:option value="0">停用</form:option>
							</form:select>
						</td>
						<td><form:label	for="gender" path="gender" cssClass="easyui-validatebox">性别：</form:label></td>
						<td>
							<form:select path="gender" cssClass="easyui-combobox">
							<form:option value="2">保密</form:option>
							<form:option value="0">女</form:option>
							<form:option value="1">男</form:option>
						</form:select>
						</td>
					</tr>
					<tr>
						<td><form:label for="roles" path="roles" cssClass="easyui-validatebox">角色：</form:label></td>
						<td>
							<form:input path="roles" lass="easyui-combobox" multiple="true" panelHeight="auto"/>
						</td>
					</tr>
					<tr><td><form:label for="remark" path="remark" cssClass="easyui-validatebox">备注：</form:label></td></tr>
					<tr>
						<td colspan="4"><form:textarea path="remark" cssStyle="width:580px;height:80px;" /></td>
					</tr>
				</table>
				<form:hidden path="id"/>
				<div style="text-align: center; padding: 5px;">
					<a href="javascript:void(0);" class="easyui-linkbutton" id="edit_submit"
						iconCls="icon-save">保 存</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" id="edit_reset"
						iconCls="icon-undo">重 置</a>
				</div>
			</form:form>
		</div>
	</body>
</html>
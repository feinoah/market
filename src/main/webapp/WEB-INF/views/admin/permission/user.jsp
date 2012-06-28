<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CarIt Market</title>
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js?v=1.4" ></script>
		<script type="text/javascript">
		$(function(){
			$('#roles').combobox({
				method:'get',
				url:'${ctx}/back/role/query/all?t='+(new Date().getTime()),
				multiple:true,
				panelHeight:'auto',
				valueField:'id',
				textField:'roleName'
			});
			checkEditControl('${ctx}/back/permission/account?baseUri=/admin/permission/user');
			$('#editWin').window({onClose:function(){
				$('#password').attr('disabled',false);
				$('#email_edit').attr('disabled',false);
			}})
			$.ajaxSettings.async = false;
			$.getJSON('${ctx}/back/field/query/gender', function(data) {
				if(data){
					fieldList=data;
				}
			});
			$.ajaxSettings.async = true;
			$('#gender').combobox({
				data:fieldList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
			});
			$('#gender_edit').combobox({
				data:fieldList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
			});
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
			checkExisted($('#email_edit'),"${ctx}/back/permission/user?name=");
			checkExisted($('#nickName_edit'),"${ctx}/back/permission/user?nickName=");
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
				var r=[];
				$.ajaxSettings.async = false;
				$.getJSON('${ctx}/back/role/query/user/'+m.id, function(data) {
					$.each(data, function(i,d){
						r[i]=d.id;
					});
				});
				$('#roles').combobox('setValues',r);
				$('#editForm input[name=email]').val(m.email);
				$('#editForm input[name=nickName]').val(m.nickName);
				$('#editForm input[name=realName]').val(m.realName);
				$('#editForm input[name=officePhone]').val(m.officePhone);
				if(m.mobile!=0){
					$('#editForm input[name=mobile]').val(m.mobile);			
				}
				$('#status_edit').combobox('setValue',m.status);
				$('#gender_edit').combobox('setValue',m.gender);
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
		#editWin label {width: 115px;}
		#editWin input {width: 180px;}
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
						<form:label for="email" path="email" >邮箱：</form:label>
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
						<form:input path="status" cssClass="easyui-validatebox"/>
					</td>
					<td>
						<form:label for="gender" path="gender">性别：</form:label>
					</td>
					<td>
						<form:input path="gender" cssClass="easyui-validatebox"/>
					</td>
					<td>
						<form:label for="mobile" path="mobile">手机号码：</form:label>
					</td>
					<td>
						<form:input path="mobile" cssClass="easyui-validatebox"/>
					</td>
				</tr>
			</table>
			<div style="text-align: center; padding: 5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" id="submit"
					iconCls="icon-search">查 询</a>
				<a href="javascript:void(0);"
					class="easyui-linkbutton" id="reset" iconCls="icon-undo">重 置</a>
			</div>
		</form:form>
		<table id="tt" style="height: auto" iconCls="icon-blank" title="用户列表" singleSelect="true" nowrap="false"
			idField="id" url="${ctx}/admin/permission/user/query" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10, 30 ]" sortName="updateTime" sortOrder="desc">
				<thead>
					<tr>
						<th field="email" width="150" align="center">邮箱</th>
						<th field="nickName" width="100" align="center">昵称</th>
						<th field="realName" width="100" align="center">真实姓名</th>
						<th field="gender" width="150" align="center" formatter="fieldFormatter">性别</th>
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
		<div id="editWin" class="easyui-window" title="用户" closed="true" style="width:625px;height:400px;padding:5px;" modal="true">
			<form:form modelAttribute="baseUser" id="editForm" action="${ctx}/admin/permission/user/save" method="post" cssStyle="padding:10px 20px;">
				<table>
					<tr>
						<td><form:label	for="email" path="email" id="emailLabel" cssClass="mustInput">邮箱：</form:label></td>
						<td><form:input path="email" id="email_edit" cssClass="easyui-validatebox" required="true" validType="email"/></td>
						<td><form:label	for="nickName" path="nickName" id="nickNameLabel" cssClass="mustInput">昵称：</form:label></td>
						<td><form:input path="nickName" id="nickName_edit" cssClass="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td><form:label	for="realName" path="realName">真实姓名：</form:label></td>
						<td><form:input path="realName" cssClass="easyui-validatebox"/></td>
						<td><form:label	for="password" path="password" id="passwordLabel" cssClass="mustInput">密码：</form:label></td>
						<td><form:password path="password" cssClass="easyui-validatebox" validType="safepass"/></td>
					</tr>
					<tr>
						<td><form:label	for="status" path="status" cssClass="mustInput">状态：</form:label></td>
						<td>
							<form:input path="status" id="status_edit" required="true"/>
						</td>
						<td><form:label	for="gender" path="gender" cssClass="mustInput">性别：</form:label></td>
						<td>
							<form:input path="gender" id="gender_edit" required="true"  cssStyle="width:180px;" />
						</td>
					</tr>
					<tr>
						<td><form:label	for="mobile" path="mobile">手机号码：</form:label></td>
						<td><form:input path="mobile" cssClass="easyui-validatebox" validType="mobile"/></td>
						<td><form:label	for="officePhone" path="officePhone">办公电话：</form:label></td>
						<td><form:input path="officePhone" cssClass="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td><form:label for="roles" path="roles" cssClass="easyui-validatebox">角色：</form:label></td>
						<td colspan="3">
							<form:input path="roles" cssStyle="width:450px;"/>
						</td>
					</tr>
					<tr><td><form:label for="remark" path="remark" cssClass="easyui-validatebox">备注：</form:label></td></tr>
					<tr>
						<td colspan="4"><form:textarea path="remark" cssClass="easyui-validatebox" cssStyle="width:535px;height:80px;" validType="maxLength[50]" maxLen="50"/></td>
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
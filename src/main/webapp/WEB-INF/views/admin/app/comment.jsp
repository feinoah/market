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
		var apps;
		var users;
		$(function(){
			$('#appId').combobox({  
			    url:'${ctx}/portal/app/all',
			    method:'get',
			    valueField:'id',  
			    textField:'displayName'  
			}); 
			$('#editForm input[name=appId]').combobox({  
				url:'${ctx}/portal/app/all',
			    method:'get',
			    valueField:'id',  
			    textField:'displayName'  
			});
			$('#userId').combobox({  
			    url:'${ctx}/portal/account/all',
			    method:'get',
			    valueField:'id',  
			    textField:'nickName'  
			}); 
			$('#editForm input[name=userId]').combobox({  
				url:'${ctx}/portal/account/all',
			    method:'get',
			    valueField:'id',  
			    textField:'nickName'  
			});
			checkEditControl('${ctx}/admin/app/comment');
			$.getJSON('${ctx}/admin/app/application/query?page=1&rows=100000', function(data) {
				if(data){
					apps=data.rows;
				}
			});
			$.getJSON('${ctx}/admin/app/account/query?page=1&rows=100000', function(data) {
				if(data){
					users=data.rows;
				}
			});
			$('.datagrid-toolbar a:first').hide();//没有新增
		});
		function edit() {
			var m = $('#tt').datagrid('getSelected');
			if (m) {
				$('#editWin').window('open');
				$('#editForm input[name=appId]').val(m.appId);
				$('#editForm input[name=appId]').attr('disabled',true);
				$('#editForm input[name=userId]').val(m.userId);
				$('#editForm input[name=userId]').attr('disabled',true);
				$('#editForm input[name=grade]').val(m.grade);
				$('#grade').numberspinner('setValue',m.grade);
				$('#editForm input[name=status]').val(m.status);
				$('#editForm textarea').val(m.comment);
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
							url : '${ctx}/admin/app/comment/delete/'+ m.id,
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
					result=val.displayName;
					return false;
				}
			});
			return result;
		}
		function usrFormatter(v){
			var result='-';
			$.each(users, function(key,val) {
				if(v==val.id){
					result=val.email;
					if(val.nickName){
						result=val.nickName;
					}
					return false;//跳出
				}
			});
			return result;
		}
		function gradeFormatter(v){
			var result='';
			for(var i=1;i<=v;i++){
				result+='★';
			}
			if(v<5){
				v=5-v;
				for(var i=1;i<=v;i++){
					result+='☆';
				}
			}
			return result;
		}
		</script>
		<style>
		#editWin label {width: 115px;}
		#editWin input {width: 180px;}
		#editWin select {width: 185px;}
		#editWin textarea {width: 450px;height: 80px;}
		</style>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="appComment"
			action="${ctx}/admin/app/comment/query"
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
						<form:label for="userId" path="userId">评论人：</form:label>
					</td>
					<td>
						<form:input path="userId" cssClass="easyui-validatebox" />
					</td>
				</tr>
				<tr>
					<td>
						<form:label for="grade" path="grade">等级：</form:label>
					</td>
					<td>
						<form:input path="grade" cssClass="easyui-numberspinner" value="1" min="1" max="5"/>
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
				<tr>
					<td><form:label for="comment" path="comment">内容：</form:label></td>
					<td><form:input path="comment" cssClass="easyui-validatebox"/></td>
				</tr>
			</table>
		</form:form>
		<div style="text-align: center; padding: 5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" id="submit"
					iconCls="icon-search">查 询</a>
				<a href="javascript:void();" class="easyui-linkbutton" id="reset"
					iconCls="icon-undo">重 置</a>
			</div>
			<table id="tt" style="height: auto;" iconCls="icon-blank" title="评论列表" align="left" singleSelect="true" 
			idField="id" url="${ctx}/admin/app/comment/query?appId=${param.appId}" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="updateTime" sortOrder="desc">
				<thead>
					<tr>
						<th field="appId" width="100" align="center" formatter="appFormatter">应用名称</th>
						<th field="userId" width="100" align="center" formatter="usrFormatter">评论人</th>
						<th field="grade" width="60" align="center" formatter="gradeFormatter">等级</th>
						<th field="comment" width="200" align="center">内容</th>
						<th field="status" width="60" align="center" formatter="statusFormatter">状态</th>
						<th field="updateTime" width="80" align="center">更新时间</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="编辑评论" closed="true" style="width:650px;height:300px;padding:5px;" modal="true">
			<form:form modelAttribute="appComment" id="editForm" action="${ctx}/admin/app/comment/save" method="post" cssStyle="padding:10px 20px;"  enctype="multipart/form-data">
				<table>
					<tr>
					<td>
						<form:label for="appId" path="appId">应用名称：</form:label>
					</td>
					<td>
						<form:input path="appId" cssClass="easyui-validatebox" />
					</td>
					<td>
						<form:label for="userId" path="userId">评论人：</form:label>
					</td>
					<td>
						<form:input path="userId" cssClass="easyui-validatebox" />
					</td>
				</tr>
				<tr>
					<td>
						<form:label for="grade" path="grade">等级：</form:label>
					</td>
					<td>
						<form:input path="grade" cssClass="easyui-numberspinner" value="1" min="1" max="5"/>
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
				<tr>
					<td><form:label for="comment" path="comment" cssClass="easyui-validatebox">内容：</form:label></td>
					<td colspan="3">
						<form:textarea path="comment" />
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
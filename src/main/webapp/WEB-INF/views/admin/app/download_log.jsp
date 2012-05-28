<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript">
		var apps;
		var users;
		$(function(){
			// 初始化
			$('#tt').datagrid({
				width:'100%'
			});
			$.ajaxSettings.async = false;
			$.getJSON('${ctx}/portal/app/all', function(data) {
				if(data){
					apps=data;
				}
			});
			$.getJSON('${ctx}/portal/account/all', function(data) {
				if(data){
					users=data;
				}
			});
			$('.datagrid-toolbar').hide();//没有编辑
			$('#appId').combobox({
				data:apps,
			    valueField:'id',  
			    textField:'appName'  
			}); 
			$('#accountId').combobox({
				data:users,
			    valueField:'id',  
			    textField:'nickName'  
			}); 
		});

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
		</script>
		<style>
		#editWin label {width: 115px;}
		#editWin input {width: 180px;}
		#editWin select {width: 185px;}
		</style>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="appDownloadLog"
			action="${ctx}/admin/app/appDownloadLog/query"
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
						<form:label for="accountId" path="accountId">下载帐号：</form:label>
					</td>
					<td>
						<form:input path="accountId" cssClass="easyui-validatebox" />
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
			<table id="tt" style="height: auto;" iconCls="icon-blank" title="评论列表" align="left" singleSelect="true" 
			idField="id" url="${ctx}/admin/app/appDownloadLog/query?appId=${param.appId}" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="updateTime" sortOrder="desc">
				<thead>
					<tr>
						<th field="appId" width="100" align="center" formatter="appFormatter">应用名称</th>
						<th field="accountId" width="100" align="center" formatter="usrFormatter">下载帐号</th>
						<th field="downloadTime" width="80" align="center">下载时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
</html>
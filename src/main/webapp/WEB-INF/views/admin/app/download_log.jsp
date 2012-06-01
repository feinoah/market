<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript">
		$(function(){
			// 初始化
			$('#tt').datagrid({width:'100%',method:'get'});
			$('.datagrid-toolbar').hide();//没有编辑
		});
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
			action="${ctx}/admin/app/download/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td><form:label for="appName" path="appName">应用名称：</form:label></td>
					<td><form:input path="appName"/></td>
					<td><form:label for="enName" path="enName">英文名称：</form:label></td>
					<td><form:input path="enName"/></td>
					<td><form:label for="userName" path="userName">下载人：</form:label></td>
					<td><form:input path="userName" /></td>
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
			idField="id" url="${ctx}/admin/app/download/query?appId=${param.appId}" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="downloadTime" sortOrder="desc">
				<thead>
					<tr>
						<th field="appName" width="150" align="center">应用名称</th>
						<th field="enName" width="150" align="center">英文名称</th>
						<th field="userName" width="100" align="center">下载人</th>
						<th field="downloadTime" width="100" align="center">下载时间</th>
						<th field="appId" width="1" align="center" hidden="true"/>
						<th field="accountId" width="1" align="center" hidden="true"/>
					</tr>
				</thead>
			</table>
		</div>
	</body>
</html>
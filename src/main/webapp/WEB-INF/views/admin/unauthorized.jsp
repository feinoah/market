<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<c:out value="${ctx}"/>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CarIt Market</title>
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript">
		$(function (){
			$.messager.alert('错误', "没权限访问！", 'error');
		});
		</script>
	</head>
	<body>
	</body>
</html>
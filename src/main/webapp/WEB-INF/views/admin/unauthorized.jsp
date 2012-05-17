<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
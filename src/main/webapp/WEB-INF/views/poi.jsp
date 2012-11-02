<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
<script type="text/javascript">
$(function (){
	$('#nav-right').show();
});
</script>
</head>
<body>
    <%@ include file="/WEB-INF/views/commons/header.jsp"%>
	<div id="main" class="ym-grid">
		<jsp:include page="/WEB-INF/views/commons/left-sidebar.jsp" flush="true">
    		<jsp:param value="3" name="index"/>
    	</jsp:include>
		<div class="ym-g85 ym-gl main-container">
			<div id="poi-info">
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
</body>
</html>
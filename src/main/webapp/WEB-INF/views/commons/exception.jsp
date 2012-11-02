<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
</head>
<body>
	<div id="main" class="ym-wrapper">
		<div class="ym-grid">
			<spring:message code="Sidebar.user.info"/>
			<nav id="nav">
				<ul class="ym-gl" id="nav-left">
					<li><a href="#" class="active"><spring:message code="Menu.user.center"/></a></li>
					<li><a href="#"><spring:message code="Menu.app.market"/></a></li>
				</ul>
			</nav>
		</div>
	</div>
	
</body>
</html>
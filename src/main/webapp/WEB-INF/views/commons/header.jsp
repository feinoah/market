<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<header class="ym-grid">
	<div class="ym-g20 ym-gl" id="logo">
		<img alt="" src="/resources/public/images/logo.png">
	</div>
	<div class="ym-g80 ym-gl">
		<nav id="nav">
			<ul class="ym-gbox ym-gl" id="nva-top">
				<li><a href="/profile?hl=zh_CN"><img src="/resources/public/images/zh-cn.gif" alt="<spring:message code="global.language.zh_CN"/>" title="<spring:message code="global.language.zh_CN"/>"></a></li>
				<li><a href="/profile?hl=en"><img src="/resources/public/images/us.gif" alt="<spring:message code="global.language.en"/>" title="<spring:message code="global.language.en"/>"></a></li>
			</ul>
			<ul class="ym-g80 ym-gl" id="nav-left">
				<li><a href="#" class="active"><spring:message code="Menu.user.center"/></a></li>
				<li><a href="<spring:message code="global.homepage"/>"><spring:message code="Menu.app.market"/></a></li>
			</ul>
			<ul class="ym-g20 ym-gl" id="nav-right">
				<li><a href="#" id="logout"><spring:message code="title.logout"/></a></li>
			</ul>
		</nav>
	</div>
</header>
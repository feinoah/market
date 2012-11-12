<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div class="ym-g20 ym-gl left-container">
	<div id="sidebar">
		<dl>
			<dt <c:if test="${param.index eq 1}">class="select"><span id="person-info-sidebar"><spring:message code="Sidebar.user.info"/></span></c:if><c:if test="${param.index!=1}">><a href="${ctx}/profile?hl=${param.hl}" id="person-info-sidebar"><spring:message code="Sidebar.user.info"/></a></c:if></dt>
			<dt <c:if test="${param.index eq 2}">class="select"><span id="obd-sidebar"><spring:message code="Sidebar.obd.info"/></span></c:if><c:if test="${param.index!=2}">><a href="${ctx}/obd?hl=${param.hl}" id="obd-sidebar"><spring:message code="Sidebar.obd.info"/></a></c:if></dt>
			<dt <c:if test="${param.index eq 3}">class="select"><span id="poi-sidebar"><spring:message code="Sidebar.poi.info"/></span></c:if><c:if test="${param.index!=3}">><a href="${ctx}/poi?hl=${param.hl}" id="poi-sidebar"><spring:message code="Sidebar.poi.info"/></a></c:if></dt>
			<dt <c:if test="${param.index eq 4}">class="select"><span id="location-sidebar"><spring:message code="Sidebar.location.info"/></span></c:if><c:if test="${param.index!=4}">><a href="${ctx}/location?hl=${param.hl}" id="location-sidebar"><spring:message code="Sidebar.location.info"/></a></c:if></dt>
		</dl>
	</div>
</div>
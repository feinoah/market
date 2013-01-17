<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div class="ym-g20 ym-gl left-container">
	<div id="sidebar">
		<dl>
			<dt <c:if test="${param.index eq 1}">class="select"><span id="person-info-sidebar"><spring:message code="Sidebar.partner.info"/></span></c:if><c:if test="${param.index!=1}">><a href="${ctx}/partner/index?hl=${param.hl}" id="person-info-sidebar"><spring:message code="Sidebar.partner.info"/></a></c:if></dt>
			<dt <c:if test="${param.index eq 2}">class="select"><span id="obd-sidebar"><spring:message code="Sidebar.partner.devices"/></span></c:if><c:if test="${param.index!=2}">><a href="${ctx}/partner/device_list?hl=${param.hl}" id="obd-sidebar"><spring:message code="Sidebar.partner.devices"/></a></c:if></dt>
		</dl>
	</div>
</div>
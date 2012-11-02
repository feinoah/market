<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
<style>
/* IE has layout issues when sorting (see #5413) */
.group { zoom: 1 }
</style>
<script type="text/javascript" src="${ctx}/resources/public/scripts/config.js?v1.2"></script>
<script type="text/javascript">
$(function (){
	$('#nav-right').show();
	$('#updatePwd').dialog({
	    height:400,
		width:600,
	    autoOpen: false,
	    show: 'blind',
	    modal: true,
	    hide: 'explode'
	});
	$('#updateAccount').dialog({
	    height:400,
		width:600,
	    autoOpen: false,
	    show: 'blind',
	    modal: true,
	    hide: 'explode'
	});
	$(function() {
        $('#person-info').accordion({
            header: '> div > h3',
            collapsible: true,
            heightStyle: 'content',
            event: "click hoverintent"
        });
        var json=$.parseJSON('${obdCurrentDataList}');
        $.each(json,function(i,o){
        	$('#currentDataList').append('<tr><td>'+o.deviceId+'</td><td>'+o.location+'</td><td>'+o.values[16]+'</td><td>'+(o.error!=null?o.error:"")+'</td></tr>');
        });
    });
});
</script>
</head>
<body>
    <%@ include file="/WEB-INF/views/commons/header.jsp"%>
	<div id="main" class="ym-grid">
		<jsp:include page="/WEB-INF/views/commons/left-sidebar.jsp"
			flush="true">
			<jsp:param value="1" name="index" />
		</jsp:include>
		<div class="ym-g85 ym-gl main-container">
			<div id="person-info">
				<div class="group">
					<h3><spring:message code="title.userInfo.basic" /></h3>
					<ul>
						<li><label><spring:message code="title.userInfo.account" /></label><span>${portalUser.email}</span><a href="javascript:$('#updatePwd').dialog('open');"><spring:message code="title.userInfo.update.pwd" /></a></li>
						<li><label><spring:message code="title.userInfo.nickname" /></label><span>${portalUser.nickName}</span><a href="javascript:$('#updatePwd').dialog('open');"><spring:message code="title.userInfo.update" /></a></li>
					</ul>
				</div>
				<div class="group">
					<h3><spring:message code="title.userInfo.device" /></h3>
					<table id="currentDataList">
						<tr><th><spring:message code="title.userInfo.device.id" /></th><th><spring:message code="title.userInfo.device.currLocation" /></th><th><spring:message code="title.userInfo.device.mileage" /></th><th><spring:message code="title.userInfo.device.faultMsg" /></th></tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
	<div id="updatePwd" title="<spring:message code="title.userInfo.update.pwd" />" class="dialog">
		修改密码
	</div>
	<div id="updateAccount" title="<spring:message code="title.userInfo.update" />" class="dialog">
		修改资料
	</div>
</body>
</html>
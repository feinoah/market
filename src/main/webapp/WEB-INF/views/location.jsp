<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
<script type="text/javascript">
$(function (){
	$('#nav-right').show();
	var json=$.parseJSON('${obdCurrentDataList}');
    $.each(json,function(i,o){
    	$('#deviceId').append('<option value="'+o.deviceId+'">'+o.deviceId+'</option>');
    });
    $('#deviceId').change(function(){
    	refresh();
    }).change();;
});
function refresh(){
	$('#map-frame').attr('src','${ctx}/location_map?deviceId='+$('#deviceId').val()/*+'&type='+$('#type').val()+'&startTime='+$('#startTime').val()+'&endTime='+$('#endTime').val()*/);
}
</script>
</head>
<body>
    <%@ include file="/WEB-INF/views/commons/header.jsp"%>
	<div id="main" class="ym-grid">
		<jsp:include page="/WEB-INF/views/commons/left-sidebar.jsp" flush="true">
    		<jsp:param value="4" name="index"/>
    	</jsp:include>
		<div class="ym-g85 ym-gl main-container">
			<div id="obd-info-top">
				<label><spring:message code="title.userInfo.device.id" /></label><select id="deviceId"></select><label>查询类型</label><select></select><div><label>开始时间</label><label>结束时间</label></div>
			</div>
			<div id="location-info">
				<iframe id="map-frame" style="width:100%;height:500px;border:none;margin:0 auto;padding:0;"></iframe>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
</body>
</html>
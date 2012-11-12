<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
<script type="text/javascript">
$(function (){
	$('#nav-right').show();
	var tooltips=$('[title]').tooltip();
	$('.reg_input').css('width',100);
	$('#timeRange').hide();
	var json=$.parseJSON('${obdCurrentDataList}');
    $.each(json,function(i,o){
    	$('#deviceId').append('<option value="'+o.deviceId+'">'+o.deviceId+'</option>');
    });
    $('#deviceId').change(function(){
    	refresh();
    }).change();
    $('#type').change(function(){
    	if(this.value==2){
    		$('#timeRange').show();
    	}else{
	    	$('#timeRange').hide();
	    	refresh();
    	}
    }).css('width',100);
    $('#startTime').change(function(){
    	$('#map-frame').attr('src','${ctx}/location_map?deviceId='+$('#deviceId').val()+'&type=2&startTime='+$('#startTime').val()+'&endTime='+$('#endTime').val());
    });
    $('#endTime').change(function(){
    	$('#map-frame').attr('src','${ctx}/location_map?deviceId='+$('#deviceId').val()+'&type=2&startTime='+$('#startTime').val()+'&endTime='+$('#endTime').val());
    });
    $('#startTime').datepicker({
		onClose: function( selectedDate ) {
			$('#endTime').datepicker( "option", "minDate", selectedDate );
		}
    });
    $('#startTime').datepicker('option', 'dateFormat', 'yy-mm-dd');  
    $('#endTime').datepicker({
		onClose: function( selectedDate ) {
			$('#startTime').datepicker( "option", "maxDate", selectedDate );
		}
    });
    $('#endTime').datepicker('option', 'dateFormat', 'yy-mm-dd');
});
function refresh(){
	if($('#type').val()==2){
		$('#map-frame').attr('src','${ctx}/location_map?deviceId='+$('#deviceId').val()+'&type='+$('#type').val()+'&startTime='+$('#startTime').val()+'&endTime='+$('#endTime').val());
	} else {
		$('#map-frame').attr('src','${ctx}/location_map?deviceId='+$('#deviceId').val()+'&type='+$('#type').val());
	}
}
</script>
</head>
<body>
    <%@ include file="/WEB-INF/views/commons/header.jsp"%>
	<div id="main" class="ym-grid">
		<jsp:include page="/WEB-INF/views/commons/left-sidebar.jsp" flush="true">
    		<jsp:param value="4" name="index"/>
    	</jsp:include>
		<div class="ym-g80 ym-gl main-container">
			<div id="obd-info-top">
				<div class="ym-g65 ym-gl">
					<label><spring:message code="title.userInfo.device.id" /></label><select id="deviceId"></select><label><spring:message code="title.location.search.type"/></label>
					<select id="type">
						<option value="0"><spring:message code="title.location.search.type.1.text"/></option>
						<option value="1"><spring:message code="title.location.search.type.2.text"/></option>
						<option value="2"><spring:message code="title.location.search.type.3.text"/></option>
					</select>
				</div>
				<div class="ym-g45 ym-gr" id="timeRange">
					<label><spring:message code="title.location.startTime"/></label><input type="text" id="startTime" class="reg_input"/><label><spring:message code="title.location.endTime"/></label><input type="text" id="endTime" class="reg_input"/>
				</div>
			</div>
			<div id="location-info">
				<iframe id="map-frame" style="width:100%;height:500px;border:none;margin:0 auto;padding:0;"></iframe>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
</body>
</html>
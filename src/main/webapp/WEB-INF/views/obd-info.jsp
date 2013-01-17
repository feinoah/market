<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/resources/public/scripts/config.js?v1.2"></script>
<script type="text/javascript">
var index=1;
$(function (){
	var tooltips=$('[title]').tooltip();
	$('#nav-right').show();
	$('#startTime').datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		onSelect: function( selectedDate ) {
			$('#endTime').datepicker('option', 'minDate', selectedDate );
		}
	});
	$('#endTime').datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		onSelect: function( selectedDate ) {
			$('#startTime').datepicker('option', 'maxDate', selectedDate );
			doPage(1);
		}
	});
	$('#dialog').dialog({
	    height:400,
		width:600,
	    autoOpen: false,
	    show: 'clip',
        hide:'highlight',
	    modal: true
    });
	$('label.bottom').click(function(){
		$( "#dialog" ).dialog('open');
		index=$(this).attr('index');
		doPage(1);
	});
	 var json=$.parseJSON('${equipmentList}');
     $.each(json,function(i,o){
     	$('#deviceId').append('<option value="'+o.deviceId+'">'+o.deviceId+'</option>');
     });
     $('#deviceId').change(function(){
    	 $.getJSON('${ctx}/obd/'+this.value+'/${portalUser.id}', function(data) {
			 if(data=='null'||data==''){
				$('span[id^=value_]').html('0');
				$('#issue_count').html('0');
				$('li[id^=value_]').removeClass('warn').each(function(){
					$(this).children('.center').children('span:first').html('');
				});
				return;
			 }
    		 var json=$.parseJSON(data);
    		 if(json.data){
				$.each(json.data,function(i){
					if(i==4||i==17){
						$('#value_'+i).html(this+'');
					}else{
						$('#value_'+i+' .center span:first').html(this+' ');
					}
					// 有参考值
					if(obdDataReference[i-1].flag){
						if(this<obdDataReference[i-1].min || this>obdDataReference[i-1].max){
							$('#value_'+i).addClass('warn');
						} else {
							$('#value_'+i).removeClass('warn');
						}
					}
				});
				$('#issue_count').html($('.warn').size());
			}
    	 });
     }).change();
});

function doPage(page){
	$('#rows').empty();
	$('#pageDiv').empty();
	var search_url='${ctx}/obd/search/'+$('#deviceId').val()+'/${portalUser.id}/'+index+'?page='+page+'&rows=5';
	if($('#startTime').val()){
		search_url+='&startTime='+$('#startTime').val();
	}
	if($('#endTime').val()){
		search_url+='&endTime='+$('#endTime').val();
	}
	$.getJSON(search_url, function(data) {
		var html='<tr><th><spring:message code="title.obd.more.time"/></th><th><spring:message code="title.obd.more.location"/></th><th><spring:message code="title.obd.more.value"/></th></tr>';
		var json=$.parseJSON(data);
		
		if(json){
			$.each(json.rows,function(i,o){		
				html+='<tr><td>'+(new Date(o.date)).format('yyyy-MM-dd hh:mm:ss')+'</td><td>'+o.location+'</td><td>'+o.value+'</td></tr>';
			});
		}
		if(json.totalPage>1){
			pagination(page, json.totalPage);
		}
		$('#rows').html(html);
	});
}

/**
 * 生成分页导航
 * @param page 当前页
 * @param total 总页数
 */
function pagination(page,total){
	var html='';
	if(page>1){
		html+='<label onclick="doPage('+(page-1)+')">«<spring:message code="title.page.prev"/></label>';
	}
	html+='<label><select onchange="doPage(this.value)">'
	for(var i=1;i<=total;i++){
		html+='<option value="'+i+'"';
		if(page==i){
			html+='selected="selected"';
		}
		html+='>'+i+'</option>';
	}
	html+='</select></label>'
	if(total>page){
		html+='<label onclick="doPage('+(page+1)+')"><spring:message code="title.page.next"/> »</label>';
	}
	$('#pageDiv').empty().html(html);
}
</script>
</head>
<body>
    <%@ include file="/WEB-INF/views/commons/header.jsp"%>
	<div id="main" class="ym-grid">
		<jsp:include page="/WEB-INF/views/commons/left-sidebar.jsp" flush="true">
    		<jsp:param value="2" name="index"/>
    	</jsp:include>
		<div class="ym-g80 ym-gl main-container">
			<div id="obd-info">
				<div id="obd-info-top">
					<label><spring:message code="title.userInfo.device.id" /></label><select id="deviceId"></select><label><spring:message code="title.obd.value_4"/></label><span id="value_4">0</span><label><spring:message code="unit.obd.4"/></label><label class="total-odo"><spring:message code="title.obd.value_17"/></label><span id="value_17">0</span><label><spring:message code="unit.obd.17"/></label>
				</div>
				<div id="obd-info-brief">
					<div id="obd-info-brief-status"><spring:message code="title.obd.current.state"/></div>
					<div id="obd-info-brief-car">
						<img alt="" src="${ctx}/resources/public/images/car.png">
					</div>
				</div>
				<div id="obd-info-list">
					<ul>
						<li id="value_1"><label><spring:message code="title.obd.value_1"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.1"/></span></label><label class="bottom" index="1"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_2"><label><spring:message code="title.obd.value_2"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.2"/></span></label><label class="bottom" index="2"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_3"><label><spring:message code="title.obd.value_3"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.3"/></span></label><label class="bottom" index="3"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_5"><label><spring:message code="title.obd.value_5"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.5"/></span></label><label class="bottom" index="4"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_6"><label><spring:message code="title.obd.value_6"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.6"/></span></label><label class="bottom" index="5"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_7"><label><spring:message code="title.obd.value_7"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.7"/></span></label><label class="bottom" index="7"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_8"><label><spring:message code="title.obd.value_8"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.8"/></span></label><label class="bottom" index="8"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_9"><label><spring:message code="title.obd.value_9"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.9"/></span></label><label class="bottom" index="9"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_10"><label><spring:message code="title.obd.value_10"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.10"/></span></label><label class="bottom" index="10"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_11"><label><spring:message code="title.obd.value_11"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.11"/></span></label><label class="bottom" index="11"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_12"><label><spring:message code="title.obd.value_12"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.12"/></span></label><label class="bottom" index="12"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_13"><label><spring:message code="title.obd.value_13"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.13"/></span></label><label class="bottom" index="13"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_15"><label><spring:message code="title.obd.value_15"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.15"/></span></label><label class="bottom" index="15"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_16"><label><spring:message code="title.obd.value_16"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.16"/></span></label><label class="bottom" index="16"><spring:message code="botton.obd.more"/></label></li>
						<li id="value_18"><label><spring:message code="title.obd.value_18"/></label><label class="center"><span></span><span class="unit"><spring:message code="unit.obd.18"/></span></label><label class="bottom" index="18"><spring:message code="botton.obd.more"/></label></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
	<div id="dialog" title="<spring:message code="botton.obd.more"/>" class="dialog">
		<p><label><spring:message code="title.location.startTime"/></label><input type="text" id="startTime" tabindex="-2"/><label><spring:message code="title.location.endTime"/></label><input type="text" id="endTime" tabindex="-1"/></p>
	    <table id="rows">
		</table>
		<div id="pageDiv"><label class="cruLabel"><span class="selected">1</span><span onclick="doPage(2)">2</span></label></div>
	</div>
</body>
</html>
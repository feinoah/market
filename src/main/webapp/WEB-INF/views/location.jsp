<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true&language=zh_CN"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/commons/header.jsp"%>
	<div id="main" class="ym-grid">
		<jsp:include page="/WEB-INF/views/commons/left-sidebar.jsp" flush="true">
    		<jsp:param value="4" name="index"/>
    	</jsp:include>
		<div class="ym-g80 ym-gl main-container">
			<div id="location-top">
				<div class="ym-g65 ym-gl">
					<label><spring:message code="title.userInfo.device.id" /></label><select id="deviceId"></select><label><spring:message code="title.location.search.type"/></label>
					<select id="type">
						<option value="0"><spring:message code="title.location.search.type.1.text"/></option>
						<option value="1" selected="selected"><spring:message code="title.location.search.type.2.text"/></option>
						<option value="2"><spring:message code="title.location.search.type.3.text"/></option>
					</select>
				</div>
				<div class="ym-g48 ym-gr" id="timeRange">
					<label><spring:message code="title.location.startTime"/></label><input type="text" id="startTime" class="reg_input"/><label><spring:message code="title.location.endTime"/></label><input type="text" id="endTime" class="reg_input"/>
				</div>
			</div>
			<div id="location-info">
				
			</div>
		</div>
	</div>
	<div id="prompt" class="dialog" title="<spring:message code="dialog.title.prompt" />">
		<p><spring:message code="dialog.no.data" /></p>
	</div>
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
	<script type="text/javascript">
	var map;
	var poly;
	var latLngs=[];
	var index=0;
	var maxLen;
	var timmer;
	var delay=300;
	var page=1;//默认页码
	var rows=25;
	var totalPage=1;//总页数
	var coordinate;
	var marker;
	var flag=false;
	
	function initialize(){
		page=1;//默认页码
		index=0;
		latLngs=[];
		if(poly){
			poly.setMap(null);
		}
		clearTimeout(timmer);
		var center = new google.maps.LatLng(22.543675, 114.057741);
		var myOptions = {
			zoom : 15,
			center : center,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map(document.getElementById("location-info"),
				myOptions);
		var polyOptions = {
	          strokeColor: '#F96D6D',
	          strokeOpacity: 1.0,
	          strokeWeight: 3
	    };
		poly=new google.maps.Polyline(polyOptions);
	    poly.setMap(map);
	    if(flag){
		    getLatLngs(page, rows);
	    }
	    flag=true;
	}
	
	function getLatLngs(_page, _rows){
		var url='${ctx}/query/locations/'+$('#deviceId').val()+'/${portalUser.id}?type='+$('#type').val()+'&page='+_page+'&rows='+_rows;
		if($('#type').val()==2){
			url+='&startTime='+$('#startTime').val()+'&endTime='+$('#endTime').val();
		}
		$.getJSON(url, function(data) {
			var json=$.parseJSON(data);
			totalPage=json.totalPage;
			if(json.rows==''||json.rows==undefined||json.rows.length==0){
				$('#prompt').dialog('open');
				return;
			}
			$.merge(latLngs, json.rows);
			refreshMap(map);
		});
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);
	
	//Refresh map function  
	function refreshMap(map){
		if(index>=latLngs.length){
			if(page<totalPage){ // 继续取后面的数据
				page=page+1;
				getLatLngs(page, rows);
			} else { // 没有数据了
				clearTimeout(timmer);
			}
		}
		coordinate=new google.maps.LatLng(latLngs[index].lat, latLngs[index].lng);
		poly.getPath().push(coordinate);
		map.setCenter(coordinate);
		// 清除前一个点
		if(marker){
			marker.setMap(null);
			shadow.setMap(null);
		}
		// 把最后一个点标记出来
		marker=new google.maps.Marker({
			position: coordinate, 
			icon:'http://maps.gstatic.com/mapfiles/ridefinder-images/mm_20_red.png',
			map: map
		});
		shadow=new google.maps.Marker({
			position: coordinate, 
			icon:'http://maps.gstatic.com/mapfiles/ridefinder-images/mm_20_shadow.png',
			map: map
		});
		index++;
		timmer=window.setTimeout(function(){  
	        refreshMap(map);
	    }, delay);
	}
	
	$(function (){
		$('#prompt').dialog({
			autoOpen:false,
	        modal: true,
	        show: 'clip',
	        hide:'highlight',
	        buttons: {
	            '<spring:message code="dialog.button.ok" />': function() {
	                $(this).dialog('close');
	            }
	        }
	    });
		$('#nav-right').show();
		var tooltips=$('[title]').tooltip();
		$('.reg_input').css('width',140);
		$('#timeRange').hide();
		if('${hl}'!='en'){
	    	$.datepicker.regional['zh'] = {
	    			closeText: '确定',
	    			prevText: '<上一月',
	    			nextText: '下一月>',
	    			currentText: '当前时间',
	    			monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
	    			monthNamesShort: ['一','二','三','四','五','六','七','八','九','十','十一','十二'],
	    			dayNames: ['星期天', '星期一','星期二','星期三','星期三','星期五','星期六'],
	    			dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
	    			dayNamesMin: ['日','一','二','三','四','五','六'],
	    			weekHeader: 'Не',
	    			dateFormat: 'yy-mm-dd',
	    			firstDay: 1,
	    			isRTL: false,
	    			showMonthAfterYear: false,
	    			yearSuffix: ''
	    		};
	    		$.datepicker.setDefaults($.datepicker.regional['zh']);
	
	    		$.timepicker.regional['zh'] = {
	    			timeOnlyTitle: '选择时间',
	    			timeText: '时间',
	    			hourText: '时',
	    			minuteText: '分',
	    			secondText: '秒',
	    			millisecText: '毫秒',
	    			timezoneText: '时区',
	    			currentText: '当前时间',
	    			closeText: '确定',
	    			timeFormat: 'HH:mm',
	    			amNames: ['上午', 'A'],
	    			pmNames: ['下午', 'P'],
	    			isRTL: false
	    		};
	    		$.timepicker.setDefaults($.timepicker.regional['zh']);
	    }
		var json=$.parseJSON('${equipmentList}');
	    $.each(json,function(i,o){
	    	$('#deviceId').append('<option value="'+o.deviceId+'">'+o.deviceId+'</option>');
	    });
	    $('#deviceId').change(function(){
	    	initialize();
	    });
	    $('#type').change(function(){
	    	if(this.value==2){
	    		$('#timeRange').show();
	    	}else{
		    	$('#timeRange').hide();
		    	initialize();
	    	}
	    }).css('width',100);
	    $('#startTime').datetimepicker({
	    	dateFormat:'yy-mm-dd',
			onClose: function( selectedDate ) {
				$('#endTime').datetimepicker('option', 'minDate', selectedDate );
				$('#endTime').datetimepicker('option', 'minDateTime', new Date(selectedDate));
			}
	    });
	    $('#endTime').datetimepicker({
	    	dateFormat:'yy-mm-dd',
			onClose: function( selectedDate ) {
				initialize();
			}
	    });
	});
	</script>
</body>
</html>
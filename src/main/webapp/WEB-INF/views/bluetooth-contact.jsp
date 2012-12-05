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
	$('.reg_input').css('width',120);
	var json=$.parseJSON('${equipmentList}');
    $.each(json,function(i,o){
    	$('#deviceId').append('<option value="'+o.deviceId+'">'+o.deviceId+'</option>');
    });
	$('button').button().click(function() {
		doPage(1);
    });
	doPage(1);
});


function doPage(page){
	$('#rows').empty();
	$('#pageDiv').empty();
	var search_url='${ctx}/bluetooth/cantact/query?email=${portalUser.email}&deviceId='+$('#deviceId').val()+'&page='+page+'&rows=8';
	if($('#bluetoothId').val()){
		search_url+='&bluetoothId='+$('#bluetoothId').val();
	}
	if($('#name').val()){
		search_url+='&name='+$('#name').val();
	}
	if($('#nameKey').val()){
		search_url+='&nameKey='+$('#nameKey').val();
	}
	if($('#num').val()){
		search_url+='&num='+$('#num').val();
	}
	$.getJSON(search_url, function(data) {
		var html='<tr><th><spring:message code="title.contact.name" /></th><th><spring:message code="title.contact.nameKey" /></th><th><spring:message code="title.contact.num" /></th><th><spring:message code="title.catalog" /></th></tr>';
		var json=$.parseJSON(data);
		if(json){
			$.each(json.rows,function(i,o){		
				html+='<tr><td>'+o.callName+'</td><td>'+o.callNameKey+'</td><td>'+o.callNum+'</td><td>'+o.callType+'</td></tr>';
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
    		<jsp:param value="5" name="index"/>
    	</jsp:include>
		<div class="ym-g80 ym-gl main-container">
			<ul id="contact-top">
				<li>
				<label><spring:message code="title.userInfo.device.id" /></label><select id="deviceId"></select>
				<label><spring:message code="title.userInfo.bluetooth" /></label>
				<select id="bluetoothId">
				<option value=""></option>
				<c:forEach items="${bluetoothList}" var="b">
				<option value="${b.bluetoothId}">${b.bluetoothName}</option>
				</c:forEach>
				</select>
				<label><spring:message code="title.contact.name" /></label><input id="name" class="reg_input"/>
				</li>
				<li>
				<label><spring:message code="title.contact.nameKey" /></label><input id="nameKey" class="reg_input"/>
				<label><spring:message code="title.contact.num" /></label><input id="num" class="reg_input"/><button><spring:message code="botton.search" /></button>
				</li>
			</ul>
			<div id="contact-list">
				<table id=rows>
				</table>
				<div id="pageDiv"><label class="cruLabel"><span class="selected">1</span><span onclick="doPage(2)">2</span></label></div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
</body>
</html>
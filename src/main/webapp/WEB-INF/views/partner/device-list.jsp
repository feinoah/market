<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/commons/meta.jsp" flush="true">
	<jsp:param value="1" name="index" />
</jsp:include>
<script type="text/javascript" src="${ctx}/resources/public/scripts/config.js?v1.2"></script>
<script type="text/javascript" src="${ctx}/resources/jquery-easyui-1.3/plugins/jquery.form.js"></script>
<script type="text/javascript">
$(function (){
	$('#nav-right').show();
	var tooltips=$('[title]').tooltip();
	$('.reg_input').css('width',150);
	$('#contact-top label').css('width', 100);
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
	$('button').button().click(function() {
		doPage(1);
    });
	doPage(1);
	$('#email').blur(function(){
		var email=$.trim($(this).val());
		if(chkEmail(email)){
			$(tooltips[2]).tooltip('close');
		} else {
			$(tooltips[2]).tooltip('option', 'content', '<spring:message code="tips.email.onShow"/>');
			$(tooltips[2]).tooltip('open');
		}
	});
});
function doPage(page){
	$('#rows').empty();
	$('#pageDiv').empty();
	var search_url='${ctx}/partner/query/devices?page='+page+'&rows=8';
	if($('#nickName').val()){
		search_url+='&nickName='+$('#nickName').val();
	}
	if($('#email').val()){
		search_url+='&email='+$('#email').val();
	}
	$.getJSON(search_url, function(data) {
		var html='<tr><th><spring:message code="title.userInfo.device.id" /></th><th><spring:message code="title.userInfo.account"/></th><th><spring:message code="title.userInfo.nickname" /></th><th><spring:message code="title.partner.addr"/></th><th><spring:message code="title.userInfo.mobile" /></th><th><spring:message code="title.operation" /></th></tr>'.replace(/:|：/g,'');
		var json=$.parseJSON(data);
		if(json){
			$.each(json.rows,function(i,o){		
				html+='<tr><td>'+o.deviceId+'</td><td>'+o.email+'</td><td>'+o.nickname+'</td><td>'+(o.address==null?"":o.address)+'</td><td>'+(o.mobile==null?"":o.mobile)+'</td><th><a href="${ctx}/partner/obd?deviceId='+o.deviceId+'&accountId='+o.id+'" target="_blank"><spring:message code="Sidebar.obd.info" /></a></th></tr>';
			});
			if(json.totalPage>1){
				pagination(page, json.totalPage);
			}
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
		<jsp:include page="/WEB-INF/views/commons/partner-sidebar.jsp"
			flush="true">
			<jsp:param value="2" name="index" />
		</jsp:include>
		<div class="ym-g80 ym-gl main-container">
			<ul id="contact-top" style="height:37px;margin-top:5px;">
				<li>
				<label><spring:message code="title.userInfo.account"/></label><input id="email" class="reg_input" title="<spring:message code="tips.email.onShow"/>"/>
				<label><spring:message code="title.userInfo.nickname" /></label><input id="nickName" class="reg_input"/>
				<button><spring:message code="botton.search" /></button>
				</li>
			</ul>
			<div id="contact-list" style="height:437px;">
				<table id=rows>
				</table>
				<div id="pageDiv"></div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
	<div id="prompt" class="dialog">
		<p></p>
	</div>
</body>
</html>
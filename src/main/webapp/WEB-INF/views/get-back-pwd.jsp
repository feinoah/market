<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
<script type="text/javascript">
var tooltips=[];
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
	tooltips=$('[title]').tooltip({ position: { my: "left+25 center", at: "right center" }});
	$('#email').change(function(){
		var email=$.trim($(this).val());
		if(!chkEmail(email)){
			$(tooltips[2]).tooltip('option', 'content', '<spring:message code="tips.email.onShow"/>');
			$(tooltips[2]).tooltip('open');
		}
	}).focus(function(){
		$(tooltips[2]).tooltip('option', 'content', '<spring:message code="tips.email.onShow"/>');
	}).keydown(function(e){
		if(e.which==13){
			get_back_pwd();
		}
	});
	$('button').button().click(function( event ) {
        event.preventDefault();
    });
});
function get_back_pwd(){
	var email=$('#email').val();
	if(!chkEmail(email)){
		$(tooltips[2]).tooltip('option', 'content', '<spring:message code="tips.email.onShow"/>');
		$(tooltips[2]).tooltip('open');
		return;
	}
	$.getJSON('${ctx}/get_back_pwd?email='+$('#email').val(), function(data) {
		var json=$.parseJSON(data);
		if(json.successful){
			$('#prompt p').html('<spring:message code="dialog.reset.password.success" />');
			$('#prompt').dialog('option','title','<spring:message code="dialog.title.prompt"/>');
			$('#prompt').dialog('open');
		} else {
			$('#prompt').dialog('option','title',json.message);
			if(json.subErrors) {
				$('#prompt p').html(json.subErrors[0].message);
			} else {
				$('#prompt p').html(json.message);
			}
			$('#prompt').dialog('open');
		}
	});
}
</script>
</head>
<body>
    <%@ include file="/WEB-INF/views/commons/header.jsp"%>
	<div id="main" class="ym-grid">
		<div class="ym-grid" id="loginRegister">
			<form  method="post" id="getPwdForm">
				<h3><spring:message code="title.request.new.pwd"/></h3>
				<dl>
					<dd>
						<span class="title"><spring:message code="title.userInfo.account"/></span><input class="reg_input" name="email" id="email" type="text" title="<spring:message code="tips.email.onShow"/>"/>
						<button onclick="get_back_pwd()"><spring:message code="botton.request.reset"/></button><button onclick="location.href='${ctx}/profile?hl=${param.hl}'"><spring:message code="botton.back.login"/></button>
					</dd>
				</dl>
			</form>
		</div>
	</div>
	<div id="prompt" class="dialog">
		<p></p>
	</div>
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
</body>
</html>
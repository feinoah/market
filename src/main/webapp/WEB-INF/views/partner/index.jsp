<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/commons/meta.jsp" flush="true">
	<jsp:param value="1" name="index" />
</jsp:include>
<style>
/* IE has layout issues when sorting (see #5413) */
.group { zoom: 1 }
</style>
<script type="text/javascript" src="${ctx}/resources/public/scripts/config.js?v1.2"></script>
<script type="text/javascript" src="${ctx}/resources/jquery-easyui-1.3/plugins/jquery.form.js"></script>
<script type="text/javascript">
var closePwdWin=false;
var closeAccountWin=false;
$(function (){
	$('#nav-right').show();
	$('#prompt').dialog({
		autoOpen:false,
        modal: true,
        show: 'clip',
        hide:'highlight',
        buttons: {
            '<spring:message code="dialog.button.ok" />': function() {
                $(this).dialog('close');
                if(closePwdWin){
                	$('#updatePwd').dialog('close');
                	closePwdWin=false;
                }
                if(closeAccountWin){
                	$('#updateAccount').dialog('close');
                	location.reload();
                }
            }
        }
    });
	$('#nav-right').show();
	$('#updatePwd').dialog({
	    height:300,
		width:400,
	    autoOpen: false,
	    show: 'clip',
	    modal: true,
	    hide: 'highlight'
	});
	$('#updateAccount').dialog({
	    height:400,
		width:600,
	    autoOpen: false,
	    show: 'clip',
	    modal: true,
	    hide: 'highlight'
	});
	$('#bindingDevice').dialog({
	    height:200,
		width:400,
	    autoOpen: false,
	    show: 'clip',
	    modal: true,
	    hide: 'highlight'
	});
    $('button').button().click(function( event ) {
        event.preventDefault();
    });
    
    var tooltips=$('[title]').tooltip();
    $('#update_pwd_btn').click(function(){
    	var submit=true;
    	$('#updatePwd input').each(function(i){
    		if(this.value==''||this.value.length<6){
    			$(tooltips[i+2]).tooltip('option', 'content', '<spring:message code="tips.password.onShow"/>');
    			$(tooltips[i+2]).tooltip('open');
    			submit=false;
    		}
    	});
		if($('#re_password').val()!=$('#password').val()){
			$(tooltips[4]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onError"/>');
			$(tooltips[4]).tooltip('open');
			submit=false;
		}
		if(submit){
			$.post('${ctx}/partner/updatePwd?oldPassword='+$('#oldPassword').val()+'&newPassword='+$('#password').val(),function(data){
				var json=$.parseJSON(data);
				if(json.code){//错误提示
					$('#prompt').dialog('option','title',json.message);
					if(json.subErrors) {
						$('#prompt p').html(json.subErrors[0].message);
					} else {
						$('#prompt p').html(json.message);
					}
				} else {
					$('#prompt').dialog('option','title','<spring:message code="dialog.title.prompt"/>');
					$('#prompt p').html('<spring:message code="dialog.success.message"/>');
					$('#reset_reg').click();
				}
				$('#prompt').dialog('open');
				closePwdWin=true;
			});
		}
    });
    $('#reset_pwd').click(function(){
    	$('#updatePwd input').val('');
    });
    $('#update_account_btn').click(function(){
   		$.post('${ctx}/partner/update?city='+$('#city').val()+'&addr='
   				+$('#addr').val()+'&contactPerson='+$('#contactPerson').val()
   				+'&phone='+$('#phone').val()+'&email='+$('#email').val()
   				,function(data){
   			var json=$.parseJSON(data);
			if(json.code){//错误提示
				$('#prompt').dialog('option','title',json.message);
				if(json.subErrors) {
					$('#prompt p').html(json.subErrors[0].message);
				} else {
					$('#prompt p').html(json.message);
				}
			} else {
				$('#prompt').dialog('option','title','<spring:message code="dialog.title.prompt"/>');
				$('#prompt p').html('<spring:message code="dialog.success.message"/>');
				$('#reset_reg').click();
			}
			$('#prompt').dialog('open');
			closeAccountWin=true;
       	});
    });
    $('#reset_account').click(function(){
    	$('#updateAccount input').val('');
    });
    $('#binding_device_btn').click(function(){
    	var deviceId=$.trim($('#deviceId').val());
    	if(deviceId==''){
    		$(tooltips[10]).tooltip('option', 'content', '<spring:message code="tips.deviceId.onShow"/>');
			$(tooltips[10]).tooltip('open');
    	} else {
			$.get('${ctx}/partner/bound/'+deviceId, function(data) {
				var json=$.parseJSON(data);
				if(json.code){//错误提示
					$('#prompt').dialog('option','title',json.message);
					if(json.subErrors) {
						$('#prompt p').html(json.subErrors[0].message);
					} else {
						$('#prompt p').html(json.message);
					}
				} else {
					$('#prompt').dialog('option','title','<spring:message code="dialog.title.prompt"/>');
					$('#prompt p').html('<spring:message code="dialog.bound.success"/>');
					$('#deviceId').val('');
				}
				$('#prompt').dialog('open');
	   		});
    	}
    });
    $('#reset_device').click(function(){
    	$('#deviceId').val('');
    });
});

</script>
</head>
<body>
    <%@ include file="/WEB-INF/views/commons/header.jsp"%>
	<div id="main" class="ym-grid">
		<jsp:include page="/WEB-INF/views/commons/partner-sidebar.jsp"
			flush="true">
			<jsp:param value="1" name="index" />
		</jsp:include>
		<div class="ym-g80 ym-gl main-container">
			<div id="person-info" style="height:502px;">
			<div class="ym-grid">
		    <ul class="ym-grid" style="margin: 3em 6em;">
				<li><label><spring:message code="title.partner.firmName"/></label><span>${partner.firmName}</span><a href="javascript:$('#updatePwd').dialog('open');"><spring:message code="title.userInfo.update.pwd" /></a></li>
				<li><label><spring:message code="title.partner.city"/></label><span>${partner.city}</span><a href="javascript:$('#updateAccount').dialog('open');"><spring:message code="title.userInfo.update" /></a></li>
				<li><label><spring:message code="title.partner.addr"/></label><span>${partner.addr}</span><a href="javascript:$('#bindingDevice').dialog('open');"><spring:message code="titie.binding.device" /></a></li>
		    	<li><label><spring:message code="title.partner.contactPerson"/></label><span>${partner.contactPerson}</span></li>
				<li><label><spring:message code="title.userInfo.account"/></label><span>${partner.email}</span></li>
		    	<li><label><spring:message code="title.partner.phone"/></label><span>${partner.phone}</span></li>
			</ul>
			</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
	<div id="updatePwd" title="<spring:message code="title.userInfo.update.pwd" />" class="dialog">
		<dl>
			<dd>
				<label><spring:message code="title.userInfo.update.oldpwd" /></label><input type="password" id="oldPassword" class="reg_input" title="<spring:message code="tips.password.onShow"/>"/>
			</dd>
			<dd>
				<label><spring:message code="title.userInfo.update.newpwd" /></label><input type="password" id="password" class="reg_input" title="<spring:message code="tips.password.onShow"/>"/>
			</dd>
			<dd>
				<label><spring:message code="title.userInfo.confirm.password" /></label><input type="password" id="re_password" class="reg_input" title="<spring:message code="tips.confirm.password.onShow"/>"/>
			</dd>
			<dd style="margin:0 auto;text-align: center;">
				<button id="update_pwd_btn"><spring:message code="botton.confirm" /></button><button id="reset_pwd"><spring:message code="botton.reset" /></button>
			</dd>
		</dl>
	</div>
	<div id="updateAccount" title="<spring:message code="title.userInfo.update" />" class="dialog">
		<dl>
			<dd>
				<label><spring:message code="title.partner.city"/></label><input type="text" id="city" class="reg_input" value="${partner.city}" title="<spring:message code="tips.partner.city.onShow"/>"/>
			</dd>
			<dd>
				<label><spring:message code="title.partner.addr"/></label><input type="text" id="addr" class="reg_input" value="${partner.addr}" title="<spring:message code="tips.partner.addr.onShow"/>"/>
			</dd>
			<dd>
				<label><spring:message code="title.partner.contactPerson"/></label><input type="text" id="contactPerson" class="reg_input" value="${partner.contactPerson}" title="<spring:message code="tips.partner.contactPerson.onShow"/>"/>
			</dd>
			<dd>
				<label><spring:message code="title.partner.phone"/></label><input type="text" id="phone" class="reg_input" value="${partner.phone}" title="<spring:message code="tips.partner.phone.onShow"/>"/>
			</dd>
			<dd>
				<label><spring:message code="title.userInfo.account"/></label><input type="text" id="email" class="reg_input" value="${partner.email}" title="<spring:message code="tips.email.onShow"/>"/>
			</dd>
			<dd style="margin:0 auto;text-align: center;">
				<button id="update_account_btn"><spring:message code="botton.confirm" /></button><button id="reset_account"><spring:message code="botton.reset" /></button>
			</dd>
		</dl>
	</div>
	<div id="bindingDevice" title="<spring:message code="titie.binding.device" />" class="dialog">
		<dl>
			<dd>
				<label><spring:message code="title.userInfo.device.id" /></label><input type="text" id="deviceId" class="reg_input" title="<spring:message code="tips.deviceId.onShow"/>"/>
			</dd>
			<dd style="margin:0 auto;text-align: center;">
				<button id="binding_device_btn"><spring:message code="botton.confirm" /></button><button id="reset_device"><spring:message code="botton.reset" /></button>
			</dd>
		</dl>
	</div>
	<div id="prompt" class="dialog">
		<p></p>
	</div>
</body>
</html>
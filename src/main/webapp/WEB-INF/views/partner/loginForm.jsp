<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
<script type="text/javascript">
var tooltips=[];
$(function (){
	tooltips=$('[title]').tooltip({ position: { my: "left+25 center", at: "right center" }});
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
	$('#firmName').blur(function(){
		var v=$.trim($(this).val());
		if(v.length<3||v.length>30){
			$('#firmNameTip').attr('class', '').addClass('onError');
			$(tooltips[4]).tooltip('option', 'content', '<spring:message code="tips.partner.firmName.onShow"/>');
		} else {
			$('#firmNameTip').attr('class', '').addClass('onSuccess');
			$(tooltips[4]).tooltip('close');
		}
	}).focus(function(){
		$('#firmNameTip').attr('class', '').addClass('onFocus');
		$(tooltips[4]).tooltip('option', 'content', '<spring:message code="tips.partner.firmName.onShow"/>');
		$(tooltips[4]).tooltip('open');
	});
	$('#city').blur(function(){
		var v=$.trim($(this).val());
		if(v.length<2||v.length>30){
			$('#cityTip').attr('class', '').addClass('onError');
			$(tooltips[5]).tooltip('option', 'content', '<spring:message code="tips.partner.city.onShow"/>');
		} else {
			$('#cityTip').attr('class', '').addClass('onSuccess');
			$(tooltips[5]).tooltip('close');
		}
	}).focus(function(){
		$('#cityTip').attr('class', '').addClass('onFocus');
		$(tooltips[5]).tooltip('option', 'content', '<spring:message code="tips.partner.city.onShow"/>');
		$(tooltips[5]).tooltip('open');
	});
	$('#contactPerson').blur(function(){
		var v=$.trim($(this).val());
		if(v.length<2||v.length>30){
			$('#contactPersonTip').attr('class', '').addClass('onError');
			$(tooltips[6]).tooltip('option', 'content', '<spring:message code="tips.partner.contactPerson.onShow"/>');
		} else {
			$('#contactPersonTip').attr('class', '').addClass('onSuccess');
			$(tooltips[6]).tooltip('close');
		}
	}).focus(function(){
		$('#contactPersonTip').attr('class', '').addClass('onFocus');
		$(tooltips[6]).tooltip('option', 'content', '<spring:message code="tips.partner.contactPerson.onShow"/>');
		$(tooltips[6]).tooltip('open');
	});
	$('#phone').blur(function(){
		var v=$.trim($(this).val());
		if(v.length<5||v.length>30){
			$('#phoneTip').attr('class', '').addClass('onError');
			$(tooltips[7]).tooltip('option', 'content', '<spring:message code="tips.partner.phone.onShow"/>');
		} else {
			$('#phoneTip').attr('class', '').addClass('onSuccess');
			$(tooltips[7]).tooltip('close');
		}
	}).focus(function(){
		$('#phoneTip').attr('class', '').addClass('onFocus');
		$(tooltips[7]).tooltip('option', 'content', '<spring:message code="tips.partner.phone.onShow"/>');
		$(tooltips[7]).tooltip('open');
	});
	$('#addr').blur(function(){
		var v=$.trim($(this).val());
		if(v.length<3||v.length>150){
			$('#addrTip').attr('class', '').addClass('onError');
			$(tooltips[8]).tooltip('option', 'content', '<spring:message code="tips.partner.addr.onShow"/>');
		} else {
			$('#addrTip').attr('class', '').addClass('onSuccess');
			$(tooltips[8]).tooltip('close');
		}
	}).focus(function(){
		$('#addrTip').attr('class', '').addClass('onFocus');
		$(tooltips[8]).tooltip('option', 'content', '<spring:message code="tips.partner.addr.onShow"/>');
		$(tooltips[8]).tooltip('open');
	});
	$('#email').blur(function(){
		var email=$.trim($(this).val());
		if(chkEmail(email)){
			$('#emailTip').attr('class', '').addClass('onSuccess');
			$(tooltips[9]).tooltip('close');
		} else {
			$('#emailTip').attr('class', '').addClass('onError');
			$(tooltips[9]).tooltip('option', 'content', '<spring:message code="tips.email.onShow"/>');
			$(tooltips[9]).tooltip('open');
		}
	}).focus(function(){
		$('#emailTip').attr('class', '').addClass('onFocus');
		$(tooltips[9]).tooltip('option', 'content', '<spring:message code="tips.email.onShow"/>');
	});
	$('#password').blur(function(){
		var v=$.trim($(this).val());
		if(v.length<6||v.length>18){
			$('#passwordTip').attr('class', '').addClass('onError');
			$(tooltips[10]).tooltip('option', 'content', '<spring:message code="tips.password.onShow"/>');
			$(tooltips[10]).tooltip('open');
		}else{
			if(chkPwd(v)){
				$('#passwordTip').attr('class', '').addClass('onCorrect');
				$(tooltips[10]).tooltip('close');
			}else{
				$('#passwordTip').attr('class', '').addClass('onError');
				$(tooltips[10]).tooltip('option', 'content', '<spring:message code="tips.password.onShow"/>');
				$(tooltips[10]).tooltip('open');
			}
		}
	}).focus(function(){
		$('#passwordTip').attr('class', '').addClass('onFocus');
		$(tooltips[10]).tooltip('option', 'content', '<spring:message code="tips.password.onShow"/>');
	});
	$('#t_RePass').blur(function(){
		var pwd=$.trim($(this).val());
		if(pwd){
			if($.trim($('#password').val())==pwd){
				$('#t_RePassTip').attr('class', '').addClass('onCorrect');
				$(tooltips[11]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onShow"/>');
				$(tooltips[11]).tooltip('close');
			}else{
				$('#t_RePassTip').attr('class', '').addClass('onError');
				$(tooltips[11]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onError"/>');
				$(tooltips[11]).tooltip('open');
			}
		}else{
			$('#t_RePassTip').attr('class', '').addClass('onError');
			$(tooltips[11]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onShow"/>');
			$(tooltips[11]).tooltip('open');
		}
	}).focus(function(){
		$('#t_RePassTip').attr('class', '').addClass('onFocus');
		$(tooltips[11]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onShow"/>');
	}).keydown(function(e){
		if(e.which==13){
			var pwd=$.trim($(this).val());
			if(pwd){
				if($.trim($('#password').val())==pwd){
					$('#t_RePassTip').attr('class', '').addClass('onCorrect');
				}  else {
					$('#t_RePassTip').attr('class', '').addClass('onError');
					$(tooltips[11]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onError"/>');
					$(tooltips[11]).tooltip('open');
				}
			} else {
				$('#t_RePassTip').attr('class', '').addClass('onFocus');
				$(tooltips[11]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onShow"/>');
				$(tooltips[11]).tooltip('open');
			}
			register();
		}
	});
	$('#login_password').keydown(function(e){
		if(e.which==13){
			login();
		}
	});
	$('#reset_reg').click(function(){
		$('#registerForm input').val('');
	});
	$('button').button().click(function( event ) {
        event.preventDefault();
    });
});

function login(){
	if($('#login_email').val()==''){
		$(tooltips[2]).tooltip('open');
		return;
	}
	
	if($('#login_password').val()==''){
		$(tooltips[3]).tooltip('open');
		return;
	}
	$.post('${ctx}/partner/login?hl=${hl}&firmName='+$('#login_firmName').val()+'&password='+$('#login_password').val(), function(data) {
		var json=$.parseJSON(data);
		if(json.code){//错误提示
			$('#prompt').dialog('option','title',json.message);
			if(json.subErrors) {
				$('#prompt p').html(json.subErrors[0].message);
			} else {
				$('#prompt p').html(json.message);
			}
			$('#prompt').dialog('open');
		} else {
			location.reload();
		}
	});
}

function register(){
	var tag=true;
	$('#registerForm span[id$="Tip"]').each(function(i,o){
		if(!$(this).hasClass('onCorrect')&&!$(this).hasClass('onSuccess')){
			$(tooltips[4+i]).tooltip('open');
			tag=false;
		}
	});
	if(tag){
		$.post('${ctx}/partner/register?hl=${hl}&firmName='
				+$('#firmName').val()+'&password='+$('#password').val()
				+'&email='+$('#email').val()+'&city='+$('#city').val()
				+'&addr='+$('#addr').val()+'&contactPerson='+$('#contactPerson').val()
				+'&phone='+$('#phone').val(),function(data){
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
				$('#prompt p').html('<spring:message code="dialog.register.success.message"/>');
				$('#reset_reg').click();
			}
			$('#prompt').dialog('open');
		});
	}
}
</script>
</head>
<body>
    <%@ include file="/WEB-INF/views/commons/header.jsp"%>
	<div id="main" class="ym-grid">
		<div class="ym-grid" id="loginRegister">
			<div class="ym-g50 ym-gl">
			<form  method="post" id="loginForm">
				<h3><spring:message code="title.login"/></h3>
				<dl>
					<dd>
						<span class="title"><spring:message code="title.partner.firmName"/></span><input class="reg_input" name="firmName" id="login_firmName" type="text" title="<spring:message code="tips.partner.firmName.onShow"/>"/>
					</dd>
					<dd>
						<span class="title"><spring:message code="title.userInfo.password"/></span><input class="reg_input" name="password" id="login_password" type="password" title="<spring:message code="tips.passwrod"/>"/>
					</dd>
					<dd style="margin:0 auto;text-align: center;">
						<button onclick="login()"><spring:message code="title.login"/></button>
						<button onclick="location.href='${ctx}/forget_pwd?hl=${param.hl}';"><spring:message code="title.forget.pwd"/></button>
					</dd>
				</dl>
			</form>
			</div>
			<div class="ym-g50 ym-gr">
			<form id="registerForm">
				<h3><spring:message code="title.register.title"/></h3>
				<dl style="margin: 1.2em 6em;">
					<dd>
						<span class="title"><spring:message code="title.partner.firmName"/></span><input class="reg_input" name="firmName" type="text" id="firmName" title="<spring:message code="tips.partner.firmName.onShow"/>"/><span id="firmNameTip" class="onShow"></span>
					</dd>
					<dd>
						<span class="title"><spring:message code="title.partner.city"/></span><input class="reg_input" name="city" id="city" type="text" title="<spring:message code="tips.partner.city.onShow"/>"/><span id="cityTip" class="onShow"></span>
					</dd>
					<dd>
						<span class="title"><spring:message code="title.partner.contactPerson"/></span><input class="reg_input" name="contactPerson" id="contactPerson" type="text" title="<spring:message code="tips.partner.contactPerson.onShow"/>"/><span id="contactPersonTip" class="onShow"></span>
					</dd>
					<dd>
						<span class="title"><spring:message code="title.partner.phone"/></span><input class="reg_input" name="phone" id="phone" type="text" title="<spring:message code="tips.partner.phone.onShow"/>"/><span id="phoneTip" class="onShow"></span>
					</dd>
					<dd>
						<span class="title"><spring:message code="title.partner.addr"/></span><input class="reg_input" name="addr" id="addr" type="text" title="<spring:message code="tips.partner.addr.onShow"/>"/><span id="addrTip" class="onShow"></span>
					</dd>
					<dd>
						<span class="title"><spring:message code="title.userInfo.account"/></span><input class="reg_input" name="email" type="text" id="email" title="<spring:message code="tips.email.onShow"/>"/><span id="emailTip" class="onShow"></span>
					</dd>
					<dd>
						<span class="title"><spring:message code="title.userInfo.password"/></span><input class="reg_input" id="password" name="password" type="password" title="<spring:message code="tips.password.onShow"/>"/><span id="passwordTip" class="onShow"></span>
					</dd>
					<dd>
						<span class="title"><spring:message code="title.userInfo.confirm.password"/></span><input class="reg_input" type="password" id="t_RePass" name="t_RePass" title="<spring:message code="tips.confirm.password.onShow"/>"/><span id="t_RePassTip" class="onShow"></span>
					</dd>
					<dd style="margin:0 auto;text-align: center;">
						<button onclick="register()"><spring:message code="title.register"/></button><button id="reset_reg"><spring:message code="botton.reset" /></button>
					</dd>
				</dl>
			</form>
			</div>
		</div>
	</div>
	<div id="prompt" class="dialog">
		<p></p>
	</div>
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
</body>
</html>
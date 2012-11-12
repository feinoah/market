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
	$('#email').blur(function(){
		var email=$.trim($(this).val());
		if(chkEmail(email)){
			$('#emailTip').attr('class', '').addClass('onLoad');
			this.title='<spring:message code="tips.email.onLoad"/>';
			$(tooltips[4]).tooltip('open');
			$.getJSON(app.name+'/portal/check/account?email='+email, function(data) {
				if (data>0) {
					$('#emailTip').attr('class', '').addClass('onError');
					$(tooltips[4]).tooltip('option', 'content', '<spring:message code="tips.email.onError"/>');
					$(tooltips[4]).tooltip('open');
				} else {
					$('#emailTip').attr('class', '').addClass('onSuccess');
					$(tooltips[4]).tooltip('option', 'content', '<spring:message code="tips.email.onShow"/>');
					$(tooltips[4]).tooltip('close');
				}
			});
		} else {
			$('#emailTip').attr('class', '').addClass('onError');
			$(tooltips[4]).tooltip('option', 'content', '<spring:message code="tips.email.onShow"/>');
			$(tooltips[4]).tooltip('open');
		}
	}).focus(function(){
		$('#emailTip').attr('class', '').addClass('onFocus');
		$(tooltips[4]).tooltip('option', 'content', '<spring:message code="tips.email.onShow"/>');
	});
	$('#nickName').blur(function(){
		var v=$.trim($(this).val());
		if(v.length<3||v.length>15){
			$('#nickNameTip').attr('class', '').addClass('onError');
			$(tooltips[5]).tooltip('option', 'content', '<spring:message code="tips.nickname.onShow"/>');
			$(tooltips[5]).tooltip('open');
		} else{
			$('#nickNameTip').attr('class', '').addClass('onLoad');
			$(tooltips[5]).tooltip('option', 'content', '<spring:message code="tips.nickname.onLoad"/>');
			$(tooltips[5]).tooltip('open');
			$.getJSON(app.name+'/portal/check/nickName?nickName='+v, function(data) {
				if (data>0) {
					$('#nickNameTip').attr('class', '').addClass('onError');
					$(tooltips[5]).tooltip('option', 'content', '<spring:message code="tips.nickname.onError"/>');
					$(tooltips[5]).tooltip('open');
				} else {
					$('#nickNameTip').attr('class', '').addClass('onSuccess');
					$(tooltips[5]).tooltip('option', 'content', '<spring:message code="tips.nickname.onShow"/>');
					$(tooltips[5]).tooltip('close');
				}
			});
		}
	}).focus(function(){
		$('#nickNameTip').attr('class', '').addClass('onFocus');
		$(tooltips[5]).tooltip('option', 'content', '<spring:message code="tips.nickname.onShow"/>');
	});
	$('#password').blur(function(){
		var v=$.trim($(this).val());
		if(v.length<6||v.length>18){
			$('#passwordTip').attr('class', '').addClass('onError');
			$(tooltips[6]).tooltip('option', 'content', '<spring:message code="tips.password.onShow"/>');
			$(tooltips[6]).tooltip('open');
		}else{
			if(chkPwd(v)){
				$('#passwordTip').attr('class', '').addClass('onCorrect');
				$(tooltips[6]).tooltip('option', 'content', '<spring:message code="tips.password.onShow"/>');
				$(tooltips[6]).tooltip('close');
			}else{
				$('#passwordTip').attr('class', '').addClass('onError');
				$(tooltips[6]).tooltip('option', 'content', '<spring:message code="tips.password.onShow"/>');
				$(tooltips[6]).tooltip('open');
			}
		}
	}).focus(function(){
		$('#passwordTip').attr('class', '').addClass('onFocus');
		$(tooltips[6]).tooltip('option', 'content', '<spring:message code="tips.password.onShow"/>');
	});
	$('#t_RePass').blur(function(){
		var pwd=$.trim($(this).val());
		if(pwd){
			if($.trim($('#password').val())==pwd){
				$('#t_RePassTip').attr('class', '').addClass('onCorrect');
				$(tooltips[7]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onShow"/>');
				$(tooltips[7]).tooltip('close');
			}else{
				$('#t_RePassTip').attr('class', '').addClass('onError');
				$(tooltips[7]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onError"/>');
				$(tooltips[7]).tooltip('open');
			}
		}else{
			$('#t_RePassTip').attr('class', '').addClass('onError');
			$(tooltips[7]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onShow"/>');
			$(tooltips[7]).tooltip('open');
		}
	}).focus(function(){
		$('#t_RePassTip').attr('class', '').addClass('onFocus');
		$(tooltips[7]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onShow"/>');
	}).keydown(function(e){
		if(e.which==13){
			var pwd=$.trim($(this).val());
			if(pwd){
				if($.trim($('#password').val())==pwd){
					$('#t_RePassTip').attr('class', '').addClass('onCorrect');
				}  else {
					$('#t_RePassTip').attr('class', '').addClass('onError');
					$(tooltips[7]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onError"/>');
					$(tooltips[7]).tooltip('open');
				}
			} else {
				$('#t_RePassTip').attr('class', '').addClass('onFocus');
				$(tooltips[7]).tooltip('option', 'content', '<spring:message code="tips.confirm.password.onShow"/>');
				$(tooltips[7]).tooltip('open');
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
	$.post('${ctx}/portal/login?email='+$('#login_email').val()+'&password='+$('#login_password').val(), function(data) {
		if(data.answerCode==-3){
			$(tooltips[3]).tooltip('option', 'content', '<spring:message code="tips.login.password.error.max"/>');
			$(tooltips[3]).tooltip('open');
		} else if(data.answerCode==-2){
			$(tooltips[2]).tooltip('option', 'content', '<spring:message code="tips.login.email.locked"/>');
			$(tooltips[2]).tooltip('open');
		} else if(data.answerCode==-1){
			$(tooltips[2]).tooltip('option', 'content', '<spring:message code="tips.login.email.notexist"/>');
			$(tooltips[2]).tooltip('open');
		} else if(data.answerCode==0){
			$(tooltips[3]).tooltip('option', 'content', '<spring:message code="tips.login.password.error"/>');
			$(tooltips[3]).tooltip('open');
		} else if(data.answerCode==1){
			if('${param.call_back}'){
				location.href='${param.call_back}';
			}else{
				location.reload();
			}
		} else {
			$(tooltips[2]).tooltip('option', 'content', '<spring:message code="tips.system.error.unknown"/>');
			$(tooltips[2]).tooltip('open');
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
		$.post('${ctx}/portal/register?email='+$('#email').val()+'&nickName='+$('#nickName').val()+'&password='+$('#password').val(),function(data){
			if(data.answerCode==1){
				location.reload();
			}else if(data.answerCode==-2){
				$(tooltips[8]).tooltip('option', 'content', '<spring:message code="tips.system.error.unknown"/>');
				$(tooltips[8]).tooltip('open');
			}else{
				$(tooltips[8]).tooltip('option', 'content', '<spring:message code="tips.system.exception"/>');
				$(tooltips[8]).tooltip('open');
			}
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
						<span class="title"><spring:message code="title.userInfo.account"/></span><input class="reg_input" name="email" id="login_email" type="text" title="<spring:message code="tips.email.onShow"/>"/>
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
				<dl>
					<dd>
						<span class="title"><spring:message code="title.userInfo.account"/></span><input class="reg_input" name="email" type="text" id="email" title="<spring:message code="tips.email.onShow"/>"/><span id="emailTip" class="onShow"></span>
					</dd>
					<dd>
						<span class="title"><spring:message code="title.userInfo.nickname"/></span><input class="reg_input" name="nickName" id="nickName" type="text" title="<spring:message code="tips.nickname.onShow"/>"/><span id="nickNameTip" class="onShow"></span>
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
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
</body>
</html>
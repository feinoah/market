<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
<style>
/* IE has layout issues when sorting (see #5413) */
.group { zoom: 1 }
</style>
<script type="text/javascript" src="${ctx}/resources/public/scripts/config.js?v1.2"></script>
<script type="text/javascript">
var closePwdWin=false;
var closeAccountWin=false;
$(function (){
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
                	closeAccountWin=false;
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
    $('#person-info').accordion({
        header: '> div > h3',
        collapsible: true,
        heightStyle: 'content',
        event: "click hoverintent"
    });
    var json=$.parseJSON('${obdCurrentDataList}');
    $.each(json,function(i,o){
    	$('#currentDataList').append('<tr><td>'+o.deviceId+'</td><td>'+o.location+'</td><td>'+o.values[16]+'</td><td>'+(o.error!=null?o.error:"")+'</td></tr>');
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
			$.post('${ctx}/portal/account/changepwd?oldPassword='+$('#oldPassword').val()+'&newPassword='+$('#password').val(),function(data){
				if(data==-4){
					location.reload();
				}else if(data==-3){
					$('#prompt p').html('<spring:message code="tips.login.password.error.max"/>');
					$('#prompt').dialog('option','title','<spring:message code="dialog.title.prompt"/>');
					$('#prompt').dialog('open');
				}else if(data==-1){
					$('#prompt p').html('<spring:message code="tips.login.password.error"/>');
					$('#prompt').dialog('option','title','<spring:message code="dialog.title.error"/>');
					$('#prompt').dialog('open');
				}else if(data==1){
					closePwdWin=true;
					$('#prompt p').html('<spring:message code="dialog.success.message" />');
					$('#prompt').dialog('option','title','<spring:message code="dialog.title.prompt"/>');
					$('#prompt').dialog('open');
				}else{
					$('#prompt p').html('<spring:message code="tips.system.error.unknown"/>');
					$('#prompt').dialog('option','title','<spring:message code="dialog.title.error"/>');
					$('#prompt').dialog('open');
				}
			});
		}
    });
    $('#reset_pwd').click(function(){
    	$('#updatePwd input').val('');
    });
    $('#nickName').change(function(){
    	var v=$.trim($(this).val());
		if(v.length<3||v.length>15){
			$(tooltips[5]).tooltip('option', 'content', '<spring:message code="tips.nickname.onShow"/>');
			$(tooltips[5]).tooltip('open');
		} else{
			$.getJSON(app.name+'/portal/check/nickName?nickName='+v, function(data) {
				if (data>0) {
					$(tooltips[5]).tooltip('option', 'content', '<spring:message code="tips.nickname.onError"/>');
					$(tooltips[5]).tooltip('open');
				}
			});
		}
    });
    $('#update_account_btn').click(function(){
    	var submit=true;
    	var nickName=$.trim($('#nickName').val());
		if(nickName==''||nickName.length<3||nickName.length>15){
			$(tooltips[5]).tooltip('option', 'content', '<spring:message code="tips.nickname.onShow"/>');
			$(tooltips[5]).tooltip('open');
			submit=false;
		}
    	if(submit){
    		$.post('${ctx}/portal/account/modify?nickName='+nickName+'&gender='
    				+$('#gender').val()+'&realName='+$('#realName').val()
    				+'&idCard='+$('#idCard').val()+'&mobile='+$('#mobile').val()
    				,function(data){
					if(data.answerCode==-2){
						location.reload();
					}else if(data.answerCode==-1){
						$('#prompt p').html('<spring:message code="tips.system.error.unknown"/>');
						$('#prompt').dialog('option','title','<spring:message code="dialog.title.error"/>');
						$('#prompt').dialog('open');
					}else if(data.answerCode==1){
						closeAccountWin=true;
						$('#prompt p').html('<spring:message code="dialog.success.message" />');
						$('#prompt').dialog('option','title','<spring:message code="dialog.title.prompt"/>');
						$('#prompt').dialog('open');
						account=map.portalUser;
					}else{
						$('#prompt p').html('<spring:message code="tips.system.exception"/>');
						$('#prompt').dialog('option','title','<spring:message code="dialog.title.error"/>');
						$('#prompt').dialog('open');
					}
        	});
    	}
    });
    $('#reset_account').click(function(){
    	$('#updateAccount input:not(:first)').val('');
    });
});
</script>
</head>
<body>
    <%@ include file="/WEB-INF/views/commons/header.jsp"%>
	<div id="main" class="ym-grid">
		<jsp:include page="/WEB-INF/views/commons/left-sidebar.jsp"
			flush="true">
			<jsp:param value="1" name="index" />
		</jsp:include>
		<div class="ym-g85 ym-gl main-container">
			<div id="person-info">
				<div class="group">
					<h3><spring:message code="title.userInfo.basic" /></h3>
					<ul>
						<li><label><spring:message code="title.userInfo.account" /></label><span>${portalUser.email}</span><a href="javascript:$('#updatePwd').dialog('open');"><spring:message code="title.userInfo.update.pwd" /></a></li>
						<li><label><spring:message code="title.userInfo.nickname" /></label><span>${portalUser.nickName}</span><a href="javascript:$('#updateAccount').dialog('open');"><spring:message code="title.userInfo.update" /></a></li>
					</ul>
				</div>
				<div class="group">
					<h3><spring:message code="title.userInfo.device" /></h3>
					<table id="currentDataList">
						<tr><th><spring:message code="title.userInfo.device.id" /></th><th><spring:message code="title.userInfo.device.currLocation" /></th><th><spring:message code="title.userInfo.device.mileage" /></th><th><spring:message code="title.userInfo.device.faultMsg" /></th></tr>
					</table>
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
				<label><spring:message code="title.userInfo.nickname" /></label><input type="text" id="nickName" class="reg_input" value="${portalUser.nickName}" title="<spring:message code="tips.password.onShow"/>"/>
			</dd>
			<dd><label><spring:message code="title.userInfo.gender" /></label>
				<select name="gender" id="gender" class="reg_input">
					<option value="0" <c:if test="${portalUser.gender eq 0}">selected="selected"</c:if>><spring:message code="title.userInfo.gender.female" /></option>
					<option value="1" <c:if test="${portalUser.gender eq 1}">selected="selected"</c:if>><spring:message code="title.userInfo.gender.male" /></option>
					<option value="2" <c:if test="${portalUser.gender eq 2}">selected="selected"</c:if>><spring:message code="title.userInfo.gender.privary" /></option>
				</select>
			</dd>
			<dd><label><spring:message code="title.userInfo.realname" /></label><input class="reg_input" id="realName" type="text" value="${portalUser.realName}"/></dd>
			<dd><label><spring:message code="title.userInfo.idCard" /></label><input class="reg_input" name="idCard" id="idCard" type="text" value="${portalUser.idCard}"/></dd>
			<dd><label><spring:message code="title.userInfo.mobile" /></label><input class="reg_input" name="mobile" id="mobile" type="text" value="${portalUser.mobile}"/></dd>
			<dd style="margin:0 auto;text-align: center;">
				<button id="update_account_btn"><spring:message code="botton.confirm" /></button><button id="reset_account"><spring:message code="botton.reset" /></button>
			</dd>
		</dl>
	</div>
	<div id="prompt" class="dialog">
		<p></p>
	</div>
</body>
</html>
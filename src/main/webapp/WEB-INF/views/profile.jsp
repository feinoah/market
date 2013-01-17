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
<script type="text/javascript" src="${ctx}/resources/jquery-easyui-1.3/plugins/jquery.form.js"></script>
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
    $('#person-info').tabs({
        event:'click'
    });
    var json=$.parseJSON('${equipmentList}');
    $.each(json,function(i,o){
    	$('#equipmentList').append('<tr><td>'+o.deviceId+'</td></tr>');
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
						location.reload();
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

function uploadPhoto(o){
	$('#photoForm').form({
		dataType:'text',  
		onSubmit:function(){
			var name=$(o).val().toLowerCase();
			var types=$(o).attr('fileType').toLowerCase()
			var tArray=types.split('|');
			var fArray=name.split('.');
			var suffix=fArray[fArray.length-1];
			var tag=false;
			for(var i in tArray){
				if(suffix==tArray[i]){
					tag=true;
				}
			}
			if(!tag){
				$('#prompt p').html('<spring:message code="dialog.upload.fileType.error"/>');
				$('#prompt').dialog('option','title','<spring:message code="dialog.title.error"/>');
				$('#prompt').dialog('open');
			} else {
				$('#photoTxt').val(name);
			}
			return tag;
		},
		success:function(data){
			var map=$.parseJSON(data);
			if(map){
				if(map.answerCode==-2){
					location.reload();
				}
				if(map.answerCode==-1){
					$('#prompt p').html('<spring:message code="dialog.upload.fail"/>');
					$('#prompt').dialog('option','title','<spring:message code="dialog.title.prompt"/>');
					$('#prompt').dialog('open');
				}
				if(map.answerCode==1){
					$('#prompt p').html('<spring:message code="dialog.success.message" />');
					$('#prompt').dialog('option','title','<spring:message code="dialog.title.prompt"/>');
					$('#prompt').dialog('open');
					$('#photo').attr('src',map.photo);
					account.thumbPhoto=map.thumbPhoto;
					$('#photoTxt').val('200*200 jpg|png');
				}
			}else{
				$('#prompt p').html('<spring:message code="tips.system.exception"/>');
				$('#prompt').dialog('option','title','<spring:message code="dialog.title.error"/>');
				$('#prompt').dialog('open');
			}
	    }
	}).submit();
}
</script>
</head>
<body>
    <%@ include file="/WEB-INF/views/commons/header.jsp"%>
	<div id="main" class="ym-grid">
		<jsp:include page="/WEB-INF/views/commons/left-sidebar.jsp"
			flush="true">
			<jsp:param value="1" name="index" />
		</jsp:include>
		<div class="ym-g80 ym-gl main-container">
			<div id="person-info">
		    <ul>
		        <li><a href="#tabs-1"><spring:message code="title.userInfo.basic" /></a></li>
		        <li><a href="#tabs-2"><spring:message code="title.userInfo.device" /></a></li>
		    </ul>
		    <div id="tabs-1">
		        <ul class="ym-g66 ym-gl">
					<li><label><spring:message code="title.userInfo.account"/></label><span>${portalUser.email}</span><a href="javascript:$('#updatePwd').dialog('open');"><spring:message code="title.userInfo.update.pwd" /></a></li>
					<li><label><spring:message code="title.userInfo.nickname" /></label><span>${portalUser.nickName}</span><a href="javascript:$('#updateAccount').dialog('open');"><spring:message code="title.userInfo.update" /></a></li>
			    	<li><label><spring:message code="title.userInfo.gender" /></label><span><c:if test="${portalUser.gender eq 0}"><spring:message code="title.userInfo.gender.female" /></c:if>
						<c:if test="${portalUser.gender eq 1}"><spring:message code="title.userInfo.gender.male" /></c:if>
						<c:if test="${portalUser.gender eq 2}"><spring:message code="title.userInfo.gender.privary" /></c:if></span></li>
			    	<li><label><spring:message code="title.userInfo.realname" /></label><span>${portalUser.realName}</span></li>
					<li><label><spring:message code="title.userInfo.idCard" /></label><span>${portalUser.idCard}</span></li>
					<li><label><spring:message code="title.userInfo.mobile" /></label><span>${portalUser.mobile}</span></li>
				</ul>
				<div class="ym-g33 ym-gr">
					<img id="photo" alt="" src="${portalUser.photo}">
					<form action="${ctx}/portal/account/changephoto" id="photoForm" method="post" enctype="multipart/form-data">
					<div class="fileinputs">
						<input type="file" class="file" name="file" id="photo" fileType='jpg|png' onchange="uploadPhoto(this)"/>  
						<div class="fakefile">  
							<input type="text" value="200*200 jpg|png" id="photoTxt" class="reg_input" style="width:125px;"/><button id="uploadImg"><spring:message code="botton.browse" /></button>
						</div>  
					</div>
					</form>
				</div>
		    </div>
		    <div id="tabs-2">
		        <table id="equipmentList">
					<tr><th><spring:message code="title.userInfo.device.id" /></th><!-- <th><spring:message code="title.userInfo.device.currLocation" /></th><th><spring:message code="title.userInfo.device.mileage" /></th><th><spring:message code="title.userInfo.device.faultMsg" /></th>--></tr>
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
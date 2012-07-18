$(function() {
	chkLogin();
	$('#logout').click(function(){
		$.getJSON(app.name+'/portal/logout', function(data) {
			if (data==1) {
				$('#loginBefor').show();
				$('#lolginAfter').hide();
				$('#welcome').empty().attr('userId','');
				account={};
			}
		});
	});
	$('#search').click(function(){
		var key=$('#search_input').val();
		if(key){
			window.open(app.name+"/html/searchApp.html?inputStr=" + key);//传入参数
		} else {
			tips('search_input','请输入关键字','bottom');
		}
	});
	$('#search_input').keydown(function(e){
		if(e.which==13){
			var key=$('#search_input').val();
			if(key){
				window.open(app.name+"/html/searchApp.html?inputStr=" + key);//传入参数
			} else {
				tips('search_input','请输入关键字','bottom');
			}
		}
	}).click(function(){
		Util.Dialog.remove('Tip_tips');
	}).change(function(){Util.Dialog.remove('Tip_tips');});
	$('#account_profile').click(function(){
		accountWin();Util.Dialog.remove('profile_tips');
	});
	$('#account_chgpwd').click(function(){
		chPwdWin();Util.Dialog.remove('profile_tips');
	});
	$('#message').click(messageTip);
});

var chkLogin=function(){
	$.getJSON(app.name+'/portal/account/login/check', function(data) {
		if (data) {
			account=data;
			$('#loginBefor').hide();
			$('#lolginAfter').show();
			$('#welcome').html(account.nickName).click(profileTip);
			$('#thumb_photo').click(profileTip);
			if(account.thumbPhoto){
				$('#thumb_photo').css({
					'background':'url('+app.name+'/'+account.thumbPhoto+')',
					'background-position':''
				});
			} else {
				$('#thumb_photo').css({
					'background':'url('+app.name+'/resources/public/images/h_bedf916a.png)',
					'background-position':'-98px -172px'
				});
			}
			Util.Dialog.remove('Tip_tips');
			$.getJSON(app.name+'/portal/app/user/unread/sysmessage/count/'+account.id, function(data) {
				$('#message').html(data);
				if(data>0){
					$('#message').css({
						'background':'url('+app.name+'/resources/public/images/h_bedf916a.png)',
						'background-position':'1px -270px'
					});
				}
			});
		}
	});
};

var login=function(){
	$('#loginForm').form({
		url:app.name+'/portal/login',
		dataType : 'text',  
		onSubmit:function(){
			var tag=true;
			$('#loginForm span[id$="Tip"]').each(function(){
				if(!$(this).hasClass('onCorrect')&&!$(this).hasClass('onSuccess')){
					tag=false;
					$('#'+$(this).attr('id').substring(0, $(this).attr('id').indexOf('Tip'))).blur();
					return false;
				}
			});
			return tag;
		}, 
		success:function(data){
			if(data){
				var map=$.parseJSON(data);
				if(map){
					if(map.answerCode==-3){
						tips('password','密码错误次数超过限制，半小时内限制登录','bottom');
					}
					if(map.answerCode==-2){
						tips('email','账号被锁定','bottom');
					}
					if(map.answerCode==-1){
						tips('email','账号不存在','bottom');
					}
					if(map.answerCode==0){
						tips('password','密码错误','bottom');
					}
					if(map.answerCode==1){
						account=map.portalUser;
						$('#loginBefor').hide();
						$('#lolginAfter').show();
						$('#welcome').html(account.nickName).click(profileTip);
						$('#thumb_photo').click(profileTip);
						if(account.thumbPhoto){
							$('#thumb_photo').css({
								'background':'url('+app.name+'/'+account.thumbPhoto+')',
								'background-position':''
							});
						} else {
							$('#thumb_photo').css({
								'background':'url('+app.name+'/resources/public/images/h_bedf916a.png)',
								'background-position':'-98px -172px'
							});
						}
						$.getJSON(app.name+'/portal/app/user/unread/sysmessage/count/'+account.id, function(data) {
							$('#message').html(data);
							if(data>0){
								$('#message').css({
									'background':'url('+app.name+'/resources/public/images/h_bedf916a.png)',
									'background-position':'1px -270px'
								});
							}
						});
						Util.Dialog.remove('loginWin');
						Util.Dialog.remove('Tip_tips');
					}
				}else{
					tips('button','后台系统异','bottom');
				}
			}else{
				tips('button','后台系统异','bottom');
			}
		}
	}).submit();
};
var reg=function(){
	$('#regForm').form({
		url:app.name+'/portal/register',
		dataType : 'text',  
		onSubmit:function(){
			var tag=true;
			$('#regForm span[id$="Tip"]').each(function(){
				if(!$(this).hasClass('onCorrect')&&!$(this).hasClass('onSuccess')){
					tag=false;
					$('#'+$(this).attr('id').substring(0, $(this).attr('id').indexOf('Tip'))).blur();
					return false;
				}
			});
			return tag;
	    }, 
    	success:function(data){
			if(data){
				var map=$.parseJSON(data);
				if(map){
					if(map.answerCode==-2){
						tips('button','未知错误','bottom');
					}
					if(map.answerCode==1){
						//tips('button','注册成功','bottom');
						account=map.portalUser;
						Util.Dialog.remove('regWin');
						chkLogin();//切换登录状态
					}
				}else{
					tips('button','后台系统异','bottom');
				}
			}else{
				tips('button','后台系统异','bottom');
			}
	    }
	}).submit();
};
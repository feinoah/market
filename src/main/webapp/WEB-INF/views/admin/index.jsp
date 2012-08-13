<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title>中通福瑞应用市场——后台管理</title>
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/default.js" ></script>
		<script>
		var fieldList;
		$(function(){
			$.ajaxSettings.async = false;
			$.getJSON('${ctx}/back/field/query/gender', function(data) {
				if(data){
					fieldList=data;
				}
			});
			$.ajaxSettings.async = true;
			$('#tt').tree({
				method:'get',
				url: '${ctx}/back/menu/tree',
				onClick: function(node){
					if(node.attributes.url&&node.attributes.url!=null){
						addTab(node.text, '${ctx}'+node.attributes.url);
					}
				},
				onLoadSuccess: function(node,data){
					if(data==null||data==''||data==undefined){
						location.href='${ctx}/back/logout';
					}
				}
			});
			var p = $('body').layout('panel','west').panel({
				onCollapse:function(){
					//alert('collapse');
				}
			});
			setTimeout(function(){
				$('body').layout('collapse','east');
			},0);
			$('#logout').click(function(){
				location.href='${ctx}/back/logout';
			});
			$('#genderLabel').html(function(){
				var result='-';
				$.each(fieldList, function(key,val) {
					if('${adminUser.gender}'==val.fieldValue){
						result=val.displayValue;
						return false;
					}
				});
				return result;
			});
			$('#aa').accordion('select','基本信息');
			$('#nickName_edit').change(function(){
				if($(this).val()!=''&&'${adminUser.nickName}'!=$(this).val()){
					$.getJSON('${ctx}/back/permission/user?nickName='+$(this).val(), function(data) {
						if(data>0){
							$.messager.alert('提示','重复记录，请修正!','info');
							$('#nickName_edit').val('');
						}
					});
				}
			});
			$('#gender_edit').combobox({
				data:fieldList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
			});
			$('#gender_edit').combobox('setValue','${adminUser.gender}');
			$('#editForm input').click(function(){
				$(this).select();
			});
			$('#submit').click(function(){
				$('#editForm').form({
					onSubmit:function(){
						var b=$(this).form('validate');
						if(b){
							$.messager.progress({title:'请稍后',msg:'提交中...'});
						}
						return b;
					},
					success:function(data){
						$.messager.progress('close');
						if(data==-3){
							$.messager.alert('错误', '未知错误', 'error');
						} else if(data==-2){
							$.messager.confirm('提示','登录超时，是否重新登录?',function(data) {
								if (data) {
									location.href='${ctx}/admin';
								}
							});
						} else if(data==-1){
							$.messager.alert('错误', '编辑失败', 'error');
						} else if (data==1){
							$.messager.confirm('提示','编辑成功',function(data) {
								if (data) {
									location.reload();
								}
							});
						} else {
							$.messager.alert('异常','后台系统异常','error');
						}
					}
				}).submit();
			});
			$('#reset').click(function(){ $('#editForm').form('clear');});
			$('#submit_pwd').click(function(){
				$('#updatePwdForm').form({
					onSubmit:function(){
						var b=$(this).form('validate');
						if(b){
							$.messager.progress({title:'请稍后',msg:'提交中...'});
						}
						return b;
					},
					success:function(data){
						$.messager.progress('close');
						if(data==-4){
							$.messager.confirm('提示','登录超时，是否重新登录?',function(data) {
								if (data) {
									location.href='${ctx}/admin';
								}
							});
						} else if(data==-2){
							$.messager.alert('错误', '账号不存在', 'error');
						} else if(data==-1){
							$.messager.alert('错误', '原始密码错误', 'error');
						} else if (data==1){
							$.messager.alert('提示', '修改成功', 'info');
						} else {
							$.messager.alert('异常','后台系统异常','error');
						}
					}
				}).submit();
			});
			$('#reset_pwd').click(function(){ $('#updatePwdForm').form('clear');});
		});
		</script>
	</head>

	<body class="easyui-layout">
		<div region="north" border="false" style="height:50px;overflow:hidden;">
			<div style="padding:10px;text-align: right;">
				<span>${adminUser.nickName}，您上次登录时间是：${adminUser.lastLoginTime}，IP地址是：${adminUser.lastLoginIp}</span>
				<button id="logout">退 出</button>
			</div>
		</div>
		<div region="west" split="true" title="导航菜单" style="width: 180px;overflow:hidden;" icon="icon-redo">
	        <div id="menu" class="easyui-accordion" fit="true" border="false">
	        	<ul id="tt" class="easyui-tree"></ul>
	        	<!-- 
	            <div title="系统管理" style="overflow:auto;" icon="icon-edit">
                    <ul>
                        <li>
                             <div><a target="mainFrame" href="<c:url value="/admin/user"/>">用户管理</a></div>
                             <div><a target="mainFrame" href="<c:url value="/admin/app/catalog"/>">类别管理</a></div>
                        </li>
                    </ul>
	            </div>
	            <div title="产品管理" style="padding: 10px;" icon="icon-edit">
                    <ul>
                        <li>
                            <div>
                                <a target="mainFrame" href="#">产品管理</a></div>
                        </li>
                    </ul>
	            </div>
	            <div title="关于" icon="icon-help">
	                <h4>EntWebSite Ver 1.0</h4>
	            </div>
	        	 -->
	        </div>
	    </div>
	    <!-- 
		<div region="east" split="true" title="East" style="width:180px;padding:10px;">east region</div>
	     -->
		<div region="center" id="mainPanle" style="background: #eee;overflow:hidden;">
	        <div id="tabs" class="easyui-tabs" fit="true" border="false">
	            <div title="我的主页" style="padding: 20px;" id="home">
	            <div id="aa" class="easyui-accordion">
					<div title="基本信息" iconCls="icon-ok" style="overflow:auto;padding:10px;">
						<h3 style="color:#0099FF;">${adminUser.nickName}</h3>
						<h3 style="color:#0099FF;">邮箱：<label style="color:#000000;">${adminUser.email}</label></h3>
						<h3 style="color:#0099FF;">真实姓名：<label style="color:#000000;">${adminUser.realName}</label></h3>
						<h3 style="color:#0099FF;">性别：<label style="color:#000000;" id="genderLabel"></label></h3>
						<h3 style="color:#0099FF;">手机号码：<label style="color:#000000;"><c:if test="${adminUser.mobile!=0}">${adminUser.mobile}</c:if></label></h3>
						<h3 style="color:#0099FF;">办公电话：<label style="color:#000000;">${adminUser.officePhone}</label></h3>
						<h3 style="color:#0099FF;">备注：</h3>
						<p>${adminUser.remark}</p>
					</div>
					<div title="编辑" iconCls="icon-edit" selected="true">
						<form action="${ctx}/back/user/update" id="editForm" method="post" style="width: 600px;">
						<table>
							<tr>
								<td style="color:#0099FF;">昵称：</td>
								<td><input type="text" name="nickName" id="nickName_edit" value="${adminUser.nickName}" class="easyui-validatebox" required="true" /></td>
								<td style="color:#0099FF;">真实姓名：</td>
								<td><input type="text" name="realName" value="${adminUser.realName}"/></td>
							</tr>
							<tr>
								<td style="color:#0099FF;">手机号码：</td>
								<td><input type="text" name="mobile" value="<c:if test="${adminUser.mobile!=0}">${adminUser.mobile}</c:if>"  class="easyui-validatebox" validType="mobile"/></td>
								<td style="color:#0099FF;">办公电话：</td>
								<td><input type="text" name="officePhone" value="${adminUser.officePhone}"/></td>
							</tr>
							<tr>
								<td style="color:#0099FF;">性别：</td>
								<td><input type="text" name="gender" id="gender_edit" required="true" /></td>
							</tr>
							<tr>
								<td style="color:#0099FF;">备注：</td>
								<td colspan="3">
									<input type="text" name="remark" id="remark" value="${adminUser.remark}" style="width: 445px;" class="easyui-validatebox" validType="maxLength[50]" maxLen="50" msg="备注"/>
								</td>
							</tr>
						</table>
						<div style="text-align: center; padding: 5px;">
							<a href="javascript:void(0);" class="easyui-linkbutton" id="submit"
								iconCls="icon-save">保 存</a>
							<a href="javascript:void();" class="easyui-linkbutton" id="reset"
								iconCls="icon-undo">重 置</a>
						</div>
						</form>
					</div>
					<div title="修改密码" iconCls="icon-edit" style="padding:10px;">
						<form action="${ctx}/back/user/changepwd" method="post" id="updatePwdForm" style="width: 400px;">
						<table>
							<tr>
								<td style="color:#0099FF;">原密码：</td>
								<td><input type="password" name="oldPassword" id="oldPassword" class="easyui-validatebox" required="true" /></td>
							</tr>
							<tr>
								<td style="color:#0099FF;">新密码：</td>
								<td><input type="password" name="password" id="password" class="easyui-validatebox" required="true" validType="safepass"/></td>
							</tr>
							<tr>
								<td style="color:#0099FF;">确认密码：</td>
								<td><input type="password" name="repwd" id="repwd" class="easyui-validatebox" required="true" validType="equalTo['#password']"/></td>
							</tr>
						</table>
						<div style="text-align: center; padding: 5px;">
							<a href="javascript:void(0);" class="easyui-linkbutton" id="submit_pwd"
								iconCls="icon-save">保 存</a>
							<a href="javascript:void();" class="easyui-linkbutton" id="reset_pwd"
								iconCls="icon-undo">重 置</a>
						</div>
						</form>
					</div>
				</div>
	            </div>
	        </div>
    	</div>
		<div region="south" border="false" style="height:65px;background:#A9FACD;padding:5px 10px;">
			<h5>版权所有</h5>
		</div>
		<noscript>
	        <div style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px;
	            width: 100%; background: white; text-align: center;">
	            <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
	        </div>
	    </noscript>
	</body>
</html>

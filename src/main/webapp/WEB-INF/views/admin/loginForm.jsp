<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
		<title>CarIt Market</title>
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript">
		$(function (){
			$('#loginWin').window('open');
			$('#loginWin').window('open');
			$('#submit').click(function(){
				$('#loginForm').form({
			    	success:function(data){
			    		if(data==-2){
							$.messager.alert('错误', "密码错误次数太多，半小时内限制登录", 'error');
			    		} else if(data==-1){
							$.messager.alert('错误', "用户不存在！", 'error');
						}else if(data==0){
			    			$.messager.alert('错误', "密码错误！", 'error');
						}else if(data==1){
							//登录成功
							location.href='${ctx}/admin'
						} else {
							$.messager.alert('异常', "后台系统异常", 'error');
						}
				    }
				}).submit();
			});
			$('#reset').bind('click',function(){ $('#loginForm').form('clear');});
		});
		</script>
	</head>
	<body>
		<div id="loginWin" class="easyui-window" title="登录后台" closed="true" style="width:600px;height:310px;padding:5px;">
		<form:form 
			action="${ctx}/back/login"
			method="post"  id="loginForm">
			<table>
				<tr>
					<td>
						<label for="email" path="email">邮箱：</label>
					</td>
					<td>
						<input type="text" name="email" class="easyui-validatebox" required="true" validType="email"/>
					</td>
				</tr>
				<tr>
					<td>
						<label  for="password" path="password">密码：</label>
					</td>
					<td>
						<input type="password" name="password" class="easyui-validatebox"  required="true" validType="length[6,20]"/>
					</td>
				</tr>
			</table>
		</form:form>
		<div style="text-align: center; padding: 5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" id="submit"
					iconCls="icon-search">登 录</a>
				<a href="javascript:void();" class="easyui-linkbutton" id="reset"
					iconCls="icon-undo">重 置</a>
			</div>
		</div>
	</body>
</html>
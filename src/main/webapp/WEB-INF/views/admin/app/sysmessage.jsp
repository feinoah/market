<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js?v=v=v=1.6" ></script>
		<script charset="utf-8" src="${ctx}/resources/kindeditor-4.1.1/kindeditor-min.js"></script>
		<script charset="utf-8" src="${ctx}/resources/kindeditor-4.1.1/lang/zh_CN.js"></script>
		<script type="text/javascript">
		var editor;
		var accounts=[];
		<c:forEach items="${accountList}" var="account">var account={};account.id='${account.id}';account.nickName='${account.nickName}';accounts.push(account);</c:forEach>
		$(function(){
			checkEditControl('${ctx}/back/permission/account?baseUri=/admin/app/sysmessage');
			$.each($('.datagrid-toolbar a'),function(i,m){
				if(i==1){
					$(this).hide();//没有修改
					return false;
				}
			});
			$('.combobox-f').each(function(){
				$(this).combobox('clear');
			});
			$('#accountId').combobox({
				data:accounts,
				editable:false,
				valueField:'id',
				textField:'nickName'
			});
			$('#accountIds').combobox({
				data:accounts,
				editable:false,
				multiple:true,
				valueField:'id',
				textField:'nickName'
			});
			editor=simpleEditer('content');
			// edit form
			$('#edit_submit_app').bind('click',function(){
				$('#editForm').form({
					onSubmit:function(){
						editor.sync();
						// 避免 form validate bug
						$('.combobox-f').each(function(){
							$(this).val($(this).combobox('getText'));
						});
						if(getStrLen($.trim(editor.html()))>800){
							$.messager.alert('提示', '消息内容超出800字符(包含HTML标签)', 'info');
							return false;
						}
						var b=$(this).form('validate');
						if(b){
							$.messager.progress({title:'请稍后',msg:'提交中...'});
						}
						return b;
				    }, 
			    	success:function(data){
			    		$.messager.progress('close');
			    		if(data==-1){
							$.messager.alert('错误', "编辑失败", 'error');
			    		} else if(data>0){
							$.messager.alert('成功', "编辑成功", 'info');
				        	$('#editWin').window('close');
				        	// update rows
				        	$('#tt').datagrid('reload');
						}else{
			    			$.messager.alert('异常', "后台系统异常", 'error');
						}
				    }
				}).submit();
			});
			$('#editWin').window({onClose:function(){
				editor.html('');
				$('#content').val('');
			}});
		});
		function edit() {
			return;
		}
		
		function msgAccountFormatter(v){
			var result=v
			$.each(accounts,function(i,o){
				if(o.id==v){
					result=o.nickName;
					return false;
				}
			});
			return result;
		}
		
		function msgCatalogFormatter(v){
			if(v==1){return '软件更新';}else{return '系统推送';}
		}

		function msgStatusFormatter(v){
			if(v==1){return '已读';}else{return '未读';}
		}
		</script>
		<style>
		#editWin label {width: 115px;}
		</style>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="systemMessage"
			action="${ctx}/admin/app/sysmessage/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td>
						<form:label for="title" path="title">标题：</form:label>
					</td>
					<td>
						<form:input path="title"/>
					</td>
					<td>
						<form:label for="accountId" path="accountId">账号：</form:label>
					</td>
					<td>
						<form:input path="accountId"/>
					</td>
					<td>
						<form:label for="catalog" path="catalog">类别：</form:label>
					</td>
					<td>
						<form:select path="catalog" cssClass="easyui-combobox">
							<form:option value="0">-系统推送-</form:option>
							<form:option value="1">-应用更新-</form:option>
						</form:select>
					</td>
					<td>
						<form:label for="status" path="status">状态：</form:label>
					</td>
					<td>
						<form:select path="status" cssClass="easyui-combobox">
							<form:option value="0">-未读-</form:option>
							<form:option value="1">-已读-</form:option>
						</form:select>
					</td>
				</tr>
			</table>
		</form:form>
		<div style="text-align: center; padding: 5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" id="submit"
					iconCls="icon-search">查 询</a>
				<a href="javascript:void();" class="easyui-linkbutton" id="reset"
					iconCls="icon-undo">重 置</a>
			</div>
			<table id="tt" style="height: auto;" iconCls="icon-blank" title="系统消息列表" align="left"   
			idField="id" url="${ctx}/admin/app/sysmessage/query" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="updateTime" sortOrder="desc">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th field="accountId" width="150" align="center" formatter="msgAccountFormatter">账号</th>
						<th field="title" width="150" align="center">标题</th>
						<th field="content" width="200" align="center">内容</th>
						<th field="catalog" width="100" align="center" formatter="msgCatalogFormatter">类别</th>
						<th field="status" width="60" align="center" formatter="msgStatusFormatter">状态</th>
						<th field="createTime" width="100" align="center">创建时间</th>
						<th field="updateTime" width="100" align="center">更新时间</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="系统消息" closed="true" style="width:510px;height:360px;padding:5px;" modal="true">
			<form:form modelAttribute="systemMessage" id="editForm" action="${ctx}/admin/app/sysmessage/save" method="post" cssStyle="padding:10px 20px;">
				<table>
					<tr>
						<td><form:label	for="title" path="title"  cssClass="mustInput">标题：</form:label></td>
						<td><form:input path="title" id="title_edit" cssClass="easyui-validatebox" required="true" cssStyle="width: 370px;"/></td>
					</tr>
					<tr>
						<td><form:label	for="accountIds" path="accountIds">账号：</form:label></td>
						<td><form:input path="accountIds" cssStyle="width: 370px;"/></td>
					</tr>
					<tr>
						<td><form:label	for="content" path="content" cssClass="easyui-validatebox">内容：</form:label></td>
					</tr>
					<tr>
						<td colspan="2">
							<textarea name="content" style="width: 420px;height: 80px;"></textarea>
						</td>
					</tr>
				</table>
				<form:hidden path="id"/>
				<div style="text-align: center; padding: 5px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_submit_app"
						iconCls="icon-save">保 存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_reset"
						iconCls="icon-undo">重 置</a>
				</div>
			</form:form>
		</div>
	</body>
</html>
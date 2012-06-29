<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js?v=1.5" ></script>
		<script type="text/javascript">
		$(function(){
			checkEditControl('${ctx}/back/permission/account?baseUri=/admin/app/comment');
			$('.datagrid-toolbar a:first').hide();//没有新增
			$('#status').combobox({
				data:statusList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
			});
			$('#status_edit').combobox({
				data:statusList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
			});
			$('.combobox-f').each(function(){
				$(this).combobox('clear');
			});
			
			$('span[id^=star_sp]').each(function(i){
				$(this).click(function(){
					if(i%2==0){
						$('#star_sp'+i).removeClass('starBright_left').addClass('starDark_left');
					}else{
						$('#star_sp'+i).removeClass('starBright_right').addClass('starDark_right');
					}
					$('span[id^=star_sp]').each(function(k){
						if(k<=i){//3
							if(k%2==0){
								$('#star_sp'+ k).removeClass('starDark_left').addClass('starBright_left');
								}
							else{
								$('#star_sp'+ k).removeClass('starDark_right').addClass('starBright_right');
							}
						}
					});
					$('#grade_edit').val((i+1));
				});
			});
		});
		function edit() {
			var m = $('#tt').datagrid('getSelected');
			if (m) {
				$('#editForm input').each(function(){
					$(this).removeClass('validatebox-invalid');
				});
				$('#editWin').window({title:'修改'+winTitle,iconCls:'icon-edit'});
				$('#editWin').window('open');
				$('#appName_edit').html(m.appName);
				$('#enName_edit').html(m.enName);
				$('#userName_edit').html(m.userName);
				$('#appId_edit').val(m.appId);
				$('#userId_edit').val(m.userId);
				$('#grade_edit').val(m.grade);
				if(m.grade){
					$('span[id^=star_sp]').each(function(i){
						if(i<m.grade){
							if(i%2==0){
								$('#star_sp'+i).removeClass('starDark_left').addClass('starBright_left');
								}
							else{
								$('#star_sp'+i).removeClass('starDark_right').addClass('starBright_right');
							}
						}
					});
				}
				$('#status_edit').combobox('setValue',m.status);
				$('#editForm textarea').val(m.comment);
				$('#id').val(m.id);
				$('#editWin').show();
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选择要修改的记录。'
				});
			}
		}

		</script>
		<style>
		#editWin label {width: 115px;}
		#editWin input {width: 180px;}
		#editWin select {width: 185px;}
		#editWin textarea {width: 450px;height: 80px;}
		</style>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="appComment"
			action="${ctx}/admin/app/comment/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td><form:label for="appName" path="appName">应用名称：</form:label></td>
					<td><form:input path="appName" /></td>
					<td><form:label for="enName" path="enName">英文名称：</form:label></td>
					<td><form:input path="enName" /></td>
					<td><form:label for="userName" path="userName">评论人：</form:label></td>
					<td><form:input path="userName" /></td>
				</tr>
				<tr>
					<td><form:label for="grade" path="grade">等级：</form:label></td>
					<td><form:input path="grade" cssClass="easyui-numberspinner"  min="1" max="5"/></td>
					<td><form:label for="status" path="status">状态：</form:label></td>
					<td>
						<form:input path="status" />
					</td>
					<td><form:label for="comment" path="comment">内容：</form:label></td>
					<td><form:input path="comment" /></td>
				</tr>
			</table>
		</form:form>
		<div style="text-align: center; padding: 5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" id="submit"
					iconCls="icon-search">查 询</a>
				<a href="javascript:void();" class="easyui-linkbutton" id="reset"
					iconCls="icon-undo">重 置</a>
			</div>
			<table id="tt" style="height: auto;" iconCls="icon-blank" title="评论列表" align="left" singleSelect="true" 
			idField="id" url="${ctx}/admin/app/comment/query?appId=${param.appId}" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="updateTime" sortOrder="desc">
				<thead>
					<tr>
						<th field="appName" width="100" align="center" >应用名称</th>
						<th field="enName" width="100" align="center" >英文名称</th>
						<th field="userName" width="100" align="center" >评论人</th>
						<th field="grade" width="60" align="center" formatter="gradeFormatter">等级</th>
						<th field="comment" width="200" align="center">内容</th>
						<th field="status" width="60" align="center" formatter="statusFormatter">状态</th>
						<th field="updateTime" width="80" align="center">更新时间</th>
						<th field="appId" width="100" align="center" hidden="true"/>
						<th field="userId" width="100" align="center" hidden="true"/>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="评论" closed="true" style="width:650px;height:300px;padding:5px;" modal="true">
			<form:form modelAttribute="appComment" id="editForm" action="${ctx}/admin/app/comment/save" method="post" cssStyle="padding:10px 20px;"  enctype="multipart/form-data">
				<table>
					<tr>
					<td><form:label for="appName" path="appName">应用名称：</form:label></td>
					<td><label id="appName_edit" class="inputLabel"></label></td>
					<td><form:label for="enName" path="enName">英文名称：</form:label></td>
					<td><label id="enName_edit" class="inputLabel"></label></td>
				</tr>
				<tr>
					<td><form:label for="userName" path="userName">评论人：</form:label></td>
					<td><label id="userName_edit" class="inputLabel"></label></td>
					<td><form:label for="grade" path="grade" cssClass="mustInput">等级：</form:label></td>
					<td><span id="star_sp0" class="starDark_left"></span><span id="star_sp1" class="starDark_right"></span><span id="star_sp2" class="starDark_left"></span><span id="star_sp3" class="starDark_right"></span><span id="star_sp4" class="starDark_left"></span><span id="star_sp5" class="starDark_right"></span><span id="star_sp6" class="starDark_left"></span><span id="star_sp7" class="starDark_right"></span><span id="star_sp8" class="starDark_left"></span><span id="star_sp9" class="starDark_right"></span></td>
					<form:hidden path="grade" id="grade_edit"/>
				</tr>
				<tr>
					<td><form:label for="status" path="status" cssClass="mustInput">状态：</form:label></td>
					<td>
						<form:input path="status" id="status_edit" required="true"/>
					</td>
				</tr>
				<tr>
					<td><form:label for="comment" path="comment" cssClass="mustInput">内容：</form:label></td>
					<td colspan="3"><form:textarea path="comment" cssClass="easyui-validatebox" required="true" validType="maxLength[500]" maxLen="500" msg="评论内容"/></td>
				</tr>
				</table>
				<form:hidden path="id"/>
				<form:hidden path="appId" id="appId_edit"/>
				<form:hidden path="userId" id="userId_edit"/>
				<div style="text-align: center; padding: 5px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_submit"
						iconCls="icon-save">保 存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_reset"
						iconCls="icon-undo">重 置</a>
				</div>
			</form:form>
		</div>
	</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js?v=1.0" ></script>
		<script charset="utf-8" src="${ctx}/resources/kindeditor-4.1.1/kindeditor-min.js"></script>
		<script charset="utf-8" src="${ctx}/resources/kindeditor-4.1.1/lang/zh_CN.js"></script>
		<script type="text/javascript">
		var newFeaturesEditer;
		var enNewFeaturesEditer;
		$(function(){
			newFeaturesEditer=simpleEditer('newFeatures');
			enNewFeaturesEditer=simpleEditer('enNewFeatures');
			checkEditControl('${ctx}/back/permission/account?baseUri=/admin/app/version');
			$('#descTabs').tabs({onSelect:function(title){
				$(this).tabs('getSelected').show()
			}});
			$('.datagrid-toolbar a:first').hide();//没有新增
			$('.icon-back').attr('title','返回').css('cursor','pointer').click(function(){
				if(document.referrer){//非IE
					window.location.href=document.referrer;
					return false;
				}else{
					history.back();
				}
			});
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
			$('#edit_submit_vesion').click(function(){
				$('#editForm').form({
					onSubmit:function(){
						tag=0;
						newFeaturesEditer.sync();
						enNewFeaturesEditer.sync();
						// 避免 form validate bug
						$('.combobox-f').each(function(){
							$(this).val($(this).combobox('getText'));
						});
						var b=true;
						if($('#apkFileTxt').val()==''&&$('#apkFile').val()==''){ //新增时
							$.messager.alert('提示', "应用文件必须上传", 'info');
							return false;
						}
						$('#editForm input[type=file]').each(function(){
							b=chkFileType($(this).val(),$(this).attr('fileType'));
							if(!b){
								$.messager.alert('提示', '请上传 '+$(this).attr('fileType')+' 类型的文件', 'info');
								b=false;
								return false;
							};
						});
						if(!b){return b;}
						b=$(this).form('validate');
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
			$('#edit_reset_vesion').click(function(){
				$('#editForm').form('clear');
				newFeaturesEditer.html('');
				enNewFeaturesEditer.html('');
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
				// init data
				$('#appName_label').html(m.appName);
				$('#enName_label').html(m.enName);
				$('#editForm input[name=version]').val(m.version);
				$('#editForm input[name=size]').val(m.size);
				$('#status_edit').combobox('setValue',m.status);
				newFeaturesEditer.html(m.newFeatures);
				enNewFeaturesEditer.html(m.enNewFeatures);
				$('#id').val(m.id);
				$('#appId').val(m.appId);
				$('#apkFileTxt').val(m.filePath);
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
		#editWin textarea {width: 460px;height: 80px;}
		</style>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="appVersionFile"
			action="${ctx}/admin/app/version/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td><form:label for="appName" path="appName">应用名称：</form:label></td>
					<td><form:input path="appName"/></td>
					<td><form:label for="enName" path="enName">英文名称：</form:label></td>
					<td><form:input path="enName"/></td>
					<td><form:label for="version" path="version">版本：</form:label></td>
					<td><form:input path="version"/></td>
				</tr>
				<tr>
					<td><form:label for="size" path="size">文件大小：</form:label></td>
					<td><form:input path="size" cssClass="easyui-validatebox" /></td>
					<td><form:label for="status" path="status">状态：</form:label></td>
					<td>
						<form:input path="status"/>
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
			<table id="tt" style="height: auto;" <c:if test="${not empty param.appId}">iconCls="icon-back"</c:if><c:if test="${empty param.appId}">iconCls="icon-blank"</c:if> title="应用版本列表" align="left" singleSelect="true"  
			idField="id" url="${ctx}/admin/app/version/query?appId=${param.appId}" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="updateTime" sortOrder="desc">
				<thead>
					<tr>
						<th field="appName" width="100" align="center">应用名称</th>
						<th field="enName" width="100" align="center">英文名称</th>
						<th field="version" width="60" align="center">版本</th>
						<th field="filePath" width="100" align="center">文件路径</th>
						<th field="size" width="60" align="center">文件大小</th>
						<th field="status" width="60" align="center" formatter="statusFormatter">状态</th>
						<th field="updateTime" width="100" align="center">更新时间</th>
						<th field="newFeatures" width="150" hidden="true"/>
						<th field="enNewFeatures" width="150" hidden="true"/>
						<th field="appId" hidden="true" width="10"/>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="应用版本" closed="true" style="width:680px;height:380px;padding:5px;overflow:hidden;" modal="true">
			<form:form modelAttribute="appVersionFile" id="editForm" action="${ctx}/admin/app/version/save" method="post" cssStyle="padding:10px 20px;"  enctype="multipart/form-data">
				<table>
					<tr>
						<td><form:label	for="appName" path="appName">应用名称：</form:label></td>
						<td><label id="appName_label"/></td>
						<td><form:label	for="enName" path="enName">英文名称：</form:label></td>
						<td><label id="enName_label"/></td>
				</tr>
				<tr>
						<td><form:label	for="version" path="version"  cssClass="mustInput">版本：</form:label></td>
						<td><form:input path="version"  cssClass="easyui-validatebox"  required="true" validType="length[1,50]"/></td>
						<td><form:label	for="filePath" path="filePath" >应用文件：</form:label></td>
						<td>
						<div class="fileinputs">  
							<input type="file" class="file" name="file" id="apkFile" fileType='apk' onchange="$('#apkFileTxt').val(this.value);" />  
							<div class="fakefile">  
								<input type="text" style="width:150px;" id="apkFileTxt"/><button>浏览</button>
							</div>  
						</div>
						</td>
				</tr>
				<tr>
						<td><form:label	for="size" path="size"  cssClass="mustInput">文件大小：</form:label></td>
						<td><form:input path="size" cssClass="easyui-validatebox" required="true"/></td>
					<td><form:label	for="status" path="status" cssClass="mustInput">状态：</form:label></td>
					<td>
						<form:input path="status" id="status_edit" cssClass="easyui-validatebox" cssStyle="width:180px;"/>
					</td>
				</tr>
				<tr>
					<td><form:label for="newFeatures" path="newFeatures" cssClass="easyui-validatebox">新特效：</form:label></td>
					<td colspan="3">
						<div id="descTabs" class="easyui-tabs" style="width:480px;height:180px;overflow:hidden;">  
							<div title="中文" style="padding:3px;overflow:hidden;">  
								<form:textarea path="newFeatures" cssStyle="width:470px;"/>
							</div>  
							<div title="英文" style="overflow:hidden;padding:3px;">  
								<form:textarea path="enNewFeatures" cssStyle="width:470px;"/>
							</div> 
						</div>  
					</td>
				</tr>
				</table>
				<form:hidden path="id"/>
				<form:hidden path="appId"/>
				<div style="text-align: center; padding: 5px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_submit_vesion"
						iconCls="icon-save">保 存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_reset_vesion"
						iconCls="icon-undo">重 置</a>
				</div>
			</form:form>
		</div>
	</body>
</html>
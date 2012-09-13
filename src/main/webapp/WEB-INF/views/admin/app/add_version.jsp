<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript">
		var newFeaturesEditer;
		var enNewFeaturesEditer;
		var simpleEditer=function(name){
			return KindEditor.create('textarea[name="'+name+'"]', {
				resizeType : 1,
				allowPreviewEmoticons : false,
				allowImageUpload : false,
				items : [
					'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', '|', 'emoticons', 'link']
			});
		};
		var statusList=[];
		$(function(){
			$.ajaxSettings.async = false;
			$.getJSON('/back/field/query/status', function(data) {
				if(data){
					statusList=data;
				}
			});
			$.ajaxSettings.async = true;
			newFeaturesEditer=simpleEditer('newFeatures');
			enNewFeaturesEditer=simpleEditer('enNewFeatures');
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
							location.href='${ctx}/admin/app/application';
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
		function chkFileType(name,types){
			if(''==name){
				return true;
			}
			if(types==''){
				return true;
			}
			var tArray=types.split('|');
			var fArray=name.split('.');
			var suffix=fArray[fArray.length-1];
			for(var i in tArray){
				if(suffix.toLowerCase()==tArray[i].toLowerCase()){
					return true;
				}
			}
			return false;
		}
		</script>
		<style>
		label {width: 115px;}
		input {width: 180px;}
		textarea {width: 490px;height: 80px;}
		.combo{height:22px;position: relative;}
		.combo-arrow{top:-13px;position: relative;}
		</style>
	</head>
	<body>
		<form:form modelAttribute="appVersionFile" id="editForm" action="${ctx}/admin/app/version/save" method="post" cssStyle="padding:10px 20px;width:600px;"  enctype="multipart/form-data">
			<table>
				<tr>
					<td><form:label	for="appName" path="appName">应用名称：</form:label></td>
					<td><label id="appName_label"/>${application.appName}</td>
					<td><form:label	for="enName" path="enName">英文名称：</form:label></td>
					<td><label id="enName_label"/>${application.enName}</td>
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
					<div id="descTabs" class="easyui-tabs" style="width:500px;height:180px;overflow:hidden;">  
						<div title="中文" style="padding:3px;overflow:hidden;">  
							<form:textarea path="newFeatures"/>
						</div>  
						<div title="英文" style="overflow:hidden;padding:3px;">  
							<form:textarea path="enNewFeatures"/>
						</div> 
					</div>  
				</td>
			</tr>
			</table>
			<form:hidden path="id"/>
			<input type="hidden" name="appId" value="${application.id}"/>
			<div style="text-align: center; padding: 5px;">
				<a href="${ctx}/admin/app/application" class="easyui-linkbutton" id="back_btn"
					iconCls="icon-back">返 回</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_submit_vesion"
					iconCls="icon-save">保 存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_reset_vesion"
					iconCls="icon-undo">重 置</a>
			</div>
		</form:form>
	</body>
</html>
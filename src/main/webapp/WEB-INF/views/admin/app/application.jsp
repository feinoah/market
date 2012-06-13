<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js?v=1.0" ></script>
		<script type="text/javascript">
		var catalogs;
		var tag=0;
		var languages;
		$(function(){
			$.ajaxSettings.async = false;
			$.getJSON('${ctx}/portal/catalog/all?local=cn', function(data) {
				if(data){
					catalogs=data;
				}
			});
			$.getJSON('${ctx}/back/field/query/platform', function(data) {
				if(data){
					fieldList=data;
				}
			});
			$.getJSON('${ctx}/back/field/query/suppor_language', function(data) {
				if(data){
					languages=data;
				}
			});
			$.ajaxSettings.async = true;
			$('#catalogId').combobox({  
			   	data:catalogs,
			    valueField:'id',  
			    textField:'name'  
			}); 
			$('#catalogId_edit').combobox({  
				data:catalogs,
			    valueField:'id',  
			    textField:'name'  
			}); 
			$('.easyui-tabs').each(function(){
				$(this).tabs({onSelect:function(title){
					$(this).tabs('getSelected').show()
				}});
			});
			$('#platform_edit').combobox({
				data:fieldList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
			});
			$('#supportLanguages_edit').combobox({
				data:languages,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
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
			$.ajaxSettings.async = true;
			// 初始化
			$('#ttt').datagrid({
				width:'100%',
				method:'get',
				toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:function() {
						tag=0;
						$('#editWin').window({title:'新增'+winTitle,iconCls:'icon-add'});
						$('#editWin').window('open');
						$('#editForm').form('clear');
						$('#id').val('');
						$('#editForm textarea').val('');
						$('#editWin').show();
					}
				}, '-', {
					text:'修改',
					iconCls:'icon-edit',
					handler:edit
				}, '-', {
					text :'删除',
					iconCls:'icon-remove',
					handler:del
				}, '-', {
					text :'版本',
					iconCls:'icon-add',
					handler:function() {
						var m = $('#ttt').datagrid('getSelected');
						if (m) {
							$('#appId').val(m.id);
							$('#editVersionWin').window('open');
							$('#editVersionForm').form('clear');
							$('#editVersionWin').show();
						} else {
							$.messager.show({
								title : '警告',
								msg : '请先选泽应用记录。'
							});
						}
					}
				}, '-', {
					text :'版本',
					iconCls:'icon-search',
					handler:searchVersion
				} ],
				onDblClickRow:edit
			});
			// edit form
			$('#edit_submit_app').bind('click',function(){
				$('#editForm').form({
					onSubmit:function(){
						// 避免 form validate bug
						$('#catalogId_edit').val($('#catalogId_edit').combobox('getText'));
						$('#status_edit').val($('#status_edit').combobox('getText'));
						$('#supportLanguages_edit').val($('#supportLanguages_edit').combobox('getText'));
						$('#editForm textarea').each(function(){
							if(getStrLen($.trim($(this).val()))>$(this).attr('maxLen')){
								$.messager.alert('提示', '描述超出'+$(this).attr('maxLen')+'字符', 'info');
								return false;
							}
						});
						if(tag==0&&$('#apkFile').val()==''){ //新增时
							$.messager.alert('提示', "应用文件必须上传", 'info');
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
				        	$('#ttt').datagrid('reload');
						}else{
			    			$.messager.alert('异常', "后台系统异常", 'error');
						}
				    }
				}).submit();
				checkExisted($('#appName_edit'),"${ctx}/portal/app/check?local=cn&appName=");
				checkExisted($('#enName_edit'),"${ctx}/portal/app/check?local=en&appName=");
			});
			$('#edit_submit_version').bind('click',function(){
				$('#editVersionForm').form({
					onSubmit:function(){
						// 避免 form validate bug
						$('#version_status_edit').val($('#version_status_edit').combobox('getText'));
						$('#editVersionForm textarea').each(function(){
							if(getStrLen($.trim($(this).val()))>$(this).attr('maxLen')){
								$.messager.alert('提示', '描述超出'+$(this).attr('maxLen')+'字符', 'info');
								return false;
							}
						});
						if($('#versionFile').val()==''){
							$.messager.alert('提示', "应用文件必须上传", 'info');
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
				        	$('#editVersionWin').window('close');
				        	// update rows
				        	$('#ttt').datagrid('reload');
						}else{
			    			$.messager.alert('异常', "后台系统异常", 'error');
						}
				    }
				}).submit();
			});
			checkEditControl();
			$('#editVersionWin').window({onClose:function(){
				$('.validatebox-tip').remove();
			}});
		});
		function edit() {
			var m = $('#ttt').datagrid('getSelected');
			if (m) {
				tag=1;
				$('#editForm input').each(function(){
					$(this).removeClass('validatebox-invalid');
				});
				$('#editWin').window({title:'修改'+winTitle,iconCls:'icon-edit'});
				$('#editWin').window('open');
				// init data
				$('#editForm input[name=appName]').val(m.appName);
				$('#editForm input[name=enName]').val(m.enName);
				$('#developer_edit').val(m.developer);
				$('#editForm input[name=version]').val(m.version);
				$('#size_edit').val(m.size);
				$('#catalogId_edit').combobox('setValue',m.catalogId);
				//$('#editForm input[name=platform]').val(m.platform);
				$('#platform_edit').combobox('setValue',m.platform);
				$('#supportLanguages_edit').combobox('setValue',m.supportLanguages);
				//$('#appLevel_edit').numberspinner('setValue',m.appLevel);
				$('#editForm input[name=price]').val(m.price);
				$('#status_edit').combobox('setValue',m.status);
				$('#permissionDesc').val(m.permissionDesc);
				$('#enPermissionDesc').val(m.enPermissionDesc);
				$('#description').val(m.description);
				$('#enDescription').val(m.enDescription);
				$('#features').val(m.features);
				$('#enFeatures').val(m.enFeatures);
				$('#id').val(m.id);
				$('#editWin').show();
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选择要修改的记录。'
				});
			}
		}

		function del() {
			var m = $('#ttt').datagrid('getSelected');
			if (m) {
				$.messager.confirm('警告','您确认要删除吗?',function(data) {
					if (data) {
						$.messager.progress({title:'请稍后',msg:'提交中...'});
						$.ajax({
							url : '${ctx}/admin/app/application/delete/'+ m.id,
							type : 'GET',
							timeout : 1000,
							error : function() {
								$.messager.alert('错误','删除失败!','error');
							},
							success : function(data) {
								$.messager.progress('close');
								if (data == -1) {
									$.messager.alert('错误','删除失败!','error');
								} else if (data > 0) {
									$.messager.alert('成功','删除成功','info');
									// update rows
									$('#ttt').datagrid('reload');
									// clear selected
									$('#ttt').datagrid('unselectAll');
								} else {
									$.messager.alert('异常','后台系统异常','error');
								}
							}
						});
					}
				});
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选择要删除的记录。'
				});
			}
		}
		function lanFormatter(v){
			var result=v;
			$.each(languages, function(key,val) {
				if(v==val.fieldValue){
					result=val.displayValue;
					return false;
				}
			});
			return result;
		}
		
		function catalogFormatter(v){
			var result='-';
			$.each(catalogs, function(key,val) {
				if(v==val.id){
					result=val.name;
					return false;
				}
			});
			return result;
		}
		function searchVersion() {
			var m = $('#ttt').datagrid('getSelected');
			if (m) {
				location.href='${ctx}/admin/app/version?appId='+m.id;
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选泽应用记录。'
				});
			}
		}
		function checkEditControl(){
			$.getJSON('${ctx}/back/permission/app', function(data) {
				if(data.save==0&&data.del==0&&data.searchVersion==0&&data.addVersion){
					$('.datagrid-toolbar').hide();
				}
				$.each($('.datagrid-toolbar a'),function(i,m){
					if(data.save==0&&i<2){
						$(this).hide();
					}
					if(data.del==0&&i==2){
						$(this).hide();
					}
					if(data.searchVersion==0&&i==3){
						$(this).hide();
					}
					if(data.addVersion==0&&i==3){
						$(this).hide();
					}
				});
			});
		}
		</script>
		<style>
		#editWin label {width: 115px;}
		#editWin input {width: 150px;}
		.easyui-tabs textarea{width: 572px;height: 82px;}
		#editVersionWin textarea {width: 410px;height: 60px;}
		.progress { position:relative; width:400px; border: 1px solid #ddd; padding: 1px; border-radius: 3px; }
		.bar { background-color: #B4F5B4; width:0%; height:20px; border-radius: 3px; }
		.percent { position:absolute; display:inline-block; top:3px; left:48%; }
		</style>
	</head>
	<body>
		<div style="width: 100%;">
		<form:form modelAttribute="application"
			action="${ctx}/admin/app/application/query"
			method="get" id="searchForm">
			<table>
				<tr>
					<td>
						<form:label for="appName" path="appName">应用名称：</form:label>
					</td>
					<td>
						<form:input path="appName" />
					</td>
					<td>
						<form:label for="enName" path="enName">英文名称：</form:label>
					</td>
					<td>
						<form:input path="enName" />
					</td>
				</tr>
				<tr>
					<td>
						<form:label for="catalogId" path="catalogId">分类：</form:label>
					</td>
					<td>
						<form:input path="catalogId"/>
					</td>
					<td>
						<form:label for="status" path="status">状态：</form:label>
					</td>
					<td>
						<form:input path="status" />
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
			<table id="ttt" style="height: auto;" iconCls="icon-blank" title="应用列表" align="left" singleSelect="true" 
			idField="id" url="${ctx}/admin/app/application/query" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="downCount" sortOrder="desc">
				<thead>
					<tr>
						<th field="appName" width="100" align="center">应用名称</th>
						<th field="enName" width="100" align="center">英文名称</th>
						<th field="version" width="60" align="center">版本</th>
						<th field="developer" width="60" align="center">开发商</th>
						<th field="icon" width="60" align="center" hidden="true"/>
						<th field="bigicon" width="60" align="center" hidden="true"/>
						<th field="size" width="60" align="center" hidden="true"/>
						<th field="catalogId" width="80" align="center" formatter="catalogFormatter">分类</th>
						<th field="appFilePath" width="100" align="center"  hidden="true">应用文件路径</th>
						<th field="platform" width="100" align="center" formatter="fieldFormatter">适用平台</th>
						<th field="supportLanguages" width="80" align="center" formatter="lanFormatter">支持语言</th>
						<th field="price" width="60" align="center">价格</th>
						<th field="appLevel" width="80" align="center" formatter="appLevelFormatter">应用评级</th>
						<th field="description" width="80" align="center"  hidden="true">描述</th>
						<th field="enDescription" width="80" align="center"  hidden="true">描述</th>
						<th field="permissionDesc" width="80" hidden="true">权限描述</th>
						<th field="enPermissionDesc" width="80" hidden="true">权限描述</th>
						<th field="status" width="60" align="center" formatter="statusFormatter">状态</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="应用" iconCls="icon-edit" closed="true" style="width:650px;height:460px;padding:3px;overflow: hidden;" modal="true">
			<form:form modelAttribute="application" id="editForm" action="${ctx}/admin/app/application/save" method="post" cssStyle="padding:0px 20px;" enctype="multipart/form-data">
				<div id="appTabs" class="easyui-tabs" style="width:590px;height:360px;overflow: hidden;">
					<div title="基本信息" style="padding:3px;">  
						<table>
							<tr>
								<td><form:label	for="appName" path="appName"  cssClass="mustInput">应用名称：</form:label></td>
								<td><form:input path="appName" id="appName_edit" cssClass="easyui-validatebox" required="true"/></td>
								<td><form:label	for="enName" path="enName"  cssClass="mustInput">英文名称：</form:label></td>
								<td><form:input path="enName" id="enName_edit" cssClass="easyui-validatebox" required="true"/></td>
							</tr>
							<tr>
								<td><form:label	for="developer" path="developer"  cssClass="mustInput">开发商：</form:label></td>
								<td><form:input path="developer" id="developer_edit" cssClass="easyui-validatebox" required="true"/></td>
								<td><label	for="size" class="mustInput">文件大小：</label></td>
								<td><input type="text" name="size" id="size_edit" class="easyui-validatebox" required="true"/></td>
							</tr>
							<tr>
								<td><label	for="version" class="mustInput">版本：</label></td>
								<td><form:input path="version" required="true" class="easyui-validatebox"/></td>
								<td><label for="catalogId" class="mustInput">分类：<label></td>
								<td><form:input path="catalogId" id="catalogId_edit" required="true" cssClass="easyui-validatebox" editable='false'/></td>
							</tr>
							<tr>
							</tr>
							<tr>
								<td><form:label	for="status" path="status" cssClass="mustInput">状态：</form:label></td>
								<td>
									<form:input path="status" id="status_edit" required="true" cssClass="easyui-validatebox" />
								</td>
								<td><form:label	for="supportLanguages" path="supportLanguages" cssClass="mustInput">支持语言：</form:label></td>
								<td>
									<form:input path="supportLanguages" id="supportLanguages_edit" required="true" cssClass="easyui-validatebox" />
								</td>
							</tr>
							<tr>
								<td><form:label	for="price" path="price">价格：</form:label></td>
								<td><form:input path="price" cssClass="easyui-validatebox" validType="number" /></td>
								<td><form:label	for="platform" path="platform">适用平台：</form:label></td>
								<td><form:input path="platform" id="platform_edit"/></td>
							</tr>
						</table>
						<div id="tabs" class="easyui-tabs" style="width:580px;height:150px;">
							<div id="desc_tabs" title="应用描述" border="false" class="easyui-tabs" style="width:580px;height:100px;">  
								<div title="中文" style="overflow:hidden;padding:3px;">  
									<form:textarea path="description" cssClass="easyui-validatebox" validType="maxLength[250]" maxLen="250"/>
								</div>  
								<div title="英文" style="overflow:hidden;padding:3px;">  
									<form:textarea path="enDescription" cssClass="easyui-validatebox" validType="maxLength[250]" maxLen="250"/>
								</div> 
							</div>
						 	<div id="permission_tabs" title="权限描述" border="false" class="easyui-tabs" style="width:580px;height:100px;">  
								<div title="中文" style="overflow:hidden;padding:3px;"> 
									<form:textarea path="permissionDesc" cssClass="easyui-validatebox" validType="maxLength[250]" maxLen="250"/>
								</div>  
								<div title="英文" style="overflow:hidden;padding:3px;">  
									<form:textarea path="enPermissionDesc" cssClass="easyui-validatebox" validType="maxLength[250]" maxLen="250"/>
								</div> 
							</div>
							<div id="features_tabs" title="特性描述" border="false" class="easyui-tabs" style="width:580px;height:100px;">  
								<div title="中文" style="overflow:hidden;padding:3px;"> 
									<form:textarea path="features" cssClass="easyui-validatebox" validType="maxLength[250]" maxLen="250"/>
								</div>  
								<div title="英文" style="overflow:hidden;padding:3px;">  
									<form:textarea path="enFeatures" cssClass="easyui-validatebox" validType="maxLength[250]" maxLen="250"/>
								</div> 
							</div>
						</div> 
						<form:hidden path="id"/>
						<!-- 
						<form:hidden path="icon" />
						<form:hidden path="images" />
						<form:hidden path="appFilePath" />
						 -->
					</div>
					<div title="附件" style="overflow:auto;padding:3px;display:none;"> 
						<table>
							<tr>
								<td><label class="mustInput">应用文件：</label></td>
								<td><input type="file"  name="apkFile" id="apkFile"/></td>
						</tr>
						<tr>
							<td><label>小图标：</label></td>
							<td><input type="file"  name="iconFile" id="iconFile"/></td>
						</tr>
						<tr>
							<td><label>大图标：</label></td>
							<td><input type="file"  name="bigIconFile" id="bigIconFile"/></td>
						</tr>
						<tr>
							<td rowspan="2"><label>截图</label></td><td><input type="file" name="imageFile" /></td><td><input type="file" name="imageFile" /></td><td><input type="file" name="imageFile" /></td>
						</tr>
						<tr><td><input type="file" name="imageFile" /></td></td><td><input type="file" name="imageFile" /></tr>
						</table>
					</div>
				</div>  
			</form:form>
			<div style="text-align: center; padding: 5px;">
				<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_submit_app"
					iconCls="icon-save">保 存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_reset"
					iconCls="icon-undo">重 置</a>
			</div>
		</div>
		
		<div id="editVersionWin" class="easyui-window" iconCls="icon-add" title="添加版本" closed="true" style="width:575px;height:300px;padding:5px;" modal="true">
			<form id="editVersionForm" action="${ctx}/admin/app/version/save" method="post" cssStyle="padding:10px 20px;"  enctype="multipart/form-data">
				<table>
					<tr>
						<td><label for="filePath" class="mustInput">应用文件：</label></td>
						<td><input type="file" name="file" id="versionFile" /></td>
						<td><label	for="size" class="mustInput">文件大小：</label></td>
						<td>
						<input type="text" name="size" id="size" class="easyui-validatebox" required="true"/>
						</td>
				</tr>
				<tr>
						<td><label	for="version" class="mustInput">版本：</label></td>
						<td><input type="text" name="version" id="version" required="true" class="easyui-validatebox"/></td>
					<td><label	for="status" class="mustInput">状态：</label></td>
					<td>
						<select name="status" id="version_status_edit" required="true" class="easyui-validatebox easyui-combobox" editable='false' >
							<option value="1">启用</option>
							<option value="0">停用</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><label class="easyui-validatebox">新特性：</label></td>
					<td colspan="3">
						<div id="new_features_tabs" class="easyui-tabs" style="width:420px;height:100px;">
							<div title="中文" style="overflow:hidden;padding:3px;">
								<textarea name="newFeatures" class="easyui-validatebox" validType="maxLength[250]" maxLen="250"></textarea>
							</div>  
							<div title="英文" style="overflow:hidden;padding:3px;">  
								<textarea name="enNewFeatures" class="easyui-validatebox" validType="maxLength[250]" maxLen="250"></textarea>
							</div> 
						</div>
					</td>
				</tr>
				</table>
				<input type="hidden" name="appId" id="appId"/>
				<div style="text-align: center; padding: 5px;">
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_submit_version"
						iconCls="icon-save">保 存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_reset_version"
						iconCls="icon-undo">重 置</a>
				</div>
			</form>
		</div>
		<div id="mask" style="z-index: 999999; width: 100%;height: 100%;background: #CCC;display: none;">
			<div id="p" class="easyui-progressbar" style="width:400px;"></div>			
		</div>
	</body>
</html>
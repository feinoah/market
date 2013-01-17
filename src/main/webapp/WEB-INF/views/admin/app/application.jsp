<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/views/commons/easyui.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/public/scripts/common.js?v1.2" ></script>
		<script type="text/javascript">
		var devs;
		var descEditer;
		var enDescEditer;
		var permissionEditer;
		var enPermissionEditer;
		var featuresEditer;
		var enFeaturesEditer;
		var newFeaturesEditer;
		var enNewFeaturesEditer;
		$(function(){
			descEditer=simpleEditer('description');
			enDescEditer=simpleEditer('enDescription');
			permissionEditer=simpleEditer('permissionDesc');
			enPermissionEditer=simpleEditer('enPermissionDesc');
			featuresEditer=simpleEditer('features');
			enFeaturesEditer=simpleEditer('enFeatures');
			newFeaturesEditer=simpleEditer('newFeatures');
			enNewFeaturesEditer=simpleEditer('enNewFeatures');
			$.ajaxSettings.async = false;
			$.getJSON('${ctx}/back/field/query/platform', function(data) {
				if(data){
					fieldList=data;
				}
			});
			$.getJSON('${ctx}/portal/app/developer/query?rows=9999', function(data) {
				if(data.rows){
					devs=data.rows;
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
			$('#developer_edit').combobox({
				data:devs,
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
				data:appStatusList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
			});
			$('#status_edit').combobox({
				data:appStatusList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue',
				onChange:function(newValue, oldValue){
					if(newValue==4){
						$('#mainPicLabel').addClass('mustInput');
						//$('#mainPicTr').show();}else{$('#mainPicTr').hide();
					} else {
						$('#mainPicLabel').removeClass('mustInput');
					}
				}
			});
			$('#version_status_edit').combobox({
				data:statusList,
				editable:false,
				valueField:'fieldValue',
				textField:'displayValue'
			});
			$('#local').combobox({
				data:localList,
				editable:false,
				valueField:'code',
				textField:'value'
			});
			$('#local_edit').combobox({
				data:localList,
				editable:false,
				valueField:'code',
				textField:'value'
			});
			// 初始化
			$('#tt').datagrid({
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
						$('.validatebox-tip').remove();
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
						var m = $('#tt').datagrid('getSelected');
						if (m) {
							location.href='${ctx}/back/add/app/version/'+m.id;
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
				} ]
			});
			// edit form
			$('#edit_submit_app').bind('click',function(){
				$('#editForm').form({
					onSubmit:function(){
						descEditer.sync();
						enDescEditer.sync();
						permissionEditer.sync();
						enPermissionEditer.sync();
						featuresEditer.sync();
						enFeaturesEditer.sync();
						// 避免 form validate bug
						$('.combobox-f').each(function(){
							$(this).val($(this).combobox('getText'));
						});
						var b=true;
						$('#editForm textarea').each(function(){
							if(getStrLen($.trim($(this).val()))>$(this).attr('maxLen')){
								$.messager.alert('提示', $(this).attr('msg')+'超出'+$(this).attr('maxLen')+'字符(包括HTML标签，一个中文两个字符)', 'info');
								b=false;
								return false;
							}
						});
						if(!b){return b;}
						if($('#apkFileTxt').val()==''){ //新增时
							$.messager.alert('提示', "应用文件必须上传", 'info');
							return false;
						}
						if($('#iconFileTxt').val()==''){ //新增时
							$.messager.alert('提示', "图标文件必须上传", 'info');
							return false;
						}
						if($('#bigIconFileTxt').val()==''){ //新增时
							$.messager.alert('提示', "大图标文件必须上传", 'info');
							return false;
						}
						if($('#mainPicLabel').hasClass('mustInput')){
							if($('#mainPicTxt').val()==''){
								$.messager.alert('提示', "置顶主图必须上传", 'info');
								return false;
							}
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
			$('#edit_reset_app').click(function(){
				$('#editForm').form('clear');
				descEditer.html('');
				enDescEditer.html('');
				permissionEditer.html('');
				enPermissionEditer.html('');
				featuresEditer.html('');
				enFeaturesEditer.html('');
			});
			checkEditControl();
			$('.combobox-f').each(function(){
				$(this).combobox('clear');
			});
			checkExisted($('#appName_edit'),'${ctx}/portal/check/app?local=cn&name=');
			checkExisted($('#enName_edit'),'${ctx}/portal/check/app?local=en&name=');
		});
		function edit(index) {
			if(index>-1){//双击
				// clear selected
				$('#tt').datagrid('clearSelections');
				$('#tt').datagrid('selectRow',index); //让双击行选定
			}
			var m = $('#tt').datagrid('getSelected');
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
				$('#developer_edit').combobox('setValue',m.developer);
				$('#editForm input[name=version]').val(m.version);
				$('#size_edit').val(m.size);
				$('#catalogId_edit').combobox('setValue',m.catalogId);
				//$('#editForm input[name=platform]').val(m.platform);
				$('#platform_edit').combobox('setValue',m.platform);
				$('#supportLanguages_edit').combobox('setValue',m.supportLanguages);
				//$('#appLevel_edit').numberspinner('setValue',m.appLevel);
				$('#editForm input[name=price]').val(m.price);
				var s=0;
				// 取最大值
				$.each(appStatusList, function(key,val) {
					if((m.status&val.fieldValue)!=0){
						s=val.fieldValue;
						return false;
					}
				});
				if(s==4){
					$('#mainPicLabel').addClass('mustInput');
					//$('#mainPicTr').show();}else{$('#mainPicTr').hide();
				} else {
					$('#mainPicLabel').removeClass('mustInput');
				}
				$('#status_edit').combobox('setValue',s);
				descEditer.html(m.description);
				enDescEditer.html(m.enDescription);
				permissionEditer.html(m.permissionDesc);
				enPermissionEditer.html(m.enPermissionDesc);
				featuresEditer.html(m.features);
				enFeaturesEditer.html(m.enFeatures);
				$('#apkFileTxt').val(m.appFilePath);
				$('#iconFileTxt').val(m.icon);
				$('#bigIconFileTxt').val(m.bigIcon);
				if(m.imageList){
					$('input[id^=imgFileTxt]').val('');
					$.each(m.imageList,function(i,v){
						$('#imgFileTxt'+(i+1)).val(v);
					});
				}
				$('input[name=imageArray]').attr('disabled',false);
				$('#mainPicTxt').val(m.mainPic);
				if(m.status!=4){
					$('#mainPicTr').hide();
				}
				$('#local_edit').combobox('setValue',m.local);
				$('#id').val(m.id);
				$('#editWin').show();
			} else {
				$.messager.show({
					title : '警告',
					msg : '请先选择要修改的记录。'
				});
			}
		}

		function searchVersion() {
			var m = $('#tt').datagrid('getSelected');
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
		function devFormatter(v){
			var result='-';
			$.each(devs, function(key,val) {
				if(v==val.id){
					result=val.name;
					return false;
				}
			});
			return result;
		}
		</script>
		<style>
		#editWin label{padding-left: 5px;}
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
					<td>
						<form:label for="local" path="local">地区：</form:label>
					</td>
					<td>
						<form:input path="local" />
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
			<table id="tt" style="height: auto;" iconCls="icon-blank" title="应用列表" align="left"   
			idField="id" url="${ctx}/admin/app/application/query" pagination="true" rownumbers="true"
			fitColumns="true" pageList="[ 5, 10]" sortName="downCount" sortOrder="desc">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th field="appName" width="100" align="center">应用名称</th>
						<th field="enName" width="100" align="center">英文名称</th>
						<th field="version" width="60" align="center">版本</th>
						<th field="developer" width="100" align="center" formatter="devFormatter">开发者</th>
						<th field="icon" width="60" align="center" hidden="true"/>
						<th field="bigicon" width="60" align="center" hidden="true"/>
						<th field="size" width="60" align="center" hidden="true"/>
						<th field="catalogId" width="60" align="center" formatter="catalogFormatter">分类</th>
						<th field="appFilePath" width="100" align="center"  hidden="true">应用文件路径</th>
						<th field="platform" width="100" align="center" formatter="fieldFormatter">适用平台</th>
						<th field="supportLanguages" width="80" align="center" formatter="lanFormatter">支持语言</th>
						<th field="local" width="80" align="center" formatter="localFormatter">地区</th>
						<th field="price" width="60" align="center">价格</th>
						<th field="appLevel" width="80" align="center" formatter="gradeFormatter">应用评级</th>
						<th field="description" width="80" align="center"  hidden="true">描述</th>
						<th field="enDescription" width="80" align="center"  hidden="true">描述</th>
						<th field="permissionDesc" width="80" hidden="true">权限描述</th>
						<th field="enPermissionDesc" width="80" hidden="true">权限描述</th>
						<th field="status" width="60" align="center" formatter="appStatusFormatter">状态</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="editWin" class="easyui-window" title="应用" iconCls="icon-edit" closed="true" style="width:650px;height:520px;padding:3px;overflow: hidden;" modal="true">
			<form:form modelAttribute="application" id="editForm" action="${ctx}/admin/app/application/save" method="post" cssStyle="padding:5px;" enctype="multipart/form-data">
				<div id="appTabs" class="easyui-tabs" style="width:620px;overflow: hidden;">
					<div title="基本信息" style="padding:3px;">  
						<table>
							<tr>
								<td><form:label	for="appName" path="appName"  cssClass="mustInput">应用名称：</form:label></td>
								<td><form:input path="appName" id="appName_edit" cssClass="easyui-validatebox" required="true" cssStyle="width:200px;"/></td>
								<td><form:label	for="enName" path="enName"  cssClass="mustInput">英文名称：</form:label></td>
								<td><form:input path="enName" id="enName_edit" cssClass="easyui-validatebox" required="true" cssStyle="width:200px;"/></td>
							</tr>
							<tr>
								<td><form:label	for="developer" path="developer"  cssClass="mustInput">开发者：</form:label></td>
								<td><form:input path="developer" id="developer_edit" required="true" editable='false' cssStyle="width:200px;"/></td>
								<td><label	for="size" class="mustInput">文件大小：</label></td>
								<td><input type="text" name="size" id="size_edit" class="easyui-validatebox" required="true" style="width:200px;"/></td>
							</tr>
							<tr>
								<!-- 
								<td><label	for="version" class="mustInput">版本：</label></td>
								<td><form:input path="version" required="true" validType="length[1,50]" class="easyui-validatebox" cssStyle="width:200px;"/></td>
								 -->
								<td><form:label	for="status" path="status" cssClass="mustInput">状态：</form:label></td>
								<td>
									<form:input path="status" id="status_edit" required="true" cssStyle="width:200px;"/>
								</td>
								<td><label for="catalogId" class="mustInput">分类：<label></td>
								<td><form:input path="catalogId" id="catalogId_edit" required="true" editable='false' cssStyle="width:200px;"/></td>
							</tr>
							<tr>
							</tr>
							<tr>
								<td><form:label	for="supportLanguages" path="supportLanguages" cssClass="mustInput">支持语言：</form:label></td>
								<td>
									<form:input path="supportLanguages" id="supportLanguages_edit" required="true" cssStyle="width:200px;"/>
								</td>
								<td><form:label	for="price" path="price">价格：</form:label></td>
								<td><form:input path="price" cssClass="easyui-validatebox" validType="number" cssStyle="width:200px;"/></td>
							</tr>
							<tr>
								<td><form:label	for="platform" path="platform">适用平台：</form:label></td>
								<td><form:input path="platform" id="platform_edit" required="true" cssStyle="width:200px;"/></td>
								<td><form:label	for="local" path="local">地区：</form:label></td>
								<td><form:input path="local" id="local_edit" required="true" cssStyle="width:200px;"/></td>
							</tr>
							<tr>
								<td colspan="4">
									<div id="tabs" class="easyui-tabs" style="width:585px;height:200px;overflow:hidden;">
										<div id="desc_tabs" title="应用描述" border="false" class="easyui-tabs" style="width: 585px;">  
											<div title="中文" style="overflow:hidden;padding:3px;">  
												<textarea name="description" style="width: 575px;" maxLen="2000" msg="中文描述"></textarea>
											</div>  
											<div title="英文" style="overflow:hidden;padding:3px;">  
												<textarea name="enDescription" style="width: 575px;" maxLen="2000" msg="英文描述"></textarea>
											</div> 
										</div>
									 	<div id="permission_tabs" title="权限描述" border="false" class="easyui-tabs" style="width: 585px;"> 
											<div title="中文" style="overflow:hidden;padding:3px;">
												<textarea name="permissionDesc" style="width: 575px;" maxLen="2000" msg="中文权限描述"></textarea>
											</div>  
											<div title="英文" style="overflow:hidden;padding:3px;">  
												<textarea name="enPermissionDesc" style="width: 575px;" maxLen="2000" msg="英文权限描述"></textarea>
											</div> 
										</div>
										<div id="features_tabs" title="特性描述" border="false" class="easyui-tabs" style="width: 585px;">  
											<div title="中文" style="overflow:hidden;padding:3px;"> 
												<textarea name="features" style="width: 575px;" maxLen="2000" msg="中文特性描述"></textarea>
											</div>  
											<div title="英文" style="overflow:hidden;padding:3px;">  
												<textarea name="enFeatures" style="width: 575px;" maxLen="2000" msg="英文特性描述"></textarea>
											</div> 
										</div>
									</div> 
								</td>
							</tr>
						</table>
						<form:hidden path="id"/>
					</div>
					<div title="附件" style="overflow:auto;padding:3px;display:none;"> 
						<table>
						<tr>
							<td><label class="mustInput">应用文件：</label></td>
							<td>
								<div class="fileinputs">
									<input type="file" class="file" name="apkFile" id="apkFile"
										fileType='apk' onchange="$('#apkFileTxt').val(this.value);" />
									<div class="fakefile">
										<input type="text" value="" id="apkFileTxt"
											style="width: 200px;" required="true"/>
										<button>浏览</button>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td><label class="mustInput">小图标：</label></td>
							<td>
								<div class="fileinputs">  
									<input type="file" class="file" name="iconFile" id="iconFile" fileType="jpg|png" onchange="$('#iconFileTxt').val(this.value);"/>
									<div class="fakefile">  
										<input type="text" value="" id="iconFileTxt" style="width:200px;" required="true" /><button>浏览</button>
									</div>  
								</div>
							</td>
						</tr>
						<tr>
							<td><label class="mustInput">大图标：</label></td>
							<td>
								<div class="fileinputs">  
									<input type="file" class="file" name="bigIconFile" id="bigIconFile" fileType="jpg|png" onchange="$('#bigIconFileTxt').val(this.value);"/>
									<div class="fakefile">  
										<input type="text" value="" id="bigIconFileTxt" style="width:200px;" required="true" /><button>浏览</button>
									</div>  
								</div>
							</td>
						</tr>
						<tr>
							<td><label id="mainPicLabel">置顶主图：</label></td>
							<td>
								<div class="fileinputs">
									<input type="file" class="file" name="mainPicFile" id="mainPicFile"
										fileType='jpg|png' onchange="$('#mainPicTxt').val(this.value);" />
									<div class="fakefile">
										<input type="text" value="" id="mainPicTxt" style="width: 200px;"/>
										<button>浏览</button>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td><label>截图一：</label></td>
							<td>
							<div class="fileinputs">  
								<input type="file" class="file" name="imageFile" fileType="jpg|png" onchange="$('#imgFileTxt1').val(this.value).attr('disabled',true);"/>
								<div class="fakefile">  
									<input type="text" name="imageArray" id="imgFileTxt1" style="width:200px;"/><button>浏览</button><button onclick="mgFileTxt1.value=''">删除</button>
								</div>  
							</div>
							</td>
						</tr>
						<tr>
							<td><label>截图二：</label></td>
							<td>
							<div class="fileinputs">  
								<input type="file" class="file" name="imageFile" fileType="jpg|png" onchange="$('#imgFileTxt2').val(this.value).attr('disabled',true);"/>
								<div class="fakefile">  
									<input type="text" name="imageArray" id="imgFileTxt2" style="width:200px;"/><button>浏览</button><button onclick="mgFileTxt2.value=''">删除</button>
								</div>  
							</div>
							</td>
						</tr>
						<tr>
							<td><label>截图三：</label></td>
							<td>
							<div class="fileinputs">  
								<input type="file" class="file" name="imageFile" fileType="jpg|png" onchange="$('#imgFileTxt3').val(this.value).attr('disabled',true);"/>
								<div class="fakefile">  
									<input type="text" name="imageArray" id="imgFileTxt3" style="width:200px;"/><button>浏览</button><button onclick="mgFileTxt3.value=''">删除</button>
								</div>  
							</div>
							</td>
						</tr>
						<tr>
							<td><label>截图四：</label></td>
							<td>
							<div class="fileinputs">  
								<input type="file" class="file" name="imageFile" fileType="jpg|png" onchange="$('#imgFileTxt4').val(this.value).attr('disabled',true);"/>
								<div class="fakefile">  
									<input type="text" name="imageArray" id="imgFileTxt4" style="width:200px;"/><button>浏览</button><button onclick="mgFileTxt4.value=''">删除</button>
								</div>  
							</div>
							</td>
						</tr>
						<tr>
							<td><label>截图五：</label></td>
							<td>
							<div class="fileinputs">  
								<input type="file" class="file" name="imageFile" fileType="jpg|png" onchange="$('#imgFileTxt5').val(this.value).attr('disabled',true);"/>
								<div class="fakefile">  
									<input type="text" name="imageArray" id="imgFileTxt5" style="width:200px;"/><button>浏览</button><button onclick="imgFileTxt5.value=''">删除</button>
								</div>  
							</div>
							</td>
						</tr>
						</table>
					</div>
				</div>  
			</form:form>
			<div style="text-align: center;">
				<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_submit_app"
					iconCls="icon-save">保 存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" id="edit_reset_app"
					iconCls="icon-undo">重 置</a>
			</div>
		</div>
		
	</body>
</html>
$(function (){
	// 初始化
	$('#tt').datagrid({
		width:'100%',
		method:'get',
		toolbar:[{
			text:'新增',
			iconCls:'icon-add',
			handler:function() {
				$('#editWin').window('open');
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
		} ]
	});
	$("#submit").bind("click", function(){
		//先取得 datagrid 的查询参数 
		var params = $('#tt').datagrid('options').queryParams;
		//自动序列化表单元素为JSON对象
        var fields =$('#searchForm').serializeArray();   
        $.each( fields, function(i, field){
            params[field.name] = field.value; //设置查询参数  
        });
        //设置好查询参数 reload 一下就可以了
        $('#tt').datagrid('reload'); 
	});
	$('#reset').bind('click',function(){ $('#searchForm').form('clear');});
	// edit form
	$('#edit_submit').bind('click',function(){
		$('#editForm').form({
	    	success:function(data){
	    		if(data==-1){
					$.messager.alert('错误', "编辑失败", 'error');
	    		} else if(data>0){
					$.messager.alert('成功', "编辑成功", 'info');
		        	$('#editWin').window('close');
		        	// clear form
		        	$('#editForm').form('clear');
		        	// update rows
		        	$('#tt').datagrid('reload');
				}else{
	    			$.messager.alert('异常', "后台系统异常", 'error');
				}
		    }
		}).submit();
	});
	$('#edit_reset').bind('click',function(){ $('#editForm').form('clear');});
	
	$('#editWin').window({
		onClose:function(){
			$('#editForm input[type=text]').val('');
			$('#editForm input[type=textarea]').val('');
		}
	});
});

function getParamURL(form){
	var _url=form.attr('action');
	$(':input', form).each(function(index){
		if(this.name&&this.value){
			if(index>0){
				_url+='&'+this.name+'='+this.value;
			} else {
				_url+='?'+this.name+'='+this.value;
			}
		}
	});
	return _url;
}
/**状态：0 停用；0x1 启用；0x2：禁止登录（密码错误次数过多，一定时间内禁止登录）*/
function statusFormatter(v){
	if (v == 0) {
		return '停用';
	}
	if ((v&2)!=0) {
		return '禁止登录';
	}
	return '启用';
}

/**
 * 性别 0：女 1：男 2：保密
 */
function genderFormatter(v){
	if(v==1){return '男'}
	if(v==1){return '女'}
	return '保密';
}

function checkEditControl(url){
	$.getJSON(url, function(data) {
		if(data.save==0&&data.del==0){
			$('.datagrid-toolbar').hide();
		}
		$.each($('.datagrid-toolbar a'),function(i,m){
			if(data.save==0&&i<2){
				$(this).hide();
			}
			if(data.del==0&&i==2){
				$(this).hide();
			}
		});
	});
}
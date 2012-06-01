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
		} ],
		onDblClickRow:edit
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
		        	//$('#editForm').form('clear');
		        	// update rows
		        	$('#tt').datagrid('reload');
				}else{
	    			$.messager.alert('异常', "后台系统异常", 'error');
				}
		    }
		}).submit();
	});
	$('#edit_reset').bind('click',function(){
		$('#editForm').form('clear');
	});
});


var idCard = function(value) {
	if (value.length == 18 && 18 != value.length)
		return false;
	var number = value.toLowerCase();
	var d, sum = 0, v = '10x98765432', w = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7,
			9, 10, 5, 8, 4, 2 ], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
	var re = number
			.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
	if (re == null || a.indexOf(re[1]) < 0)
		return false;
	if (re[2].length == 9) {
		number = number.substr(0, 6) + '19' + number.substr(6);
		d = [ '19' + re[4], re[5], re[6] ].join('-');
	} else
		d = [ re[9], re[10], re[11] ].join('-');
	if (!isDateTime.call(d, 'yyyy-MM-dd'))
		return false;
	for ( var i = 0; i < 17; i++)
		sum += number.charAt(i) * w[i];
	return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
}

var isDateTime = function(format, reObj) {
	format = format || 'yyyy-MM-dd';
	var input = this, o = {}, d = new Date();
	var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format
			.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
	var len = f1.length, len1 = f3.length;
	if (len != f2.length || len1 != f4.length)
		return false;
	for ( var i = 0; i < len1; i++)
		if (f3[i] != f4[i])
			return false;
	for ( var i = 0; i < len; i++)
		o[f1[i]] = f2[i];
	o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
	o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
	o.dd = s(o.dd, o.d, d.getDate(), 31);
	o.hh = s(o.hh, o.h, d.getHours(), 24);
	o.mm = s(o.mm, o.m, d.getMinutes());
	o.ss = s(o.ss, o.s, d.getSeconds());
	o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
	if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0)
		return false;
	if (o.yyyy < 100)
		o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
	d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
	var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM
			&& d.getDate() == o.dd && d.getHours() == o.hh
			&& d.getMinutes() == o.mm && d.getSeconds() == o.ss
			&& d.getMilliseconds() == o.ms;
	return reVal && reObj ? d : reVal;
	function s(s1, s2, s3, s4, s5) {
		s4 = s4 || 60, s5 = s5 || 2;
		var reVal = s3;
		if (s1 != undefined && s1 != '' || !isNaN(s1))
			reVal = s1 * 1;
		if (s2 != undefined && s2 != '' && !isNaN(s2))
			reVal = s2 * 1;
		return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
	}
};

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
/** 状态：0 停用；0x1 启用；0x2：禁止登录（密码错误次数过多，一定时间内禁止登录） */
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
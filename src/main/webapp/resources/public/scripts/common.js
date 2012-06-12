var app={name:'/market'};
var winTitle;
var fieldList;
var statusList;
// 扩展
$.extend($.fn.validatebox.defaults.rules, {  
	minLength:{validator: function(v, p){return v.length>p[0];},message: '最少输入{0}个字符'},
	maxLength:{validator: function(v, p){return v.length<p[0];},message: '最多输入{0}个字符'},
	CHS:{validator:function(v, p){return /^[\u0391-\uFFE5]+$/.test(v);},message:'请输入汉字'},
	ZIP:{validator:function(v, p){return /^[1-9]\d{5}$/.test(v);},message:'邮政编码不存在'},
	QQ:{validator:function(v, p){return /^[1-9]\d{4,10}$/.test(v);},message:'QQ号码不正确'},
	mobile:{validator:function(v, p){return /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/.test(v);},message:'手机号码不正确'},
	loginName:{validator:function(v, p) {return /^[\u0391-\uFFE5\w]+$/.test(v);},message:'登录名称只允许汉字、英文字母、数字及下划线。'},
	safepass:{validator:function(v, p) {return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(v));},message:'密码由字母和数字组成，至少6位'},
	equalTo:{validator:function(v, p){return v == $(p[0]).val();},message:'两次输入的字符不一至'},
	number:{validator:function(v, p) {return /^\d+$/.test(v);},message:'请输入数字'},
	gRemote:{validator:function(v,p){
		var data={};
		data[p[1]]=v;
		var _368=$.ajax({url:p[0],dataType:"json",data:data,async:false,cache:false,type:"get"}).responseText;
		return _368==0;
	},message:'重复！请修正此值'}
});
$(function (){
	winTitle=$('#editWin').window('options').title;
	$.ajaxSettings.async = false;
	$.getJSON(app.name+'/back/field/query/status', function(data) {
		if(data){
			statusList=data;
		}
	});
	$.ajaxSettings.async = true;
	// 初始化
	$('#tt').datagrid({
		width:'100%',
		method:'get',
		toolbar:[{
			text:'新增',
			iconCls:'icon-add',
			handler:function() {
				$('#editWin').window({title:'新增'+winTitle,iconCls:'icon-add'});
				$('#editForm').form('clear');
				$('#editForm textarea').val('');
				$('#id').val('');
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
			onSubmit:function(){
				// 避免 form validate bug
				$('.easyui-combobox').each(function(){
					$(this).val($(this).combobox('getText'));
				});
				$('#editForm textarea').each(function(){
					if($.trim($(this).val()).length>$(this).attr('maxLen')){
						$.messager.alert('提示', '描述超长，最多输入'+$(this).attr('maxLen')+'个字符', 'info');
						return false;
					}
				});
				$('#editForm input[name=email]').each(function(){
					editForm
				});
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
	$('#editWin').window({onClose:function(){
		$('div.validatebox-tip').remove();
	}});
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

function fieldFormatter(v){
	var result='-';
	$.each(fieldList, function(key,val) {
		if(v==val.fieldValue){
			result=val.displayValue;
			return false;
		}
	});
	return result;
}

function statusFormatter(v){
	var result='-';
	$.each(statusList, function(key,val) {
		if(v==val.fieldValue){
			result=val.displayValue;
			return false;
		}
	});
	return result;
}
function gradeFormatter(v){
	var result='';
	v=parseInt(v/2);
	for(var i=1;i<=v;i++){
		result+='★';
	}
	if(v<5){
		v=5-v;
		for(var i=1;i<=v;i++){
			result+='☆';
		}
	}
	return result;
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

function checkExisted(item,url){
	if(item.val()!=''){
		$.getJSON(url+item.val(), function(data) {
			if(data>0){
				$.messager.alert('提示','该邮箱地址已注册!','info');
				item.val('').select();
				return false;
			}
		});
	}
	return true;
}

//获得字符串长度
function getStrLen(str){
	if(str == null || str == ''){
		return 0;
	}
	var len = 0;
	var reg = /[a-zA-Z0-9]/;
	for(var i=0;i<str.length;i++){
		if(reg.test(str.charAt(i))){
			len++;
		}else{
			len = len+2;
		}
	}
	return len;
}
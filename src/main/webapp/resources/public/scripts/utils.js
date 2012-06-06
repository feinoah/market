var app={name:'/market'};

var chkLogin=function(){
	$.getJSON(app.name+'/portal/account/login/check', function(data) {
		if (data) {
			$('li[name=before_login]').hide();
			$('#after_login').show().children().html('欢迎您：'+data.nickName);
		}
	});
}

var regWin=function(){
	Util.Dialog({
		boxID : 'regWin',
		title : '用户注册',
		content : 'url:get?html/reg.html?t='+new Date().getTime(),
		showbg: true,
		width : 580,
		height : 280
	});
	return false;
}

var loginWin=function(){
	Util.Dialog({
		boxID : 'loginWin',
		title : '用户登录',
		content : 'url:get?html/login.html?t='+new Date().getTime(),
		showbg: true,
		width : 480,
		height : 220
	});
	return false;
}

var tips=function(ref,txt,_arrow){
	Util.Dialog({
		type: 'tips',
		boxID: 'Tip_tips',
		referID: ref,
		//width: 150,
		height: 14,
		border: { opacity: '0', radius: '3'},
		closestyle: 'gray',
		arrow: _arrow,
		fixed: false,
		arrowset: {val: '10px'},
		content: 'text:'+txt,
		position: { 
			left: '-20px', 
			top: '-50px',
			lin: true,
			tin: false
		}
	});
}
/**
 * 检测邮箱合法性
 * @param v
 * @returns
 */
function chkEmail(v) {
	if (v == null || $.trim(v)=='') {
		return false;
	}
	var pattern = /^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
	return pattern.test(v);
}
/**
 * 检查密码合法性
 * @param val
 * @returns
 */
function chkPwd(val){
	if(val==null || $.trim(val)=='') {
		return false;
	}
	var pwdRe=new RegExp('^[a-zA-Z0-9!@#$%^&*()_]{6,18}$');
	return pwdRe.test(val);
}

/**
 * 检查身份证号码，支持X
 * @param num
 * @returns {Boolean}
 */
function isIdCardNo(num) {
	var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
			2, 1);
	var error;
	var varArray = new Array();
	var intValue;
	var lngProduct = 0;
	var intCheckDigit;
	var intStrLen = num.length;
	var idNumber = num;
	// initialize
	if ((intStrLen != 15) && (intStrLen != 18)) {
		//error = "输入身份证号码长度不对！";
		//alert(error);
		//frmAddUser.txtIDCard.focus();
		return false;
	}
	// check and set value
	for (i = 0; i < intStrLen; i++) {
		varArray[i] = idNumber.charAt(i);
		if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
			//error = "错误的身份证号码！.";
			//alert(error);
			//frmAddUser.txtIDCard.focus();
			return false;
		} else if (i < 17) {
			varArray[i] = varArray[i] * factorArr[i];
		}
	}
	if (intStrLen == 18) {
		//check date
		var date8 = idNumber.substring(6, 14);
		if (checkDate(date8) == false) {
			//error = "身份证中日期信息不正确！.";
			//alert(error);
			return false;
		}
		// calculate the sum of the products
		for (i = 0; i < 17; i++) {
			lngProduct = lngProduct + varArray[i];
		}
		// calculate the check digit
		intCheckDigit = 12 - lngProduct % 11;
		switch (intCheckDigit) {
		case 10:
			intCheckDigit = 'X';
			break;
		case 11:
			intCheckDigit = 0;
			break;
		case 12:
			intCheckDigit = 1;
			break;
		}
		// check last digit
		if (varArray[17].toUpperCase() != intCheckDigit) {
			//error = "身份证效验位错误!正确为： " + intCheckDigit + ".";
			//alert(error);
			return false;
		}
	} else { //length is 15
	//check date
		var date6 = idNumber.substring(6, 12);
		if (checkDate(date6) == false) {
			//alert("身份证日期信息有误！.");
			return false;
		}
	}
	//alert ("Correct.");
	return true;
}

/**
 * 检查日期合法性， yyyyMMdd格式
 * @param date
 */
function checkDate(date){
	var today=new Date(); 
    var year=today.getFullYear();
    var month=today.getMonth()+1;
    var day=today.getDate();
    var _year=date.substring(0,4);
    var _month=date.substring(4,6);
    var _day=date.substring(6,8);
    if(_year<1900){return false;}
    if(_year<0){return false;}
    if(_month<0){return false;}
    if(_day<0){return false;}
	if(_year>year){
		return false;
	}else if(_year==year){
		if(_month>month){			
			return false;
		} else if(_month=month){
			if(_day>day){
				return false;
			}
		}
	}
	if(_day==29 && _month==2){
		 // 如果是2月份
	   if( (y / 100) * 100 == y && (y / 400) * 400 != y){
	     // 如果年份能被100整除但不能被400整除 (即闰年)
		   return true;
	   }else{
	     return false;
	   }
	}
	if(_day==30 && _month==2){
		return false;
	}
	if(_day==31){
		if(_month == 2 || _month == 4 || _month == 6 || _month == 9 || _month == 11){
		    // 2、4、6、9、11月没有31日
		   return false;
	    }
	}
	return true;
}

/**
 * 检查手机号码
 * @param s
 * @returns {Boolean}
 */
function chkMobile(s) {
	var regu = /^[1][3,5,8][0-9]{9}$/;
	var re = new RegExp(regu);
	return re.test(s);
}

/**
 * 验证中文
 * @param str
 * @returns {Boolean}
 */
function isCh(str){
	 // 正则表达式对象
	var re = new RegExp("^[\\u4e00-\\u9fa5]+$", "");
	 // 验证是否刚好匹配
	return re.test(str);
}

/**
 * 判断是否包含中文
 * @param str
 */
function hasCh(str){
	var patrn=/[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
	return patrn.exec(str);
}

/**
 * 分享到腾讯微博
 * @param _url 链接URL
 * @param _pic '图片url1|图片url2|图片url3....'
 * @param _t 内容
 */
function shareToTx(_url,_pic, _t){
	if(_url==null||_url==''){
		_url=document.location
	}
	_url = encodeURIComponent(_url);
	var _pic = encodeURI('');//（例如：var _pic='图片url1|图片url2|图片url3....）
	var metainfo = document.getElementsByTagName("meta");
	if(_t.length > 120){
		_t= _t.substr(0,117)+'...';
	}
	var _t = encodeURI(_t);
	var _u = 'http://share.v.t.qq.com/index.php?c=share&a=index&url='+_url+'&appkey=085b1bfa56e24008a8c7feba290e2324&pic='+_pic+'&assname=&title='+_t;
	window.open( _u,'', 'width=700, height=680, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, location=yes, resizable=no, status=no' );
}
/**
 * 分享到QQ空间
 * @param _url
 * @param _desc
 * @param _title
 * @param _site
 * @param _pics
 */
function shareToQZone(_url, _desc, _title, _site, _pics){
	if(_url==null||_url==''){
		_url=document.location
	}
	var p = {
	url:_url,
	desc:_desc,/* 默认分享理由(可选) */
	title:_title,/* 分享标题(可选) */
	site:_site,/* 分享来源 如：腾讯网(可选) */
	pics:_pics /* 分享图片的路径(可选) */
	};
	var s = [];
	for(var i in p){
	s.push(i + '=' + encodeURIComponent(p[i]||''));
	}
	window.open('http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?'+s.join('&'));
}
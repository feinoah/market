<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>中通福瑞——应用市场</title>
<%@ include file="/WEB-INF/views/commons/nocache.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx }/resources/public/styles/common-1.0.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/resources/public/styles/index-1.0.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/resources/public/styles/XYTipsWindow.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/resources/public/scripts/SlideTrans.js"></script>
<script type="text/javascript" src="${ctx }/resources/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/jquery-easyui-1.2.6/plugins/jquery.form.js"></script>
<script type="text/javascript" src="${ctx }/resources/public/scripts/utils.js"></script>
<!--[if lte IE 6]>  
<div id="ie6-warning"> 
<img src="x.gif" width="14" height="14" onclick="closeme();" alt="关闭提示" />您正在使用 Internet Explorer 6 低版本的IE浏览器。为更好的浏览本页，建议您将浏览器升级到 <a href="http://www.microsoft.com/china/windows/internet-explorer/ie8howto.aspx" target="_blank">IE8</a> 或以下浏览器：<a href="http://www.firefox.com.cn/download/">Firefox</a> / <a href="http://www.google.cn/chrome">Chrome</a> / <a href="http://www.apple.com.cn/safari/">Safari</a> / <a href="http://www.Opera.com/">Opera</a>  
</div>  
<script type="text/javascript">  
function closeme() 
{ 
   var div = document.getElementById("ie6-warning"); 
   div.style.display ="none"; 
} 
function position_fixed(el, eltop, elleft){  
// check if this is IE6  
if(!window.XMLHttpRequest)  
window.onscroll = function(){  
el.style.top = (document.documentElement.scrollTop + eltop)+"px";  
el.style.left = (document.documentElement.scrollLeft + elleft)+"px";  
}  
else el.style.position = "fixed";  
}  
position_fixed(document.getElementById("ie6-warning"),0, 0);  
</script>  
<![endif]--> 
<script type="text/javascript">
	var _loadingICO;
	$(function() {
		chkLogin();
		_loadingICO='resources/public/images/loading.gif';
		$('body').append("<script src='${ctx }/resources/public/scripts/XYTipsWindow-3.0.js'><\/script>")
		$('#reg').click(function(){
			regWin();
		});
		$('#login').click(function(){
			loginWin();
		});
		$('.tabBar li').each(function(i){
			$(this).click(function(){
				$('.content').hide().eq(i).show();
				$('.tabBar li').removeClass('currTab');
				$(this).addClass('currTab');
			});
		});
		$('.tab li').each(function(i){
			$(this).click(function(){
				$('.rank').hide().eq(i).show();
				$('.tab li').removeClass('currTab');
				$(this).addClass('currTab');
			});
		});
		$('.appTab li').each(function(i){
			$(this).click(function(){
				$('.container').hide().eq(i).show();
				$('.appTab li').removeClass('currTab');
				$(this).addClass('currTab');
			});
		});
	});
</script>
</head>
<body>
	<!-- header start -->
	<div id="header">
		<div id="nav">
			<div id="loginBefor">
				<button id="login">登录</button>
				<button id="reg">注册</button>
			</div>
			<div id="lolginAfter"></div>
		</div>
		<div id="searchContainer">
			<div id="logo">
				<input id="searchTxt" type="text"/>
				<button id="searchBtn"></button>
			</div>
		</div>
	</div>
	<!-- header end -->
	
	<!-- ad start -->
	<div id="ad">
		<ul>
		</ul>
		<div id="page_nav">
			<span class="current">1</span>
			<span>2</span>
			<span>3</span>
			<span>4</span>
			<span>5</span>
		</div>
	</div>
	<!-- ad end -->
	
	<!-- main start -->
	<div id="main">
		<div id="left_menu">
			<ul class="tabBar">
				<li class="currTab">
					<span>排行榜</span>
				</li>
				<li><span>分类浏览</span></li>
				<li><span>最新免费</span></li>
			</ul>
			<!-- 排行榜 -->
			<div class="content">
				<ul class="tab">
					<li class="currTab">热门免费</li>
					<li>热门新品</li>
				</ul>
				<div class="rank">
					<ul>
						<c:forEach items="${hotFreeList}" var="hotFree" varStatus="stat" begin="0" end="7">
						<li><span>${stat.count}</span><a href="#">${hotFree.appName}</a><label>${hotFree.developer}</label></li>
						</c:forEach>
					</ul>
					<div class="more">
					<a>所有免费应用<span>>></span></a>
					</div>
				</div>
				<div class="rank" style="display: none;">
				</div>
			</div>
			<!-- 分类 -->
			<div class="content" style="display: none;">
				<ul class="catalog">
					<c:forEach items="${catalogList}" var="catalog" varStatus="stat" begin="0" end="9">
					<li><span>${stat.count}</span><a href="#">${catalog.name}</a></li>
					</c:forEach>
				</ul>
			</div>
			<!-- hotfree -->
			<div class="content" style="display: none;">
				<ul class="hotfree">
					<c:forEach items="${hotFreeList}" var="hotFree" varStatus="stat" begin="0" end="9">
					<li><span>${stat.count}</span><a href="#">${hotFree.appName}</a><label>${hotFree.developer}</label></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div id="right_container">
			<ul class="appTab">
				<li class="currTab">推荐应用</li>
				<li>推荐应用·车机</li>
			</ul>
			<div class="container">
				<ul>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
					<li><img /><span>Map</span><label>GOOGLE INC</label></li>
				</ul>
				<div class="page">
					<span class="prev"><</span>
					<span></span>
					<span></span>
					<span class="next">></span>
				</div>
			</div>
		</div>
	</div>
	<!-- main end -->
	<div id="footer">footer</div>
</body>
</html>

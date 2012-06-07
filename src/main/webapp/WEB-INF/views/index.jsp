<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>中通福瑞——应用市场</title>
<%@ include file="/WEB-INF/views/commons/nocache.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${ctx }/resources/favicon.ico" type="image/x-icon" />
<link rel="icon" href="${ctx }/resources/favicon.ico" type="image/x-icon" />
<link href="${ctx }/resources/public/styles/index.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/resources/public/styles/XYTipsWindow.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/resources/public/scripts/SlideTrans.js"></script>
<script type="text/javascript" src="${ctx }/resources/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/public/scripts/utils.js"></script>
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
	});
</script>
</head>
<body style="margin: 0; padding: 0; text-align: center;">

	<div id="wrapper">
		<div id="header">
			<ul>
				<li><img src="${ctx }/resources/public/images/carit_logo1.png"/></li>
				<li><input type="text" name="txtNo"/></li>
				<li><button id="search">搜索</button></li>
				<li name="before_login"><button id="login">登录</button></li>
				<li name="before_login"><button id="reg" type="button">注册</button></li>
				<li id="after_login" style="display: none;"><span></span></li>
			</ul>
		</div>

		<div class="container" id="idContainer"
			style="height: 311px; width: 1024px; background-color: #DEDEDE;">
			<table id="idSlider" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><a
						href="http://www.cnblogs.com/cloudgamer/archive/2008/07/06/SlideTrans.html"><img
							src="${ctx }/resources/public/images/first_ad.png" /></a></td>
					<td><a
						href="http://www.cnblogs.com/cloudgamer/archive/2009/01/06/Tween.html"><img
							src="${ctx }/resources/public/images/first_ad.png" /></a></td>
					<td><a
						href="http://www.cnblogs.com/cloudgamer/archive/2008/07/21/ImgCropper.html"><img
							src="${ctx }/resources/public/images/first_ad.png" /></a></td>
					<td><a
						href="http://www.cnblogs.com/cloudgamer/archive/2008/07/21/ImgCropper.html"><img
							src="${ctx }/resources/public/images/first_ad.png" /></a></td>
				</tr>
			</table>
			<ul class="num" id="idNum">
			</ul>
		</div>

		<div id="left" style="float: left; width: 29%; margin-top: 7px">
			<div id="Tab1" style="float: left; width: 330px; height: 500px;">

				<div class="Menubox">
					<ul>
						<li id="one1" onclick="setTab('one',1,2)">排行榜</li>
						<li id="one2" onclick="setTab('one',2,2)">分类浏览</li>
					</ul>
				</div>
				<div class="Contentbox" style="background-color: white">
					<div id="con_one_1">
						<font color="#104E8B" size="5px" style="margin-left: 20px"><b>热门免费</b></font>
						<ul id="freeTop">
							<c:forEach items="${hotFreeList}" var="hotFree" varStatus="stat" begin="0" end="4">
							<li><span>${stat.index}</span><a href="#">${hotFree.appName}</a><label>${hotFree.developer}</label></li>
							</c:forEach>
							<li class="more">全部免费应用</li>
						</ul>
					</div>
					<div id="con_one_2" style="display: none">
						<ul id="catalog_con">
							<c:forEach items="${catalogList}" var="catalog" varStatus="stat">
							<li><span>${stat.index}</span><a href="#">${catalog.name}</a></li>
							</c:forEach>
							<li>&nbsp;</li>
						</ul>
					</div>
				</div>
			</div>
			<div id="left_bottom"
				style="float: left; width: 330px; height: 445px; margin-top: 13px; background-color: white">
				<div id="con_one_1" style="padding: 30px 0;">
					<font color="#104E8B" size="5px" style="margin-left: 20px"><b>热门免费新品</b></font>
					<ul id="freeNewTop">
						<li><span>1</span><a href="d">Maps</a><label>GOOGLEINC.</label></li>
						<li class="more">全部新应用</li>
					</ul>
				</div>

			</div>
		</div>
		<div id="Tab2"
			style="float: right; width: 66%; height: 586px; margin-top: 7px;">
			<div class="Menubox">
				<ul>
					<li id="two1" onclick="setTab('two',1,2)">店员推荐</li>
					<li id="two2" onclick="setTab('two',2,2)">店员推荐·车机</li>

				</ul>
			</div>
			<div class="Contentbox2" style="background-color: white">
				<div id="con_two_1">
					<div id="clerk_recommend">
						<c:forEach items="${hotFreeList}" var="app">
						<div title="${app.appName}">
							<img src="${app.icon}" alt="${app.appName}" />
							<label class="label1">${app.appName}</label><label>${app.developer}</label>
							<img src="${ctx }/resources/public/images/star.jpg" style="display: inline-block" /><span>(${app.downCount})</span>
						</div>
						</c:forEach>
					<div class="carousel-more-link">
						<a href="">查看更多</a><a href="" class="more-arrow-link"><span
							class="more-arrow" style="margin-right: 45px">›</span></a>
					</div>

				</div>
				<div id="con_two_2" style="display: none">
					<div id="clerk_recommend_car">
						<c:forEach items="${hotNewFreeList}" var="app">
						<div title="${app.appName}">
							<img src="${app.icon}" alt="${app.appName}" />
							<label class="label1">${app.appName}</label><label>${app.developer}</label>
							<img src="${ctx }/resources/public/images/star.jpg" style="display: inline-block" /><span>(${app.downCount})</span>
						</div>
						</c:forEach>
					</div>
					<div class="carousel-more-link">
						<a href="">查看更多</a><a href="" class="more-arrow-link"><span
							class="more-arrow" style="margin-right: 45px">›</span></a>
					</div>
				</div>
			</div>
		</div>
		<div class="wide-editorial-image-panel"
			style="float: right; background-color: white">
			<div class="panel-image goog-inline-block">
				<a href=""><img
					src="${ctx }/resources/public/images/editer_recommend.jpg" alt="编辑推荐"></a>
			</div>
			<div class="panel-summary goog-inline-block">
				<h3>
					<a href="">编辑推荐</a>
				</h3>
				<div class="panel-description">我们的工作人员从 Google Play
					中挑选的部分最佳应用。</div>
				13691854095
				<div class="panel-see-more">
					<a href="">查看全部</a><a href="" class="more-arrow-link"> <span
						class="more-arrow">›</span></a>
				</div>
			</div>
		</div>
	</div>
	<script>
		var nums = [], timer, n = $$("idSlider").getElementsByTagName("td").length, st = new SlideTrans(
				"idContainer", "idSlider", 4, {
					Vertical : false,
					onStart : function() {//设置按钮样式
						forEach(nums, function(o, i) {
							o.className = st.Index == i ? "on" : "";
						})
					}
				});

		for ( var i = 1; i <= n; AddNum(i++)) {
		};
		function AddNum(i) {
			var num = $$("idNum").appendChild(document.createElement("li"));
			num.innerHTML = i--;
			num.onmouseover = function() {
				timer = setTimeout(function() {
					num.className = "on";
					st.Auto = false;
					st.Run(i);
				}, 200);
			}
			num.onmouseout = function() {
				clearTimeout(timer);
				num.className = "";
				st.Auto = true;
				st.Run();
			}
			nums[i] = num;
		}
		st.Run();

		/*第一种形式 第二种形式 更换显示样式*/
		function setTab(name, cursel, n) {
			for (i = 1; i <= n; i++) {
				var menu = document.getElementById(name+i);
				var con = document.getElementById("con_"+name+"_"+i);
				menu.className = i == cursel ? "hover" : "";
				con.style.display = i == cursel ? "block" : "none";
			}
		}
		window.onload = function() {
			setTab("one", 1, 2);
			setTab("two", 1, 2);
		}
	</script>

</body>
</html>

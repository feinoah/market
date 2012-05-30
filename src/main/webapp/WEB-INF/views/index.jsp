<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!--  <base href="<c:out value="${pageContext.request.contextPath}"/>"/>-->
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="<c:url value="/resources/public/scripts/SlideTrans.js"/>" ></script>
	<link href="<c:url value="/resources/public/styles/default.css"/>" rel="stylesheet" type="text/css" />
	<link href="<c:url value="/resources/public/styles/app_detail.css"/>" rel="stylesheet" type="text/css" />
		<title>CarIt Market</title></head>
	<body style="margin:0;padding:0;text-align:center;">

<div id="wrapper" style="width:1024px;margin:0 auto;text-align:left;position:relative;">
<div id="header" style="height:75px;width:100%;background-color:#DEDEDE;">
<table>
<tr>
<td><img src="resources/public/images/carit_logo1.png" height="62px" width="154px" style="margin-left: 50px;margin-top: 8px"/></td>
<td><input type="text" name="txtNo" size="20" style="height:40px;width:312px;margin-left:200px;"></input></td>
<td><button type="button" style="height:42px;width:72px;margin-left:5px;">search</button></td>
<td><button type="button" style="height:42px;width:72px;margin-left:30px;">logo in</button></td>
<td><button type="button" style="height:42px;width:72px;margin-left:5px;">register</button></td>
</tr>
</table>
</div>

<div class="container" id="idContainer" style="height:311px;width:1024px;background-color:#DEDEDE;">
<table id="idSlider" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><a href="http://www.cnblogs.com/cloudgamer/archive/2008/07/06/SlideTrans.html"><img src="resources/public/images/first_ad.png"/></a></td>
			<td><a href="http://www.cnblogs.com/cloudgamer/archive/2009/01/06/Tween.html"><img src="resources/public/images/first_ad.png"/></a></td>
			<td><a href="http://www.cnblogs.com/cloudgamer/archive/2008/07/21/ImgCropper.html"><img src="resources/public/images/first_ad.png"/></a></td>
		    <td><a href="http://www.cnblogs.com/cloudgamer/archive/2008/07/21/ImgCropper.html"><img src="resources/public/images/first_ad.png"/></a></td>
		</tr>
	</table>
	<!--  <div style="width: 1024px;height: 45px;background-color:#DEDEDE;">

</div>-->
	<ul class="num" id="idNum">
	</ul>
</div>

  

<div id="left" style="float:left;width:29%;margin-top:7px">
<div id="Tab1" style="float:left;width:330px;height:500px;">

<div class="Menubox">
<ul>
   <li id="one1" onclick="setTab('one',1,2)" >排行榜</li>
   <li id="one2" onclick="setTab('one',2,2)" >分类浏览</li>
   
</ul>
</div>
 <div class="Contentbox" style="background-color: white">  
   <div id="con_one_1" >
   <font color="#104E8B" size="5px" style="margin-left: 20px"><b>热门免费</b></font>
     <table border="2px" cellspacing="0px" width="242x" style="border-collapse:collapse;margin-left:42px;border-right: 0px;border-left: 0px">
   <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
    <label style="margin-left: 20px;width :10px;display: inline-block">1</label>
    <label style="margin-left: 25px;width:60%;display: inline-block">Maps</label>
      <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">GOOGLEINC.</label>
   </td></tr>
     <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
      <label style="margin-left: 20px;width :10px;display: inline-block">2</label>
      <label style="margin-left: 25px;width:60%;display: inline-block">Adobe Flash Player 11</label>
       <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">ADOBESYSTEMS.</label>
     </td></tr>
       <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
        <label style="margin-left: 20px;width :10px;display: inline-block">3</label>
        <label style="margin-left: 25px;width:60%;display: inline-block">Google Search</label>
         <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">GOOGLEINC.</label>
       </td></tr>
         <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
          <label style="margin-left: 20px;width :10px;display: inline-block">4</label>
          <label style="margin-left: 25px;width:60%;display: inline-block">手机QQ2012</label>
           <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">腾讯</label>
         </td></tr>
           <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
            <label style="margin-left: 20px;width :10px;display: inline-block">5</label>
            <label style="margin-left: 25px;width :60%;display: inline-block">Facebook for Android</label>
             <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">FACEBOOK.</label>
           </td></tr>
   </table>
   <font color="#104E8B" size="3px" style="margin-left: 90px;display: inline-block;margin-top: 15px"><b>所有热门免费应用</b></font>
   <img src="resources/public/images/all_free_app_button.jpg" alt="" style="display: inline-block"/>
   </div>
   <div id="con_one_2" style="display:none">
   <table border="2px" cellspacing="0px" width="242x" style="border-collapse:collapse;margin-left:42px;border-right: 0px;border-left: 0px">
   <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
    <label style="margin-left: 20px;width :10px;display: inline-block">1</label>
    <label style="margin-left: 25px;width:60%;display: inline-block">Maps</label>
      <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">GOOGLEINC.</label>
   </td></tr>
     <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
      <label style="margin-left: 20px;width :10px;display: inline-block">2</label>
      <label style="margin-left: 25px;width:60%;display: inline-block">Adobe Flash Player 11</label>
       <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">ADOBESYSTEMS.</label>
     </td></tr>
       <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
        <label style="margin-left: 20px;width :10px;display: inline-block">3</label>
        <label style="margin-left: 25px;width:60%;display: inline-block">Google Search</label>
         <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">GOOGLEINC.</label>
       </td></tr>
         <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
          <label style="margin-left: 20px;width :10px;display: inline-block">4</label>
          <label style="margin-left: 25px;width:60%;display: inline-block">手机QQ2012</label>
           <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">腾讯</label>
         </td></tr>
           <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
            <label style="margin-left: 20px;width :10px;display: inline-block">5</label>
            <label style="margin-left: 25px;width :60%;display: inline-block">Facebook for Android</label>
             <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">FACEBOOK.</label>
           </td></tr>
   </table>
   </div>
 
   
   
 </div>

</div>
<div id="left_bottom" style="float:left;width:330px;height:445px;margin-top:13px;background-color: white">
<div style="margin-top:25px;margin-left:32px">
<font color="#104E8B" size="3px" style="margin-left: 10px"><b>热门免费新品</b></font>

<table border="2px" cellspacing="0px" width="242x" style="border-collapse:collapse;border-right: 0px;border-left: 0px">
   <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
    <label style="margin-left: 20px;width :10px;display: inline-block">1</label>
    <label style="margin-left: 25px;width:60%;display: inline-block">Maps</label>
      <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">GOOGLEINC.</label>
   </td></tr>
     <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
      <label style="margin-left: 20px;width :10px;display: inline-block">2</label>
      <label style="margin-left: 25px;width:60%;display: inline-block">Adobe Flash Player 11</label>
       <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">ADOBESYSTEMS.</label>
     </td></tr>
       <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
        <label style="margin-left: 20px;width :10px;display: inline-block">3</label>
        <label style="margin-left: 25px;width:60%;display: inline-block">Google Search</label>
         <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">GOOGLEINC.</label>
       </td></tr>
         <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
          <label style="margin-left: 20px;width :10px;display: inline-block">4</label>
          <label style="margin-left: 25px;width:60%;display: inline-block">手机QQ2012</label>
           <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">腾讯</label>
         </td></tr>
           <tr style="height: 70px;"><td style="border-right: 0px;border-left: 0px">
            <label style="margin-left: 20px;width :10px;display: inline-block">5</label>
            <label style="margin-left: 25px;width :60%;display: inline-block">Facebook for Android</label>
             <label style="margin-left: 58px;width :60%;color:gray;font-size: 12px">FACEBOOK.</label>
           </td></tr>
   </table>
 <a href="" style="margin-left:10px">所有热门免费应用</a><a href="" class="more-arrow-link"><span class="more-arrow">›</span></a>
</div>

</div>
</div>
<div id="Tab2" style="float:right;width:66%;height:586px;margin-top:7px;">
<div class="Menubox">
<ul>
   <li id="two1" onclick="setTab('two',1,2)" >店员推荐</li>
   <li id="two2" onclick="setTab('two',2,2)" >店员推荐·车机</li>
 
</ul>
</div>
 <div class="Contentbox2" style="background-color: white">  
   <div id="con_two_1" >
   <div style="border-bottom: 1px solid #D7D7D7;margin-left:38px;margin-right:38px"> 
   
   
   <table width="600px" cellspacing="0px" style="border-collapse:collapse;border-right: 0px;border-left: 0px;border-bottom: 1px">
<tr style="height: 154px;">
   <td align="left" style="width: 150px">
   <img src="resources/public/images/icon1.jpg" alt="" style="display: block"/>
   <label style="width:100%;display: block">Recipe Search</label>
    <label style="display: block;color: gray;font-size: 12px">ALLTHECOOKS.COM</label>
     <img src="resources/public/images/star.jpg" style="display: inline-block"/><label style="display: inline-block;font-size: 12px">(10,898)</label>
     <input name="install" type="submit" id="install" value="" style="background:url(resources/public/install.jpg); border-style:none; width:36px; height:18px; background-repeat:no-repeat;" />
    <a href="appDetail.jsp">安装</a>
      </td>
   <td style="width: 150px">
    <img src="resources/public/images/icon1.jpg" alt="" style="display: block"/>
   <label style="width:100%;display: block">Recipe Search</label>
    <label style="display: block;color: gray;font-size: 12px">ALLTHECOOKS.COM</label>
     <img src="resources/public/images/star.jpg" style="display: inline-block"/><label style="display: inline-block;font-size: 12px">(10,898)</label>
      <img src="resources/public/images/install.jpg" alt="" />
      </td>
   <td style="width: 150px">
    <img src="resources/public/images/icon1.jpg" alt="" style="display: block"/>
   <label style="width:100%;display: block">Recipe Search</label>
    <label style="display: block;color: gray;font-size: 12px">ALLTHECOOKS.COM</label>
     <img src="resources/public/images/star.jpg" style="display: inline-block"/><label style="display: inline-block;font-size: 12px">(10,898)</label>
      <img src="resources/public/images/install.jpg" alt="" />
      </td>
   <td style="width: 150px">
    <img src="resources/public/images/icon1.jpg" alt="" style="display: block"/>
   <label style="width:100%;display: block">Recipe Search</label>
    <label style="display: block;color: gray;font-size: 12px">ALLTHECOOKS.COM</label>
     <img src="resources/public/images/star.jpg" style="display: inline-block"/><label style="display: inline-block;font-size: 12px">(10,898)</label>
      <img src="resources/public/images/install.jpg" alt="" />
      </td></tr>
<tr style="height: 154px"><td>
    <img src="resources/public/images/icon1.jpg" alt="" style="display: block"/>
   <label style="width:100%;display: block">Recipe Search</label>
    <label style="display: block;color: gray;font-size: 12px">ALLTHECOOKS.COM</label>
     <img src="resources/public/images/star.jpg" style="display: inline-block"/><label style="display: inline-block;font-size: 12px">(10,898)</label>
      <img src="resources/public/images/install.jpg" alt="" />
   </td><td>
    <img src="resources/public/images/icon1.jpg" alt="" style="display: block"/>
   <label style="width:100%;display: block">Recipe Search</label>
    <label style="display: block;color: gray;font-size: 12px">ALLTHECOOKS.COM</label>
     <img src="resources/public/images/star.jpg" style="display: inline-block"/><label style="display: inline-block;font-size: 12px">(10,898)</label>
      <img src="resources/public/images/install.jpg" alt="" />
   </td><td>
    <img src="resources/public/images/icon1.jpg" alt="" style="display: block"/>
   <label style="width:100%;display: block">Recipe Search</label>
    <label style="display: block;color: gray;font-size: 12px">ALLTHECOOKS.COM</label>
     <img src="resources/public/images/star.jpg" style="display: inline-block"/><label style="display: inline-block;font-size: 12px">(10,898)</label>
      <img src="resources/public/images/install.jpg" alt="" />
   </td><td>
    <img src="resources/public/images/icon1.jpg" alt="" style="display: block"/>
   <label style="width:100%;display: block">Recipe Search</label>
    <label style="display: block;color: gray;font-size: 12px">ALLTHECOOKS.COM</label>
     <img src="resources/public/images/star.jpg" style="display: inline-block"/><label style="display: inline-block;font-size: 12px">(10,898)</label>
      <img src="resources/public/images/install.jpg" alt="" />
   </td></tr>
<tr style="height: 154px"><td>
    <img src="resources/public/images/icon1.jpg" alt="" style="display: block"/>
   <label style="width:100%;display: block">Recipe Search</label>
    <label style="display: block;color: gray;font-size: 12px">ALLTHECOOKS.COM</label>
     <img src="resources/public/images/star.jpg" style="display: inline-block"/><label style="display: inline-block;font-size: 12px">(10,898)</label>
      <img src="resources/public/images/install.jpg" alt="" />
   </td><td>
    <img src="resources/public/images/icon1.jpg" alt="" style="display: block"/>
   <label style="width:100%;display: block">Recipe Search</label>
    <label style="display: block;color: gray;font-size: 12px">ALLTHECOOKS.COM</label>
     <img src="resources/public/images/star.jpg" style="display: inline-block"/><label style="display: inline-block;font-size: 12px">(10,898)</label>
      <img src="resources/public/images/install.jpg" alt="" />
   </td><td>
    <img src="resources/public/images/icon1.jpg" alt="" style="display: block"/>
   <label style="width:100%;display: block">Recipe Search</label>
    <label style="display: block;color: gray;font-size: 12px">ALLTHECOOKS.COM</label>
     <img src="resources/public/images/star.jpg" style="display: inline-block"/><label style="display: inline-block;font-size: 12px">(10,898)</label>
      <img src="resources/public/images/install.jpg" alt="" />
   </td><td>
    <img src="resources/public/images/icon1.jpg" alt="" style="display: block"/>
   <label style="width:100%;display: block">Recipe Search</label>
    <label style="display: block;color: gray;font-size: 12px">ALLTHECOOKS.COM</label>
     <img src="resources/public/images/star.jpg" style="display: inline-block"/><label style="display: inline-block;font-size: 12px">(10,898)</label>
      <img src="resources/public/images/install.jpg" alt="" />
   </td></tr>
   </table>
   </div>
   <div class="carousel-more-link">
   <a href="">查看更多</a><a href="" class="more-arrow-link"><span class="more-arrow" style="margin-right: 45px">›</span></a></div>
   
   </div>
   <div id="con_two_2" style="display:none">店员推荐·车机</div>
  
 </div>
 </div>
 
 <div class="wide-editorial-image-panel" style="float:right;background-color: white">
 <div class="panel-image goog-inline-block">
 <a href=""><img src="resources/public/images/editer_recommend.jpg" alt="编辑推荐"></a></div>
 <div class="panel-summary goog-inline-block">
 <h3><a href="">编辑推荐</a></h3>
 <div class="panel-description">我们的工作人员从 Google Play 中挑选的部分最佳应用。</div>13691854095
 <div class="panel-see-more"><a href="">查看全部</a><a href="" class="more-arrow-link">
 <span class="more-arrow">›</span></a></div></div></div>
 
 
</div>

<script>
var nums = [], timer, n = $$("idSlider").getElementsByTagName("td").length,
st = new SlideTrans("idContainer", "idSlider", 4, { Vertical: false,onStart: function(){//设置按钮样式
	forEach(nums, function(o, i){ o.className = st.Index == i ? "on" : ""; })
}});

for(var i = 1; i <= n; AddNum(i++)){};
function AddNum(i){
	var num = $$("idNum").appendChild(document.createElement("li"));
	num.innerHTML = i--;
	num.onmouseover = function(){
		timer = setTimeout(function(){ num.className = "on"; st.Auto = false; st.Run(i); }, 200);
	}
	num.onmouseout = function(){ clearTimeout(timer); num.className = ""; st.Auto = true; st.Run(); 
	}
	nums[i] = num;
}
st.Run();

/*第一种形式 第二种形式 更换显示样式*/
function setTab(name,cursel,n){
 for(i=1;i<=n;i++){
  var menu=document.getElementById(name+i);
  var con=document.getElementById("con_"+name+"_"+i);
  menu.className=i==cursel?"hover":"";
  con.style.display=i==cursel?"block":"none";
 }
}
 window.onload = function()
 {
   setTab("one",1,2);
   setTab("two",1,2);
 }

</script>

	</body>
</html>

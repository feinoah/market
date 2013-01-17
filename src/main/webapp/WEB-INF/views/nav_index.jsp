<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
    <head>
    	<title>中通福瑞电子科技有限公司--Android Market 3G安卓DVD导航、安卓车载电脑、安卓智能车机、android专用导航、安卓车载DVD一体机</title>
	    <meta charset="utf-8"/>
		<!-- Mobile viewport optimisation -->
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<meta name="keywords" content="中通福瑞  汽车  导航  车机   助手" />
		<meta name="description" content="中通福瑞  汽车  导航  车机   助手">
		<link rel="shortcut icon" href="${ctx }/resources/favicon.ico" type="image/x-icon">
	   	<link rel="icon" href="${ctx }/resources/favicon.ico" type="image/x-icon">
       	<link href="${ctx}/resources/public/styles/MetroJs.css" rel="stylesheet" type="text/css" />
       	<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
       	<script type="text/javascript" src="${ctx}/resources/public/scripts/jquery.masonry.min.js"></script>
	   	<script type="text/javascript" src="${ctx}/resources/public/scripts/MetroJs.js"></script>
       	<script type="text/javascript">
       	$(function(){
    	   $('.wrapper_home').each(function(){
			  $(this).masonry({
				  itemSelector : '.item',
				  columnWidth : 10
			  });
    	   });
    	   <c:forEach items="${catalogList}" var="catalog" varStatus="stat">
    	   if($('#${catalog.id}_container:has("div")').length==0){
    		   $('#${catalog.id}_title').remove();
    		   $('#${catalog.id}_container').remove();
    	   }
    	   </c:forEach>
           $('.item').click(function(){
        	   open($(this).attr('url'));
           });
           // logo 图片加载出错则删除图片标签
           $('.item img').error(function(){
        	   $(this).parent().html($(this).parent().attr('name'));
        	   //$(this).remove();
           });
		});
       </script>
    </head>
    <body>
    	<header></header>
        <div id="main">
		<c:forEach items="${catalogList}" var="catalog">
        	<c:if test="${not empty catalog.navs}">
        	<h2 id="${catalog.id}_title"><a name="${catalog.id}" href="#${catalog.id}">${catalog.name}</a></h2>
			<div id="${catalog.id}_container" class="wrapper_home">
			<c:forEach items="${catalog.navs}" var="nav">
				<div name="${nav.name}" url="${nav.url}" class="item ${nav.cssClass}"><c:if test="${empty nav.logo}">${nav.name}</c:if><c:if test="${not empty nav.logo}"><img src="${nav.logo}"/></c:if></div>
			</c:forEach>
			</div>
        	</c:if>
        </c:forEach>
		</div>
	    <footer>
			<div>
				<p>© Company 2012 – <a href="http://www.caritglobal.com">CARIT ELECTRONICS</a></p>
			</div>
		</footer>
    </body>
</html>
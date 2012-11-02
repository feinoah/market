	<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
	<title><spring:message code="global.company.name"/>--<spring:message code="global.website.title"/></title>
	<meta charset="utf-8"/>
	<!-- Mobile viewport optimisation -->
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<meta name="keywords" content="<spring:message code="global.keywords"/>" />
	<meta name="description" content="<spring:message code="global.description"/>">
	<%@ include file="/WEB-INF/views/commons/nocache.jsp"%>
	<link rel="shortcut icon" href="${ctx }/resources/favicon.ico" type="image/x-icon">
	<link rel="icon" href="${ctx }/resources/favicon.ico" type="image/x-icon">
	<link href="${ctx }/resources/public/styles/flexible-grids.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/base/jquery-ui.css" />
	<link rel="stylesheet" href="${ctx }/resources/public/styles/XYTipsWindow.css" type="text/css"/>
	<!--[if lte IE 7]>
	<link href="${ctx }/resources/yaml/core/iehacks.css" rel="stylesheet" type="text/css" />
	<![endif]-->

	<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/ui/1.9.0/jquery-ui.min.js"></script>
	<script type="text/javascript" src="resources/public/scripts/utils.js?v1.2"></script>
	<script type="text/javascript" src="resources/public/scripts/index.js?v1.2"></script>
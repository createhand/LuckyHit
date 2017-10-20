<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires", -1); 
	if (request.getProtocol().equals("HTTP/1.1")) 
	    response.setHeader("Cache-Control", "no-cache");
%>
<!DOCTYPE HTML language="ko">
<html>
<head>
<title><tiles:getAsString name="title" /></title>
<!-- script -->
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%><tiles:getAsString name="script" />"></script>

<!-- css -->
<link rel="stylesheet" type="text/css" href="css/default.css" />
<link rel="stylesheet" type="text/css" href="css/layout.css" />
<link rel="stylesheet" type="text/css" href="css/jquery/ui.core.css" />
<link rel="stylesheet" type="text/css" href="css/jquery/ui.datepicker.css" />
<link rel="stylesheet" type="text/css" href="css/jquery/ui.theme.css" />
</head>
<!-- body oncontextmenu="return false" onselectstart="return false" ondragstart="return false" -->
<body>
	<tiles:insertAttribute name="content"/>
</body>
</html>
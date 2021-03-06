<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires", -1); 
	if (request.getProtocol().equals("HTTP/1.1")) 
	    response.setHeader("Cache-Control", "no-cache");
%>

<html xmlns="http://www.w3.org/1999/xhtml" xmlns:og="http://ogp.me/ns/fb#" xmlns:fb="http://www.facebook.com/2008/fbml" lang="ko-KR">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta property="og:type" content="article">
<meta property="og:site_name" content="LuckyHit">
<title><tiles:insertAttribute name="title"/></title>
 <!-- css -->
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>css/main.css" media="screen" />
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>css/print.css" media="print" />
 <!-- script -->
 <script type="text/javascript" src="<%=request.getContextPath() %>js/common.js"></script>
 <script type="text/javascript" src="<%=request.getContextPath() %>js/util.js"></script>
 <script type="text/javascript" src="<%=request.getContextPath() %>js/jquery/jquery-1.8.3.min.js"></script>
 <script type="text/javascript" src="<%=request.getContextPath() %><tiles:getAsString name="script" />"></script>
 </head>
<!-- <body oncontextmenu="return false" onselectstart="return false" ondragstart="return false">  -->
<body>
<div id="page">
	<tiles:insertAttribute name="header"/>
	<div id="body">
	<tiles:insertAttribute name="content"/>
	</div>
	<tiles:insertAttribute name="footer"/>
</div>
</body></html>
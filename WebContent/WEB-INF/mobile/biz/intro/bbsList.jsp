<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<%
	String bo_table = request.getParameter("bo_table");
	if(bo_table == null) bo_table = "freeboard";
%>
<%
%>
      <div class="content_title">
  		<h2 class="header">
  			<span id="freeboard" style="font-size: 18px;">자유게시판</span> | <span id="preview" style="font-size: 18px;">리뷰&프리뷰</span>
  		</h2>
      </div>
<div class="content">
	<iframe src="http://kimpele.com/gnuboard4/bbs/board.php?bo_table=<%=bo_table%>" frameborder="0" style="border:0px;width: 100%;height:100%;padding: 0px;margin: 0px;"></iframe>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="kr.co.toto.comn.model.TAData" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<%
	String errMsg = (String)request.getAttribute("errMsg");
	int result = (Integer)request.getAttribute("result");
	TAData params = (TAData)request.getAttribute("params");
%>
<script>
<%
	if(StringUtils.isBlank(errMsg) && result == 1) {
%>
		alert("등록되었습니다.");
		location.href = "<%=request.getContextPath() %>gameResultDetail.do?pageNo=<%=params.getString("pageNo")%>&gmCd=<%=params.getString("gmCd")%>&gmPostNo=<%=params.getString("gmPostNo")%>";
<%
	} else {
%>
	alert("<%=errMsg%>");
	history.back();
<%
	}
%>
</script>
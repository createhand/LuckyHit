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
	boolean isLogin = (Boolean)request.getAttribute("isLogin");
	TAData userInfo = (TAData)request.getAttribute("userInfo");
	
	if(isLogin) {
		session.setAttribute("userNo", userInfo.getString("USER_NO"));
		session.setAttribute("userId", userInfo.getString("USER_ID"));
		session.setAttribute("userNm", userInfo.getString("USER_NM"));
	}
	
%>
<script>
	if(<%=isLogin%>) {
		location.href = "<%=request.getContextPath() %>myPage.do";
	} else {
		alert("<%=errMsg%>");
		location.href = "<%=request.getContextPath() %>userLogin.do";
	}
</script>
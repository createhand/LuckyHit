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
%>
<script>
<%
	if(StringUtils.isBlank(errMsg)) {
		//정상가입 후 로그인 처리
		TAData userInfo = (TAData)request.getAttribute("userInfo");
		session.setAttribute("userNo", userInfo.getString("USER_NO"));
		session.setAttribute("userId", userInfo.getString("USER_ID"));
		session.setAttribute("userNm", userInfo.getString("USER_NM"));
%>
		alert("가입되었습니다.");
		location.href = "<%=request.getContextPath() %>myPage.do";
<%
	} else {
%>
	alert("<%=errMsg%>");
	history.back();
<%
	}
%>
</script>
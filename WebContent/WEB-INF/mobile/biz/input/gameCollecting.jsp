<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<%
	Integer insertGameCnt = (Integer)request.getAttribute("insertGameCnt");
	Integer updateGameCnt = (Integer)request.getAttribute("updateGameCnt");
	Integer insertMatchCnt = (Integer)request.getAttribute("insertMatchCnt");
	Integer updateMatchCnt = (Integer)request.getAttribute("updateMatchCnt");
	
	List<String> errMsg = (List<String>)request.getAttribute("errMsg");
%>
등록된 게임 : <%=insertGameCnt.intValue()%><br/>
수정된 게임 : <%=updateGameCnt.intValue()%><br/>
등록된 경기 : <%=insertMatchCnt.intValue()%><br/>
수정된 경기 : <%=updateMatchCnt.intValue()%><br/>
<br/><br/><br/>
<%
	if(errMsg.size() > 0) {
		out.println("<b>오류메시지</b><br/><br/>");
	}
%>
<%
	for(int i=0;i<errMsg.size();i++) {
		out.print(errMsg.get(i)+"<br/>");
	}
%>
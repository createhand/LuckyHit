<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<%
	Integer iResult = (Integer)request.getAttribute("iResult");
	Integer uResult = (Integer)request.getAttribute("uResult");
	Integer sResult = (Integer)request.getAttribute("sResult");
	List<String> errMsg = (List<String>)request.getAttribute("errMsg");
%>
시즌 <%=sResult.intValue()%> 건이 등록되었습니다.<br/>
팀 <%=iResult.intValue()%> 건이 등록되었습니다.<br/>
팀 순위정보 <%=uResult.intValue()%> 건이 수정되었습니다.<br/><br/><br/>
오류메시지<br/>
<%
	for(int i=0;i<errMsg.size();i++) {
		out.print(errMsg.get(i)+"<br/>");
	}
%>
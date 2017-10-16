<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<%
	Integer mResult = (Integer)request.getAttribute("mResult");
	Integer sResult = (Integer)request.getAttribute("sResult");
	List<String> errMsgList = (List<String>)request.getAttribute("errMsg");
	String errMsg = "";
	
	for(int i=0;i<errMsgList.size();i++) {
		errMsg = errMsgList.get(i)+"\\n";
	}
%>
<script>
<%
	if(StringUtils.isNotBlank(errMsg)) {
%>		
		alert("오류메시지 : <%=errMsg%>");
<%		
	} else {
%>
		alert("정상적으로 등록되었습니다.");
<%		
	}
%>
location.href = "gameResult.do"
</script>
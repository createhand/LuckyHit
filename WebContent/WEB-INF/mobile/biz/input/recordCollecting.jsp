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
	List<String> errMsgList = (List<String>)request.getAttribute("errMsg");
	String msg = iResult.intValue() + " 건 입력되었습니다.\\n";
	msg += uResult.intValue() + " 건 수정되었습니다.\\n";
	String errMsg = "";
	
	for(int i=0;i<errMsgList.size();i++) {
		errMsg = errMsgList.get(i)+"\\n";
	}
	
%>
<script>
alert("<%=msg%>");
<%
	if(StringUtils.isNotBlank(errMsg)) {
%>		
		alert("오류메시지 : <%=errMsg%>");
<%		
	}
%>
location.href = "recordInput.do"
</script>
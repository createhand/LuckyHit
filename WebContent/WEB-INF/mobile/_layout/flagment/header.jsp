<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.toto.comn.*" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="kr.co.toto.lgin.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<%
	String userId = (String)session.getAttribute("userId");
	String userNickNm = (String)session.getAttribute("userNickNm");
%>
  <div id="header" class="header">
      <h1><a href="/gamePickList.do">LuckyHit</a></h1><br/>
      <h1 style="color:white;">운빨!</h1><br/>
<%
	if(StringUtils.isNotBlank(userNickNm)) {
%>
		<h1 style="text-align:white ;"><%=userNickNm %></h1>
<%		
	}
%>
  </div>
  <div id="nav">
    <ol>
      <li class="pick"><a href="<%=request.getContextPath() %>gamePickList.do" accesskey="1">마이페이지</a></li>
      <li class="calc"><a href="<%=request.getContextPath() %>gameCalc.do" accesskey="2">계산기</a></li>
      <li class="result"><a href="<%=request.getContextPath() %>gameResult.do" accesskey="3">적중결과</a></li>
      <li class="bbs"><a href="<%=request.getContextPath() %>gameDetailList.do" accesskey="4">게임픽</a></li>
<%--       <li class="intro"><a href="<%=request.getContextPath() %>/introPage.do" accesskey="5">공지사항</a></li>       --%>
      <li class="intro"><a href="<%=request.getContextPath() %>gameInput.do" accesskey="4">게임수집</a></li>
    </ol>
  </div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.toto.comn.*" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="kr.co.toto.lgin.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<script>
	function logout() {
		if(confirm("로그아웃 하시겠습니까?")) {
			location.href = "/logout.jsp";
		}
	}
</script>
  <div id="header" class="header">
      <h1 style="color:white;font-size:20px;">LuckyHit</h1><br/>
      <h1 style="color:white;">운빨!</h1><br/>
<%
	if(nowLogin) {
%>
		<h1 style="color:white;text-align:right ;" onClick="logout();"><%=userNm %>(<%=userId %>)</h1>
<%
	}
%>
  </div>
  <div id="nav">
    <ol>
      <li class="pick">
<%
	if(StringUtils.isNotBlank(userNm)) {
%>
		<a href="<%=request.getContextPath() %>myPage.do" accesskey="1">마이페이지</a>
<%		
	} else {
%>
		<a href="<%=request.getContextPath() %>userLogin.do" accesskey="1">로그인</a>
<%	
	}
%>      
      </li>
      <li class="calc"><a href="<%=request.getContextPath() %>gameCalc.do" accesskey="2">계산기</a></li>
      <li class="result"><a href="<%=request.getContextPath() %>gameResult.do" accesskey="3">공개픽</a></li>
      <li class="bbs"><a href="<%=request.getContextPath() %>gameDetailList.do" accesskey="4">픽올리기</a></li>
      <li class="intro"><a href="<%=request.getContextPath() %>introPage.do" accesskey="5">링크</a></li>
<%
//관리자 게임수집 링크
	if(nowLogin && (userId.equals("createhand") || userId.equals("usuyoung"))) {
%>      
<%--       <li class="intro"><a href="<%=request.getContextPath() %>gameInput.do" accesskey="6">게임수집</a></li> --%>
<%
	}
%>      
    </ol>
  </div>
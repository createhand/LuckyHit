<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<script>
	function loginProc() {
		if(isBlank(document.getElementById("userId").value)) {
			alert("아이디를 입력해야 합니다.");
			document.getElementById("userId").focus();
			return false;
		}
		if(isBlank(document.getElementById("userPwd").value)) {
			alert("비밀번호를 입력해야 합니다.");
			document.getElementById("userPwd").focus();
			return false;
		}
		document.frm.action = "<%=request.getContextPath() %>userLoginProc.do";
		document.frm.submit();
	}
</script>
<form name="frm" method="POST">
    <div class="content_title">
  		<h2 class="header">로그인</h2>
	</div>
    <div class="content">
		<div class="content_info">
			<table id="mcTbl" class="common">
			  	<thead>
					<tr>
						<th>아이디</th><td><input type="text" name="userId" id="userId" style="width:100%;"></td>
					</tr>
					<tr>
						<th>비밀번호</th><td><input type="password" name="userPwd" id="userPwd" style="width:100%;"></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="2" onClick="loginProc();">로그인</td>
					</tr>
					<tr>
						<td colspan="2" onClick="location.href='<%=request.getContextPath() %>userRegist.do'">회원가입</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</form>

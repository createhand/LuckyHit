<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<script>
	function userRegistProc() {
		if(isBlank(document.getElementById("userId").value)) {
			alert("아이디를 입력해야 합니다.");
			document.getElementById("userId").focus();
			return false;
		}
		if(isBlank(document.getElementById("userNm").value)) {
			alert("닉네임을 입력해야 합니다.");
			document.getElementById("userNm").focus();
			return false;
		}
		if(isBlank(document.getElementById("userPwd").value)) {
			alert("비밀번호를 입력해야 합니다.");
			document.getElementById("userPwd").focus();
			return false;
		}
		if(document.getElementById("userPwd").value != document.getElementById("userPwdConfirm").value) {
			alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
			document.getElementById("userPwdConfirm").focus();
			return false;
		}
		
		document.frm.action = "<%=request.getContextPath() %>userRegistProc.do";
		document.frm.submit();
	}
</script>
<form name="frm" method="POST">
    <div class="content_title">
  		<h2 class="header">회원가입</h2>
	</div>
    <div class="content">
		<div class="content_info">
			<table id="mcTbl" class="common">
			  	<thead>
					<tr>
						<th>아이디</th><td><input type="text" name="userId" id="userId" style="width:100%;"></td>
					</tr>
					<tr>
						<th>닉네임</th><td><input type="text" name="userNm" id="userNm" style="width:100%;"></td>
					</tr>
					<tr>
						<th>비밀번호</th><td><input type="password" name="userPwd" id="userPwd" style="width:100%;"></td>
					</tr>
					<tr>
						<th>비밀번호 확인</th><td><input type="password" name="userPwdConfirm" id="userPwdConfirm" style="width:100%;"></td>
					</tr>
					<tr>
						<th>이메일(옵션)</th><td><input type="text" name="userEml" id="userEml" style="width:100%;"></td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="2" onClick="userRegistProc();">회원가입</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</form>

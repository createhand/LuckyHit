<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.util.*" %>
<%
String userNo = (String)session.getAttribute("userNo");
String userId = (String)session.getAttribute("userId");
String userNm = (String)session.getAttribute("userNm");
boolean nowLogin = false;

if(StringUtils.isNotBlank(userNo)) {
	nowLogin = true;
}
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
      <div class="content_title">
  		<h2 class="header">
  			공지사항
  		</h2>
      </div>
<div class="content">
	<iframe src="http://kimpele.com/gnuboard4/bbs/board.php?bo_table=notice" style="border:0px;width: 100%;height:100%;padding: 0px;margin: 0px;"></iframe>
</div>

      <div class="content_title">
  		<h2 class="header">
  			문의
  		</h2>
      </div>
<div class="content">
	<div class="content_info">
		<div id="intro">
		건의사항이나 문의사항은 아래메일 주소로 연락주세요~<br/>
		<div class="email">문의 : help@kimpele.com</div>
		</div>		
	</div>
</div>
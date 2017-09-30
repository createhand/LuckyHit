<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*, java.math.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@page import="kr.co.toto.base.persistence.domain.DomainConst"%>
<%@ page import="kr.co.toto.biz.game.persistence.domain.GameDetailListDt" %>
<%@ page import="kr.co.toto.base.persistence.domain.GameMt" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<%
List<HashMap> list = (List<HashMap>)request.getAttribute("list");
HashMap pick = (HashMap<String, String>)request.getAttribute("pick");
String regDate = pick.get("rgDate").toString();
HashMap searchParam = (HashMap<String, String>)request.getAttribute("searchParam");
%>
      <div class="content_title">
  		<h2 class="header"><%=pick.get("gmName") %> <%=pick.get("gmTurn") %>회
  		</h2>
  		<p class="date"><%=regDate%></p>
      </div>
      <div class="content">
		  <div class="content_info">
			<form name="frm" method="POST">
			<input type="hidden" name="view" />
			<input type="hidden" name="tmCd" value="" />
			<input type="hidden" name="tmCdA" value="" />
		  <table class="common">
		  	<thead>
			<tr>
				<th class="no" width="10%">번호</th>
				<th class="team" width="30%">홈</th>
				<th class="team" width="30%">원정</th>
				<th class="pick" width="20%"></th>
				<th class="pick" width="20%"></th>
				<th class="pick" width="20%"></th>
				<th class="detail" width="10%"></th>
			</tr>
			</thead>
			<tbody>
				<tr>
			
<%
	if(list.size() == 0) {
%>
				<tr>
					<td colspan="7">등록된 경기가 없습니다</td>
				</tr>
<%		
	}

	for(int i=0;i<list.size();i++) {
		
		HashMap mr = (HashMap)list.get(i);
		
		String mcResult = mr.get("mcResult").toString();
		String mcEnd = mr.get("mcEnd").toString();
		String mcDate = mr.get("mcDate").toString();
		String mcTime = mr.get("mcTime").toString();
		String mcDay = mr.get("mcDay").toString();
		String tmCdH = mr.get("tmCdH").toString();
		String tmCdA = mr.get("tmCdA").toString();
		String tmNmH = mr.get("homeTeam").toString();
		String tmNmA = mr.get("awayTeam").toString();
		String expResult = mr.get("expResult").toString();
		String stCd = mr.get("stCd").toString();
%>
			<tr>
				<td rowspan="2"><%=mr.get("gmListNo") %></td>
				<td><%=tmNmH%></td>
				<td><%=tmNmA%></td>
<%
		//승 픽 체크
		if(expResult.indexOf(DomainConst.RECORD_WIN) > -1) {
%>
				<td style="background-color: TOMATO;">승&nbsp;</td>
<%			
		} else {
%>
				<td>&nbsp;</td>
<%			
		}
%>

<%
		//무 픽 체크
		if(expResult.indexOf(DomainConst.RECORD_DRAW) > -1) {
%>
				<td style="background-color: TOMATO;">무&nbsp;</td>
<%			
		} else {
%>
				<td>&nbsp;</td>
<%			
		}
%>

<%
		//패 픽 체크
		if(expResult.indexOf(DomainConst.RECORD_LOSE) > -1) {
%>
				<td style="background-color: TOMATO;">패&nbsp;</td>
<%			
		} else {
%>
				<td>&nbsp;</td>
<%			
		}
%>
				
				<td class="normal"><a href="javascript:goDetail('<%=tmCdH%>','<%=tmCdA%>');">상세</a></td>
			</tr>
			<tr>
				<td colspan="6">
				<fmt:parseDate value="<%=mcDate %>" var="dateFmt" pattern="yyyymmdd"/>
				<fmt:formatDate value="${dateFmt}" pattern="yyyy.mm.dd"/>
				(<%=new BizUtil().getDayStr(Integer.parseInt(mcDay)) %>)
				<%=mcTime%> <%=stCd%></td>
			</tr>
<%
	}
%>
			</tbody>		
		  </table>
		  </form>
		  </div>
      </div>
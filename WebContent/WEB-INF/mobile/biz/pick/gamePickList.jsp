<%@page import="kr.co.toto.base.persistence.domain.DomainConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*, java.math.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
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
				<th class="no">번호</th>
				<th class="team">홈</th>
				<th class="team">원정</th>
				<th class="pick">시스템</th>
				<th class="pick">김펠레</th>
				<th class="pick">신내림</th>
				<th class="detail"></th>
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
		
		String[] arryExpResult = null;
		if(expResult != null) {
			arryExpResult = expResult.split(":");
		}
		int len = arryExpResult.length;
		int idx = len-3 > 0 ? len-3 : 0;
%>
			<tr>
				<td rowspan="2"><%=mr.get("gmListNo") %></td>
				<td><%=tmNmH%></td>
				<td><%=tmNmA%></td>
				<td style="color:blue;"><%=new BizUtil().getWinstrTeam(arryExpResult[idx])%>&nbsp;</td>
				<td style="color:blue;"><%=len > 1 ? new BizUtil().getWinstrTeam(arryExpResult[idx+1]) : ""%>&nbsp;</td>
				<td style="color:red;"><%=len > 2 ? new BizUtil().getWinstrTeam(arryExpResult[idx+2]) : ""%>&nbsp;</td>
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
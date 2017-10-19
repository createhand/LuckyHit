<%@page import="kr.co.toto.base.persistence.domain.DomainConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<%
	List<HashMap> gameList = (List<HashMap>)request.getAttribute("gameList");
	List<HashMap> selectedGame = (List<HashMap>)request.getAttribute("selectedGame");
	HashMap selectGameInfo = (HashMap)request.getAttribute("selectGameInfo");
	String gmCd = (String)request.getAttribute("gmCd");
	String gmPostNo = (String)request.getAttribute("gmPostNo");
	String gmSeq = "";
%>
	<form name="frm" method="GET">
      <div class="content_title">
  		<h2 class="header">공개픽
  		<select name="gmList" onChange="getResult(this)">
<%
			for(int i=0;i<gameList.size();i++) {
				
				HashMap obj = gameList.get(i);
				String getGmCd = obj.get("gmCd").toString();
				String getGmPostNo = obj.get("gmPostNo").toString();
				if(gmCd.equals(getGmCd)) gmSeq = obj.get("gmSeq").toString();
				
%>  		
				<option value="<%=getGmCd%>@<%=obj.get("gmPostNo")%>" <%=gmCd.equals(getGmCd) && gmPostNo.equals(getGmPostNo) ? "selected" : ""%>>
				<%=obj.get("gmEndDate").toString().substring(0, 4) %> <%=obj.get("gmName") %> <%=obj.get("gmTurn") %>회-<%=obj.get("gmPostNo") %> <%=obj.get("gmPostContent") %>
				</option>
<%
			}
%>
		</select>
		<input type="hidden" id="gmCd" name="gmCd" />
		<input type="hidden" id="gmPostNo" name="gmPostNo" />
  		</h2>
      </div>
      <div class="content">
		  <div class="content_info">
		  <table class="common">
		  	<thead>
		  	<tr>
		  		<th colspan="6" style="color: TOMATO;">등록시간 : <%=selectGameInfo.get("regDt") %></th>
		  	</tr>
			<tr>
				<th>번호</th>
				<th>홈</th>
				<th>원정</th>
				<th>예측</th>
				<th>결과</th>
				<th>적중</th>
			</tr>
			</thead>
			<tbody>
				<tr>			
<%
	double accCnt = 0;
	for(int i=0;i<selectedGame.size();i++) {
		
		HashMap obj = selectedGame.get(i);
		
		//예상 결과
		String expResult = obj.get("expResult").toString();
		//경기 결과
		String mcResult = "";
		//경기 종료여부
		String matchEnd = obj.get("matchEnd").toString();
		
		String result = "예정";
		String csStyle = "normal";
		if(StringUtils.equals(matchEnd, DomainConst.YES)) {
			
			 mcResult = obj.get("matchResult").toString();
			 
			if(expResult.indexOf(mcResult) > -1) {
				accCnt++;
				result = "적중";
				csStyle = "result";
			} else {
				result = "미적중";
			}
		}		
%>
			<tr>
				<td><%=obj.get("gmListNo") %></td>
				<td><%=obj.get("tmNameBetH") %></td>
				<td><%=obj.get("tmNameBetA") %></td>
				<td><%=new BizUtil().getConvertWinstrResult(expResult) %></td>
				<td><%=new BizUtil().getWinstrResult(mcResult) %></td>
				<td class="<%=csStyle%>"><%=result%></td>
			</tr>
<%		
	}	
%>
			<tr>
				<td colspan="6"><span id="common">적중수 : <font color='red'><%=accCnt%>/14</font> / 적중률 : <font color='red'><%=Math.ceil((accCnt/14)*100) %>%</font></span></td>	
			</tr>
			<tr>
				<td colspan="6"><a href="http://m.betman.co.kr/winningResultToto.so?method=detail&gameId=G011&gameRound=<%=gmSeq%>&page=3" target="_blank"><span id="common">당첨결과</span></a></td>
			</tr>
			</tbody>		
		  </table>
		  </div>
      </div>
      </form>
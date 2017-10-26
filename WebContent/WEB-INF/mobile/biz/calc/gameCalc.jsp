<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="kr.co.toto.base.persistence.domain.DomainConst"%>
<%@ page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="kr.co.toto.comn.model.TAData" %>
<%@page import="kr.co.toto.base.persistence.domain.DomainConst"%>
<%@ page import="kr.co.toto.biz.game.persistence.domain.GameDetailListDt" %>
<%@ page import="kr.co.toto.base.persistence.domain.GameMt" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<%
	List<HashMap> gameList = (List<HashMap>)request.getAttribute("gameList");
	List<TAData> list = (List<TAData>)request.getAttribute("selectedGame");
	GameMt gameMt = (GameMt)request.getAttribute("gmInfo");
	double totalBetCnt = (Double)request.getAttribute("totalBetCnt");
%>
<form name="frm" method="GET">
      <div class="content_title">
  		<h2 class="header">계산기
  		<select name="gmCd" onChange="getResult(this)"> 
<%
			for(int i=0;i<gameList.size();i++) {
				HashMap obj = gameList.get(i);
				String getGmCd = obj.get("gmCd").toString();
%>
				<option value="<%=getGmCd%>" <%=gameMt.getGmCd().equals(getGmCd) ? "selected" : ""%>>
				<%=obj.get("gmEndDate").toString().substring(0, 4) %> <%=obj.get("gmName") %> <%=obj.get("gmTurn") %>회</option>
<%
			}
%>
		</select>
  		</h2>
      </div>      
      <div class="content">
		  <div class="content_info">
		  <table id="mcTbl" class="common">
		  	<thead>
			<tr>
				<th width="10%">번호</th>
				<th width="32%">홈</th>
				<th width="16%">vs</th>
				<th width="32%">원정</th>
				<th width="10%">결과</th>
			</tr>
			</thead>
			<tbody>
				<tr>
			
<%
	if(list.size() == 0) {
%>
				<tr>
					<td colspan="4">등록된 경기가 없습니다</td>
				</tr>
<%		
	}

	for(int i=0;i<list.size();i++) {
		
		TAData dt = (TAData)list.get(i);
		double winBet = (dt.getInt("winBetCnt")/totalBetCnt)*100;
		double drawBet = (dt.getInt("drawBetCnt")/totalBetCnt)*100;
		double loseBet = (dt.getInt("loseBetCnt")/totalBetCnt)*100;
		if(gameMt.getGmEnd().equals("N")) dt.set("matchResult", "");
		String winClass = "normal", drawClass = "normal", loseClass = "normal";
		if(dt.getString("matchResult").equals(DomainConst.RECORD_WIN)) winClass="win";
		if(dt.getString("matchResult").equals(DomainConst.RECORD_LOSE)) loseClass="win";
		if(dt.getString("matchResult").equals(DomainConst.RECORD_DRAW)) drawClass="win";
%>
			<tr id="gm<%=dt.getInt("gameListNo") %>" class="off">
				<td><%=dt.getInt("gameListNo") %></td>
				<td class="clickOff"><span class="<%=winClass%>"><%=dt.getString("homeTeamName") %><br/><div id="ratio"><fmt:formatNumber value="<%=winBet %>" pattern="###.##" /></div></span></td>
				<td class="clickOff"><span class="<%=drawClass%>">VS<br/><div id="ratio"><fmt:formatNumber value="<%=drawBet %>" pattern="###.##" /></div></span></td>
				<td class="clickOff"><span class="<%=loseClass%>"><%=dt.getString("awayTeamName") %><br/><div id="ratio"><fmt:formatNumber value="<%=loseBet %>" pattern="###.##" /></div></span></td>
				<td class="result"><%=new BizUtil().getWinstrResult(dt.getString("matchResult")) %></td>
			</tr>
<%
	}
%>
			<tr>
				<td colspan="5">전체 배팅수 : <span id="common"><fmt:formatNumber value="<%=totalBetCnt %>" pattern="#" /></span></td>
			</tr>
			<tr>
				<td colspan="5"><span id="myRatio"><fmt:formatNumber value="<%=totalBetCnt %>" pattern="#" /></span></td>
			</tr>
			<tr>
				<td colspan="5"><a href="http://m.betman.co.kr/winningResultToto.so?method=detail&gameId=G011&gameRound=<%=gameMt.getGmSeq() %>&page=3" target="_blank"><span id="common">당첨결과</span></a></td>
			</tr>
			</tbody>		
		  </table>
		  
		  </div>
      </div>
</form>      
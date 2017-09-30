<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="kr.co.toto.base.persistence.domain.TeamMt"%>
<%@page import="kr.co.toto.base.persistence.domain.DomainConst"%>
<%@ page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*, java.math.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.comn.model.TAData" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="kr.co.toto.biz.game.persistence.domain.GameDetailListDt" %>
<%@ page import="kr.co.toto.base.persistence.domain.GameMt" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<%
List<HashMap> gameList = (List<HashMap>)request.getAttribute("gameList");
List<GameDetailListDt> list = (List<GameDetailListDt>)request.getAttribute("gameDetailList");
GameMt gameMt = (GameMt)request.getAttribute("gameMt");
String gmCd = (String)request.getAttribute("gmCd");
double totalBetCnt = (Double)request.getAttribute("totalBetCnt");
%>

		<div id="content">
			<table class="common">
			<form name="searchFrm" method="get">
			<thead>
				<tr>
					<th colspan="3">
  		<select name="gmCd" onChange="getResult(this)">
<%
			for(int i=0;i<gameList.size();i++) {
				HashMap obj = gameList.get(i);
				String getGmCd = obj.get("gmCd").toString();
%>  		
				<option value="<%=getGmCd%>" <%=gmCd.equals(getGmCd) ? "selected" : ""%>>
				<%=obj.get("gmEndDate").toString().substring(0, 4) %>  <%=obj.get("gmTurn") %>회 <%=obj.get("gmName") %>
				<%
					if(StringUtils.equals(obj.get("gmEndDate").toString(), "Y")) {
						out.print("(종료)");
					}
				%>
				</option>
<%
			}
%>
		</select>					
					</th>
				</tr>			
<!-- 				<tr> -->
<%-- 					<th colspan="3"><%=gameMt.getGmName() %> <%=gameMt.getGmTurn() %>회</th> --%>
<!-- 				</tr> -->
			</thead>
			</table>
			</form>
			<form name="frm" action="/gamePicking.do" method="post">
			<table id="mcTbl" class="common">
			<tbody>
<%
	if(list.size() == 0) {
%>
				<tr>
					<td colspan="3">등록된 경기가 없습니다</td>
				</tr>
<%		
	}

	for(int i=0;i<list.size();i++) {
		
		String hLtRecord = "", hLtAgRecord = "", aLtRecord = "", aLtAgRecord = "";		
		int hLtGtPoint = 0, hLtLsPoint = 0, hLtGtAgPoint = 0, hLtLsAgPoint = 0; 
		int aLtGtPoint = 0, aLtLsPoint = 0, aLtGtAgPoint = 0, aLtLsAgPoint = 0;
		GameDetailListDt mr = (GameDetailListDt)list.get(i);
		List<String> homeTeamRecordList = mr.getHomeTeamlatestRecord();
		List<String> awayTeamRecordList = mr.getAwayTeamlatestRecord();
		List<String> homeTeamAgainstRecordList = mr.getHomeTeamAgainstRecord();
		List<String> awayTeamAgainstRecordList = mr.getAwayTeamAgainstRecord();
		
		HashMap<String, BigDecimal> homeScore = mr.getHomeTeamlatestScore();
		HashMap<String, BigDecimal> awayScore = mr.getAwayTeamlatestScore();
		HashMap<String, BigDecimal> homeTeamAgainstScore = mr.getHomeTeamAgainstScore();
		HashMap<String, BigDecimal> awayTeamAgainstScore = mr.getAwayTeamAgainstScore();
		
		for(int j=0;j<homeTeamRecordList.size();j++) hLtRecord += homeTeamRecordList.get(j);
		for(int j=0;j<homeTeamAgainstRecordList.size();j++) hLtAgRecord += homeTeamAgainstRecordList.get(j);
		for(int j=0;j<awayTeamRecordList.size();j++) aLtRecord += awayTeamRecordList.get(j);
		for(int j=0;j<awayTeamAgainstRecordList.size();j++) aLtAgRecord += awayTeamAgainstRecordList.get(j);
		
		hLtGtPoint = homeScore.get("totalGetScore").intValue(); 
		hLtLsPoint = homeScore.get("totalLostScore").intValue();
		hLtGtAgPoint = homeTeamAgainstScore.get("totalGetScore").intValue();
		hLtLsAgPoint = homeTeamAgainstScore.get("totalLostScore").intValue();		
		
		aLtGtPoint = awayScore.get("totalGetScore").intValue(); 
		aLtLsPoint = awayScore.get("totalLostScore").intValue();
		aLtGtAgPoint = awayTeamAgainstScore.get("totalGetScore").intValue();
		aLtLsAgPoint = awayTeamAgainstScore.get("totalLostScore").intValue();
		
		TeamMt homeTeamInfo = mr.getHomeTeamInfo();
		TeamMt awayTeamInfo = mr.getAwayTeamInfo();
		TAData homeTeamSeasonInfo = mr.getHomeTeamSeaonInfo();
		TAData awayTeamSeasonInfo = mr.getAwayTeamSeaonInfo();
		
		List<TAData> homeTeamAmzaingList = mr.getHomeTeamAmzaingList(); 
		List<TAData> awayTeamAmzaingList = mr.getAwayTeamAmzaingList();
		
		double winBet = (mr.getWinBetCnt()/totalBetCnt)*100;
		double drawBet = (mr.getDrawBetCnt()/totalBetCnt)*100;
		double loseBet = (mr.getLoseBetCnt()/totalBetCnt)*100;
		
%>
				<tr>
					<td colspan="3">
						<%=mr.getGameListNo()%>.
						<%=DateUtil.getDate2String(DateUtil.getDate(mr.getMatchDate(), "yyyyMMdd"), "yyyy-MM-dd")%>
						(<%=new BizUtil().getDayStr(Integer.parseInt(mr.getMatchDay()))%>)
						<%
						if(mr.getMatchTime().length() == 4) {
							out.print(DateUtil.getDate2String(DateUtil.getDate(mr.getMatchTime(), "HHmm"), "HH:mm"));
						}
						%>
						/ 예상 : 
						<%=mr.getExpectMatchResultCode().toString().equals("W") ? "승" : ""%>
						<%=mr.getExpectMatchResultCode().toString().equals("D") ? "무" : ""%>
						<%=mr.getExpectMatchResultCode().toString().equals("L") ? "패" : ""%>
						<input type="hidden" id="expResult" name="expResult">
					</td>
				</tr>
				<tr id="gm<%=mr.getGameListNo() %>" class="off">
					<td width="40%" class="clickOff" onclick="getSubGameList('<%=mr.getGameListNo() %>', 'W')">
						<img src="<%=homeTeamInfo.getTmImgUrl() %>" width="70" height="70" alt="<%=mr.getHomeTeamName() %>"/><br/>
						<%=mr.getHomeTeamName() %><br/><br/>
						(<%=homeTeamSeasonInfo.getInt("RANK") %>위)<br/><br/>
						<div id="ratio"><fmt:formatNumber value="<%=winBet %>" pattern="###.##" /></div>
					</td>
					<td class="clickOff" onclick="getSubGameList('<%=mr.getGameListNo() %>', 'D')">VS<br/>
					<div id="ratio"><fmt:formatNumber value="<%=drawBet %>" pattern="###.##" /></div>
					</td>
					<td width="40%" class="clickOff" onclick="getSubGameList('<%=mr.getGameListNo() %>', 'L')">
						<img src="<%=awayTeamInfo.getTmImgUrl() %>" width="70" height="70" alt="<%=mr.getAwayTeamName() %>"/><br/>
						<%=mr.getAwayTeamName() %><br/><br/>
						(<%=awayTeamSeasonInfo.getInt("RANK") %>위)<br/><br/>
						<div id="ratio"><fmt:formatNumber value="<%=loseBet %>" pattern="###.##" /></div>
					</td>
				</tr>
				<tr>
					<td>
						<%=hLtRecord%><br/><br/>
						득:<%=homeScore.get("totalGetScore") %>/실:<%=homeScore.get("totalLostScore") %>
					</td>
					<td>최근</td>
					<td>
						<%=aLtRecord%><br/><br/>
						득:<%=awayScore.get("totalGetScore") %>/실:<%=awayScore.get("totalLostScore") %>
					</td>
				</tr>
				<tr>
					<td>
						<%=hLtAgRecord%><br/><br/>
						득:<%=homeTeamAgainstScore.get("totalGetScore") %>/실:<%=homeTeamAgainstScore.get("totalLostScore") %>
					</td>
					<td>상대</td>
					<td>
						<%=aLtAgRecord%><br/><br/>
						득:<%=awayTeamAgainstScore.get("totalGetScore") %>/실:<%=awayTeamAgainstScore.get("totalLostScore") %>
					</td>
				</tr>
				<tr>
					<td>
					<%
						for(TAData amazingInfo : homeTeamAmzaingList) {
							out.print(DateUtil.getDate2String(DateUtil.getDate(amazingInfo.getString("MC_DATE"), "yyyyMMdd"), "yyyy-MM-dd")+"<br/>"+amazingInfo.getInt("PRDT_WIN")+":"+amazingInfo.getInt("PRDT_DRAW")+":"+amazingInfo.getInt("PRDT_LOSS")+"<br/>");
							out.print(amazingInfo.getString("TM_NAME_H")+"("+amazingInfo.getString("SCORE_H")+")"+":");
							out.print("("+amazingInfo.getString("SCORE_A")+")"+amazingInfo.getString("TM_NAME_A")+"<br/><br/><br/>");
						}
					%>
					</td>
					<td>이변</td>
					<td>
					<%
						for(TAData amazingInfo : awayTeamAmzaingList) {
							out.print(DateUtil.getDate2String(DateUtil.getDate(amazingInfo.getString("MC_DATE"), "yyyyMMdd"), "yyyy-MM-dd")+"<br/>"+amazingInfo.getInt("PRDT_WIN")+":"+amazingInfo.getInt("PRDT_DRAW")+":"+amazingInfo.getInt("PRDT_LOSS")+"<br/>");
							out.print(amazingInfo.getString("TM_NAME_H")+"("+amazingInfo.getString("SCORE_H")+")"+":");
							out.print("("+amazingInfo.getString("SCORE_A")+")"+amazingInfo.getString("TM_NAME_A")+"<br/><br/><br/>");
						}
					%>					
						<input type="hidden" id="hLtRecord" name="hLtRecord" value="<%=hLtRecord%>" />
						<input type="hidden" id="hLtAgRecord" name="hLtAgRecord" value="<%=hLtAgRecord%>" />
						<input type="hidden" id="aLtRecord" name="aLtRecord" value="<%=aLtRecord%>" />
						<input type="hidden" id="aLtAgRecord" name="aLtAgRecord" value="<%=aLtAgRecord%>" />
						
						<input type="hidden" id="hLtGtPoint" name="hLtGtPoint" value="<%=hLtGtPoint%>" />
						<input type="hidden" id="hLtLsPoint" name="hLtLsPoint" value="<%=hLtLsPoint%>" />
						<input type="hidden" id="hLtGtAgPoint" name="hLtGtAgPoint" value="<%=hLtGtAgPoint%>" />
						<input type="hidden" id="hLtLsAgPoint" name="hLtLsAgPoint" value="<%=hLtLsAgPoint%>" />
						
						<input type="hidden" id="aLtGtPoint" name="aLtGtPoint" value="<%=aLtGtPoint%>" />
						<input type="hidden" id="aLtLsPoint" name="aLtLsPoint" value="<%=aLtLsPoint%>" />
						<input type="hidden" id="aLtGtAgPoint" name="aLtGtAgPoint" value="<%=aLtGtAgPoint%>" />
						<input type="hidden" id="aLtLsAgPoint" name="aLtLsAgPoint" value="<%=aLtLsAgPoint%>" />
						
						<input type="hidden" id="gmListNo" name="gmListNo" value="<%=mr.getGameListNo()%>" />
						<input type="hidden" id="mcCd" name="mcCd" value="<%=mr.getMatchCode()%>" />
					</td>
				</tr>
				<tr>
					<td colspan="3">
					<%
						if(mr.getMatchEnd().equals("Y")) {
							if(mr.getMatchResult().equals("W")) {
								out.print(mr.getHomeTeamName());								
							} else if(mr.getMatchResult().equals("L")) {
								out.print(mr.getAwayTeamName());
							} else {
								out.print("무");
							}
							out.print("<br/>"+mr.getScoreHome()+":"+mr.getScoreAway());
						} else {
							out.print("경기예정");
						}
					%>
					</td>
				</tr>
<%		
	}
%>
			<tr>
				<th colspan="3">전체 배팅수 : <span id="common"><fmt:formatNumber value="<%=totalBetCnt %>" pattern="#" /></span></th>
			</tr>
			<tr>
				<td colspan="3">픽비율 : <span id="myRatio"><fmt:formatNumber value="<%=totalBetCnt %>" pattern="#" /></span></td>
			</tr>
			<tr>
				<td colspan="3"><br/><br/><br/></td>
			</tr>
			<tr>
				<th colspan="3">포스팅 내용</th>
			</tr>
			<tr>
				<td colspan="3">
					<textarea name="gmPostContent" rows="10" cols="30"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<input type="button" value="픽 등록" onclick="checkPick()";/>
				</td>
			</tr>
			</tbody>
			</table>
			<input type="hidden" name="gmCd" value="<%=gameMt.getGmCd() %>" />
			</form>
		</div>
		<br/><br/><br/><br/><br/><br/><br/>

  <nav class="floating-menu">
  <table class="subTable" style="width:100%;">
<%
  	for(int i=0;i<list.size();i++) {
		GameDetailListDt mr = (GameDetailListDt)list.get(i);
		TeamMt homeTeamInfo = mr.getHomeTeamInfo();
		TeamMt awayTeamInfo = mr.getAwayTeamInfo();
		if(i % 2 == 0) {
			out.print("<tr>");
		}
		out.print("<td>"+mr.getGameListNo()+". "+homeTeamInfo.getTmNameBet()+" VS "+awayTeamInfo.getTmNameBet());
		out.print("(<gm id='subGame_"+mr.getGameListNo()+"' class='subGame' style='font-color:red;'></gm>)</td>");
		if(i % 2 == 1) {
			out.print("</tr>");
		}
  	}
%>
	<tr>
		<td colspan="2" class="result">
			픽비율 : <gm id="subRatio" class="subGame"><fmt:formatNumber value="<%=totalBetCnt %>" pattern="#" /></gm>
<!-- 			/ -->
<!-- 			구입금액 : 10,000원 -->
		</td>
	</tr>
	<tr>
		<td colspan="2" class="result">
			<input type="button" value="픽 등록" onclick="checkPick()";/>
		</td>
	</tr>
  </table>
  </nav>
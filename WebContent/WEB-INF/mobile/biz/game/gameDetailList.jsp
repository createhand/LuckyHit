<%@page import="kr.co.toto.biz.record.service.RecordService"%>
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
<%@ page import="kr.co.toto.base.persistence.domain.GameMt" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<%!
public TAData getPopupHtml(List<TAData> matchList, String tmCd) {
	
	TAData rtnObj = new TAData();
	int winCnt = 0, drawCnt = 0, loseCnt = 0;
	StringBuffer html = new StringBuffer();
	
	for(TAData matchInfo : matchList) {
		String win=null, draw=null, lose=null;
		if(StringUtils.equals(matchInfo.getString("mcResult"), "W")) {
			win = "class=\"win\"";
			if(StringUtils.equals(matchInfo.getString("tmCdH"), tmCd)) {
				winCnt++;
			} else {
				loseCnt++;
			}
		} else if(StringUtils.equals(matchInfo.getString("mcResult"), "L")) {
			lose = "class=\"win\"";
			if(StringUtils.equals(matchInfo.getString("tmCdH"), tmCd)) {
				loseCnt++;
			} else {
				winCnt++;
			}
		} else {
			draw = "class=\"win\"";
			drawCnt++;
		}
		html.append("<span'>"+DateUtil.formatDateTime(matchInfo.getString("mcDate"), "yyyy-MM-dd")+"("+new BizUtil().getDayStr(matchInfo.getInt("mcDay"))+")</span><br/>");
		html.append("<span "+win+" style='font-size:13px;'>"+matchInfo.getString("tmNameBetH")+"["+matchInfo.getString("scoreH")+"]</span>");
		html.append("<span "+draw+" style='font-size:13px;'> VS </span>");
		html.append("<span "+lose+" style='font-size:13px;'>"+matchInfo.getString("tmNameBetA")+"["+matchInfo.getString("scoreA")+"]</span><br/>");
	}
	
	rtnObj.set("html", html);
	rtnObj.set("winCnt", winCnt);
	rtnObj.set("drawCnt", drawCnt);
	rtnObj.set("loseCnt", loseCnt);
	
	return rtnObj;
}

%>
<%
RecordService recordService = new RecordService();
List<HashMap> gameList = (List<HashMap>)request.getAttribute("gameList");
List<TAData> list = (List<TAData>)request.getAttribute("gameDetailList");
GameMt gameMt = (GameMt)request.getAttribute("gameMt");
String gmCd = (String)request.getAttribute("gmCd");
double totalBetCnt = (Double)request.getAttribute("totalBetCnt");
%>
		<div id="content">
			<form name="searchFrm" method="get">
			<table class="common">
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
			
			<form name="frm" method="post" onsubmit="checkPick();return false;">
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
		
		TAData mr = (TAData)list.get(i);
		
		
		//*********** 홈팀 최근 경기 요약 ***********//
		TAData homeTeamlatestInfo = (TAData)mr.get("homeTeamlatestInfo");
		
		//최근 경기(6경기)
		TAData homeTeamResult = (TAData)homeTeamlatestInfo.get("homeTeamResult");
		//최근 홈경기(6경기)
		TAData homeTeamResultAtHome = (TAData)homeTeamlatestInfo.get("homeTeamResultAtHome");
		//최근 상대경기(6경기)
		TAData homeAgainstResult = (TAData)homeTeamlatestInfo.get("homeAgainstResult");		
		//최근 홈에서 상대경기(6경기)
		TAData homeAgainstResultAtHome = (TAData)homeTeamlatestInfo.get("homeAgainstResultAtHome");		
		
		
		//*********** 어웨이팀 최근 경기 요약 ***********//
		TAData awayTeamlatestInfo = (TAData)mr.get("awayTeamlatestInfo");
		
		//최근 경기(6경기)
		TAData awayTeamResult = (TAData)awayTeamlatestInfo.get("awayTeamResult");
		//최근 홈경기(6경기)
		TAData awayTeamResultAtHome = (TAData)awayTeamlatestInfo.get("awayTeamResultAtHome");
		//최근 상대경기(6경기)
		TAData awayAgainstResult = (TAData)awayTeamlatestInfo.get("awayAgainstResult");		
		//최근 홈에서 상대경기(6경기)
		TAData awayAgainstResultAtHome = (TAData)awayTeamlatestInfo.get("awayAgainstResultAtHome");		
		
		
		//*********** 시즌성적 ***********//
		TAData homeTeamSeasonInfo = (TAData)mr.get("homeTeamSeasonInfo");
		if(homeTeamSeasonInfo == null) homeTeamSeasonInfo = new TAData();
		homeTeamSeasonInfo.setNullToInitialize(true);
		TAData awayTeamSeasonInfo = (TAData)mr.get("awayTeamSeasonInfo");
		if(awayTeamSeasonInfo == null) awayTeamSeasonInfo = new TAData(true);
		awayTeamSeasonInfo.setNullToInitialize(true);
		//*********** 이변경기 ***********//
		List<TAData> homeTeamAmzaingList = (List<TAData>)mr.get("homeTeamAmzaingList"); 
		List<TAData> awayTeamAmzaingList = (List<TAData>)mr.get("awayTeamAmzaingList");
		
		double winBet = (mr.getInt("winBetCnt")/totalBetCnt)*100;
		double drawBet = (mr.getInt("drawBetCnt")/totalBetCnt)*100;
		double loseBet = (mr.getInt("loseBetCnt")/totalBetCnt)*100;
		
%>
				<tr id="mcDate<%=mr.getInt("gameListNo") %>">
					<td colspan="3" width="100%" style="font-size: 14px;">
						<a style="font-size: 14px;" id="match<%=mr.getInt("gameListNo")%>"><%=mr.getInt("gameListNo")%>.</a>
						<%=DateUtil.getDate2String(DateUtil.getDate(mr.getString("matchDate"), "yyyyMMdd"), "yyyy-MM-dd")%>
						(<%=new BizUtil().getDayStr(mr.getInt("matchDay"))%>)
						<%
						if(mr.getString("matchTime").length() == 4) {
							out.print(DateUtil.getDate2String(DateUtil.getDate(mr.getString("matchTime"), "HHmm"), "HH:mm"));
						}
						%>
						/ 예상 : 
						<%=mr.getString("expectMatchResultCode").toString().equals("W") ? "승" : ""%>
						<%=mr.getString("expectMatchResultCode").toString().equals("D") ? "무" : ""%>
						<%=mr.getString("expectMatchResultCode").toString().equals("L") ? "패" : ""%>
						<input type="hidden" id="expResult" name="expResult">
					</td>
				</tr>
				<tr id="gm<%=mr.getInt("gameListNo") %>" class="off" style="line-height: 1.7em;">
					<td width="40%" class="clickOff" onclick="getSubGameList(this, '<%=mr.getInt("gameListNo") %>', 'W')" style="color:#595959;">
						<img src="<%=mr.getString("homeTeamImg") %>" width="70" height="70" alt="<%=mr.getString("homeTeamName") %>"/><br/>
						<%=mr.getString("homeTeamName") %>(<%=homeTeamSeasonInfo.getInt("RANKING") %>위)<br/>
						<div id="ratio"><fmt:formatNumber value="<%=winBet %>" pattern="###.##" /></div>
					</td>
					<td class="clickOff" onclick="getSubGameList(this, '<%=mr.getInt("gameListNo") %>', 'D')" style="color:#595959;">VS<br/>
					<div id="ratio"><fmt:formatNumber value="<%=drawBet %>" pattern="###.##" /></div>
					</td>
					<td width="40%" class="clickOff" onclick="getSubGameList(this, '<%=mr.getInt("gameListNo") %>', 'L')" style="color:#595959;">
						<img src="<%=mr.getString("awayTeamImg") %>" width="70" height="70" alt="<%=mr.getString("awayTeamName") %>"/><br/>
						<%=mr.getString("awayTeamName") %>(<%=awayTeamSeasonInfo.getInt("RANKING") %>위)<br/>
						<div id="ratio"><fmt:formatNumber value="<%=loseBet %>" pattern="###.##" /></div>
					</td>
				</tr>
				<tr id="mcLatestHomeAway<%=mr.getInt("gameListNo") %>" >
					<td>
						<a href="javascript:layer_popup('#layerPopup','matchListHomeAway<%=mr.getInt("gameListNo") %>')" style="font-size: 13px;color:#678197;">
						<%if(homeTeamResult.getString("resultStr").length() >= 1) { %>
						<span style="font-size: 13px;color:black;"><%=homeTeamResult.getString("resultStr").substring(0, 1)%></span><%=homeTeamResult.getString("resultStr").substring(1)%>
						<%} %>
						<br/>
						득:<%=homeTeamResult.getInt("getScore")%>/실:<%=homeTeamResult.getInt("lostScore")%>
						</a>
						<div id="matchListHomeAway<%=mr.getInt("gameListNo") %>" style="display: none;">
							<%
								TAData homeResultPopupObj = getPopupHtml((List<TAData>)homeTeamResult.get("matchList"), mr.getString("homeTeamCode"));
								TAData awayResultPopupObj = getPopupHtml((List<TAData>)awayTeamResult.get("matchList"), mr.getString("awayTeamCode"));
							%>						
							<!-- 최근 6경기 상세정보 -->
							<table class="common">
								<tbody>
								<tr>
									<td colspan="2" style="background-color: #678197;color: white;">최근 경기</td>
								</tr>
								<tr>
									<td width="50%"><%=mr.getString("homeTeamName") %></td>
									<td width="50%"><%=mr.getString("awayTeamName") %></td>
								</tr>
								<tr>
									<td style="font-size: 13px;line-height: 1.4em;text-align: left;padding-left: 10px;">
									<%=homeResultPopupObj.get("html").toString() %>									
									</td>
									<td style="font-size: 13px;line-height: 1.4em;text-align: left;padding-left: 10px;">
									<%=awayResultPopupObj.get("html").toString() %>
									</td>
								</tr>
								<tr>
									<td style="color:red;">
										<%=homeResultPopupObj.getInt("winCnt") %>승 
										<%=homeResultPopupObj.getInt("drawCnt") %>무 
										<%=homeResultPopupObj.getInt("loseCnt") %>패
									</td>
									<td style="color:red;">
										<%=awayResultPopupObj.getInt("winCnt") %>승 
										<%=awayResultPopupObj.getInt("drawCnt") %>무 
										<%=awayResultPopupObj.getInt("loseCnt") %>패
									</td>
								</tr>
								</tbody>
							</table>
						</div>
					</td>
					<td>최근<br/>경기</td>
					<td>
						<a href="javascript:layer_popup('#layerPopup','matchListHomeAway<%=mr.getInt("gameListNo") %>')" style="font-size: 13px;color:#678197;">
						<%if(awayTeamResult.getString("resultStr").length() >= 1) { %>
						<span style="font-size: 13px;color:black;"><%=awayTeamResult.getString("resultStr").substring(0, 1)%></span><%=awayTeamResult.getString("resultStr").substring(1)%>
						<%} %>
						<br/>
						득:<%=awayTeamResult.getInt("getScore")%>/실:<%=awayTeamResult.getInt("lostScore")%>
						</a>
					</td>
				</tr>
				<tr id="mcLatestHome<%=mr.getInt("gameListNo") %>" >
					<td>
						<a href="javascript:layer_popup('#layerPopup','matchListHome<%=mr.getInt("gameListNo") %>')" style="font-size: 13px;color:#678197;">
						<%if(homeTeamResultAtHome.getString("resultStr").length() >= 1) { %>
						<span style="font-size: 13px;color:black;"><%=homeTeamResultAtHome.getString("resultStr").substring(0, 1)%></span><%=homeTeamResultAtHome.getString("resultStr").substring(1)%>
						<%} %>
						<br/>
						득:<%=homeTeamResultAtHome.getInt("getScore")%>/실:<%=homeTeamResultAtHome.getInt("lostScore")%>
						</a>
						<div id="matchListHome<%=mr.getInt("gameListNo") %>" style="display: none;">
							<%
								homeResultPopupObj = getPopupHtml((List<TAData>)homeTeamResultAtHome.get("matchList"), mr.getString("homeTeamCode"));
								awayResultPopupObj = getPopupHtml((List<TAData>)awayTeamResultAtHome.get("matchList"), mr.getString("awayTeamCode"));
							%>						
							<!-- 최근 최근 홈, 원정 경기 상세정보 -->
							<table class="common">
								<tbody>
								<tr>
									<td colspan="2" style="background-color: #678197;color: white;">최근 홈, 원정</td>
								</tr>
								<tr>
									<td width="50%"><%=mr.getString("homeTeamName") %></td>
									<td width="50%"><%=mr.getString("awayTeamName") %></td>
								</tr>
								<tr>
									<td style="font-size: 13px;line-height: 1.4em;text-align: left;padding-left: 10px;">
									<%=homeResultPopupObj.get("html").toString() %>									
									</td>
									<td style="font-size: 13px;line-height: 1.4em;text-align: left;padding-left: 10px;">
									<%=awayResultPopupObj.get("html").toString() %>
									</td>
								</tr>
								<tr>
									<td style="color:red;">
										<%=homeResultPopupObj.getInt("winCnt") %>승 
										<%=homeResultPopupObj.getInt("drawCnt") %>무 
										<%=homeResultPopupObj.getInt("loseCnt") %>패
									</td>
									<td style="color:red;">
										<%=awayResultPopupObj.getInt("winCnt") %>승 
										<%=awayResultPopupObj.getInt("drawCnt") %>무 
										<%=awayResultPopupObj.getInt("loseCnt") %>패
									</td>
								</tr>
								</tbody>
							</table>
						</div>						
					</td>
					<td>최근<br/>홈, 원정</td>
					<td>
						<a href="javascript:layer_popup('#layerPopup','matchListHome<%=mr.getInt("gameListNo") %>')" style="font-size: 13px;color:#678197;">
						<%if(awayTeamResultAtHome.getString("resultStr").length() >= 1) { %>
						<span style="font-size: 13px;color:black;"><%=awayTeamResultAtHome.getString("resultStr").substring(0, 1)%></span><%=awayTeamResultAtHome.getString("resultStr").substring(1)%>
						<%} %>
						<br/>
						득:<%=awayTeamResultAtHome.getInt("getScore")%>/실:<%=awayTeamResultAtHome.getInt("lostScore")%>
						</a>
					</td>
				</tr>
				
				
				
				<%
				if(homeAgainstResult != null || awayAgainstResult != null) {
				%>
				<tr id="mcAgainstHomeAway<%=mr.getInt("gameListNo") %>" >
					<td>
						<a href="javascript:layer_popup('#layerPopup','matchListAgainst<%=mr.getInt("gameListNo") %>')" style="font-size: 13px;color:#678197;">
						<%if(homeAgainstResult.getString("resultStr").length() >= 1) { %>
						<span style="font-size: 13px;color:black;"><%=homeAgainstResult.getString("resultStr").substring(0, 1)%></span><%=homeAgainstResult.getString("resultStr").substring(1)%>
						<%} %>
						<br/>
						득:<%=homeAgainstResult.getInt("getScore")%>/실:<%=homeAgainstResult.getInt("lostScore")%>
						</a>
						<div id="matchListAgainst<%=mr.getInt("gameListNo") %>" style="display: none;">
							<%
								if(homeAgainstResult != null) {
									homeResultPopupObj = getPopupHtml((List<TAData>)homeAgainstResult.get("matchList"), mr.getString("homeTeamCode"));
								}
								
								if(awayAgainstResult != null) {
									awayResultPopupObj = getPopupHtml((List<TAData>)awayAgainstResult.get("matchList"), mr.getString("awayTeamCode"));
								}
							%>						
							<!-- 최근 상대전적 6경기 상세정보 -->
							<table class="common">
								<tbody>
								<tr>
									<td colspan="2" style="background-color: #678197;color: white;">최근 상대전적 6경기</td>
								</tr>
								<tr>
									<td width="50%"><%=mr.getString("homeTeamName") %></td>
									<td width="50%"><%=mr.getString("awayTeamName") %></td>
								</tr>
								<tr>
									<td style="font-size: 13px;line-height: 1.4em;text-align: left;padding-left: 10px;">
									<%=homeResultPopupObj.get("html").toString() %>									
									</td>
									<td style="font-size: 13px;line-height: 1.4em;text-align: left;padding-left: 10px;">
									<%=awayResultPopupObj.get("html").toString() %>
									</td>
								</tr>
								<tr>
									<td style="color:red;">
										<%=homeResultPopupObj.getInt("winCnt") %>승 
										<%=homeResultPopupObj.getInt("drawCnt") %>무 
										<%=homeResultPopupObj.getInt("loseCnt") %>패
									</td>
									<td style="color:red;">
										<%=awayResultPopupObj.getInt("winCnt") %>승 
										<%=awayResultPopupObj.getInt("drawCnt") %>무 
										<%=awayResultPopupObj.getInt("loseCnt") %>패
									</td>
								</tr>
								</tbody>
							</table>							
						</div>
					</td>
					<td>상대<br/>경기</td>
					<td>
						<a href="javascript:layer_popup('#layerPopup','matchListAgainst<%=mr.getInt("gameListNo") %>')" style="font-size: 13px;color:#678197;">
						<%if(awayAgainstResult != null && awayAgainstResult.getString("resultStr").length() >= 1) { %>
						<span style="font-size: 13px;color:black;"><%=awayAgainstResult.getString("resultStr").substring(0, 1)%></span><%=awayAgainstResult.getString("resultStr").substring(1)%>
						<%} %>
						<br/>
						득:<%=awayAgainstResult.getInt("getScore")%>/실:<%=awayAgainstResult.getInt("lostScore")%>
						</a>
					</td>
				</tr>
				<tr id="mcAgainstHome<%=mr.getInt("gameListNo") %>" >
					<td>
						<a href="javascript:layer_popup('#layerPopup','matchListAgainstHome<%=mr.getInt("gameListNo") %>')" style="font-size: 13px;color:#678197;">
						<%if(homeAgainstResultAtHome.getString("resultStr").length() >= 1) { %>
						<span style="font-size: 13px;color:black;"><%=homeAgainstResultAtHome.getString("resultStr").substring(0, 1)%></span><%=homeAgainstResultAtHome.getString("resultStr").substring(1)%>
						<%} %>
						<br/>
						득:<%=homeAgainstResultAtHome.getInt("getScore")%>/실:<%=homeAgainstResultAtHome.getInt("lostScore")%>
						</a>
						
						<div id="matchListAgainstHome<%=mr.getInt("gameListNo") %>" style="display: none;">
							<%
								homeResultPopupObj = getPopupHtml((List<TAData>)homeAgainstResultAtHome.get("matchList"), mr.getString("homeTeamCode"));
								awayResultPopupObj = getPopupHtml((List<TAData>)awayAgainstResultAtHome.get("matchList"), mr.getString("awayTeamCode"));
							%>						
							<!-- 최근 홈에서 상대전적 6경기 상세정보 -->
							<table class="common">
								<tbody>
								<tr>
									<td colspan="2" style="background-color: #678197;color: white;">최근 홈에서 상대전적 6경기</td>
								</tr>
								<tr>
									<td width="50%"><%=mr.getString("homeTeamName") %></td>
									<td width="50%"><%=mr.getString("awayTeamName") %></td>
								</tr>
								<tr>
									<td style="font-size: 13px;line-height: 1.4em;text-align: left;padding-left: 10px;">
									<%=homeResultPopupObj.get("html").toString() %>									
									</td>
									<td style="font-size: 13px;line-height: 1.4em;text-align: left;padding-left: 10px;">
									<%=awayResultPopupObj.get("html").toString() %>
									</td>
								</tr>
								<tr>
									<td style="color:red;">
										<%=homeResultPopupObj.getInt("winCnt") %>승 
										<%=homeResultPopupObj.getInt("drawCnt") %>무 
										<%=homeResultPopupObj.getInt("loseCnt") %>패
									</td>
									<td style="color:red;">
										<%=awayResultPopupObj.getInt("winCnt") %>승 
										<%=awayResultPopupObj.getInt("drawCnt") %>무 
										<%=awayResultPopupObj.getInt("loseCnt") %>패
									</td>
								</tr>
								</tbody>
							</table>
						</div>						
					</td>
					<td>상대<br/>홈경기</td>
					<td>
						<a href="javascript:layer_popup('#layerPopup','matchListAgainstHome<%=mr.getInt("gameListNo") %>')" style="font-size: 13px;color:#678197;">
						<%if(awayAgainstResultAtHome.getString("resultStr").length() >= 1) { %>
						<span style="font-size: 13px;color:black;"><%=awayAgainstResultAtHome.getString("resultStr").substring(0, 1)%></span><%=awayAgainstResultAtHome.getString("resultStr").substring(1)%>
						<%} %>
						<br/>
						득:<%=awayAgainstResultAtHome.getInt("getScore")%>/실:<%=awayAgainstResultAtHome.getInt("lostScore")%>
						</a>
					</td>
				</tr>
				
				<%
				}
				%>
				
				
				
				<tr id="mcNews<%=mr.getInt("gameListNo") %>" >
				
						<%
						//영상링크
						String daumLgCd = recordService.getDaumLgCd(mr.getString("lgCd"));
						
						String homeTeamCpCd = mr.getString("homeTeamCodeCp");
						String awayTeamCpCd = mr.getString("awayTeamCodeCp");
						
						if(StringUtils.isNumeric(homeTeamCpCd)) {
							homeTeamCpCd = String.valueOf(Integer.parseInt(homeTeamCpCd));
							awayTeamCpCd = String.valueOf(Integer.parseInt(awayTeamCpCd));
						} else {
							homeTeamCpCd = homeTeamCpCd.substring(3, 6);
							awayTeamCpCd = awayTeamCpCd.substring(3, 6);
						}
						%>				
					<td colspan="3" style="font-size: 13px;line-height: 1.6em;text-align: left;padding-left: 30px;">
						<%=mr.getString("homeTeamName") %> :
						<a href="http://live.sports.media.daum.net/video/highlight/<%=daumLgCd %>/team/<%=homeTeamCpCd %>" target=_blank class="win">영상</a>
						/
						<a href="https://search.naver.com/search.naver?query=<%=mr.getString("homeTeamName") %>" target=_blank  class="win">네이버</a>
						/
						<a href="https://search.daum.net/search?q=<%=mr.getString("homeTeamName") %>" target=_blank  class="win">다음</a>
						<br/>
						<%=mr.getString("awayTeamName") %> :
						<a href="http://live.sports.media.daum.net/video/highlight/<%=daumLgCd %>/team/<%=awayTeamCpCd %>" target=_blank class="win">영상</a>
						/
						<a href="https://search.naver.com/search.naver?query=<%=mr.getString("awayTeamName") %>&x=0&y=0" target=_blank class="win">네이버</a>
						/
						<a href="https://search.daum.net/search?q=<%=mr.getString("awayTeamName") %>" target=_blank class="win">다음</a>
						
						<!-- hidden -->
						<input type="hidden" id="gmListNo" name="gmListNo" value="<%=mr.getInt("gameListNo")%>" />
						<input type="hidden" id="mcCd" name="mcCd" value="<%=mr.getString("matchCode")%>" />
					</td>
				</tr>
				<tr id="mcResult<%=mr.getInt("gameListNo") %>" >
					<td colspan="3" style="background-color: AliceBlue">
					<%
						if(mr.getString("matchEnd").equals("Y")) {
							if(mr.getString("matchResult").equals("W")) {
								out.print(mr.getString("homeTeamName"));								
							} else if(mr.getString("matchResult").equals("L")) {
								out.print(mr.getString("awayTeamName"));
							} else {
								out.print("무");
							}
							out.print("<br/>"+mr.getString("scoreHome")+":"+mr.getString("scoreAway"));
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
				<th colspan="3">픽 제목</th>
			</tr>
			<tr>
				<td colspan="3">
					<input type="text" name="gmPostTitle" id="gmPostTitle" placeholder="픽을 구분할 수 있는 간단한 제목을 입력해주세요(필수)" maxlength="200" style="font-size:14px;width:100%;" />
				</td>
			</tr>
			<tr>
				<th colspan="3">픽 내용</th>
			</tr>
			<tr>
				<td colspan="3">
					<textarea name="gmPostContent" rows="10" cols="30" placeholder="픽에 대한 내용을 등록해주세요(옵션)" style="font-size:14px;width:100%;"></textarea>
				</td>
			</tr>
			</tbody>
			</table>
			<input type="hidden" name="gmCd" value="<%=gameMt.getGmCd() %>" />
			<input type="hidden" name="userId" value="<%=userId %>" />
			<input type="hidden" name="pubYn" id="pubYn" value="0" />
			</form>
		</div>
		<br/><br/><br/><br/><br/><br/><br/>


<script>
<%
//for(int i=0;i<list.size();i++) {
//	TAData mr = (TAData)list.get(i);
%>

<%
//}
%>
function showMatchList(id) {
	if(document.getElementById(id).style.display == "block") {
		document.getElementById(id).style.display = "none";
	} else {
		document.getElementById(id).style.display = "block";
	}
}

function layer_popup(el, dataId) {
	
	layerPopupMatchList.innerHTML = document.getElementById(dataId).innerHTML;

    var $el = $(el);        //레이어의 id를 $el 변수에 저장
    var isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수

    isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

    var $elWidth = ~~($el.outerWidth()),
        $elHeight = ~~($el.outerHeight()),
        docWidth = $(document).width(),
        docHeight = $(document).height();

    // 화면의 중앙에 레이어를 띄운다.
    if ($elHeight < docHeight || $elWidth < docWidth) {
        $el.css({
            marginTop: -$elHeight /2,
            marginLeft: -$elWidth/2
        })
    } else {
        $el.css({top: 0, left: 0});
    }

    $el.find('a.btn-layerClose').click(function(){
        isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
        return false;
    });

    $('.layer .dimBg').click(function(){
        $('.dim-layer').fadeOut();
        return false;
    });
}
</script>

<!-- 경기 요약 정보 레이어 영역 -->
<div class="dim-layer">
    <div class="dimBg"></div>
    <div id="layerPopup" class="pop-layer">
        <div class="pop-container">
            <div id="layerPopupContents" class="pop-conts">
                <!--content //-->
                <p id="layerPopupMatchList" class="ctxt mb20"></p>
                <div class="btn-r">
                    <a href="#" class="btn-layerClose" style="width:40%">닫기</a>
                </div>
                <!--// content-->
            </div>
        </div>
    </div>
</div>
		
<!-- 하단 픽 영역 -->
  <nav class="floating-menu">
  <table class="subTable" style="border:0px 0px 0px 0px;width:100%;">
  <tr>
  	<td>
  		<ul>
<%
  	for(int i=0;i<7;i++) {
		TAData mr = (TAData)list.get(i);
		out.print("<li><a  style=\"font-size:13px;padding-left:5px;line-height:1.3em;text-align:left;\" href='#match"+mr.getInt("gameListNo")+"'>"+mr.getInt("gameListNo")+". "+mr.getString("homeTeamNameBet")+" VS "+mr.getString("awayTeamNameBet"));
		out.print("(<gm id='subGame_"+mr.getInt("gameListNo")+"' class='subGame' style='font-color:red;'></gm>)</a></li>");
  	}
%>
		</ul>
  	</td>
  	<td>
  		<ul>
<%
  	for(int i=7;i<list.size();i++) {
		TAData mr = (TAData)list.get(i);
		out.print("<li><a  style=\"font-size:13px;padding-left:5px;line-height:1.3em;text-align:left;\" href='#match"+mr.getInt("gameListNo")+"'>"+mr.getInt("gameListNo")+". "+mr.getString("homeTeamNameBet")+" VS "+mr.getString("awayTeamNameBet"));
		out.print("(<gm id='subGame_"+mr.getInt("gameListNo")+"' class='subGame' style='font-color:red;'></gm>)</li>");
  	}
%>
		</ul>  	
  	</td>
  </tr>
	<tr>
		<td colspan="2" class="result">
			픽비율 : <gm id="subRatio" class="subGame"><fmt:formatNumber value="<%=totalBetCnt %>" pattern="#" /></gm>
		</td>
	</tr>
	<tr>
		<td class="result" style="height:40px;font-size:15px; background-color:white; color: #595959;" onclick="checkPick();"><gm id="totalPickCnt">0</gm>조합 픽올리기</td>
		<td class="result" style="font-size:15px; background-color:white; color: #595959;">
<%
	String msg = "";
	if(nowLogin) {
%>
		<input type="checkbox" name="pubYnChk" id="pubYnChk"/> 비공개픽
<%	
	} else {
%>
		<a href="javascript:alert('로그인해야만 비공개픽을 등록하실 수 있습니다.')" style="font-size:15px;color:#595959;">비공개픽</a>
		
<%		
	}
%>		
		</td>
	</tr>
  </table>
  </nav>
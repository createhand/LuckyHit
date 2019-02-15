<%@page import="kr.co.toto.base.persistence.domain.DomainConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="kr.co.toto.comn.model.TAData" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
<%
	TAData params = (TAData)request.getAttribute("params");
	List<TAData> replyList = (List<TAData>)request.getAttribute("replyList");
	List<TAData> selectedGame = (List<TAData>)request.getAttribute("selectedGame");
	TAData selectGameInfo = (TAData)request.getAttribute("selectGameInfo");
%>
	<form name="frm" method="POST">
      <div class="content_title">
  		<h2 class="header">공개픽 - <%=selectGameInfo.getString("gmName") %> <%=selectGameInfo.getString("gmTurn") %>회
		<input type="hidden" id="gmCd" name="gmCd" value="<%=selectGameInfo.getString("gmCd") %>" />
		<input type="hidden" id="gmPostNo" name="gmPostNo" value="<%=selectGameInfo.getString("gmPostNo") %>" />
  		</h2>
      </div>
      <div class="content">
		  <div class="content_info">
		  <table class="common">
		  	<thead>
		  	<tr>
				<td colspan="3" style="border-right: 0;">
				<div style="text-align: left;"><a href="<%=request.getContextPath() %>gameDetailList.do" style="font-size:15px;color:#595959;line-height: 1.8em;padding-left: 10px;">픽올리기</a></div>
				</td>
				<td colspan="3">
				<div style="text-align: right;"><a href="gameResult.do?pageNo=<%=params.getString("pageNo") %>" style="font-size:15px;color:#595959;line-height: 1.8em;padding-right: 10px;">목록보기</a></div>
				</td>
			</tr>
		  	<tr>
		  		<th colspan="6" style="color: TOMATO;">등록시간 : <%=selectGameInfo.get("regDt") %> <%=selectGameInfo.get("regTm") %></th>
		  	</tr>			
			<tr>
				<td colspan="6">
					<div class="content_list"><p style="text-align: left;font-size:18px;line-height:1.6em;color:#595959;"><%=selectGameInfo.get("gmPostTitle") %></p></div>
					<div style="font-size:13px;line-height:1.8em;text-align: right;color:#595959;padding-right: 10px;">
					<%
					if(StringUtils.isBlank(selectGameInfo.getString("userId")) || selectGameInfo.getString("userId").equals("null")) {
						out.print("익명");
					} else {
						out.print(selectGameInfo.getString("userNm")+"("+selectGameInfo.getString("userId")+")");
					}
					%>
					</div>
					<div class="content_list"><p style="text-align: left;font-size:14px;line-height:1.3em"><%=selectGameInfo.get("gmPostContent") %></p><br/></div>
				</td>
			</tr>		  	
<%
	if(selectedGame.size() > 0) {
%>		  	
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
			
			TAData obj = selectedGame.get(i);
			
			//예상 결과
			String expResult = obj.getString("expResult");
			//경기 결과
			String mcResult = "";
			//경기 종료여부
			String matchEnd = obj.getString("matchEnd");
			
			String result = "예정";
			String csStyle = "normal";
			if(StringUtils.equals(matchEnd, DomainConst.YES)) {
				
				 mcResult = obj.getString("matchResult").toString();
				 
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
				<td><%=obj.getString("gmListNo") %></td>
				<td><%=obj.getString("tmNameBetH") %></td>
				<td><%=obj.getString("tmNameBetA") %></td>
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
<%
	}
%>			
			
			
			<tr>
				<td colspan="6" style="background-color: #678197;color:white;">
				댓글[<%=replyList.size() %>]
				</td>
			</tr>
<%
		for(TAData replyInfo : replyList) {
%>
			<tr>
				<td colspan="4" style="border-right:0;border-bottom: 0;">
					<div style="padding-left:10px;padding-top:5px;text-align: left;color:#595959;font-size: 13px;">
<%
		if(StringUtils.isBlank(replyInfo.getString("USER_NM"))) {
			out.print("익명");
		} else {
			out.print(replyInfo.getString("USER_NM")+"("+replyInfo.getString("USER_ID")+")");
		}
%>
					</div>
				</td>
				<td colspan="2" style="border-bottom: 0;">
					<div style="padding-right:5px;text-align: right;color:#595959;font-size: 11px;">
<%
		if(StringUtils.equals(DateUtil.getToday("yy-MM-dd"), replyInfo.getString("REG_DT"))) {
			out.print(replyInfo.getString("REG_TM"));
		} else {
			out.print(replyInfo.getString("REG_DT"));
		}
%>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<div style="text-align: left;padding-left: 10px;font-size: 13px;color:#595959;line-height: 1.5em;">
					<%=replyInfo.getString("REPLY_CONTENT") %>
					</div>
				</td>
			</tr>
<%				
		}
%>			

			<tr>
				<td colspan="6">
					<textarea id="replyContent" name="replyContent" rows="7" style="width: 100%;font-size: 15px;" placeholder="댓글을 입력하세요."></textarea>
					<input type="button" id="btnWrite" name="btnWrite" value="댓글쓰기" style="width:100%;height: 35px;font-size: 15px;" onclick="writeReply();" />
				</td>
			</tr>
			<tr>
				<td colspan="3" style="border-right: 0;">
				<div style="text-align: left;"><a href="<%=request.getContextPath() %>gameDetailList.do" style="font-size:15px;color:#595959;line-height: 1.8em;padding-left: 10px;">픽올리기</a></div>
				</td>
				<td colspan="3">
				<div style="text-align: right;"><a href="gameResult.do?pageNo=<%=params.getString("pageNo") %>" style="font-size:15px;color:#595959;line-height: 1.8em;padding-right: 10px;">목록보기</a></div>
				</td>
			</tr>
			</tbody>
		  </table>
		  </div>
      </div>
      </form>
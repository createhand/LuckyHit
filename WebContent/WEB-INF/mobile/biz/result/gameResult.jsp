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
	List<TAData> pickList = (List<TAData>)request.getAttribute("pickList");

	//페이징 관련
	int  totalCount = (Integer)request.getAttribute("totalCount");
	TAData pagingInfo = (TAData)request.getAttribute("pagingInfo");
	int totalPageCount = pagingInfo.getInt("totalPageCount");
	int pageNo = pagingInfo.getInt("pageNo");
	
	String gmCd = (String)request.getAttribute("gmCd");
	String gmPostNo = (String)request.getAttribute("gmPostNo");
	String gmSeq = "";
%>
	<form name="frm" method="GET">
      <div class="content_title">
  		<h2 class="header">공개픽
		<input type="hidden" id="pageNo" name="pageNo" value="<%=pageNo %>" />
  		</h2>
      </div>
      <div class="content">
		  <div class="content_info">
		  <table class="common">
		  	<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>예측</th>
				<th>결과</th>
				<th>적중</th>
			</tr>
			</thead>
			<tbody>
				<tr>			
<%
	for(TAData pickInfo : pickList) {
%>
			<tr>
				<td><%=pickInfo.getString("RNUM") %></td>
				<td><%=pickInfo.getString("RNUM") %></td>
			</tr>
<%		
	}	
%>
			<tr>
				<td colspan="6">
					<div class="content_list" style="text-align: left;font-size:16px;"><p>제목 : <%=selectGameInfo.get("gmPostTitle") %></p></div>
					<div class="content_list" style="text-align: left;font-size:14px;padding:0 10px 0 10px;line-height:1.3em"><%=selectGameInfo.get("gmPostContent") %></div>
				</td>
			</tr>
			<tr>
				<td colspan="6"><span id="common">적중수 : <font color='red'><%=accCnt%>/14</font> / 적중률 : <font color='red'><%=Math.ceil((accCnt/14)*100) %>%</font></span></td>	
			</tr>
			<tr>
				<td colspan="6"><a href="http://m.betman.co.kr/winningResultToto.so?method=detail&gameId=G011&gameRound=<%=gmSeq%>&page=3" target="_blank"><span id="common">당첨결과</span></a></td>
			</tr>
			<tr>
				<td colspan="6">
				<%
					for(int i=0;i<totalPageCount;i++) {
						int no = i+1;
						if(no == pageNo) {
							out.print("<a href=\"\"><b>" + no + "</b></a>");
						} else {
							out.print(no);	
						}
					}
				%>
				</td>
			</tr>
			</tbody>
		  </table>
		  </div>
      </div>
      </form>
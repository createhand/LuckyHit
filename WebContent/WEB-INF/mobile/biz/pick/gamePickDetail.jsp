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
List<HashMap<String, String>> agList = (List<HashMap<String, String>>)request.getAttribute("agList");
List<HashMap<String, String>> hList = (List<HashMap<String, String>>)request.getAttribute("hList");
List<HashMap<String, String>> aList = (List<HashMap<String, String>>)request.getAttribute("aList");
%>
      <div class="content_title">
  		<h2 class="header">최근 5경기 상대전적  		
  		</h2>  		  		
      </div>      
      <div class="content">
		  <div class="content_info">
			<form name="frm" method="GET">
		  <table class="common">
		  	<thead>
			<tr>
				<th>일시</th>
				<th>리그</th>
				<th>홈</th>
				<th>원정</th>
				<th>스코어</th>
				<th>결과</th>
			</tr>
<%
			for(int i=0;i<agList.size();i++) {
				HashMap mr = agList.get(i);
				String mcDate = mr.get("mcDate").toString();
%>
			<tr>
				<td>
				<fmt:parseDate value="<%=mcDate %>" var="dateFmt" pattern="yyyymmdd"/>
				<fmt:formatDate value="${dateFmt}" pattern="yyyy.mm.dd"/><br/>
				(<%=new BizUtil().getDayStr(Integer.parseInt(mr.get("mcDay").toString()))%>)
				<%=mr.get("mcTime")%></td>
				<td><%=mr.get("lgNm")%></td>
				<td><%=mr.get("tmNameBetH")%></td>
				<td><%=mr.get("tmNameBetA")%></td>
				<td><%=mr.get("scoreH")%> : <%=mr.get("scoreA")%></td>
				<td><%=new BizUtil().getWinstrResult(mr.get("winStr").toString()) %></td>
			</tr>
<%
			}
%>			
			</thead>	
			<tbody>
			</tbody>		
		  </table>
		  </form>
		  </div>
      </div>
      
      
      <div class="content_title">
  		<h2 class="header">최근 5경기 홈팀전적
  		</h2>  		  		
      </div>      
      <div class="content">
		  <div class="content_info">
			<form name="frm" method="GET">
		  <table class="common">
		  	<thead>
			<tr>
				<th>일시</th>
				<th>리그</th>
				<th>홈</th>
				<th>원정</th>
				<th>스코어</th>
				<th>결과</th>
			</tr>
<%
			for(int i=0;i<hList.size();i++) {
				HashMap mr = hList.get(i);
				String mcDate = mr.get("mcDate").toString();
%>
			<tr>
				<td>
				<fmt:parseDate value="<%=mcDate %>" var="dateFmt" pattern="yyyymmdd"/>
				<fmt:formatDate value="${dateFmt}" pattern="yyyy.mm.dd"/><br/>
				(<%=new BizUtil().getDayStr(Integer.parseInt(mr.get("mcDay").toString()))%>)
				<%=mr.get("mcTime")%></td>
				<td><%=mr.get("lgNm")%></td>
				<td><%=mr.get("tmNameBetH")%></td>
				<td><%=mr.get("tmNameBetA")%></td>
				<td><%=mr.get("scoreH")%> : <%=mr.get("scoreA")%></td>
				<td><%=new BizUtil().getWinstrResult(mr.get("winStr").toString()) %></td>
			</tr>
<%
			}
%>			
			</thead>	
			<tbody>
			</tbody>		
		  </table>
		  </form>
		  </div>
      </div>
      
      
      <div class="content_title">
  		<h2 class="header">최근 5경기 원정팀 전적  		
  		</h2>  		  		
      </div>      
      <div class="content">
		  <div class="content_info">
			<form name="frm" method="GET">
		  <table class="common">
		  	<thead>
			<tr>
				<th>일시</th>
				<th>리그</th>
				<th>홈</th>
				<th>원정</th>
				<th>스코어</th>
				<th>결과</th>
			</tr>
<%
			for(int i=0;i<aList.size();i++) {
				HashMap mr = aList.get(i);
				String mcDate = mr.get("mcDate").toString();
%>
			<tr>
				<td>
				<fmt:parseDate value="<%=mcDate %>" var="dateFmt" pattern="yyyymmdd"/>
				<fmt:formatDate value="${dateFmt}" pattern="yyyy.mm.dd"/><br/>
				(<%=new BizUtil().getDayStr(Integer.parseInt(mr.get("mcDay").toString()))%>)
				<%=mr.get("mcTime")%></td>
				<td><%=mr.get("lgNm")%></td>
				<td><%=mr.get("tmNameBetH")%></td>
				<td><%=mr.get("tmNameBetA")%></td>
				<td><%=mr.get("scoreH")%> : <%=mr.get("scoreA")%></td>
				<td><%=new BizUtil().getWinstrResult(mr.get("winStr").toString()) %></td>
			</tr>
<%
			}
%>			
			</thead>	
			<tbody>
			</tbody>		
		  </table>
		  </form>
		  </div>
      </div>
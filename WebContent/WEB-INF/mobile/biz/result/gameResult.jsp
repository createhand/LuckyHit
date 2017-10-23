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
	TAData pagingInfo = (TAData)request.getAttribute("pagingInfo");
	
	int countPerPage = pagingInfo.getInt("countPerPage");
	int countPerBlock = pagingInfo.getInt("countPerBlock");
	
	int totalPageCount = pagingInfo.getInt("totalPageCount");
	int totalBlockCount = pagingInfo.getInt("totalBlockCount");
	int  totalCount = (Integer)request.getAttribute("totalCount");
	
	int pageNo = pagingInfo.getInt("pageNo");
	int blockNo = pagingInfo.getInt("blockNo");
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
				<th width="10%">번호</th>
				<th width="75%">제목</th>
<!-- 				<th width="25%">작성자</th> -->
				<th width="15%">작성일시</th>
			</tr>
			</thead>
			<tbody>
				<tr>			
<%
	for(TAData pickInfo : pickList) {
%>
			<tr>
				<td><%=pickInfo.getInt("RNUM") %></td>
				<td style="text-align: left;padding-left:5px;">
					<a href="<%=request.getContextPath() %>gameResultDetail.do?pageNo=<%=pageNo%>&gmCd=<%=pickInfo.getString("gmCd")%>&gmPostNo=<%=pickInfo.getString("gmPostNo")%>">
					<%=pickInfo.getString("gmPostTitle") %></a><br/>
					<div style="font-size: 10px;text-align: right;padding-right: 5px;color: black;">
<%
		if(StringUtils.isBlank(pickInfo.getString("userNm"))) {
			out.print("익명");
		} else {
			out.print(pickInfo.getString("userNm")+"("+pickInfo.getString("userId")+")");
		}
%>
					</div>
				</td>
<!-- 				<td> -->
<%
/*
		if(StringUtils.isBlank(pickInfo.getString("userNm"))) {
			out.print("익명");
		} else {
			out.print(pickInfo.getString("userNm")+"("+pickInfo.getString("userId")+")");
		}
*/
%>
<!-- 				</td> -->
				<td>
					<%
					if(StringUtils.equals(DateUtil.getToday("yyyy-MM-dd"), pickInfo.getString("regDt"))) {
						out.print(pickInfo.getString("regTm"));	
					} else {
						out.print(DateUtil.getToday("MM-dd"));	
					}
%>
				</td>
			</tr>
<%		
	}
%>
			</tbody>
		  </table>
			  <div>
			  	<table class="common">
			  	<tr>
			  		<td class="normal">
<%

		int pageStNo = (blockNo*countPerBlock)+1;
		int pageEnNo = (blockNo*countPerBlock)+1+countPerBlock;

		if(blockNo != 0) {
			out.print("<a href=\""+request.getContextPath()+"gameResult.do?pageNo="+(pageStNo-1)+"\"> [이전]</a> ");
		}

		for(int i=pageStNo;i<pageEnNo;i++) {
			if(i>totalPageCount) continue;
			out.print("<a href=\""+request.getContextPath()+"gameResult.do?pageNo="+i+"\"> ["+i+"]</a> ");
		}
		
		if((blockNo+1) < totalBlockCount) {
			out.print("<a href=\""+request.getContextPath()+"gameResult.do?pageNo="+(pageEnNo)+"\"> [다음]</a>");
		}
%>
					</td>
				</tr>			  
				</table>
			  </div>
		  </div>
      </div>
      </form>
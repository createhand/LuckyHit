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
				<td colspan="6" style="padding-right: 10px;">
				<div style="text-align: right;"><a href="<%=request.getContextPath() %>gameDetailList.do" style="font-size:15px;color:#595959;line-height: 1.8em;padding-left: 10px;">픽올리기</a></div>
				</td>
			</tr>		  	
			<tr>
				<th width="10%">번호</th>
				<th width="*">제목</th>
<!-- 				<th width="25%">작성자</th> -->
				<th width="12%">조회</th>
			</tr>
			</thead>
			<tbody>
				<tr>			
<%
	int rowCnt = 0;
	for(TAData pickInfo : pickList) {
		String gmPostTitle = pickInfo.getString("gmPostTitle");
		int replyCnt = pickInfo.getInt("replyCnt");
		if(gmPostTitle.length() > 25) {
			gmPostTitle = gmPostTitle.substring(0, 25)+"..";
		}
%>
			<tr>
				<td><%=totalCount - ((pageNo-1)*countPerPage)-rowCnt %></td>
				<td style="text-align: left;padding-top:7px;padding-left:7px;line-height: 1.1em;">
<%-- 				[<%=pickInfo.getString("gmTurn") %>]회 --%>
					<a style="font-size: 14px;line-height: 1.3em;color:#595959;" href="<%=request.getContextPath() %>gameResultDetail.do?pageNo=<%=pageNo%>&gmCd=<%=pickInfo.getString("gmCd")%>&gmPostNo=<%=pickInfo.getString("gmPostNo")%>">
					<%=gmPostTitle %>
<%
				if(replyCnt > 0) {
					out.print("["+replyCnt+"]");
				}
%>					
					</a>
					<br/><br/>
					<div style="font-size: 12px;text-align: right;padding-right: 5px;color: #678197;">
<%
		if(StringUtils.isBlank(pickInfo.getString("userNm"))) {
			out.print("익명");
		} else {
			out.print(pickInfo.getString("userNm")+"("+pickInfo.getString("userId")+")");
		}
%>
					</div>
					<div style="font-size: 12px;text-align: right;padding-right: 5px;color: #678197;">
<%
					if(StringUtils.equals(DateUtil.getToday("yyyy-MM-dd"), pickInfo.getString("regDt"))) {
						out.print(pickInfo.getString("regTm"));	
					} else {
						out.print(DateUtil.getToday("MM-dd"));	
					}
%>
					</div>
				</td>
				<td style="line-height: 1.6em;font-size: 13px;">
				<%=pickInfo.getInt("viewCnt") %><br/>				
<%
// 					if(StringUtils.equals(DateUtil.getToday("yyyy-MM-dd"), pickInfo.getString("regDt"))) {
// 						out.print(pickInfo.getString("regTm"));	
// 					} else {
// 						out.print(DateUtil.getToday("MM-dd"));	
// 					}
%>
				</td>
			</tr>
<%
		rowCnt++;
	}
%>
			  	<tr>
			  		<td class="normal" colspan="6">
<%

		int pageStNo = (blockNo*countPerBlock)+1;
		int pageEnNo = (blockNo*countPerBlock)+1+countPerBlock;

		if(blockNo != 0) {
			out.print("<a href=\""+request.getContextPath()+"gameResult.do?pageNo="+(pageStNo-1)+"\">[이전]</a> ");
		}

		for(int i=pageStNo;i<pageEnNo;i++) {
			if(i>totalPageCount) continue;
			out.print("<a style=\"padding-left:4px;padding-right:4px;color:#595959;\" href=\""+request.getContextPath()+"gameResult.do?pageNo="+i+"\">["+i+"]</a> ");
		}
		
		if((blockNo+1) < totalBlockCount) {
			out.print("<a href=\""+request.getContextPath()+"gameResult.do?pageNo="+(pageEnNo)+"\">[다음]</a>");
		}
%>
					</td>
				</tr>
				<tr>
					<td colspan="6" style="padding-right: 10px;">
					<div style="text-align: right;"><a href="<%=request.getContextPath() %>gameDetailList.do" style="font-size:15px;color:#595959;line-height: 1.8em;padding-left: 10px;">픽올리기</a></div>
					</td>
				</tr>				
			</tbody>
		  </table>
		  </div>
      </div>
      </form>
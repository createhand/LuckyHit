<%@page import="kr.co.toto.base.persistence.domain.DomainConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
HashMap searchParam = (HashMap<String, String>)request.getAttribute("searchParam");
%>
      <div class="content_title">
  		<h2 class="header"><%=pick.get("gmName") %> <%=pick.get("gmTurn") %>회 <%=pick.get("gmPostNo") %>번째 픽!  		
  		</h2>
  		<p class="date">작성일자 : <%=pick.get("rgDate") %></p>
  		
      </div>
      
      <div class="content">
  
  <!-- Handmark Mobile Publishing START -->
    <div class="story_lead_photo" style="background-color:#EEE; width:280px; padding:5px;">
      <div class="photo">
        <a href="/photos/2013/apr/23/40239/"><img src="http://golfweek.media.clients.ellingtoncms.com/img/croppedphotos/2013/04/23/148494007x_t280.jpg?ed0c8a36ff9a452a7fd5d01f708da75494446577" alt="R&amp;A CEO Peter Dawson"></a>
      </div>
      <p class="byline" style="margin-bottom:5px; font-size:12px; line-height:16px; color:#333;">
          Photo by Getty Images
      </p>
      <p class="caption" style="font-size:12px; line-height:16px; color:#666; margin-bottom:5px;">R&amp;A CEO Peter Dawson</p>
    </div>

  <div class="content_info">
        <div style="float:left; margin-right:5px;"><img class="mugshot" src="http://golfweek.media.clients.ellingtoncms.com/img/staff/2009/tait_r40x60.jpg?f1039b71e485193cf6b94dae210f6b0394615e3f" title="Alistair Tait" alt="Alistair Tait"></div>
         <p class="byline">Story by Alistair Tait</p>
      	 <p class="date">Tuesday, April 23, 2013</p>
    <div style="clear:both"></div>
  </div>
  
  <div class="content_info">
	<form name="frm" method="GET">
	<input type="hidden" name="view" />
	<input type="hidden" name="latest" value="<%=searchParam.get("latest") %>" />
	<input type="hidden" name="last" value="<%=searchParam.get("last") %>" />
	<input type="hidden" name="gmCd" value="<%=searchParam.get("gmCd") %>" />
	<input type="hidden" name="gmPostNo" value="<%=searchParam.get("gmPostNo") %>" />  
  <table class="common">
	<tr>
		<th class="head" rowspan="2">경기시간</th>
		<th>홈</th>
		<th>최근전적</th>
		<th>최근득실</th>
	</tr>
	<tr>
		<th>원정</th>
		<th>상대전적</th>
		<th>상대득실</th>		
	<tbody>
	<tr>
			
<%
	if(list.size() == 0) {
%>
				<tr>
					<td colspan="11">등록된 경기가 없습니다</td>
				</tr>
<%		
	}

	for(int i=0;i<list.size();i++) {
		
		String hLtRecord = "", hLtAgRecord = "", aLtRecord = "", aLtAgRecord = "";		
		String hLtGtPoint = "0", hLtLsPoint = "0", hLtGtAgPoint = "0", hLtLsAgPoint = "0"; 
		String aLtGtPoint = "0", aLtLsPoint = "0", aLtGtAgPoint = "0", aLtLsAgPoint = "0";
		HashMap mr = (HashMap)list.get(i);
		
		hLtRecord = mr.get("hLtRecord").toString();
		hLtAgRecord = mr.get("hLtAgRecord").toString();
		aLtRecord = mr.get("aLtRecord").toString();
		aLtAgRecord = mr.get("aLtAgRecord").toString();
		
		hLtGtPoint = mr.get("hLtGtPoint").toString(); 
		hLtLsPoint = mr.get("hLtLsPoint").toString();
		hLtGtAgPoint = mr.get("hLtGtAgPoint").toString();
		hLtLsAgPoint = mr.get("hLtLsAgPoint").toString();		
		
		aLtGtPoint = mr.get("aLtGtPoint").toString(); 
		aLtLsPoint = mr.get("aLtLsPoint").toString();
		aLtGtAgPoint = mr.get("aLtGtAgPoint").toString();
		aLtLsAgPoint = mr.get("aLtLsAgPoint").toString();
		
		String mcResult = mr.get("mcResult").toString();
		String mcEnd = mr.get("mcEnd").toString();
		String mcDay = mr.get("mcDay").toString();
		String tmNmH = mr.get("tmNmH").toString();
		String tmNmA = mr.get("tmNmA").toString();
		String expResult = mr.get("expResult").toString();
		
%>
				<tr>
					<td rowspan="4" class="head"><%=mr.get("gmListNo")%>경기<br/><%=mr.get("mcDate")%>
					(<%=new BizUtil().getDayStr(Integer.parseInt(mcDay))%>)
					<%=mr.get("mcTime")%><br/>
					<%=mcEnd.equals("Y") ? "종료" : "예정" %>
					</td>
					<td rowspan="2"><%=tmNmH %></td>
					<td><%=hLtRecord%></td>	
					<td>득점 : <%=hLtGtPoint%> 실점 : <%=hLtLsPoint%></td>
				</tr>
				<tr>									
					<td><%=hLtAgRecord%></td>					
					<td>득점 : <%=hLtGtAgPoint%> 실점 : <%=hLtLsAgPoint%></td>
				<tr>
					<td rowspan="2"><%=tmNmA %></td>
					<td><%=aLtRecord%></td>	
					<td>득점 : <%=aLtGtPoint%> 실점 : <%=aLtLsPoint%></td>
				</tr>
				<tr>									
					<td><%=aLtAgRecord%></td>					
					<td>득점 : <%=aLtGtAgPoint%> 실점 : <%=aLtLsAgPoint%></td>
				<tr>
				<tr>
					<td colspan="4" class="expect">
					예상 : 
					<%
					if(expResult.equals("H")) out.print(tmNmH);
					else if(expResult.equals("A")) out.print(tmNmA);
					else out.print("무승부");
					%></td>
				</tr>
<%
					if(mcEnd.equals("Y")) {
						out.print("<tr><td colspan=\"4\" class=\"expect\">경기결과 : ");
						if(mcResult.equals("H")) {
							out.print(tmNmH);								
						} else if(mcResult.equals("A")) {
							out.print(tmNmA);
						} else {
							out.print("무승부");
						}
						out.print(" <b>"+mr.get("scoreH")+" : "+mr.get("scoreA")+"</b>");
						out.print("</td></tr>");
					}
%>
				
				
				
<%		
	}
%>
		

			</tbody>		
  </table>


  <div class="comments" >
    <%=pick.get("gmPostContent") %>
  </div>  
  
<table class="calendar">
<%
	if(searchParam.get("latest").toString().equals("N")) {
%>
		<tr><td colspan="11"><a href="javascript:goView('next');">다음글</a></td></tr>
<%		
	}

	if(searchParam.get("last").toString().equals("N")) {
%>
		<tr><td colspan="11"><a href="javascript:goView('prev');">이전글</a></td></tr>
<%		
	}
%>
</table>

  </form>
  </div>


<div class="content_list">
        
	<hr />
  <div id="comments" style="padding:5px">
  	<!--<fb:comments xid="35550" url="http://www.golfweek.com/news/2013/apr/23/quail-hollow-scrambles-repair-greens/" title="Quail Hollow scrambles to repair greens" width="310px"></fb:comments>-->
            <div id="disqus_thread"></div>
        <script type="text/javascript">
            /* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
            var disqus_shortname = 'golfweek'; // required: replace example with your forum shortname

            /* * * DON'T EDIT BELOW THIS LINE * * */
            (function() {
                var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
                dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';
                (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
            })();
        </script>
        <noscript>Please enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments.</a></noscript>  
    </div>
  <hr />

      </div>




      </div>
      
      <form action="/search/" method="get" class="search_form">
        <p>
          <input type="text" name="q" value="" id="id_query">
          <input type="submit" value="Search" class="button">
        </p>
      </form>    
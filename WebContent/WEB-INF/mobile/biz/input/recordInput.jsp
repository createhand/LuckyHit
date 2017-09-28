<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/mobile/include/common.jsp" %>
경기일정 및 결과 수집
<form name="frm" method="POST" action="<%=request.getContextPath() %>recordCollecting.do">
<select name="league" id="league">
<option value="all">전부</option>
<option value="kl">K리그 클래식</option>
<option value="kl2">K리그 챌린지</option>
<option value="epl">프리미어리그</option>
<option value="primera">프리메라리가</option>
<option value="bundesliga">분데스리가</option>
<option value="seriea">시리에A</option>
<option value="ligue1">리그1</option>
<option value="eredivisie">에레디비지에</option>
<option value="uefacl">UEFA챔스</option>
<option value="uefacup">유로파</option>
</select>
경기일자(From)<input type="text" name="stDt" id="stDt"/>~
경기일자(To)<input type="text" name="enDt" id="enDt"/><br/>
<input type="submit" id="submitBtn" name="submitBtn" value="수집"/>
</form>
<br/>
<br/>
<br/>
<br/>
<br/>
팀 등록
<form name="frm" method="POST" action="<%=request.getContextPath() %>teamCollecting.do">
<select name="league" id="league">
<option value="all">전부</option>
<option value="kl">K리그 클래식</option>
<option value="kl2">K리그 챌린지</option>
<option value="epl">프리미어리그</option>
<option value="primera">프리메라리가</option>
<option value="bundesliga">분데스리가</option>
<option value="seriea">시리에A</option>
<option value="ligue1">리그1</option>
<option value="eredivisie">에레디비지에</option>
</select>
<select name="seasonKey" id="seasonKey">
<option value="20172018">2017~2018</option>
<option value="20162017">2016~2017</option>
<option value="20152016">2015~2016</option>
<option value="20142015">2014~2015</option>
<option value="20132014">2013~2014</option>
<option value="20122013">2012~2013</option>
<option value="20112012">2011~2012</option>
<option value="20102011">2010~2011</option>
<option value="20092010">2009~2010</option>
<option value="20082009">2008~2009</option>
</select>
<input type="submit" id="submitBtn" name="submitBtn" value="수집"/>
</form>


<!-- 
<form name="frm" method="POST" action="<%=request.getContextPath() %>recordCollectingJ.do">
<select name="league" id="league">
<option value="jlg">J리그</option>
</select>

<select name="year">
<option value="2017">2017</option>
<option value="2016">2016</option>
<option value="2015">2015</option>
<option value="2014">2014</option>
<option value="2013">2013</option>
<option value="2012">2012</option>
<option value="2011">2011</option>
<option value="2010">2010</option>
<option value="2009">2009</option>
</select>

<select name="month">
<option value="">전체</option>
<option value="12">12</option>
<option value="11">11</option>
<option value="10">10</option>
<option value="09">09</option>
<option value="08">08</option>
<option value="07">07</option>
<option value="06">06</option>
<option value="05">05</option>
<option value="04">04</option>
<option value="03">03</option>
<option value="02">02</option>
<option value="01">01</option>
</select>
<input type="submit" id="submitBtn" name="submitBtn" value="수집"/>
</form>
J리그 : <a href="http://sdb.spojoy.com/team/index.spo?team_id=2007&pgTk=schedule&sel_year=2013&sel_month=5" target="_blank">링크열기</a><br/>
-->

<br/><br/><br/><br/><br/><br/>
게임입력
<form name="frm" method="POST" action="<%=request.getContextPath() %>gameCollecting.do">
게임번호 
시작회차 : <input type="text" name="gameStSeq" /> ~ 종료회차 : <input type="text" name="gameEnSeq" />
게임종료체크 : 
<select name="gmEndChk">
<option value="N">N</option>
<option value="Y">Y</option>
</select>

<br/> 
<input type="button" value="수집" onClick="gameCollecting();"/>
</form>

<b>승무패 URL : <a href="http://www.betman.co.kr/gameInfoMain.so?gameId=G011&gameRound=2831" target="_blank">http://www.betman.co.kr/gameInfoMain.so?gameId=G011&gameRound=2831</a></b><br/>
<b>주의: 게임번호 내에서만 수<br/></b>
13년도 게임번호 : 2817 ~ 2854<br/>
14년도 게임번호 : 140001 ~ 140039(20, 21, 22회 월드컵 제외)<br/>
15년도 게임번호 : 150001 ~ 150045<br/>
16년도 게임번호 : 160001 ~ 160045<br/>
17년도 게임번호 : 170001 ~ 현재<br/>
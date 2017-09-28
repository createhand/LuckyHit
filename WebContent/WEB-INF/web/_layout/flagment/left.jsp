<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="kr.co.toto.base.persistence.domain.DomainConst"%>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="kr.co.toto.comn.*" %>
<%@ page import="kr.co.toto.lgin.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.util.Map" %>
<%@ include file="/WEB-INF/web/include/common.jsp" %>
		<div id="sidebar">
			<div id="login">
				<h1>toto</h1>
				<form method="post" action="">
					<fieldset>
					<ul>
						<li>
							<label for="text1">Username:</label>
							<input type="text" name="text1" id="text1" class="text" />
						</li>
						<li>
							<label for="text2">Password:</label>
							<input type="password" name="text2" id="text2" class="text" />
						</li>
						<li>
							<input type="submit" name="button1" id="button1" value="Login" class="button" />
							<a href="#">Lost password?</a> </li>
					</ul>
					</fieldset>
				</form>
			</div>
			<!-- end #login -->
			<div id="box1">
				<h2>Veroeros nulla</h2>
				<ul class="list1">
					<li class="first">
						<h3>05.04.2008</h3>
						<p>Lorem ipsum dolor sit amet onse adipisicing elit, sed do eiusmod empor dolore. <a href="#">More&hellip;</a></p>
					</li>
					<li>
						<h3>05.03.2008</h3>
						<p>Lorem ipsum dolor sit amet onse adipisicing elit, sed do eiusmod empor dolore. <a href="#">More&hellip;</a></p>
					</li>
					<li>
						<h3>05.02.2008</h3>
						<p>Lorem ipsum dolor sit amet onse adipisicing elit, sed do eiusmod empor dolore. <a href="#">More&hellip;</a></p>
					</li>
				</ul>
			</div>
			<!-- end #box1 -->
		</div>
		<!-- end #sidebar -->
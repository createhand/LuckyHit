<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="kr.co.toto.util.*" %>
<%@ page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ include file="/WEB-INF/web/include/common.jsp" %>
		<div id="content">
			<div id="search">
				<form method="get" action="">
					<fieldset>
					<ul>
						<li>
							<label for="text3">Search our site:</label>
							<input type="text" name="text3" id="text3" class="text" />
							<input type="image" name="button2" id="button2" src="images/homepage07.gif" />
						</li>
					</ul>
					</fieldset>
				</form>
			</div>
			<!-- end #search -->
			<div id="box2"><img src="images/homepage08.jpg" alt="" width="139" height="115" class="right border" />
				<h1>Welcome <span>lorem ipsum dolor sit amet</span></h1>
				<p><strong>Lorem ipsum dolor</strong> sit amet, consectur adipisicing elit, sed do eiusmod empor  aliqua. Ut enim quis rud exercitation ullamco sed lorem ipsum dolor sit amet blandit consequat etiam feugiat. Lorem laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in <a href="#">voluptate velit esse cillum</a> dolore eu fugiat nulla pariatur. Lorem ipsum dolor volutpat consequat.</p>
				<p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi blandit lorem ipsum sed volutpat.</p>
			</div>
			<!-- end #box2 -->
			<div id="box3">
				<h2>Etiam et nullam <span>lorem ipsum dolor sit amet</span></h2>
				<p><img src="images/homepage10.jpg" alt="" width="209" height="61" class="border" /></p>
				<p><strong>Sed ut perspiciatis</strong> unde omnis iste natus error sit <a href="#">voluptatem accusantium</a> doloremque laudantium, totam rem aperiam. Ut enim quis rud exercitation ullamco sed. <a href="#">More&hellip;</a></p>
			</div>
			<!-- end #box3 -->
			<div id="box4">
				<h2>Blandit tempus <span>lorem ipsum dolor sit amet</span></h2>
				<ul class="list1">
					<li class="first">
						<p><strong>Volutpat consequat</strong> sit amet, consectur adipisicing elit, sed do eiusmod empor aliqua. Ut enim quis rud. Lorem ipsum. <a href="#">More&hellip;</a></p>
					</li>
					<li>
						<p><strong>ㅁㅁㅁㅁBlandit lorem sed</strong> sit amet, consectur adipisicing elit, sed do eiusmod empor  aliqua. Ut enim quis rud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure reprehenderit. <a href="#">More&hellip;</a></p>
					</li>
				</ul>
			</div>
			<!-- end #box4 -->
		</div>
		<!-- end #content -->
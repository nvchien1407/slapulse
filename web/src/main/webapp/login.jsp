<%-- ------- DO NOT REMOVE THIS NOTICE         ------- --%>
<%-- ------- COPYRIGHT(c)2006 RENEWTEK PTY LTD ------- --%>
<%-- ------- DO NOT REMOVE THIS NOTICE         ------- --%>

<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://www.springmodules.org/tags/commons-validator" prefix="v" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/request-1.0" prefix="r" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="/oscache" prefix="cache" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib uri="/WEB-INF/Calendar.tld" prefix="Calendar" %>

<title><fmt:message key="login.title"/></title>
<content tag="heading"><fmt:message key="login.heading"/></content>
<body id="login"/>

<c:import url="/loginMenu.jsp"/>

<p><fmt:message key="welcome.message"/></p>

<%String filenetLogin = (String)request.getSession(true).getAttribute("FileNetLogin");
  String errorMsg = request.getParameter("error");%>
<% if (filenetLogin != null && filenetLogin.equals("true") && errorMsg == null) {
  request.getSession(true).removeAttribute("FileNetLogin");%>
  <c:redirect url="/calendar.html"/>
<%} else { %>
<%-- Include the login form --%>
	<c:import url="/WEB-INF/pages/loginForm.jsp"/>
	<p><fmt:message key="login.passwordHint"/></p>
<%}%>


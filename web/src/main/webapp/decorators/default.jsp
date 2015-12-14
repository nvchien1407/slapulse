<%-- ------- DO NOT REMOVE THIS NOTICE         ------- --%>
<%-- ------- COPYRIGHT(c)2006 RENEWTEK PTY LTD ------- --%>
<%-- ------- DO NOT REMOVE THIS NOTICE         ------- --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        
<%-- Include common set of tag library declarations for each layout --%>
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

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <%-- Include common set of meta tags for each layout --%>
        <%@ include file="/common/meta.jsp" %>
        <title><fmt:message key="webapp.prefix"/><decorator:title/></title>
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/default.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/helptip.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='/styles/print.css'/>" />    
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/displaytag.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/sla.css'/>" />

        <script type="text/javascript" src="<c:url value='/scripts/prototype.js'/>"></script> 
        <script type="text/javascript" src="<c:url value='/scripts/effects.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/helptip.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/table.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/calendar.js'/>"></script>

        <decorator:head/>
    </head>

<body<decorator:getProperty property="body.id" writeEntireProperty="true"/> leftMargin=0 topMargin=0>
   <!--Header-->
   <c:import url="/common/header.jsp"/>
   <!--Header end-->
   <!--Menu-->
   <div class="left_content">
   <c:if test="${sessionScope.username != null}">
      <c:import url="/common/menu.jsp"/>
   </c:if>
      </div>
   <!--Menu End-->
   <div class="right_content">
      <!--Content-->
         <div class="main_title"><decorator:title default="Welcome" /></div>
         <decorator:body/>
      <!--Content End-->
   </div>
   <br class="clear"/>
   <!--Footer-->
   <c:import url="/common/footer.jsp"/>
   <!--Footer End-->
</body>
</html>

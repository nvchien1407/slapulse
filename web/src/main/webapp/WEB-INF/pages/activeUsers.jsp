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

<title><fmt:message key="activeUsers.title"/></title>
<content tag="heading"><fmt:message key="activeUsers.heading"/></content>
<body id="activeUsers"/>

<fmt:message key="activeUsers.message"/>

<div class="separator"></div>

<button type="button" onclick="location.href='mainMenu.html'">
    <fmt:message key="button.cancel"/>
</button>
    
<display:table name="${userNames}" uid="user" cellspacing="0" cellpadding="0"
    defaultsort="1" class="list activeUserList" pagesize="50" requestURI="" sort="list">
  
    <%-- Table columns --%>
    <display:column property="username" width="30%" headerClass="sortable"
        titleKey="user.username" sortable="true"/>
    <display:column titleKey="activeUsers.fullName" headerClass="sortable"
        sortable="true">
        ${user.firstName} ${user.lastName}
        <c:if test="${not empty user.email}">
        <a href="mailto:${user.email}">
            <img src="<c:url value="/images/iconEmail.gif"/>" 
                alt="<fmt:message key="icon.email"/>" class="icon"/></a>
        </c:if>
    </display:column>
        
    <display:setProperty name="paging.banner.item_name" value="user" />
    <display:setProperty name="paging.banner.items_name" value="users" />
</display:table>

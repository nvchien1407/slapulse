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

<head>
    <title><fmt:message key="userProfile.title"/></title>
    <content tag="heading"><fmt:message key="userProfile.heading"/></content>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>

<!-- You could also add a message to the default decorator and not use this page. -->
<c:choose>
    <c:when test="${param.from == 'list'}">
        <fmt:message key="userProfile.admin.message"/>
    </c:when>
    <c:otherwise>
        <fmt:message key="userProfile.message"/>
    </c:otherwise>
</c:choose>

<div class="separator"></div>

<jsp:include page="/WEB-INF/pages/userForm.jsp"/>

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

<title><fmt:message key="auditDocList.title"/></title>
<content tag="heading"><fmt:message key="auditDocList.heading"/></content>

<c:set var="buttons">
    <button type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editAuditDoc.html"/>'">
        <fmt:message key="button.add"/>
    </button>

    <button type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'">
        <fmt:message key="button.cancel"/>
    </button>
</c:set>
<%@ include file="/common/messages.jsp"%>

<c:out value="${buttons}" escapeXml="false"/>

<display:table name="${auditDocList}" cellspacing="0" cellpadding="0"
    uid="auditDocList" pagesize="25" class="list" 
    export="true" requestURI="" sort="list" defaultsort="1">

    <display:column property="id" sortable="true" headerClass="sortable"
        url="/editAuditDoc.html" paramId="id" paramProperty="id"
        titleKey="auditDoc.id"/>
    <display:column property="auditDocUrl" sortable="true" headerClass="sortable"
         titleKey="auditDoc.auditDocUrl"/>
    <display:setProperty name="paging.banner.item_name" value="auditDoc"/>
    <display:setProperty name="paging.banner.items_name" value="auditDocs"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<script type="text/javascript">
highlightTableRows("auditDocList");
</script>

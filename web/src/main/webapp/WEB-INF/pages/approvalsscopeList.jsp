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

<title><fmt:message key="approvalsScopeList.title"/></title>
<content tag="heading"><fmt:message key="approvalsScopeList.heading"/></content>

<c:set var="buttons">
    <button type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editApprovalsScope.html"/>'">
        <fmt:message key="button.add"/>
    </button>

    <button type="button" onclick="location.href='<c:url value="/supplierSearch.html"/>'">
        <fmt:message key="button.cancel"/>
    </button>
</c:set>
<%@ include file="/common/messages.jsp"%>

<c:out value="${buttons}" escapeXml="false"/>

<display:table name="${approvalsScopeList}" cellspacing="0" cellpadding="0"
    uid="approvalsScopeList" pagesize="25" class="list" 
    export="true" requestURI="" sort="list" defaultsort="1">

    <display:column property="id" sortable="true" headerClass="sortable"
        url="/editApprovalsScope.html" paramId="id" paramProperty="id"
        titleKey="approvalsScope.id"/>
    <display:column property="approvalsScopeName" sortable="true" headerClass="sortable"
         titleKey="approvalsScope.approvalsScopeName"/>
    <display:column property="type" sortable="true" headerClass="sortable"
         titleKey="approvalsScope.type"/>
    <display:setProperty name="paging.banner.item_name" value="approvalsScope"/>
    <display:setProperty name="paging.banner.items_name" value="approvalsScopes"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<script type="text/javascript">
highlightTableRows("approvalsScopeList");
</script>

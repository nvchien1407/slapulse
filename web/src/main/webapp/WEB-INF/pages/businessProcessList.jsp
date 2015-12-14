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

<title><fmt:message key="businessProcessList.title"/></title>
<content tag="heading"><fmt:message key="businessProcessList.heading"/></content>
<%@ include file="/common/messages.jsp"%>

<c:set var="buttons">
    <button type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editBusinessProcess.html"/>'">
        <fmt:message key="button.add"/>
    </button>

    <button type="button" onclick="location.href='<c:url value="/calendar.html"/>'">
        <fmt:message key="button.cancel"/>
    </button>
</c:set>

<display:table name="${businessProcessList}" cellspacing="0" cellpadding="0"
    uid="businessProcessList" pagesize="25" class="list" 
    requestURI="" sort="list" defaultsort="1">

    <display:column property="id" sortable="true" headerClass="sortable"
        url="/editBusinessProcess.html" paramId="id" paramProperty="id"
        titleKey="businessProcess.id"/>
    <display:column property="type.itemName" sortable="true" headerClass="sortable"
         titleKey="businessProcess.type"/>
    <display:column property="name.itemName" sortable="true" headerClass="sortable"
         titleKey="businessProcess.name"/>
    <display:column property="step.itemName" sortable="true" headerClass="sortable"
         titleKey="businessProcess.step"/>
    <display:column property="txn.itemName" sortable="true" headerClass="sortable"
         titleKey="businessProcess.txn"/>
    <display:column property="description" sortable="true" headerClass="sortable"
         titleKey="businessProcess.description"/>
    <display:setProperty name="paging.banner.item_name" value="Business Process"/>
    <display:setProperty name="paging.banner.items_name" value="Business Processes"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<script type="text/javascript">
highlightTableRows("businessProcessList");
</script>

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

<title><fmt:message key="auditorList.title"/></title>
<content tag="heading"><fmt:message key="auditorList.heading"/></content>

<c:set var="buttons">
    <button type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editAuditor.html"/>'">
        <fmt:message key="button.add"/>
    </button>

    <button type="button" onclick="location.href='<c:url value="/supplierSearch.html"/>'">
        <fmt:message key="button.cancel"/>
    </button>
</c:set>
<%@ include file="/common/messages.jsp"%>
<c:out value="${buttons}" escapeXml="false"/>

<display:table name="${auditorList}" cellspacing="0" cellpadding="0"
    uid="auditorList" pagesize="25" class="list" 
    export="true" requestURI="" sort="list" defaultsort="1">

    <display:column property="id" sortable="true" headerClass="sortable"
        url="/editAuditor.html" paramId="id" paramProperty="id"
        titleKey="auditor.id"/>
    <display:column property="auditorName" sortable="true" headerClass="sortable"
         titleKey="auditor.auditorName"/>
    <display:column property="phoneNo" sortable="true" headerClass="sortable"
         titleKey="auditor.phoneNo"/>
    <display:column property="faxNo" sortable="true" headerClass="sortable"
         titleKey="auditor.faxNo"/>
    <display:column property="email" sortable="true" headerClass="sortable"
         titleKey="auditor.email"/>
    <display:column property="mobileNo" sortable="true" headerClass="sortable"
         titleKey="auditor.mobileNo"/>
    <display:setProperty name="paging.banner.item_name" value="auditor"/>
    <display:setProperty name="paging.banner.items_name" value="auditors"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<script type="text/javascript">
highlightTableRows("auditorList");
</script>

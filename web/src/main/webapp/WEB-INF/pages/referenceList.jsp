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

<title><fmt:message key="referenceList.title"/></title>
<content tag="heading"><fmt:message key="referenceList.heading"/></content>
<%@ include file="/common/messages.jsp"%>

<c:set var="buttons">
    <button type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editReference.html?isGroup=true"/>'">
        <fmt:message key="button.addGroup"/>
    </button>

	<button type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editReference.html"/>'">
        <fmt:message key="button.addItem"/>
    </button>

	<button type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editReference.html?editGroup=true"/>'">
        <fmt:message key="button.editGroup"/>
    </button>

    <button type="button" onclick="location.href='<c:url value="/calendar.html"/>'">
        <fmt:message key="button.cancel"/>
    </button>
</c:set>

<c:out value="${buttons}" escapeXml="false"/>

<display:table name="${referenceList}" cellspacing="0" cellpadding="0"
    uid="referenceList" pagesize="25" class="list" 
    export="false" requestURI="" sort="list" defaultsort="1">

    <display:column property="id" sortable="true" headerClass="sortable"
        url="/editReference.html" paramId="id" paramProperty="id"
        titleKey="reference.id"/>
    <display:column property="subGroupName" sortable="true" headerClass="sortable"
         titleKey="reference.subGroupName"/>
    <display:column property="itemName" sortable="true" headerClass="sortable"
         titleKey="reference.itemName"/>
    <display:column property="note" sortable="true" headerClass="sortable"
         titleKey="reference.note"/>
    <display:setProperty name="paging.banner.item_name" value="Reference"/>
    <display:setProperty name="paging.banner.items_name" value="References"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<script type="text/javascript">
highlightTableRows("referenceList");
</script>

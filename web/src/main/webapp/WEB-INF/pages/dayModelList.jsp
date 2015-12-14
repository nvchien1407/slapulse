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

<title><fmt:message key="dayModelList.title"/></title>
<content tag="heading"><fmt:message key="dayModelList.heading"/></content>

<c:set var="buttons">
    <button type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editDayModel.html"/>'">
        <fmt:message key="button.add"/>
    </button>

    <button type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'">
        <fmt:message key="button.cancel"/>
    </button>
</c:set>

<c:out value="${buttons}" escapeXml="false"/>

<display:table name="${dayModelList}" cellspacing="0" cellpadding="0"
    uid="dayModelList" pagesize="25" class="list" 
    export="false" requestURI="" sort="list">

    <display:column property="nonWorkingDay" sortable="true" headerClass="sortable"
         titleKey="dayModel.nonWorkingDay"/>
    <display:column property="id" sortable="true" headerClass="sortable"
        url="/editDayModel.html" paramId="id" paramProperty="id"
        titleKey="dayModel.id"/>
    <display:column property="defaultDay" sortable="true" headerClass="sortable"
         titleKey="dayModel.defaultDay"/>
    <display:setProperty name="paging.banner.item_name" value="dayModel"/>
    <display:setProperty name="paging.banner.items_name" value="dayModels"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<script type="text/javascript">
highlightTableRows("dayModelList");
</script>

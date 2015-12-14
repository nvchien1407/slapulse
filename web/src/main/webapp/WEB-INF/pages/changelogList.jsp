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

<title><fmt:message key="changeLogList.title"/></title>
<content tag="heading"><fmt:message key="changeLogList.heading"/></content>

<c:set var="calendarIcon">
   <c:url value="/images/scw.gif"/>
</c:set>

<c:set var="buttons">
    <button type="button" onclick="location.href='<c:url value="/"/>'">
        <fmt:message key="button.cancel"/>
    </button>
</c:set>

<c:out value="${buttons}" escapeXml="false"/>

<!-- 
<div>
<form method="get">
	<table>
		<tr>
			<td>
            	<Calendar:label key="changeLog.showSince"/>
            </td>
			<td>
				<input type="text" name="operateTime" value="${operateTime}" id="operateTime"/>
				<img id="imgOperateTime" name="operateTime" src="${calendarIcon}" width="15" height="15"  onclick="displayCalendar(this)" onmouseover="this.width+=1;this.height+=1;" onmouseout="this.width-=1;this.height-=1;"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
		        <fmt:message key="changeLog.showSinceInstruction"/>
            </td>
		</tr>
		<tr>
			<td>
                <input type="submit" name="search" value="Search"/>
            </td>
		</tr>
	</table>
</form>
</div>
 -->
 
<display:table name="${changeLogList}" cellspacing="0" cellpadding="0"
    uid="changeLogList" pagesize="100" class="list" 
    export="true" requestURI="" sort="list" defaultsort="1">

    <display:column property="id" sortable="true" headerClass="sortable"
        titleKey="changeLog.id"/>
    <display:column property="operateType" sortable="true" headerClass="sortable"
         titleKey="changeLog.operateType"/>
    <display:column property="userId" sortable="true" headerClass="sortable"
         titleKey="changeLog.userId"/>
    <display:column property="operateTime" sortable="true" headerClass="sortable"
         titleKey="changeLog.operateTime"/>
    <display:column property="tableName" sortable="true" headerClass="sortable"
         titleKey="changeLog.tableName"/>
    <display:column property="entityId" sortable="true" headerClass="sortable"
         titleKey="changeLog.entityId"/>
    <display:column property="changes" sortable="true" headerClass="sortable"
         titleKey="changeLog.changes"/>
    <display:setProperty name="paging.banner.item_name" value="Change log"/>
    <display:setProperty name="paging.banner.items_name" value="Change logs"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<script type="text/javascript">
highlightTableRows("changeLogList");
</script>

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
<%@ include file="/common/messages.jsp"%>
<title><fmt:message key="defaultWeekList.title"/></title>
<content tag="heading"><fmt:message key="defaultWeekList.heading"/></content>

<c:set var="buttons">
    <button type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editDefaultWeek.html"/>'">
        <fmt:message key="button.add"/>
    </button>

    <button type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'">
        <fmt:message key="button.cancel"/>
    </button>
</c:set>
<c:url value="/defaultWeeks.html" var="weekUrl"/>

<script type="text/javascript">
	function go(list)
	{
		var regionId = list.options[list.selectedIndex].value;
		location.href = '${weekUrl}?region=' + regionId;
	}
</script>

<c:choose>
	<c:when test='${ empty param.region}'>
       <SELECT NAME="region" onChange="go(this)">
			<OPTION>Select Calendar
	        <c:forEach var="day" items="${regions}">
				<OPTION VALUE="${day.region.id}" >${day.region.itemName}
	        </c:forEach>
		</SELECT>		
    </c:when>
<c:otherwise>
	<table class="detail">
	    <tr>
	        <th>
	            <Calendar:label key="calendar.title"/>
	        </th>
	        <td align="left">
				${defaultWeekList.region.itemName}
				<input type="hidden" name="region" value="${defaultWeekList.region.id}" />
	        </td>
	    </tr>
	    <tr>
	    	<td colspan=2>

<display:table name="${defaultWeekList.weekDays}" cellspacing="0" cellpadding="0"
    uid="defaultWeekList" pagesize="25" class="list" decorator="com.renewtek.webapp.action.CalendarDateWrapper"
    export="false" requestURI="" sort="list">

    <display:column property="link" sortable="true" headerClass="sortable"
         titleKey="defaultWeek.day"/>
    <display:column property="nonWorkingDay" sortable="true" headerClass="sortable"
         titleKey="defaultWeek.nonWorkingDay"/>
    <display:setProperty name="paging.banner.item_name" value="week day"/>
    <display:setProperty name="paging.banner.items_name" value="week days"/>
</display:table>
<p>
<c:url var="defaultDayURL" value="/editDayModel.html">
	<c:param name="name" value="${defaultWeekList.region.itemName}"/>
</c:url> 
<c:set var="buttons">
    <button type="button" style="margin-right: 5px"
        onclick="location.href='${defaultDayURL}'">
        <fmt:message key="button.updateDefaultDay"/>
    </button>
</c:set>
	<c:out value="${buttons}" escapeXml="false"/>

</p>
</td>
</tr>
</table>
<script type="text/javascript">
highlightTableRows("defaultWeekList");
</script>
</c:otherwise>
</c:choose>

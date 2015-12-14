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
<title><fmt:message key="calendar.title"/></title>
<content tag="heading"><fmt:message key="calendar.title"/></content>
<table>
<c:url value="/calendar.html" var="nextMonthUrl">
	<c:param name="calendarDate" value="${calendarDays.nextMonth}"/>
	<c:param name="region" value="${calendarDays.region.id}"/>
</c:url>
<c:url value="/calendar.html" var="prevMonthUrl">
	<c:param name="calendarDate" value="${calendarDays.previousMonth}"/>
	<c:param name="region" value="${calendarDays.region.id}"/>
</c:url>
<c:url value="/calendar.html" var="prevYearUrl">
	<c:param name="calendarDate" value="${prevYear}"/>
	<c:param name="region" value="${calendarDays.region.id}"/>
</c:url>
<c:url value="/calendar.html" var="nextYearUrl">
	<c:param name="calendarDate" value="${nextYear}"/>
	<c:param name="region" value="${calendarDays.region.id}"/>
</c:url>
<c:url value="/editCalendar.html" var="calendarUrl"/>
<script type="text/javascript">
	function go(list)
	{
		var regionId = list.options[list.selectedIndex].value;
		location.href = "${calendarUrl}?region=" + regionId;
	}
</script>
<%@ include file="/common/messages.jsp"%>

	<tr>
		<td>
       <SELECT NAME="region" onChange="go(this)">
			<OPTION>Select Calendar
	        <c:forEach var="calendar" items="${regions}">
				<OPTION VALUE="${calendar.id}" >${calendar.itemName}
	        </c:forEach>
		</SELECT>		
		</td>
	</tr>
</table>


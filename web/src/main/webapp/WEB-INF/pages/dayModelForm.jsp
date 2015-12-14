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

<title><fmt:message key="defaultWeekDetail.title"/></title>
<content tag="heading"><fmt:message key="defaultWeekDetail.heading"/></content>
<%@ include file="/common/messages.jsp"%>

<table class="detail">
    <tr>
        <th>
            <Calendar:label key="calendar.title"/>
        </th>
        <td>
        	${defaultDay.region.itemName}
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="workHourRange.title"/>
        </th>
        <td>
	<c:url value="/editWorkHourRange.html" var="editWorkHoursUrl">
		<c:param name="dayId" value="${defaultDay.id}"/>
		<c:param name="dayType" value="DefaultDay"/>		
	</c:url>
	        
	<c:set var="buttons">
	    <button type="button" style="margin-right: 5px" ID="addBtn"
	        onclick="location.href='${editWorkHoursUrl}'">
	        <fmt:message key="button.add"/>
	    </button>
	</c:set>
	
	<c:out value="${buttons}" escapeXml="false"/>
	
	<display:table name="${defaultDay.workHourRanges}" cellspacing="0" cellpadding="0"
	    uid="calendarDateList" pagesize="25" class="list" sort="list" defaultorder="ascending" defaultsort="1"
	    export="false" requestURI="" decorator="com.renewtek.webapp.action.WorkHourWrappper">
	
	    <display:column property="fromTime" sortable="true" headerClass="sortable"
	         titleKey="workHourRange.fromTime"/>
	    <display:column property="toTime" sortable="true" headerClass="sortable"
		     href="${editWorkHoursUrl}" paramId="id" paramProperty="id"
	         titleKey="workHourRange.toTime"/>
	</display:table>
	</td>
    </tr>

    <tr>
        <td></td>
        <td class="buttonBar">            
		<c:url value="/calendar.html" var="calendarUrl"/>
	        
	    <button type="button" style="margin-right: 5px" ID="addBtn"
	        onclick="location.href='${calendarUrl}'">
	        <fmt:message key="button.save"/>
	    </button>
	
        </td>
    </tr>
</table>


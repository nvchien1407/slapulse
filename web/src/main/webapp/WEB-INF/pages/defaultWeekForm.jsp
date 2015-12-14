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

<title><fmt:message key="defaultWeekDetail.title"/></title>
<content tag="heading"><fmt:message key="defaultWeekDetail.heading"/></content>
<form method="post" action="<c:url value="/editDefaultWeek.html"/>" id="defaultWeekForm"  onsubmit="return validateDefaultWeek(this)">
<table class="detail">
    <tr>
        <th>
            <Calendar:label key="defaultWeek.day"/>
        </th>
        <td>
        	${defaultWeek.day}
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="calendar.title"/>
        </th>
        <td>
        	${defaultWeek.region.itemName}
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="calendarDate.nonWorkingDay"/>
        </th>
        <td>
		<spring:bind path="defaultWeek.nonWorkingDay">
     		<input name="${status.expression}" ID="${status.expression}" type="checkbox" <c:if test="${status.value}">checked="checked"</c:if> value = "true" />
			<input type="hidden" name="_${status.expression}">     		
		</spring:bind>
		</td>
    </tr>
<c:if test="${!defaultWeek.nonWorkingDay}">        
    <tr>
        <th>
            <Calendar:label key="workHourRange.title"/>
        </th>
        <td>
	<c:url value="/editWorkHourRange.html" var="editWorkHoursUrl">
		<c:param name="dayId" value="${defaultWeek.id}"/>
		<c:param name="dayType" value="DefaultWeekDay"/>		
	</c:url>
	        
	<c:set var="buttons">
	    <button type="button" style="margin-right: 5px" ID="addBtn"
	        onclick="location.href='${editWorkHoursUrl}'">
	        <fmt:message key="button.add"/>
	    </button>
	</c:set>
	<c:choose>
		<c:when test = "${! empty defaultWeek.id}">
			<c:out value="${buttons}" escapeXml="false"/>
			<display:table name="${defaultWeek.workHourRanges}" cellspacing="0" cellpadding="0"
			    uid="calendarDateList" pagesize="25" class="list" sort="list" defaultorder="ascending" defaultsort="1"
			    export="false" requestURI="" decorator="com.renewtek.webapp.action.WorkHourWrappper">
			
			    <display:column property="fromTime" sortable="true" headerClass="sortable"
			         titleKey="workHourRange.fromTime"/>
			    <display:column property="toTime" sortable="true" headerClass="sortable"
				     href="${editWorkHoursUrl}" paramId="id" paramProperty="id"
			         titleKey="workHourRange.toTime"/>
			</display:table>
		</c:when>
		<c:otherwise>
			<display:table name="${defaultWeek.workHourRanges}" cellspacing="0" cellpadding="0"
			    uid="calendarDateList" pagesize="25" class="list" sort="list" defaultorder="ascending" defaultsort="1"
			    export="false" requestURI="" decorator="com.renewtek.webapp.action.WorkHourWrappper">
			
			    <display:column property="fromTime" sortable="true" headerClass="sortable"
			         titleKey="workHourRange.fromTime"/>
			    <display:column property="toTime" sortable="true" headerClass="sortable"
			         titleKey="workHourRange.toTime"/>
			</display:table>
		</c:otherwise>
	</c:choose>
	</td>
    </tr>
</c:if>

<c:choose>
	<c:when test="${! empty defaultWeek.id}">
		<input type="hidden" name="id" value="${defaultWeek.id}"/> 
	</c:when>
	<c:otherwise>
		<input type="hidden" name="region" value="${defaultWeek.region.id}"/> 
		<input type="hidden" name="day" value="${defaultWeek.day}"/> 
	</c:otherwise>
</c:choose>
    <tr>
        <td></td>
        <td class="buttonBar">            
            <input type="submit" class="button" name="save" 
                onclick="bCancel=false" value="<fmt:message key="button.save"/>" />
            <c:if test="${!empty defaultWeek.id}">
	            <input type="submit" class="button" name="delete"
	                onclick="bCancel=true;return confirmDelete('CalendarDate')" 
	                value="<fmt:message key="button.delete"/>" />
             </c:if>
            <input type="submit" class="button" name="cancelBtn" onclick="bCancel=true"
                value="<fmt:message key="button.cancel"/>" />        
        </td>
    </tr>
</table>
</form>

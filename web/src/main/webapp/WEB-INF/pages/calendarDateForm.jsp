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

<title><fmt:message key="calendarDateDetail.title"/></title>
<content tag="heading"><fmt:message key="calendarDateDetail.heading"/></content>
<c:url value="/editDefaultWeek.html" var="weekFormUrl"/>
<script type="text/javascript">
	function processNonWorkingDay()
	{
		nonwork=document.forms[0].nonWorkingDay;
		if (nonwork.checked)
		{
			document.getElementById("fromTime").disabled=true;
			document.getElementById("toTime").disabled=true;
		}else{
			document.getElementById("fromTime").disabled=false;
			document.getElementById("toTime").disabled=false;
		}	
	}
	if (window.addEventListener)
		window.addEventListener("load", processNonWorkingDay, false)
	else if (window.attachEvent)
		window.attachEvent("onload", processNonWorkingDay)
	else if (document.getElementById)
		window.onload=processNonWorkingDay
	function go(list)
	{
		var regionId = list.options[list.selectedIndex].value;
		location.href = '${weekFormUrl}?region=' + regionId;
	}
</script>

<spring:bind path="calendarDate.*">
    <c:if test="${not empty status.errorMessages}">
    <div class="error">	
        <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon" />
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    </c:if>
</spring:bind>
<%@ include file="/common/messages.jsp"%>

<form method="post" action="<c:url value="/editCalendarDate.html"/>" id="calendarDateForm"  onsubmit="return validateCalendarDate(this)">
<table class="detail">
    <tr>
        <th>
            <Calendar:label key="calendarDate.name"/>
        </th>
        <td>
            <spring:bind path="calendarDate.name">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" />
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="calendarDate.dateInfo"/>
        </th>
        <td>
           	${calendarDate.dateInfoString}
        </td>
    </tr>

    <tr>
        <th>
            <Calendar:label key="calendar.title"/>
        </th>
        <td>
           	${calendarDate.region.itemName}
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="calendarDate.description"/>
        </th>
        <td>
		<spring:bind path="calendarDate.description">
     		<textarea name="${status.expression}" ID="${status.expression}">${status.value}</textarea>
		</spring:bind>
		</td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="calendarDate.nonWorkingDay"/>
        </th>
        <td>
		<spring:bind path="calendarDate.nonWorkingDay">
     		<input name="${status.expression}" ID="${status.expression}" type="checkbox" <c:if test="${status.value}">checked="checked"</c:if> 
     			onClick="processNonWorkingDay(this)" value = "true" />
			<input type="hidden" name="_${status.expression}">     		
		</spring:bind>
		</td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="workHourRange.title"/><br>
        </th>
        <td>
        	<table>
        		<tr>
					<spring:bind path="calendarDate.fromTime">
				        <td width="10">
				     		<input name="${status.expression}" ID="${status.expression}" type="text" size="3" value="${status.value}"/>
				        </td>
				        <td>
				     		<c:if test="${not empty status.errorMessages}">					     		
					            <img src="<c:url value="/images/iconWarning.gif"/>"
					                alt="<fmt:message key="icon.warning"/>" class="icon" />
					        </c:if>
				        </td>
					</spring:bind>
					<spring:bind path="calendarDate.toTime">
				        <td width="10">
				     		<input name="${status.expression}" ID="${status.expression}" type="text" size="3" value="${status.value}"/>
				        </td>
				        <td>
				     		<c:if test="${not empty status.errorMessages}">					     		
					            <img src="<c:url value="/images/iconWarning.gif"/>"
					                alt="<fmt:message key="icon.warning"/>" class="icon" />
					        </c:if>
				        </td>
					</spring:bind>
        		</tr>        		
        	</table>
		</td>
    </tr>
<input type="hidden" name="region" value="${calendarDate.region.id}"/> 
<input type="hidden" name="date" value="${calendarDate.dateInfoString}"/> 
			<c:url value="/editCalendar.html" var="calendarUrl">
				<c:param name="calendarDate" value="${calendarDate.dateInfoString}"/>
				<c:param name="region" value="${calendarDate.region.id}"/>
			</c:url>
             
			<c:set var="cancelButton">
			    <button type="button" style="margin-right: 5px" ID="back"
			        onclick="location.href='${calendarUrl}'">
			        <fmt:message key="button.back"/>
			    </button>
			</c:set>

    <tr>
        <td></td>
        <td class="buttonBar">            
            <input type="submit" class="button" name="save" 
                onclick="bCancel=false" value="<fmt:message key="button.save"/>" />
            <c:if test="${!empty calendarDate.id}">
	            <input type="submit" class="button" name="delete"
	                onclick="bCancel=true;return confirmDelete('CalendarDate')" 
	                value="<fmt:message key="button.delete"/>" />
             </c:if>
			<c:out value="${cancelButton}" escapeXml="false"/>	             
        </td>
    </tr>
</table>
</form>

<v:javascript formName="calendarDate" cdata="false"
    dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" 
    src="<c:url value="/scripts/validator.jsp"/>"></script>


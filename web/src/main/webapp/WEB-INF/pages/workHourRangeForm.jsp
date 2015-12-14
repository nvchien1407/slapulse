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
<title><fmt:message key="workHourRangeDetail.title"/></title>
<content tag="heading"><fmt:message key="workHourRangeDetail.heading"/></content>

<spring:bind path="workHourRange.*">
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

<form method="post" action="<c:url value="/editWorkHourRange.html"/>" id="workHourRangeForm"
    onsubmit="return validateWorkHourRange(this)">
<table class="detail">
    	<c:if test="${! empty calendarDate}">
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
	            <Calendar:label key="calendarDate.region"/>
	        </th>
	        <td>        
				${calendarDate.region.itemName}
	        </td>
	    </tr>	        
    	</c:if>
    	<c:if test="${! empty weekDay}">
	    <tr>
	        <th>
	            <Calendar:label key="defaultWeek.day"/>
	        </th>
	        <td>        
				${weekDay.day}
	        </td>
	    </tr>	        
	    <tr>
	        <th>
	            <Calendar:label key="calendarDate.region"/>
	        </th>
	        <td>        
				${weekDay.region.itemName}
	        </td>
	    </tr>	        
    	</c:if>
    <tr>
        <th>
            <Calendar:label key="workHourRange.fromTime"/>
        </th>
        <td>
        <table>
        <tr>
        	<td>Hour</td>
        	<td>Minute</td>
        </tr>
        <tr>
        	<td>
			<spring:bind path="workHourRange.startHour">
		       <SELECT NAME="${status.expression}" ID="${status.expression}">
			        <c:forEach var="hour" items="${hours}">
						<OPTION VALUE="${hour}"
						<c:if test='${status.value == hour}'>
							selected
						</c:if>										
						>${hour}
			        </c:forEach>
				</SELECT>		
			</spring:bind>:		
			</td>
			<td>
			<spring:bind path="workHourRange.startMinute">
		       <SELECT NAME="${status.expression}" ID="${status.expression}">
			        <c:forEach var="minute" items="${minutes}">
						<OPTION VALUE="${minute}"
						<c:if test='${status.value == minute}'>
							selected
						</c:if>										
						>${minute}
			        </c:forEach>
				</SELECT>		
			</spring:bind>
			</td>
		</tr>
		</table>
        </td>
    </tr>

					<input type="hidden" name="id" value="${workHourRange.id}"/> 
					<input type="hidden" name="dayId" value="${param.dayId}"/> 
					<input type="hidden" name="dayType" value="${param.dayType}"/> 

    <tr>
        <th>
            <Calendar:label key="workHourRange.toTime"/>
        </th>
        <td>
        <table>
        <tr>
        	<td>
			<spring:bind path="workHourRange.endHour">
		       <SELECT NAME="${status.expression}" ID="${status.expression}">
			        <c:forEach var="hour" items="${hours}">
						<OPTION VALUE="${hour}"
						<c:if test='${status.value == hour}'>
							selected
						</c:if>										
						>${hour}
			        </c:forEach>
				</SELECT>		
			</spring:bind>:		
			</td>
			<td>
			<spring:bind path="workHourRange.endMinute">
		       <SELECT NAME="${status.expression}" ID="${status.expression}">
			        <c:forEach var="minute" items="${minutes}">
						<OPTION VALUE="${minute}"
						<c:if test='${status.value == minute}'>
							selected
						</c:if>										
						>${minute}
			        </c:forEach>
				</SELECT>		
			</spring:bind>
			</td>
		</tr>
		</table>
        </td>
    </tr>

    <tr>
        <td></td>
        <td class="buttonBar">            
            <input type="submit" class="button" name="save" 
                onclick="bCancel=false" value="<fmt:message key="button.save"/>" />
            <input type="submit" class="button" name="delete"
                onclick="bCancel=true;return confirmDelete('WorkHourRange')" 
                value="<fmt:message key="button.delete"/>" />
        </td>
    </tr>
</table>
</form>

<v:javascript formName="workHourRange" cdata="false"
    dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" 
    src="<c:url value="/scripts/validator.jsp"/>"></script>


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
<script type="text/javascript">
	function commonName(name)
	{
		var i = name.indexOf(".nonWorkingDay");
		var idField = name.substring(0, i);			
		return idField;
	}
	function processNonWorkingDay(chck)
	{
		commonPart = commonName(chck.id);
		if (chck.checked)
		{
			document.getElementById(commonPart + ".fromTime").disabled=true;
			document.getElementById(commonPart + ".toTime").disabled=true;
		}else{
			document.getElementById(commonPart + ".fromTime").disabled=false;
			document.getElementById(commonPart + ".toTime").disabled=false;
		}	
	}
	function preprocess()
	{
		form=document.forms[0]
		for (i=0;i < form.elements.length;++ i)
		{
			id = form.elements[i].id
			if (id.indexOf(".nonWorkingDay") > 0)
			{
				processNonWorkingDay(form.elements[i])
			}
		}
	}
	if (window.addEventListener)
		window.addEventListener("load", preprocess, false)
	else if (window.attachEvent)
		window.attachEvent("onload", preprocess)
	else if (document.getElementById)
		window.onload=preprocess
	
</script>

<title><fmt:message key="calendar.title"/></title>
<content tag="heading"><fmt:message key="calendar.title"/></content>
<table>
<c:url value="/editCalendar.html" var="nextMonthUrl">
	<c:param name="calendarDate" value="${calendarDays.nextMonth}"/>
	<c:if test="${not empty calendarDays.region}">
		<c:param name="region" value="${calendarDays.region.id}"/>
	</c:if>
</c:url>
<c:url value="/editCalendar.html" var="prevMonthUrl">
	<c:param name="calendarDate" value="${calendarDays.previousMonth}"/>
	<c:if test="${not empty calendarDays.region}">
		<c:param name="region" value="${calendarDays.region.id}"/>
	</c:if>
</c:url>
<c:url value="/editCalendar.html" var="prevYearUrl">
	<c:param name="calendarDate" value="${prevYear}"/>
	<c:if test="${not empty calendarDays.region}">
		<c:param name="region" value="${calendarDays.region.id}"/>
	</c:if>
</c:url>
<c:url value="/editCalendar.html" var="nextYearUrl">
	<c:param name="calendarDate" value="${nextYear}"/>
	<c:if test="${not empty calendarDays.region}">
		<c:param name="region" value="${calendarDays.region.id}"/>
	</c:if>
</c:url>
<c:url value="/editCalendar.html" var="calendarUrl"/>
<script type="text/javascript">
	function go(list)
	{
		var regionId = list.options[list.selectedIndex].value;
		location.href = '${calendarUrl}?region=' + regionId;
	}
</script>
<%@ include file="/common/messages.jsp"%>

<c:choose>
	<c:when test='${ empty calendarDays }'>
	<tr>
		<td>
       <SELECT NAME="region" onChange="go(this)">
			<OPTION>Select Calendar
	        <c:forEach var="day" items="${regions}">
				<OPTION VALUE="${day.region.id}" >${day.region.itemName}
	        </c:forEach>
		</SELECT>		
		</td>
	</tr>
    </c:when>
   	<c:otherwise>
	<tr>
		<th colspan=2>
				<c:if test="${not empty calendarDays.region}">
					${calendarDays.region.itemName}
				</c:if>
		</th>
	</tr>
	<tr>
		<td width="180" height="200" valign=top>
			<table>
				<tr>
					<td>
			<table>
				<tr>
					<td><a href='${prevYearUrl}'><<</a></td>
					<td colspan="5" align="center">${calendarDays.year}</td>
					<td><a href='${nextYearUrl}'>>></a></td>
				</tr>
				<tr>
					<td><a href='${prevMonthUrl}'><<</a></td>
					<td colspan="5" align="center">${calendarDays.monthYear}</td>
					<td><a href='${nextMonthUrl}'>>></a></td>
				</tr>
				<tr>
					<c:forEach var="result" items="${calendarDays.weekDays}">
					    <td width=16>${result}</td>
					</c:forEach>
				</tr>
				<c:set var="count" value="0"/>
				<c:forEach var="day" items="${calendarDays.days}">
				<c:url value="/editCalendarDate.html" var="editUrl">
					<c:param name="date" value="${day.dateInfoString}"/>
					<c:param name="region" value="${calendarDays.region.id}"/>
				</c:url>
		
				<c:if test="count == 0">
					<tr>
				</c:if>
		    	<td 
		        <c:choose>
    	    		 <c:when test='${!empty day.id and !day.nonWorkingDay}'>
						class="specialDay"
    		    	</c:when>
		        	<c:otherwise>
		        		<c:choose>		        		
		        		<c:when test='${!empty day.id and day.nonWorkingDay}'>
							class="holiday"		        			
		        		</c:when>
		        		<c:otherwise>
		        			<c:if test="${day.nonWorkingDay}">
								class="defaultHoliday"		        			
							</c:if>
		        		</c:otherwise>
		        		</c:choose>
		        	</c:otherwise>
    			</c:choose>		    
		    	>
		    	<c:choose>
		    		<c:when test="${empty calendarDays.region.id}">
			    		${day.date}
			    	</c:when>
			    	<c:otherwise>
			    		<a href='${editUrl}'>${day.date}</a>
			    	</c:otherwise>
			    </c:choose>
			    </td>
			     <c:choose>
	        		 <c:when test='${count == 6}'>
						<c:set var="count" value="0"/>
						</tr>
	    		    </c:when>
		        	<c:otherwise>
						<c:set var="count" value="${count + 1}"/>
		        	</c:otherwise>
    			</c:choose>
			</c:forEach></tr>				
			</table>
					</td>
				</tr>
				<tr>
					<td>
			<table>
				<tr>
					<td ></td><td>&nbsp;</td>
				</tr>
				<tr>
					<td ></td><td>&nbsp;</td>
				</tr>
				<tr>
					<td width=14 class="defaultHoliday"></td><td><fmt:message key="calendarDate.daytype.nonworkingday"/></td>
				</tr>
				<tr>
					<td width=14 class="holiday"></td><td><fmt:message key="calendarDate.daytype.holiday"/></td>
				</tr>
				<tr>
					<td width=14 class="specialDay"></td><td><fmt:message key="calendarDate.daytype.specialday"/></td>
				</tr>
			</table>
					</td>
				</tr>
			</table>
		</td>
	<td valign="top" cellpadding="5" width="75%">
<c:url value="/editDefaultWeek.html" var="editFormUrl"/>
	<spring:bind path="calendarDays.*">
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
	
	<form method="post" action="<c:url value="/editCalendar.html"/>" id="calendarForm"
	    onsubmit="return validateCalendar(this)">
	<table class="detail">
	    <tr>
	        <th>
	            <Calendar:label key="calendar.name"/>
	        </th>
	        <td>
				<spring:bind path="calendarDays.region.itemName">				
					<input type="text" name="${status.expression}" value="${status.value}" />
				</spring:bind>
				<input type="hidden" name="region" value="${param.region}" />
	        </td>
            <th>
                <Calendar:label key="calendar.timezone"/>
            </th>
	        <td>
             <spring:bind path="calendarDays.region.timezone">
               <select name="${status.expression}">
                   <option value="">Server default TZ</option>
                   <c:forEach var="locationEntry" items="${locationMap}">
                     <option value="${locationEntry.key}" <c:out value="${status.value==locationEntry.key?'selected':''}"/>>
                             ${locationEntry.value}</option>
                   </c:forEach>
               </select>
           </spring:bind>
           </td>
	    </tr>
	    <tr>
	    	<td colspan=2>
				<table>
					<tr>
				        <th>
				            <Calendar:label key="defaultWeek.day"/>
				        </th>				        
				        <th>
				            <Calendar:label key="defaultWeek.nonWorkingDay"/>
				        </th>
					</tr>
					<c:forEach var="day" items="${calendarDays.defaultWeekDays}" varStatus="s">
				
				    <tr>
				        <td>
				            <spring:bind path="calendarDays.defaultWeekDays[${s.index}].day">
					            
								<c:choose>
									<c:when test='${status.value == "Monday"}'>
										<fmt:message key="calendarDate.defaultWeekdays.Monday"/>
								    </c:when>
									<c:when test='${status.value == "Tuesday"}'>
										<fmt:message key="calendarDate.defaultWeekdays.Tuesday"/>
								    </c:when>
									<c:when test='${status.value == "Wednesday"}'>
										<fmt:message key="calendarDate.defaultWeekdays.Wednesday"/>
								    </c:when>
									<c:when test='${status.value == "Thursday"}'>
										<fmt:message key="calendarDate.defaultWeekdays.Thursday"/>
								    </c:when>
									<c:when test='${status.value == "Friday"}'>
										<fmt:message key="calendarDate.defaultWeekdays.Friday"/>
								    </c:when>
									<c:when test='${status.value == "Saturday"}'>
										<fmt:message key="calendarDate.defaultWeekdays.Saturday"/>
								    </c:when>
									<c:when test='${status.value == "Sunday"}'>
										<fmt:message key="calendarDate.defaultWeekdays.Sunday"/>
								    </c:when>
								   	<c:otherwise>
									   	${status.value}
								   	</c:otherwise>
								</c:choose>  
					            
				                <input type="hidden" name="${status.expression}" id="${status.expression}" 
				                    value="${status.value}" READONLY/>
				            </spring:bind>
				        </td>
				        <td>
				            <spring:bind path="calendarDays.defaultWeekDays[${s.index}].nonWorkingDay">
					     		<input name="${status.expression}" ID="${status.expression}" type="checkbox" <c:if test="${status.value}">checked="checked"</c:if>  onClick="processNonWorkingDay(this)" value = "true"/>
								<input type="hidden" name="_${status.expression}">     		
				                <span class="fieldError">${status.errorMessage}</span>
				            </spring:bind>
				        </td>
				            <spring:bind path="calendarDays.defaultWeekDays[${s.index}].fromTime">
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
				            <spring:bind path="calendarDays.defaultWeekDays[${s.index}].toTime">
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
				        <td width="10">
						<spring:bind path="calendarDays.defaultWeekDays[${s.index}].id">
							<input type="hidden" name="${status.expression}" value="${status.value}"/> 
			                <span class="fieldError">${status.errorMessage}</span>
						</spring:bind>
				        </td>
				    </tr>
				 </c:forEach>
					
				</table>
	    	</td>
	    </tr>
	
	    <tr>
	        <td></td>
	        <td class="buttonBar">            
	            <input type="submit" class="button" name="save" 
	                onclick="bCancel=false" value="<fmt:message key="button.save"/>" />
	        </td>
	    </tr>
	</table>
	</form>
<v:javascript formName="defaultWeek" cdata="false"
    dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" 
    src="<c:url value="/scripts/validator.jsp"/>"></script>
	
	
	</td>
	</tr>
	<tr>
		<td colspan=2>
		<td>	
	</tr>
	<tr>
		<td colspan=2>
		<c:set var="buttons">
		    <button type="button" style="margin-right: 5px"
			onclick="location.href='<c:url value="/editSupplier.html"/>'">
			<fmt:message key="button.add"/>
		    </button>

		    <button type="button" onclick="location.href='<c:url value="/"/>'">
			<fmt:message key="button.cancel"/>
		    </button>
		</c:set>

		<display:table name="${calendarDays.exceptionalDays}" cellspacing="0" cellpadding="0"
		    uid="exceptionDayList" pagesize="25" class="list" 
		    export="false" requestURI="" sort="list" defaultsort="1" 
		    decorator="com.renewtek.webapp.action.CalendarDateWrapper">	
			<display:column property="link" sortable="true" headerClass="sortable" 
		 titleKey="calendarDate.name"/>
		    <display:column property="dateInfoString"
			 sortable="true" headerClass="sortable"
			 titleKey="calendarDate.dateInfo"/>
		    <display:column property="nonWorkingDay" 
			 sortable="true" headerClass="sortable"
			 titleKey="calendarDate.nonWorkingDay"/>
		    <display:column property="workHours" 
			 sortable="true" headerClass="sortable"
			 titleKey="calendarDate.workHours"/>
		    <display:setProperty name="paging.banner.all_items_found"><fmt:message key="paging.banner.allitemsfound"/></display:setProperty>
		    <display:setProperty name="paging.banner.item_name"><fmt:message key="paging.banner.itemname"/></display:setProperty>
		    <display:setProperty name="paging.banner.items_name"><fmt:message key="paging.banner.itemsname"/></display:setProperty>
		</display:table>

		<script type="text/javascript">
		highlightTableRows("exceptionDayList");
		</script>

		
		</td>		
	<tr>
   	</c:otherwise>
</c:choose>  
</table>


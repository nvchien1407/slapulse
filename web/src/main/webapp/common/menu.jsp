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

<c:set var="servletPath" value="<%=request.getServletPath()%>"/>

<c:set var="editCalendar">
   ${(servletPath=='/editCalendar.html' || servletPath=='/editCalendarDate.html')?'selected':''}
</c:set>
<c:set var="calendar">
   ${(servletPath=='/calendar.html')?'selected':''}
</c:set>
<c:set var="references">
   ${(servletPath=='/references.html' || servletPath=='/editReference.html')?'selected':''}
</c:set>
<c:set var="serviceLevelAgreements">
   ${(servletPath=='/serviceLevelAgreements.html' || servletPath=='/editServiceLevelAgreement.html')?'selected':''}
</c:set>
<c:set var="businessProcesss">
   ${(servletPath=='/businessProcesss.html' || servletPath=='/editBusinessProcess.html')?'selected':''}
</c:set>
<c:set var="importExport">
   ${(servletPath=='/importExport.html' )?'selected':''}
</c:set>
<div class="category">
   <a href="editCalendar.html" class="${editCalendar}"><fmt:message key="menu.calendar.CreateCalendar"/></a>
   <a href="calendar.html" class="${calendar}"><fmt:message key="menu.calendar.CalendarMaintenance"/></a>
   <a href="references.html" class="${references}"><fmt:message key="menu.calendar.ReferenceData"/></a>
   <a href="serviceLevelAgreements.html" class="${serviceLevelAgreements}"><fmt:message key="menu.calendar.SLADefinition"/></a>
   <a href="businessProcesss.html" class="${businessProcesss}"><fmt:message key="menu.calendar.BPM"/></a>
   <a href="importExport.html" class="${importExport}"><fmt:message key="menu.importExport"/></a>
</div>


	
	

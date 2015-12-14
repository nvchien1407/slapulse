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

<title><fmt:message key="changeLogDetail.title"/></title>
<content tag="heading"><fmt:message key="changeLogDetail.heading"/></content>

<spring:bind path="changeLog.*">
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

<form method="post" action="<c:url value="/editChangeLog.html"/>" id="changeLogForm">
<table class="detail">

<spring:bind path="changeLog.id">
<input type="hidden" name="${status.expression}" value="${status.value}"/> 
</spring:bind>

    <tr>
        <th>
            <Calendar:label key="changeLog.userId"/>
        </th>
        <td>
            <spring:bind path="changeLog.userId">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" DISABLED/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
     </tr>

     <tr>
        <th>
            <Calendar:label key="changeLog.tableName"/>
        </th>
        <td>
            <spring:bind path="changeLog.tableName">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" DISABLED/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th>
            <Calendar:label key="changeLog.entityId"/>
        </th>
        <td>
            <spring:bind path="changeLog.entityId">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" DISABLED/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th>
            <Calendar:label key="changeLog.operateType"/>
        </th>
        <td>
            <spring:bind path="changeLog.operateType">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" DISABLED/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th>
            <Calendar:label key="changeLog.operateTime"/>
        </th>
        <td>
            <spring:bind path="changeLog.operateTime">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" DISABLED/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th>
            <Calendar:label key="changeLog.changes"/>
        </th>
        <td>
            <spring:bind path="changeLog.changes">
                <textarea rows=15 cols=80 name="${status.expression}" id="${status.expression}" DISABLED
                    > ${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <td></td>
        <td class="buttonBar">            
		    <button type="button" onclick="location.href='<c:url value="/changeLogs.html"/>'">
		        <fmt:message key="button.cancel"/>
		    </button>
                
        </td>
    </tr>
</table>
</form>



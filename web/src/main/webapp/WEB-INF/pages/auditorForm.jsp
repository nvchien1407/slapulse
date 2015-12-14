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

<title><fmt:message key="auditorDetail.title"/></title>
<content tag="heading"><fmt:message key="auditorDetail.heading"/></content>

<spring:bind path="auditor.*">
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

<form method="post" action="<c:url value="/editAuditor.html"/>" id="auditorForm"
    onsubmit="return validateAuditor(this)">
<table class="detail">

<spring:bind path="auditor.id">
<input type="hidden" name="${status.expression}" value="${status.value}"/> 
</spring:bind>

    <tr>
        <th align="left">
            <Calendar:label key="auditor.auditorName"/>
        </th>
        <td>
            <spring:bind path="auditor.auditorName">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" maxlength="32"/>*
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th align="left">
            <Calendar:label key="auditor.phoneNo"/>
        </th>
        <td>
            <spring:bind path="auditor.phoneNo">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" maxlength="16"/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th align="left">
            <Calendar:label key="auditor.faxNo"/>
        </th>
        <td>
            <spring:bind path="auditor.faxNo">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" maxlength="16"/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th align="left">
            <Calendar:label key="auditor.email"/>
        </th>
        <td>
            <spring:bind path="auditor.email">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}"  maxlength="64"/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th align="left">
            <Calendar:label key="auditor.mobileNo"/>
        </th>
        <td>
            <spring:bind path="auditor.mobileNo">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}"  maxlength="16"/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <th align="left">
        </th>
        <td><p></p>
        </td>
    </tr>
    <tr>
        <th align="left">
            <Calendar:label key="auditor.supplierAudits"/>
        </th>
        <td>
        <c:if test='${auditor.id != null}'>	    						        
				<c:url value="/supplierAudits.html" var="auditUrl">
					<c:param name="auditor_id" value="${auditor.id}"/>
				</c:url>
				<a href="${auditUrl}"><fmt:message key="supplierAuditList.title"/></a>
                <span class="fieldError">${status.errorMessage}</span>
         </c:if>
        </td>
    </tr>
    <tr>
        <th align="left">
        </th>
        <td><p></p>
        </td>
    </tr>

    <tr>
        <td></td>
        <td class="buttonBar">            
            <input type="submit" class="button" name="save" 
                onclick="bCancel=false" value="<fmt:message key="button.save"/>" />
		    <button type="button" onclick="location.href='<c:url value="/auditors.html"/>'">
		        <fmt:message key="button.cancel"/>
		    </button>
        </td>
    </tr>
</table>
</form>



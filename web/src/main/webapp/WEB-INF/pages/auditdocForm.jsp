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

<title><fmt:message key="auditDocDetail.title"/></title>
<content tag="heading"><fmt:message key="auditDocDetail.heading"/></content>

<spring:bind path="auditDoc.*">
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

<form method="post" action="<c:url value="/editAuditDoc.html"/>" id="auditDocForm"
    onsubmit="return validateAuditDoc(this)">
<table class="detail">

<spring:bind path="auditDoc.id">
<input type="hidden" name="${status.expression}" value="${status.value}"/> 
</spring:bind>

    <tr>
        <th align="left">
            <Calendar:label key="auditDoc.auditDocUrl"/>
        </th>
        <td>
            <spring:bind path="auditDoc.auditDocUrl">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" maxlength="255"/>*
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th align="left">
            <Calendar:label key="auditDoc.supplierAudit"/>
        </th>
        <td>
            <spring:bind path="auditDoc.supplierAudit">
                <!--select name="${status.expression}">
					<option>Select Supplier Audit</option>
			        <c:forEach var="audit" items="${audits}">
						<option value="${audit.id}" <c:if test="${audit.id eq auditDoc.supplierAudit.id}">selected</c:if>>${audit.supplier.companyName} - ${audit.dueDate} 
			        </c:forEach>
				</select-->
				
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <td></td>
        <td class="buttonBar">            
            <input type="submit" class="button" name="save" 
                onclick="bCancel=false" value="<fmt:message key="button.save"/>" />
            <input type="submit" class="button" name="delete"
                onclick="bCancel=true;return confirmDelete('AuditDoc')" 
                value="<fmt:message key="button.delete"/>" />
            <input type="submit" class="button" name="cancel" onclick="bCancel=true"
                value="<fmt:message key="button.cancel"/>" />        
        </td>
    </tr>
</table>
</form>



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

<title><fmt:message key="approvalsScopeDetail.title"/></title>
<content tag="heading"><fmt:message key="approvalsScopeDetail.heading"/></content>

<spring:bind path="approvalsScope.*">
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

<form method="post" action="<c:url value="/editApprovalsScope.html"/>" id="approvalsScopeForm"
    onsubmit="return validateApprovalsScope(this)">
<table class="detail">

<spring:bind path="approvalsScope.id">
<input type="hidden" name="${status.expression}" value="${status.value}"/> 
</spring:bind>

    <tr>
        <th align="left">
            <Calendar:label key="approvalsScope.approvalsScopeName"/>
        </th>
        <td>
            <spring:bind path="approvalsScope.approvalsScopeName">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" maxlength="255"/>*
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th align="left">
            <Calendar:label key="approvalsScope.type"/>
        </th>
        <td>
            <spring:bind path="approvalsScope.type">
                <select name="${status.expression}" <c:if test="${approvalsScope.id ne null}">disabled</c:if>>
	            	<option value="">Select a Group</option>
	            	<option value="TEXT" <c:if test="${approvalsScope.type eq 'TEXT'}">selected</c:if>>Free Text</option>
	            	<option value="BOOLEAN" <c:if test="${approvalsScope.type eq 'BOOLEAN'}">selected</c:if>>Yes/No</option>	            	
            	</select>*
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <td></td>
        <td class="buttonBar">            
            <input type="submit" class="button" name="save" 
                onclick="bCancel=false" value="<fmt:message key="button.save"/>" />
		    <button type="button" onclick="location.href='<c:url value="/approvalsScopes.html"/>'">
		        <fmt:message key="button.cancel"/>
		    </button>
        </td>
    </tr>
</table>
</form>



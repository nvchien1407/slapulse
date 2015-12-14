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

<title><fmt:message key="upload.title"/></title>
<content tag="heading"><fmt:message key="upload.heading"/></content>

<!--
	The most important part is to declare your form's enctype to be "multipart/form-data"
-->

<spring:bind path="fileUpload.*">
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

<fmt:message key="upload.message"/>
<div class="separator"></div>

<form method="post" id="uploadForm" action="<c:url value="/uploadFile.html"/>"
    enctype="multipart/form-data" onsubmit="return validateFileUpload(this)">
	<input type="hidden" name="audit_id" value="${param.audit_id}"/>
	<input type="hidden" name="type" value="${param.type}"/>
<table class="detail">
    <tr>
        <th>
            <Calendar:label key="uploadForm.name" />
        </th>
        <td>
        	<spring:bind path="fileUpload.title">
            <input type="text" name="${status.expression}" id="${status.expression}" size="40" value="${status.value}"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
	<c:choose>
		<c:when test='${param.type == "URL"}'>
			<tr>
				<th>
					<Calendar:label key="uploadForm.url"/>
				</th>
				<td>
					<spring:bind path="fileUpload.auditDocUrl">
						<input type="text" name="${status.expression}" id="${status.expression}"/>
						<i>[http://domain url]</i>
						<span class="fieldError">${status.errorMessage}</span>
					</spring:bind>
				</td>
			</tr>															
		    <tr>
		        <td></td>
				<td class="buttonBar">
		            <input type="submit" name="save" class="button" onclick="bCancel=false"
				        value="<fmt:message key="button.save"/>" />
					<input type="submit" name="cancel" class="button" onclick="bCancel=true"
		                value="<fmt:message key="button.cancel"/>" />
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<th>
					<Calendar:label key="uploadForm.file"/>
				</th>
				<td>
					<spring:bind path="fileUpload.file">
						<input type="file" name="file" id="file" size="50" value="${status.value}"/>
						<span class="fieldError">${status.errorMessage}</span>
					</spring:bind>					
					<spring:bind path="fileUpload.auditDocUrl">
						<input type="hidden" name="${status.expression}" id="${status.expression}" value="http://www.renewtek.com"/>
					</spring:bind>
				</td>
			</tr>
		    <tr>
				<td></td>
		        <td class="buttonBar">
				    <input type="submit" name="upload" class="button" onclick="bCancel=false"
						value="<fmt:message key="button.upload"/>" />
		            <input type="submit" name="cancel" class="button" onclick="bCancel=true"
				        value="<fmt:message key="button.cancel"/>" />
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
</table>
</form>

<script type="text/javascript">
<!--
highlightFormElements();
// -->
</script>

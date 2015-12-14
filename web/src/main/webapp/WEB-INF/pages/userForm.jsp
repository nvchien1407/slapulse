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

<spring:bind path="user.*">
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

<form method="post" action="<c:url value="/editUser.html"/>" id="userForm"
    onsubmit="return onFormSubmit(this)">
    
<spring:bind path="user.version">
<input type="hidden" name="version" value="${status.value}"/> 
</spring:bind>
<input type="hidden" name="from" value="${param.from}" />

<c:if test="${cookieLogin == 'true'}">
    <spring:bind path="user.password">
    <input type="hidden" name="password" value="${status.value}"/>
    </spring:bind>
</c:if>

<c:if test="${empty user.username}">
    <input type="hidden" name="encryptPass" value="true" />
</c:if>

<table class="detail">
<c:set var="pageButtons">
    <tr>
    	<td></td>
    	<td class="buttonBar">
            <input type="submit" class="button" name="save" 
                onclick="bCancel=false" value="<fmt:message key="button.save"/>" />
            
        <c:if test="${param.from == 'list'}">
            <input type="submit" class="button" name="delete"
                onclick="bCancel=true;return confirmDelete('user')" 
                value="<fmt:message key="button.delete"/>" />
        </c:if>
        
            <input type="submit" class="button" name="cancel" onclick="bCancel=true"
                value="<fmt:message key="button.cancel"/>" />
        </td>
    </tr>
</c:set>
    <tr>
        <th>
            <Calendar:label key="user.username"/>
        </th>
        <td>
        <spring:bind path="user.username">
        <c:choose>
            <c:when test="${empty user.username}">
                <input type="text" name="username" value="${status.value}" id="username"/>
                <span class="fieldError">${status.errorMessage}</span>
            </c:when>
            <c:otherwise>
                ${user.username}
                <input type="hidden" name="username" value="${status.value}" id="username"/>
            </c:otherwise>
        </c:choose>
        </spring:bind>
        </td>
    </tr>
    <c:if test="${cookieLogin != 'true'}">
    <tr>
        <th>
            <Calendar:label key="user.password"/>
        </th>
        <td>
            <spring:bind path="user.password">
            <input type="password" id="password" name="password" size="40" 
                value="${status.value}" onchange="passwordChanged(this)"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    </c:if>
    <tr>
        <th>
            <Calendar:label key="user.firstName"/>
        </th>
        <td>
            <spring:bind path="user.firstName">
            <input type="text" name="firstName" value="${status.value}" id="firstName"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="user.lastName"/>
        </th>
        <td>
            <spring:bind path="user.lastName">
            <input type="text" name="lastName" value="${status.value}" id="lastName"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="user.address.address"/>
        </th>
        <td>
            <spring:bind path="user.address.address">
            <input type="text" name="address.address" value="${status.value}" id="address.address" size="50"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="user.address.city"/>
        </th>
        <td>
            <spring:bind path="user.address.city">
            <input type="text" name="address.city" value="${status.value}" id="address.city" size="40"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="user.address.province"/>
        </th>
        <td>
            <spring:bind path="user.address.province">
            <input type="text" name="address.province" value="${status.value}" id="address.province" size="40"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="user.address.country"/>
        </th>
        <td>
            <spring:bind path="user.address.country">
            <Calendar:country name="address.country" prompt="" default="${user.address.country}"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="user.address.postalCode"/>
        </th>
        <td>
            <spring:bind path="user.address.postalCode">
            <input type="text" name="address.postalCode" value="${status.value}" id="address.postalCode" size="10"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="user.email"/>
        </th>
        <td>
            <spring:bind path="user.email">
            <input type="text" name="email" value="${status.value}" id="email" size="50"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="user.phoneNumber"/>
        </th>
        <td>
            <spring:bind path="user.phoneNumber">
            <input type="text" name="phoneNumber" value="${status.value}" id="phoneNumber"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="user.website"/>
        </th>
        <td>
            <spring:bind path="user.website">
            <input type="text" name="website" value="${status.value}" id="website" size="50"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
            <c:if test="${!empty user.website}">
            <a href="${user.website}"><fmt:message key="user.visitWebsite"/></a>
            </c:if>
        </td>
    </tr>
    <tr>
        <th>
            <Calendar:label key="user.passwordHint"/>
        </th>
        <td>
            <spring:bind path="user.passwordHint">
            <input type="text" name="passwordHint" value="${status.value}" id="passwordHint" size="50"/>
            <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
<c:choose>
    <c:when test="${param.from == 'list' or param.method == 'Add'}">
    <tr>
        <th>
            <label for="enabled"><fmt:message key="user.enabled"/>?</label>
        </th>
        <td>
            <spring:bind path="user.enabled">
                <input type="hidden" name="_${status.expression}"  value="visible" /> 
                <input type="checkbox" name="${status.expression}" value="true" 
                    <c:if test="${status.value}">checked="checked"</c:if> /> 
            </spring:bind>
        </td>
    </tr>
    <tr>
        <td></td>
        <td>
            <fieldset class="pickList">
                <legend>
                    <fmt:message key="userProfile.assignRoles"/>
                </legend>
	            <table class="pickList">
	                <tr>
	                    <th class="pickLabel">
	                        <Calendar:label key="user.availableRoles" 
	                            colon="false" class="required"/>
	                    </th>
	                    <td>
	                    </td>
	                    <th class="pickLabel">
	                        <Calendar:label key="user.roles"
	                            colon="false" class="required"/>
	                    </th>
	                </tr>
	                <c:set var="leftList" value="${availableRoles}" scope="request"/>
	                <c:set var="rightList" value="${user.roleList}" scope="request"/>
	                <c:import url="/WEB-INF/pages/pickList.jsp">
	                    <c:param name="listCount" value="1"/>
	                    <c:param name="leftId" value="availableRoles"/>
	                    <c:param name="rightId" value="userRoles"/>
	                </c:import>
	            </table>
            </fieldset>
        </td>
    </tr>
    </c:when>
    <c:when test="${not empty user.username}">
    <tr>
        <th>
            <Calendar:label key="user.roles"/>
        </th>
        <td>
        <c:forEach var="role" items="${user.roleList}" varStatus="status">
            ${role.label}<c:if test="${!status.last}">,</c:if>
            <input type="hidden" name="userRoles" 
                value="${role.label}" />
        </c:forEach>
            <spring:bind path="user.enabled">
            <input type="hidden" name="${status.expression}" value="${status.value}" /> 
            </spring:bind>
        </td>
    </tr>
    </c:when>
</c:choose>
    
    <%-- Print out buttons - defined at top of form --%>
    <%-- This is so you can put them at the top and the bottom if you like --%>
	<c:out value="${pageButtons}" escapeXml="false" />

</table>
</form>

<script type="text/javascript">
<!--
highlightFormElements();
<%-- if we're doing an add, change the focus --%>
<c:choose><c:when test="${user.username == null}"><c:set var="focus" value="username"/></c:when>
<c:when test="${cookieLogin == 'true'}"><c:set var="focus" value="firstName"/></c:when>
<c:otherwise><c:set var="focus" value="password"/></c:otherwise></c:choose>

var focusControl = document.forms["userForm"].elements["${focus}"];

if (focusControl.type != "hidden" && !focusControl.disabled) {
    focusControl.focus();
}

function passwordChanged(passwordField) {
    var origPassword = "${user.password}";
    if (passwordField.value != origPassword) {
        createFormElement("input", "hidden", 
                          "encryptPass", "encryptPass", 
                          "true", passwordField.form);
    }
}

<!-- This is here so we can exclude the selectAll call when roles is hidden -->
function onFormSubmit(theForm) {
<c:if test="${param.from == 'list'}">
    selectAll('userRoles');
</c:if>
    return validateUser(theForm);
}
// -->
</script>

<v:javascript formName="user" staticJavascript="false"/>
<script type="text/javascript"
      src="<c:url value="/scripts/validator.jsp"/>"></script>



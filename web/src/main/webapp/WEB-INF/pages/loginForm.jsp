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

<div id="loginTable">
<%-- If you don't want to encrypt passwords programmatically, or you don't
     care about using SSL for the login, you can change this form's action
     to "j_security_check" --%>
<form method="post" id="loginForm" action="<%= response.encodeURL("j_security_check") %>" 
    onsubmit="saveUsername(this);return validateForm(this)">
<table width="100%">
    <tr>
        <td colspan="2">
            <c:if test="${param.error != null}">
            <div class="error fade-ffff00" id="loginError"
                style="margin-right: 0; margin-bottom: 3px; margin-top: 3px">
                    <img src="<c:url value="/images/iconWarning.gif"/>"
                        alt="<fmt:message key="icon.warning"/>" class="icon" />
                    <fmt:message key="errors.password.mismatch"/>
                    <%--c:out value="${sessionScope.ACEGI_SECURITY_LAST_EXCEPTION.message}"/--%>
                </div>
            </c:if>
        </td>
    </tr>
    <tr>
        <th align="left">
            <label for="j_username" class="required">
                * <fmt:message key="label.username"/>:
            </label>
        </th>
        <td>
            <input type="text" name="j_username" id="j_username" size="25" tabindex="1" />
        </td>
    </tr>
    <tr>
        <th align="left">
            <label for="j_password" class="required">
                * <fmt:message key="label.password"/>:
            </label>
        </th>
        <td>
            <input type="password" name="j_password" id="j_password" size="20" tabindex="2" />
        </td>
    </tr>
    <tr>
        <td></td>
        <td>
            <!-- for Resin -->
            <input type="hidden" name="j_uri" value="" />
            <input type="submit" class="button" name="login" value="<fmt:message key="button.login"/>" tabindex="4" />
            <input type="reset" class="button" name="reset" value="<fmt:message key="button.reset"/>" tabindex="5" 
                onclick="document.getElementById('j_username').focus()" />
        </td>
    </tr>
</table>
</form>
</div>

<%@ include file="/scripts/login.js"%>

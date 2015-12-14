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

<title><fmt:message key="display.title"/></title>
<content tag="heading"><fmt:message key="display.heading"/></content>

Below is a list of attributes that were gathered in FileUploadController.java.

<div class="separator"></div>

<table class="detail" cellpadding="5">
    <tr>
    	<th>Friendly Name:</th>
    	<td>${friendlyName}</td>
    </tr>
    <tr>
    	<th>Filename:</th>
    	<td>${fileName}</td>
    </tr>
    <tr>
    	<th>File content type:</th>
    	<td>${contentType}</td>
    </tr>
    <tr>
    	<th>File size:</th>
    	<td>${size}</td>
    </tr>
    <tr>
    	<th class="tallCell">File Location:</th>
    	<td>The file has been written to: <br />
            <a href="${link}">
            <c:out value="${location}" escapeXml="false"/></a>
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="buttonBar">
            <input type="button" name="done" id="done" value="Done"
                onclick="location.href='mainMenu.html'" />
            <input type="button" name="done" id="done" value="Upload Another"
                onclick="location.href='selectFile.html'" />
        </td>
    </tr>
</table>



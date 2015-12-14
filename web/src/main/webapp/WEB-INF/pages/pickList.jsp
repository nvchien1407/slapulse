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
<tr>
    <td>
        <select name="${param.leftId}" 
            multiple="multiple"
            onDblClick="moveSelectedOptions(this,document.getElementById('${param.rightId}'),true)"
            id="${param.leftId}" size="5">
    <c:if test="${leftList != null}">
        <c:forEach var="list" items="${leftList}" varStatus="status">
            <option value="${list.value}">
                <c:out value="${list.label}" escapeXml="false" />
            </option>
        </c:forEach>
    </c:if>
        </select>
    </td>
    <td class="moveOptions">
        <button name="moveRight" id="moveRight${param.listCount}" type="button" 
            onclick="moveSelectedOptions(document.getElementById('${param.leftId}'),document.getElementById('${param.rightId}'),true)">
            &gt;&gt;</button><br />
        <button name="moveAllRight" id="moveAllRight${param.listCount}" type="button"
            onclick="moveAllOptions(document.getElementById('${param.leftId}'),document.getElementById('${param.rightId}'),true)">
            All &gt;&gt;</button><br />
        <button name="moveLeft" id="moveLeft${param.listCount}" type="button"
            onclick="moveSelectedOptions(document.getElementById('${param.rightId}'),document.getElementById('${param.leftId}'),true)">
            &lt;&lt;</button><br />
        <button name="moveAllLeft" id="moveAllLeft${param.listCount}" type="button"
            onclick="moveAllOptions(document.getElementById('${param.rightId}'),document.getElementById('${param.leftId}'),true)">
            All &lt;&lt;</button>
    </td>
    <td>
        <select name="${param.rightId}" 
            multiple="multiple"
            id="${param.rightId}" size="5">
    <c:if test="${rightList != null}">
        <c:forEach var="list" items="${rightList}" varStatus="status">
            <option value="${list.value}">
                <c:out value="${list.label}" escapeXml="false"/>
            </option>
        </c:forEach>
    </c:if>
        </select>
    </td>
</tr>
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

<title><fmt:message key="userList.title"/></title>
<content tag="heading"><fmt:message key="userList.heading"/></content>

<c:set var="buttons">
    <button type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editUser.html"/>?method=Add&from=list'">
        <fmt:message key="button.add"/>
    </button>
    
    <button type="button" onclick="location.href='<c:url value="/supplierSearch.html" />'">
        <fmt:message key="button.cancel"/>
    </button>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<display:table name="${userList}" cellspacing="0" cellpadding="0"
    requestURI="" defaultsort="1" uid="users"
    pagesize="25" class="list userList" export="true" sort="list">
  
    <%-- Table columns --%>
    <display:column property="username" sortable="true" 
    	headerClass="sortable" width="17%"
        url="/editUser.html?from=list" 
        paramId="username" paramProperty="username"
        titleKey="user.username"/>
    <display:column property="firstName" sortable="true" 
    	headerClass="sortable" width="20%"
        titleKey="user.firstName" />
    <display:column property="lastName" sortable="true" 
    	headerClass="sortable" width="13%"
        titleKey="user.lastName"/>
    <display:column property="email" sortable="true" headerClass="sortable" 
    	width="26%" autolink="true"
        titleKey="user.email" />
        
    <display:setProperty name="paging.banner.item_name" value="user"/>
    <display:setProperty name="paging.banner.items_name" value="users"/>

    <display:setProperty name="export.excel.filename" value="User List.xls"/>
    <display:setProperty name="export.csv.filename" value="User List.csv"/>
    <display:setProperty name="export.pdf.filename" value="User List.pdf"/>
</display:table>

<c:out value="${buttons}" escapeXml="false" />
            
<script type="text/javascript">
<!--
highlightTableRows("users");
//-->
</script>

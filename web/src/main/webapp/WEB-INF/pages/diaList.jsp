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
<c:choose>
	<c:when test="${!supplier.isActive}">
		<c:set var="disabledVar" value="DISABLED"/>
	</c:when>
	<c:otherwise>		
		<c:set var="disabledVar" value=""/>
	</c:otherwise>
</c:choose>    

<title><fmt:message key="diaList.title"/></title>
<content tag="heading"><fmt:message key="diaList.heading"/></content>
<c:choose>
	<c:when test='${not empty param.supplier_id}'>
		<c:url value="/editDia.html" var="diaUrl">
			<c:param name="supplier_id" value="${param.supplier_id}"/>
		</c:url>
		<c:url value="/editSupplier.html" var="backUrl">
			<c:param name="id" value="${param.supplier_id}"/>
		</c:url>
	</c:when>
	<c:otherwise>
		<c:url value="/editDia.html" var="diaUrl"/>
		<c:url value="/supplierSearch.html" var="backUrl"/>
	</c:otherwise>
</c:choose>
<c:set var="buttons">
    <button type="button" style="margin-right: 5px" ${disabledVar}
        onclick="location.href='<c:out value='${diaUrl}'/>'">
        <fmt:message key="button.add"/>
    </button>

    <button type="button" onclick="location.href='<c:out value='${backUrl}'/>'">
        <fmt:message key="button.back"/>
    </button>
</c:set>
<%@ include file="/common/messages.jsp"%>
<display:table name="${diaList}" cellspacing="0" cellpadding="0"
    uid="diaList" pagesize="25" class="list" 
    export="true" requestURI="" decorator="com.renewtek.webapp.action.DiaWrapper" sort="list" defaultsort="1">

    <display:column property="id" sortable="true" headerClass="sortable"
        url="/editDia.html" paramId="id" paramProperty="id"
        titleKey="dia.id"/>
    <display:column property="nameofperson" sortable="true" headerClass="sortable"
         titleKey="dia.nameofperson"/>
    <display:column property="title" sortable="true" headerClass="sortable"
         titleKey="dia.title"/>
    <display:column property="stampIdentifier" sortable="true" headerClass="sortable"
         titleKey="dia.stampIdentifier"/>
    <display:column property="dateApproved" sortable="true" headerClass="sortable"
         titleKey="dia.dateApproved"/>
    <display:column property="dateCancelled" sortable="true" headerClass="sortable"
         titleKey="dia.dateCancelled"/>
    <display:setProperty name="paging.banner.item_name" value="dia"/>
    <display:setProperty name="paging.banner.items_name" value="dias"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<script type="text/javascript">
highlightTableRows("diaList");
</script>

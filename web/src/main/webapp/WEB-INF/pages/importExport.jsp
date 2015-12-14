<%-- ------- DO NOT REMOVE THIS NOTICE         ------- --%>
<%-- ------- COPYRIGHT(c)2011 SMS MANAGEMENT & TECHNOLOGY ------- --%>
<%-- ------- DO NOT REMOVE THIS NOTICE         ------- --%>

<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://www.springmodules.org/tags/commons-validator" prefix="v" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/request-1.0" prefix="r" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="/oscache" prefix="cache" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib uri="/WEB-INF/Calendar.tld" prefix="Calendar" %>

<title><fmt:message key="importExportDetail.title"/></title>
<content tag="heading"><fmt:message key="importExportDetail.heading"/></content>

<%@ include file="/common/messages.jsp"%>

<div id="messages">
	<p>${message}</p>
</div>

<hr/>
<div id="import">
	<h3><fmt:message key="importExportDetail.import"/></h3>
	<p><fmt:message key="importExportDetail.import.instructions"/></p>
	<div id="importWorkflow">
		<form action="importExport.html" id="import" method="post" enctype="multipart/form-data">
	        <table>
	            <tr>
	                <td><strong>Workflow file to upload</strong></td>
	                <td><input type="file" name="fileWorkflow" /></td>
	            </tr>
	            <tr>
	                <td>&nbsp;</td>
	                <td><input type="submit" name="import" value="Import"/></td>
	            </tr>
	        </table>
		</form>
	</div>
</div>

<hr/>
<div id="export">
	<h3><fmt:message key="importExportDetail.export"/></h3>
	<p><fmt:message key="importExportDetail.export.instructions"/></p>
	<div id="exportWorkflow">
		<form action="importExport.html" id="export" method="post">
	        <table>
	            <tr>
	                <td>&nbsp;</td>
	                <td><input type="submit" name="exportWorkflow" value="Export workflows"/></td>
	            </tr>
	        </table>
		</form>
	</div>
</div>

<hr/>
<div id="changelog">
	<h3><fmt:message key="changeLogList.heading"/></h3>
	<p><a href="changeLog.html"><fmt:message key="importExportDetail.changeLog"/></a></p>
</div>
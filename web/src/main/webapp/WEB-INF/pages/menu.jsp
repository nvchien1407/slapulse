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

<div id="menu">
<menu:useMenuDisplayer name="ListMenu" permissions="rolesAdapter">
    <menu:displayMenu name="AdminMenu"/>
    <menu:displayMenu name="UserMenu"/>
    <menu:displayMenu name="FileUpload"/>
    <menu:displayMenu name="FlushCache"/>
    <menu:displayMenu name="Clickstream"/>
    
    <!--ChangeLog-START-->
    <menu:displayMenu name="ChangeLogMenu"/>
    <!--ChangeLog-END-->
    <!--Reference-START-->
    <menu:displayMenu name="ReferenceMenu"/>
    <!--Reference-END-->
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    <!--ApprovalsScope-START-->
    <menu:displayMenu name="ApprovalsScopeMenu"/>
    <!--ApprovalsScope-END-->
    <!--AuditDoc-START-->
    <menu:displayMenu name="AuditDocMenu"/>
    <!--AuditDoc-END-->
    <!--Auditor-START-->
    <menu:displayMenu name="AuditorMenu"/>
    <!--Auditor-END-->
    <!--ChangeLog-START-->
    <menu:displayMenu name="ChangeLogMenu"/>
    <!--ChangeLog-END-->
    <!--Dia-START-->
    <menu:displayMenu name="DiaMenu"/>
    <!--Dia-END-->
    <!--Reference-START-->
    <menu:displayMenu name="ReferenceMenu"/>
    <!--Reference-END-->
    <!--Supplier-START-->
    <menu:displayMenu name="SupplierMenu"/>
    <!--Supplier-END-->
    
    <!--SupplierAudit-START-->
    <menu:displayMenu name="SupplierAuditMenu"/>
    <!--SupplierAudit-END-->
    <!--SupplierUpdatesNotify-START-->
    <menu:displayMenu name="SupplierUpdatesNotifyMenu"/>
    <!--SupplierUpdatesNotify-END-->
    <!--SupplierApprovalsScope-START-->
    <menu:displayMenu name="SupplierApprovalsScopeMenu"/>
    <!--SupplierApprovalsScope-END-->
    <!--DefaultWeek-START-->
    <menu:displayMenu name="DefaultWeekMenu"/>
    <!--DefaultWeek-END-->
    <!--WorkHourRange-START-->
    <menu:displayMenu name="WorkHourRangeMenu"/>
    <!--WorkHourRange-END-->
    
    
    
    
    
    <!--ServiceLevelAgreement-START-->
    <menu:displayMenu name="ServiceLevelAgreementMenu"/>
    <!--ServiceLevelAgreement-END-->
    <!--BusinessProcess-START-->
    <menu:displayMenu name="BusinessProcessMenu"/>
    <!--BusinessProcess-END-->
    <menu:displayMenu name="ImportExportMenu"/>
</menu:useMenuDisplayer>





























































































</div>
<script type="text/javascript">
    initializeMenus();
</script>
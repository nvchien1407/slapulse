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
<%@ include file="/common/tabscript2.jsp"%>
<c:choose>
	<c:when test="${!supplier.isActive}">
		<c:set var="disabledVar" value="DISABLED"/>
	</c:when>
	<c:otherwise>		
		<c:set var="disabledVar" value=""/>
	</c:otherwise>
</c:choose>
<title><fmt:message key="supplierDetail.title"/></title>
<content tag="heading"><fmt:message key="supplierDetail.heading"/></content>
<%@ include file="/common/messages.jsp"%>
<spring:bind path="supplier.*">
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

<form method="post" action="<c:url value="/editDpd.html"/>" id="dpdForm"
    onsubmit="return validateSupplier(this)">

<spring:bind path="supplier.id">
<input type="hidden" name="${status.expression}" value="${status.value}"/> 
</spring:bind>
<table>
    <tr>
        <th align="left">
            <fmt:message key="supplier.companyName"/>
        </th>
        <td>
            ${supplier.companyName}
        </td>
        <th align="left">
            <fmt:message key="supplier.street"/>
        </th>
        <td>
            ${supplier.street}
        </td>
        <th align="left">
            <fmt:message key="supplier.postcode"/>
        </th>
        <td>
            ${supplier.postcode}
        </td>
	</tr>
	<tr>
        <th align="left">
            <fmt:message key="supplier.city"/>
        </th>
        <td>
            ${supplier.city}
        </td>
        <th align="left">
            <fmt:message key="supplier.CountryState"/>
        </th>
        <td>
            ${supplier.referenceByCountryState.itemName}
        </td>
        <th align="left">
        </th>
        <td>
        </td>
    </tr>		
	<tr>
        <th align="left">
            <Calendar:label key="supplier.dpdName"/>
        </th>
        <td>
            <spring:bind path="supplier.dpdName">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" maxlength="50" ${disabledVar}/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
        <th align="left">
            <Calendar:label key="supplier.dpdEmail"/>
        </th>
        <td>
            <spring:bind path="supplier.dpdEmail">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}"  maxlength="64" ${disabledVar}/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>			
        </td>
        <th align="left">
        </th>
        <td>
        </td>
    </tr>		
</table>
<br>					
<ul id="tablist">
<li><a href="http://www.dynamicdrive.com" class="current" onClick="return expandcontent('CompanyDetails', this)">Hardware</a></li>
<li><a href="new.htm" onClick="return expandcontent('sc2', this)" >Operating System</a></li>
<li><a href="hot.htm" onClick="return expandcontent('sc3', this)" >Applications</a></li>
<li><a href="search.htm" onClick="return expandcontent('sc4', this)">Data Exchange</a></li>
<li><a href="search.htm" onClick="return expandcontent('sc5', this)">Data Security</a></li>
<li><a href="search.htm" onClick="return expandcontent('sc6', this)">Configuration Management</a></li>

</ul>

<DIV id="tabcontentcontainer">

<div id="CompanyDetails" class="tabcontent">
	<table width=100%>
		<tr>
			<td><fmt:message key="dpd.hardware.1.1"/></td>
			<td>
            <spring:bind path="supplier.hwWorkstations">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.hardware.1.2"/></td>
			<td>
            <spring:bind path="supplier.hwWorkstationSpec">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.hardware.1.3"/></td>
			<td>
            <spring:bind path="supplier.hwServerSpec">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.hardware.1.4"/></td>
			<td>
            <spring:bind path="supplier.hwMaintAgree">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
	</table>
</div>

<div id="sc2" class="tabcontent">
	<table width=100%>
		<tr>
			<td><fmt:message key="dpd.opsystem.2.1"/></td>
			<td>
            <spring:bind path="supplier.osPlatform">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.opsystem.2.2"/></td>
			<td>
            <spring:bind path="supplier.osAdmin">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
	</table>
</div>

<div id="sc3" class="tabcontent">
	<table width=100%>
		<tr>
			<td><fmt:message key="dpd.applications.3.1"/></td>
			<td>
            <spring:bind path="supplier.appCadSysVersion">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.applications.3.2"/></td>
			<td>
            <spring:bind path="supplier.appSeats">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.applications.3.3"/></td>
			<td>
            <spring:bind path="supplier.appCadOther">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.applications.3.4"/></td>
			<td>
            <spring:bind path="supplier.appProgSys">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.applications.3.5"/></td>
			<td>
            <spring:bind path="supplier.appProgSeats">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.applications.3.6"/></td>
			<td>
            <spring:bind path="supplier.appSimVerSw">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.applications.3.7"/></td>
			<td>
            <spring:bind path="supplier.appCmmProg">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.applications.3.8"/></td>
			<td>
            <spring:bind path="supplier.appDocMeth">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.applications.3.9"/></td>
			<td>
            <spring:bind path="supplier.appMaintAgree">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
	</table>
</div>

<div id="sc4" class="tabcontent">
	<table width=100%>
		<tr>
			<td><fmt:message key="dpd.dataexchange.4.1"/></td>
			<td>
            <spring:bind path="supplier.deTransferMedium">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.dataexchange.4.2"/></td>
			<td>
            <spring:bind path="supplier.deCadTranslator">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.dataexchange.4.3"/></td>
			<td>
            <spring:bind path="supplier.deConformityCheck">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
	</table>
</div>
<div id="sc5" class="tabcontent">
	<table width=100%>
		<tr>
			<td><fmt:message key="dpd.datasecurity.5.1"/></td>
			<td>
            <spring:bind path="supplier.dsBackupProc">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.datasecurity.5.2"/></td>
			<td>
            <spring:bind path="supplier.dsDisasterProc">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.datasecurity.5.3"/></td>
			<td>
            <spring:bind path="supplier.dsArchiveProc">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.datasecurity.5.4"/></td>
			<td>
            <spring:bind path="supplier.dsArchiveFireProof">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="dpd.datasecurity.5.5"/></td>
			<td>
            <spring:bind path="supplier.dsFileStructure">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
	</table>
</div>
<div id="sc6" class="tabcontent">
	<table width=100%>
		<tr>
			<td><fmt:message key="dpd.configmanagement.6.1"/></td>
			<td>
            <spring:bind path="supplier.cmCadCamConfig">
                <textarea maxlength="255" rows="2" cols=32 name="${status.expression}" id="${status.expression}" 
                    ${disabledVar}>${status.value}</textarea>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
			</td>
		</tr>
	</table>
</div>
</DIV>
	<br>
	<table class="detail">	
    <tr>
        <td></td>
        <td class="buttonBar">            
            <input type="submit" class="button" name="save" 
                onclick="bCancel=false" value="<fmt:message key="button.save"/>" ${disabledVar}/>
            <input type="submit" class="button" name="back" 
                onclick="bCancel=false" value="<fmt:message key="button.back"/>" />
        </td>
    </tr>
</table>
</form>

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
	<c:when test="${not empty dia.supplier && !dia.supplier.isActive}">
		<c:set var="disabledVar" value="DISABLED"/>
	</c:when>
	<c:otherwise>		
		<c:set var="disabledVar" value=""/>
	</c:otherwise>
</c:choose>    

<title><fmt:message key="diaDetail.title"/></title>
<content tag="heading"><fmt:message key="diaDetail.heading"/></content>

<spring:bind path="dia.*">
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
<form method="post" action="<c:url value="/editDia.html"/>" id="diaForm"
    onsubmit="return validateDia(this)">
<table class="detail">

<spring:bind path="dia.id">
<input type="hidden" name="${status.expression}" value="${status.value}"/> 
</spring:bind>

    <tr>
        <th align="left">
            <Calendar:label key="dia.nameofperson"/>
        </th>
        <td>
            <spring:bind path="dia.nameofperson">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}"  maxlength="255" ${disabledVar}/>*
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th align="left">
            <Calendar:label key="dia.title"/>
        </th>
        <td>
            <spring:bind path="dia.title">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" maxlength="32" ${disabledVar}/>*
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th align="left">
            <Calendar:label key="dia.stampIdentifier"/>
        </th>
        <td>
            <spring:bind path="dia.stampIdentifier">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" ${disabledVar}/>*
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th align="left">
            <Calendar:label key="dia.dateApproved"/>
        </th>
        <td>
            <spring:bind path="dia.dateApproved">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}" maxlength="10" ${disabledVar}/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
            <i>[dd/MM/yyyy]</i>
        </td>
    </tr>

    <tr>
        <th align="left">
            <Calendar:label key="dia.dateCancelled"/>
        </th>
        <td>
            <spring:bind path="dia.dateCancelled">
                <input type="text" name="${status.expression}" id="${status.expression}" 
                    value="${status.value}"  maxlength="10" ${disabledVar}/>
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
            <i>[dd/MM/yyyy]</i>
        </td>
    </tr>

    <tr>
        <th align="left">
            <Calendar:label key="dia.supplier"/>
        </th>
        <td>
            <spring:bind path="dia.supplier">
	            <select name="${status.expression}" ${disabledVar}> 
				<c:choose>
					<c:when test='${not empty supplier_id}'>
						<option value="${dia.supplier.id}" selected>${dia.supplier.companyName}
						<c:url value="/dias.html" var="backUrl">
							<c:param name="supplier_id" value="${supplier_id}"/>
						</c:url>						
					</c:when>
					<c:otherwise>
							<c:url value="/dias.html" var="backUrl"/>
							<option>Select Supplier</option>
					        <c:forEach var="aSupplier" items="${Calendar}">
								<option value="${aSupplier.id}" <c:if test="${aSupplier.id eq dia.supplier.id}">selected</c:if>>${aSupplier.companyName}
					        </c:forEach>
						</select>*
					</c:otherwise>
				</c:choose>            	            	
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>
    
    <tr>
        <td></td>
        <td class="buttonBar">            
            <input type="submit" class="button" name="save" 
                onclick="bCancel=false" value="<fmt:message key="button.save"/>" ${disabledVar}/>
		    <button type="button" onclick="location.href='<c:out value='${backUrl}'/>'">
        		<fmt:message key="button.cancel"/>
		    </button>
        </td>
    </tr>
</table>
</form>



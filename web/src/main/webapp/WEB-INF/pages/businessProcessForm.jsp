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

<script type="text/javascript">
function do_onload() {
   //document.getElementById('emailTemplate').disabled = true;
   //document.getElementById('fromEmail').disabled = true;
  // document.getElementById('toEmail').disabled = true;
   enableFieldForEmailNotificationSelected(document.getElementById('emailNotificationID').checked);
}
if (window.addEventListener)
   window.addEventListener("load", do_onload, false)
else if (window.attachEvent)
   window.attachEvent("onload", do_onload)
else if (document.getElementById)
   window.onload = do_onload
   
function enableFieldForEmailNotificationSelected(value) {
   document.getElementById('emailTemplate').disabled = !value;
   document.getElementById('fromEmail').disabled = !value;
   document.getElementById('toEmail').disabled = !value;
}
</script>
<title><fmt:message key="businessProcessDetail.title"/></title>
<content tag="heading"><fmt:message key="businessProcessDetail.heading"/></content>
<%@ include file="/common/messages.jsp"%>

<spring:bind path="businessProcess.*">
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

<form method="post" action="<c:url value="/editBusinessProcess.html"/>" id="businessProcessForm"
    onsubmit="return validateBusinessProcess(this)">
<table class="detail">

<input type="hidden" name="id" value="${businessProcess.id}"/> 
    <tr>
        <th>
            <Calendar:label key="businessProcess.name"/>
        </th>
        <td>
            <spring:bind path="businessProcess.name">
	        	<select name="businessProcessName"
	        		class="required" id="businessProcessNameId">
					<option value="">Select Name</option>
	        		<c:forEach var="businessProcessName" items="${businessProcessNameList}">
						<option value="${businessProcessName.id}" <c:if test='${businessProcessName.id == businessProcess.name.id}'>selected</c:if>>
							${businessProcessName.itemName}
						</option>
	        		</c:forEach>
                	<span class="fieldError">${status.errorMessage}</span>
				</select>*
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th>
            <Calendar:label key="businessProcess.type"/>
        </th>
        <td>
            <spring:bind path="businessProcess.type">
	        	<select name="businessProcessType" 
	        		class="required" id="businessProcessTypeId">
					<option value="">Select Type</option>
	        		<c:forEach var="businessProcessType" items="${businessProcessTypeList}">
						<option value="${businessProcessType.id}" <c:if test='${businessProcessType.id == businessProcess.type.id}'>selected</c:if>>
							${businessProcessType.itemName}
						</option>
	        		</c:forEach>
                	<span class="fieldError">${status.errorMessage}</span>
				</select>*
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th>
            <Calendar:label key="businessProcess.step"/>
        </th>
        <td>
            <spring:bind path="businessProcess.step">
	        	<select name="businessProcessStep" id="businessProcessStepId">
					<option value="">Select Step</option>
	        		<c:forEach var="businessProcessStep" items="${businessProcessStepList}">
						<option value="${businessProcessStep.id}" <c:if test='${businessProcessStep.id == businessProcess.step.id}'>selected</c:if>>
							${businessProcessStep.itemName}
						</option>
	        		</c:forEach>
                	<span class="fieldError">${status.errorMessage}</span>
				</select>
            </spring:bind>
        </td>
    </tr>
    
    <tr>
        <th>
            <Calendar:label key="businessProcess.txn"/>
        </th>
        <td>
            <spring:bind path="businessProcess.txn">
	        	<select name="businessProcessTxn" id="businessProcessTxnId">
					<option value="">Select Transaction</option>
	        		<c:forEach var="businessProcessTxn" items="${businessProcessTxnList}">
						<option value="${businessProcessTxn.id}" <c:if test='${businessProcessTxn.id == businessProcess.txn.id}'>selected</c:if>>
							${businessProcessTxn.itemName}
						</option>
	        		</c:forEach>
                	<span class="fieldError">${status.errorMessage}</span>
				</select>
            </spring:bind>
        </td>
    </tr>
    
    <tr>
        <th>
            <Calendar:label key="businessProcess.description"/>
        </th>
        <td>
            <spring:bind path="businessProcess.description">
                <textarea 
                	name="${status.expression}"
                	id="${status.expression}"
                	rows="3" cols="50">${status.value}</textarea>*
                <span class="fieldError">${status.errorMessage}</span>
            </spring:bind>
        </td>
    </tr>

     <tr>
        <th>
            <Calendar:label key="businessProcess.emailNotification"/>
        </th>
        <td>
            <spring:bind path="businessProcess.emailNotification">
             <input type="checkbox" name="emailNotificationcb" id="emailNotificationID" onClick="enableFieldForEmailNotificationSelected(this.checked)"
             <c:if test='${businessProcess.emailNotification}'>checked</c:if> />
            </spring:bind>
        </td>
    </tr>
    
   <tr>
        <th>
            <Calendar:label key="businessProcess.emailTemplate"/>
        </th>
        <td>
           <spring:bind path="businessProcess.emailTemplate">
                  <select name="emailTemplate" id="emailTemplate">
                     <option value="">Select Template</option>
                     <c:forEach var="template" items="${emailTemplate.pickListItems}" varStatus="status">
                     <option value="${template.value}" <c:if test='${template.value == businessProcess.emailTemplate}'>selected="selected"</c:if>>
                       ${template.value}
                     </option>
                     </c:forEach>
                  </select>
                  <span class="fieldError">${status.errorMessage}</span>
               </spring:bind>
        </td>
    </tr>

   <tr>
        <th>
            <Calendar:label key="businessProcess.fromEmail"/>
        </th>
        <td>
           <spring:bind path="businessProcess.fromEmail">
                  <select name="fromEmail" id="fromEmail">
                     <option value="">Select Email</option>
                     <c:forEach var="femail" items="${fromEmail.pickListItems}" varStatus="status">
                     <option value="${femail.value}" <c:if test='${femail.value == businessProcess.fromEmail}'>selected</c:if>>
                       ${femail.value}
                     </option>
                     </c:forEach>
                  </select>
                  <span class="fieldError">${status.errorMessage}</span>
               </spring:bind>
        </td>
    </tr>
    
     <tr>
        <th>
            <Calendar:label key="businessProcess.toEmail"/>
        </th>
        <td>
           <spring:bind path="businessProcess.toEmail">
                  <select name="toEmail" id="toEmail">
                     <option value="">Select Email</option>
                     <c:forEach var="temail" items="${toEmail.pickListItems}" varStatus="status">
                     <option value="${temail.value}" <c:if test='${temail.value == businessProcess.toEmail}'>selected</c:if>>
                       ${temail.value}
                     </option>
                     </c:forEach>
                  </select>
                  <span class="fieldError">${status.errorMessage}</span>
               </spring:bind>
        </td>
    </tr>
   
    <tr>
        <td class="buttonBar">            
            <input type="submit" class="button" name="save" 
                onclick="bCancel=false" value="<fmt:message key="button.save"/>" />
            <input type="submit" class="button" name="delete"
                onclick="bCancel=true;return confirmDelete('BusinessProcess')" 
                value="<fmt:message key="button.delete"/>" />
            <input type="submit" class="button" name="cancel" onclick="bCancel=true"
                value="<fmt:message key="button.cancel"/>" />        
        </td>
    </tr>
</table>
</form>

<v:javascript formName="businessProcess" cdata="false"
    dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" 
    src="<c:url value="/scripts/validator.jsp"/>"></script>


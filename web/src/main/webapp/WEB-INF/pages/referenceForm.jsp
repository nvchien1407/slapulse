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

<title><fmt:message key="referenceDetail.title"/></title>
<content tag="heading"><fmt:message key="referenceDetail.heading"/></content>

<spring:bind path="reference.*">
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

<c:if test="${not empty errorMessages}">
    <div class="error"> 
        <c:forEach var="error" items="${errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon" />
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
</c:if>


<form method="post" action="<c:url value="/editReference.html"/>" id="referenceForm"
    onsubmit="return validateReference(this)">
<table class="detail">
<c:if test='${actionType != null}'>
   <input type='hidden' name="actionType" value="<c:out value='${actionType}'/>"/>  
</c:if>


<input type="hidden" name="id" value="${reference.id}"/> 
<c:if test='${not empty param.isGroup}'>
   <input type="hidden" name="isGroup" value="<c:out value='${param.isGroup}'/>">
</c:if>
<c:if test="${actionType eq 'editGroup'}"> 
   <tr>
      <th align="left">
            <Calendar:label key="reference.subGroupName"/>
        </th>
        <td>
            <select name="dropDownGroupName" id="editGroupName">
               <option value="">Select a Group</option>
               <c:forEach var="group" items="${groups}">
                  <option value="<c:out value="${group}"/>"><c:out value="${group}"/></option>
               </c:forEach>
            </select>*
            <spring:bind path="reference.itemName">
            <input type="hidden" name="itemName" value="None"/>
         </spring:bind>
         <span class="fieldError"><c:out value="${status.errorMessage}"/></span>
        </td>
   </tr>
</c:if>
   
    <tr>
        <th align="left">
         <c:choose>
             <c:when test="${actionType eq 'editGroup'}">
               <Calendar:label key="reference.newSubGroupName"/>
             </c:when>
             <c:otherwise>
               <Calendar:label key="reference.subGroupName"/>
                  </c:otherwise>
         </c:choose>
            
        </th>
        <td>
            <spring:bind path="reference.subGroupName">
            <c:choose>
                <c:when test="${actionType eq 'addGroup' || actionType eq 'editGroup'}"> 
            
                      <input type="text" name="<c:out value="${status.expression}"/>" id="<c:out value="${status.expression}"/>" 
                        value="<c:out value="${status.value}"/>" 
                        size="60" maxlength="100"/>*
                  </c:when>
               <c:otherwise>
                      <select name="<c:out value="${status.expression}"/>" <c:if test="${reference.id ne null}">disabled</c:if>>
                        <option value="">Select a Group</option>
                        <c:forEach var="group" items="${groups}">
                           <option value="<c:out value="${group}"/>" <c:if test="${group eq reference.subGroupName}">selected</c:if>><c:out value="${group}"/></option>
                        </c:forEach>
                      </select>*
               </c:otherwise>
            </c:choose>
                <span class="fieldError"><c:out value="${status.errorMessage}"/></span>
            </spring:bind>
        </td>
    </tr>

<c:if test="${actionType ne 'editGroup'}">
    <tr>
        <th align="left">
            <Calendar:label key="reference.itemName"/>
        </th>
        <td>
            <spring:bind path="reference.itemName">
                <input type="text" name="<c:out value="${status.expression}"/>" id="<c:out value="${status.expression}"/>" 
                    value="<c:out value="${status.value}"/>"
                    size="60" maxlength="250"/>*
                <span class="fieldError"><c:out value="${status.errorMessage}"/></span>
            </spring:bind>
        </td>
    </tr>

    <tr>
        <th align="left">
            <Calendar:label key="reference.note"/>
        </th>
        <td>
            <spring:bind path="reference.note">
                <textarea name="<c:out value="${status.expression}"/>" 
                  id="<c:out value="${status.expression}"/>"
                    rows="4" cols="50" maxlength="255"><c:out value="${status.value}"/></textarea>
                <span class="fieldError"><c:out value="${status.errorMessage}"/></span>
            </spring:bind>
        </td>
    </tr>
</c:if>
    <tr>
        <td></td>
        <td class="buttonBar"> 
         <c:if test='${not empty reference.id}'>           
               <input type="submit" class="button" name="delete"
                   onclick="bCancel=true;return confirmDelete('Reference Data')" 
                   value="<fmt:message key="button.delete"/>" />        
            </c:if>
            <input type="submit" class="button" name="save" 
                onclick="bCancel=false; return checkRequired('editGroupName', 'Old Group Name ')" value="<fmt:message key="button.save"/>" />
          <button type="button" onclick="location.href='<c:url value="/references.html"/>'">
            <fmt:message key="button.cancel"/>
          </button>
        </td>
    </tr>
</table>
</form>

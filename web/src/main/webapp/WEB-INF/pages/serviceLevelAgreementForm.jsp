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
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="/WEB-INF/Calendar.tld" prefix="Calendar" %>

<script type="text/javascript">

   function do_onload() {
      if (document.getElementById('durationTypeTFormulaeId').checked) {
         durationSelected("tFormulae");
      } else if (document.getElementById('durationTypeFixedTimeId').checked) {
         durationSelected("fixedTime");
      } else {
         durationSelected("duration");
      }
      stopSlaSelected(document.getElementById('stopSlaWhenPausedCheckId').checked);
      enableCutOffTimeSelected(document.getElementById('enableCutOffTimeCheckId').checked);
      notifyDeadlineApproachingSelected(document.getElementById('notifyDeadlineApproachingCheckId').checked);
   }

   if (window.addEventListener)
      window.addEventListener("load", do_onload, false)
   else if (window.attachEvent)
      window.attachEvent("onload", do_onload)
   else if (document.getElementById)
      window.onload = do_onload

   function durationSelected(value) {
      if (value == "tFormulae") {
         document.getElementById('deadlineEndOfDayId').disabled = false;
         document.getElementById('deadlineEndOfBusinessId').disabled = false;
         document.getElementById('deadlineSameTimeId').disabled = false;
         document.getElementById('tFormulaeDaysId').disabled = false;
         document.getElementById('includeSpecialDaysCheckId').disabled = false;
         document.getElementById('durationHoursId').disabled = true;
         document.getElementById('durationMinutesId').disabled = true;
         document.getElementById('workTimeWorkingHoursId').disabled = true;
         document.getElementById('workTime24HourId').disabled = true;
         document.getElementById('fixedTimeDeadlineId').disabled = true;

      } else if (value == "duration") {
         document.getElementById('deadlineEndOfDayId').disabled = true;
         document.getElementById('deadlineEndOfBusinessId').disabled = true;
         document.getElementById('deadlineSameTimeId').disabled = true;
         document.getElementById('tFormulaeDaysId').disabled = true;
         document.getElementById('tFormulaeDaysId').value = "";
         document.getElementById('includeSpecialDaysCheckId').disabled = true;
         document.getElementById('durationHoursId').disabled = false;
         document.getElementById('durationMinutesId').disabled = false;
         document.getElementById('workTimeWorkingHoursId').disabled = false;
         document.getElementById('workTime24HourId').disabled = false;
         document.getElementById('fixedTimeDeadlineId').disabled = true;

      } else if (value == "fixedTime") {
         document.getElementById('deadlineEndOfDayId').disabled = true;
         document.getElementById('deadlineEndOfBusinessId').disabled = true;
         document.getElementById('deadlineSameTimeId').disabled = true;
         document.getElementById('tFormulaeDaysId').disabled = true;
         document.getElementById('tFormulaeDaysId').value = "";
         document.getElementById('includeSpecialDaysCheckId').disabled = true;
         document.getElementById('durationHoursId').disabled = true;
         document.getElementById('durationMinutesId').disabled = true;
         document.getElementById('workTimeWorkingHoursId').disabled = true;
         document.getElementById('workTime24HourId').disabled = true;
         document.getElementById('fixedTimeDeadlineId').disabled = false;
      }
   }

   function notifyDeadlineApproachingSelected(value) {
      document.getElementById('notificationThresholdDaysId').disabled = !value;
      document.getElementById('notificationThresholdHoursId').disabled = !value;
      document.getElementById('notificationThresholdMinutesId').disabled = !value;
      document.getElementById('notifyDeadlineForwardingCheckId').disabled = !value;
   }

   function stopSlaSelected(value) {
      document.getElementById('pauseMinutesId').disabled = !value;
      document.getElementById('pauseHoursId').disabled = !value;
   }

   function enableCutOffTimeSelected(value) {
      document.getElementById('fixedTimeThresholdId').disabled = !value;
      document.getElementById('fixedTimeDaysToRollId').disabled = !value;
   }

   function removeBusinessProcess(i) {
      var thisTable = document.getElementById('businessProcessList');
      var row = thisTable.rows[i];
      var cell = row.cells[5];
      var data = cell.innerHTML;
      document.getElementById('businessProcessToBeDeletedId').value = data;
      document.getElementById('serviceLevelAgreementForm').submit();
   }

</script>

<style>
   .dottedBorder {
      border-style: dotted;
      border-width: thin;
   }
</style>

<title><fmt:message key="serviceLevelAgreementDetail.title"/></title>
<content tag="heading"><fmt:message key="serviceLevelAgreementDetail.heading"/></content>
<%@ include file="/common/messages.jsp" %>

<spring:bind path="serviceLevelAgreement.*">
   <c:if test="${not empty status.errorMessages}">
      <div class="error">
         <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                 alt="<fmt:message key="icon.warning"/>" class="icon"/>
            <c:out value="${error}" escapeXml="false"/><br/>
         </c:forEach>
      </div>
   </c:if>
</spring:bind>

<form method="post" action="<c:url value="/editServiceLevelAgreement.html"/>" id="serviceLevelAgreementForm"
      onsubmit="return validateServiceLevelAgreement(this)">
<table class="detail" width="85%">

<input type="hidden" name="id" value="${serviceLevelAgreement.id}"/> 
<input type="hidden" name="businessProcessToBeDeleted" id="businessProcessToBeDeletedId"/>


<tr>
   <td>
      <table>
         <tr>
            <th>
               <Calendar:label key="serviceLevelAgreement.name"/>
            </th>
            <td colspan=4>
               <spring:bind path="serviceLevelAgreement.name">
                  <input type="text" name="${status.expression}"
                         id="${status.expression}" size="60"
                         value="${status.value}"/>
               </spring:bind>
            </td>
         </tr>
      </table>
   </td>
</tr>

<tr>
<td>
<table width="100%">
<tr>
   <td width="36%">
      <c:set var="tFormulae">
         <fmt:message key="serviceLevelAgreement.TFormulae"/>
      </c:set>
      <input type="radio" name="durationGroup"
             id="durationTypeTFormulaeId"
             onClick="durationSelected('tFormulae')"
             <c:if test='${serviceLevelAgreement.durationType == "T-Formulae"}'>checked</c:if>
             value="T-Formulae">
      <c:out value="${tFormulae}"/>
      </input>
   </td>
   <td width="34%">
      <c:set var="duration">
         <fmt:message key="serviceLevelAgreement.duration"/>
      </c:set>
      <input type="radio" name="durationGroup"
             id="durationTypeDurationId"
             onClick="durationSelected('duration')"
             <c:if test='${serviceLevelAgreement.durationType == "Duration"}'>checked</c:if>
             value="Duration">
      ${duration}
      </input>
   </td>
   <td width="30%">
      <c:set var="fixedTime">
         <fmt:message key="serviceLevelAgreement.fixedTime"/>
      </c:set>
      <input type="radio" name="durationGroup"
             id="durationTypeFixedTimeId"
             onClick="durationSelected('fixedTime')"
             <c:if test='${serviceLevelAgreement.durationType == "Fixed Deadline"}'>checked</c:if>
             value="Fixed Deadline">
      ${fixedTime}
      </input>
   </td>
</tr>
<tr>
   <td id="tFormulaTDId" class='dottedBorder'>
      <table>
         <tr>
            <td>
               <table>
                  <tr>
                     <th>
                        <Calendar:label key="serviceLevelAgreement.TFormulaeDays"/>
                     </th>
                     <td>
                        <spring:bind path="serviceLevelAgreement.TFormulaeDays">
                           <input type="text" name="${status.expression}"
                                  id="tFormulaeDaysId" size="3"
                                  value="${status.value}"/>
                        </spring:bind>
                     </td>
                  </tr>
               </table>
            </td>
         <tr>
            <td>
               <table>
                  <tr>
                     <td align="left">
                        <c:set var="endOfBusiness">
                           <fmt:message key="serviceLevelAgreement.durationType.endOfBusiness"/>
                        </c:set>
                        <input type="radio" name="deadlineGroup"
                               id="deadlineEndOfBusinessId"
                               <c:if test='${serviceLevelAgreement.deadlineType == endOfBusiness}'>checked</c:if>
                               value="${endOfBusiness}">
                        ${endOfBusiness}
                        </input>
                     </td>
                     <td>
                        <c:set var="endOfDay">
                           <fmt:message key="serviceLevelAgreement.durationType.endOfDay"/>
                        </c:set>
                        <input type="radio" name="deadlineGroup"
                               id="deadlineEndOfDayId"
                               <c:if test='${serviceLevelAgreement.deadlineType == endOfDay}'>checked</c:if>
                               value="${endOfDay}">
                        ${endOfDay}
                        </input>
                     </td>
                  </tr>
                  <tr>
                     <td align="left">
                        <c:set var="sameTime">
                           <fmt:message key="serviceLevelAgreement.durationType.sameTime"/>
                        </c:set>
                        <input type="radio" name="deadlineGroup"
                               id="deadlineSameTimeId"
                               <c:if test='${serviceLevelAgreement.deadlineType == sameTime}'>checked</c:if>
                               value="${sameTime}">
                        ${sameTime}
                        </input>
                     </td>
                     <td>&nbsp;</td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr>
            <td>
               <table>
                  <tr>
                     <td>
                        <spring:bind path="serviceLevelAgreement.includeSpecialDays">
                           <input type="checkbox" name="includeSpecialDaysCheck"
                                  id="includeSpecialDaysCheckId"
                                  <c:if test='${serviceLevelAgreement.includeSpecialDays == "Yes"}'>checked</c:if> />
                           <Calendar:label key="serviceLevelAgreement.includeSpecialDays"/>
                        </spring:bind>
                     </td>
                  </tr>
               </table>
            </td>
         </tr>
      </table>
   </td>
   <td id="durationTDId" class='dottedBorder' valign="top" align="left">
      <table>
         <tr>
            <td>
               <table>
                  <tr>
                     <th>
                        <Calendar:label key="serviceLevelAgreement.durationHours"/>
                     </th>
                     <td>
                        <spring:bind path="serviceLevelAgreement.durationHours">
                           <input type="text" name="${status.expression}"
                                  id="durationHoursId" size="3"
                                  value="${status.value}"/>
                        </spring:bind>
                     </td>
                     <th>
                        <Calendar:label key="serviceLevelAgreement.durationMinutes"/>
                     </th>
                     <td>
                        <spring:bind path="serviceLevelAgreement.durationMinutes">
                           <select name="${status.expression}"
                                   class="required" id="durationMinutesId">
                              <c:forEach var="minutes" items="${durationMinutesList}">
                                 <option value="${minutes}"
                                         <c:if test='${minutes == serviceLevelAgreement.durationMinutes}'>selected</c:if>>
                                       ${minutes}
                                 </option>
                              </c:forEach>
                           </select>
                        </spring:bind>
                     </td>
                  </tr>
               </table>
            </td>
         <tr>
            <td>
               <table>
                  <tr>
                     <td align="left">
                        <c:set var="workingHours">
                           <fmt:message key="serviceLevelAgreement.workTime.workingHours"/>
                        </c:set>
                        <input type="radio" name="workTimeGroup"
                               id="workTimeWorkingHoursId"
                               <c:if test='${serviceLevelAgreement.workTime == workingHours}'>checked</c:if>
                               value="${workingHours}">
                        ${workingHours}
                        </input>
                     </td>
                     <td align="left">
                        <c:set var="twentyFourHourClock">
                           <fmt:message key="serviceLevelAgreement.workTime.24HourClock"/>
                        </c:set>
                        <input type="radio" name="workTimeGroup"
                               id="workTime24HourId"
                               <c:if test='${serviceLevelAgreement.workTime == twentyFourHourClock}'>checked</c:if>
                               value="${twentyFourHourClock}">
                        ${twentyFourHourClock}
                        </input>
                     </td>
                  </tr>
               </table>
            </td>
         </tr>
      </table>
   </td>
   <td id="fixedTimeTDId" class='dottedBorder' valign="top" align="left">
      <table>
         <tr>
            <th>
               <Calendar:label key="serviceLevelAgreement.fixedTimeDeadline"/>
            </th>
            <td>
               <spring:bind path="serviceLevelAgreement.fixedTimeDeadline">
                  <input type="text" name="${status.expression}"
                         id="fixedTimeDeadlineId" size="5"
                         value="${status.value}"/>
               </spring:bind>
            </td>
         </tr>
      </table>
   </td>
</tr>
</table>
</td>
</tr>

<tr>
   <td class="dottedBorder">
      <table cellpadding="5">
         <tr>
            <td>
               <spring:bind path="serviceLevelAgreement.enableCutOffTime">
                  <input type="checkbox" name="enableCutOffTimeCheck"
                         id="enableCutOffTimeCheckId"
                         onClick="enableCutOffTimeSelected(this.checked)"
                         <c:if test='${serviceLevelAgreement.enableCutOffTime}'>checked</c:if> />
                  <Calendar:label key="serviceLevelAgreement.enableCutOffTime"/>
               </spring:bind>
            </td>
         </tr>
         <tr>
            <th>
               <Calendar:label key="serviceLevelAgreement.fixedTimeThreshold"/>
            </th>
            <td>
               <spring:bind path="serviceLevelAgreement.fixedTimeThreshold">
                  <input type="text" name="${status.expression}"
                         id="fixedTimeThresholdId" size="5"
                         value="${status.value}"/>
               </spring:bind>
            </td>
            <th>
               <Calendar:label key="serviceLevelAgreement.fixedTimeDaysToRoll"/>
            </th>
            <td>
               <spring:bind path="serviceLevelAgreement.fixedTimeDaysToRoll">
                  <input type="text" name="${status.expression}"
                         id="fixedTimeDaysToRollId" size="5"
                         value="${status.value}"/>
               </spring:bind>
            </td>
         </tr>
      </table>
   </td>
</tr>

<tr>
   <td class="dottedBorder">
      <table cellpadding="5">
         <tr>
            <td>
               <spring:bind path="serviceLevelAgreement.stopSlaWhenPaused">
                  <input type="checkbox" name="stopSlaCheck"
                         id="stopSlaWhenPausedCheckId"
                         onClick="stopSlaSelected(this.checked)"
                         <c:if test='${serviceLevelAgreement.stopSlaWhenPaused}'>checked</c:if> />
                  <Calendar:label key="serviceLevelAgreement.stopSlaWhenPaused"/>
               </spring:bind>
            </td>
         </tr>
         <tr>
            <th>
               <Calendar:label key="serviceLevelAgreement.pauseThreshold"/>
            </th>
            <th>
               <Calendar:label key="serviceLevelAgreement.pauseThresholdHours"/>
            </th>
            <td>
               <spring:bind path="serviceLevelAgreement.pauseThresholdHours">
                  <input type="text" name="${status.expression}"
                         id="pauseHoursId" size="3"
                         value="${status.value}"/>
               </spring:bind>
            </td>
            <th>
               <Calendar:label key="serviceLevelAgreement.pauseThresholdMinutes"/>
            </th>
            <td>
               <spring:bind path="serviceLevelAgreement.pauseThresholdMinutes">
                  <select name="${status.expression}"
                          id="pauseMinutesId" class="required">
                     <c:forEach var="minutes" items="${pauseThresholdMinutesList}">
                        <option value="${minutes}"
                                <c:if test='${minutes == serviceLevelAgreement.pauseThresholdMinutes}'>selected</c:if>>
                              ${minutes}
                        </option>
                     </c:forEach>
                  </select>
               </spring:bind>
            </td>
         </tr>
      </table>
   </td>
</tr>

<tr>
   <td class="dottedBorder">
      <table cellpadding="5">
         <tr>
            <td>
               <spring:bind path="serviceLevelAgreement.notifyDeadlineApproaching">
                  <input type="checkbox" name="notifyDeadlineCheck"
                         id="notifyDeadlineApproachingCheckId"
                         onClick="notifyDeadlineApproachingSelected(this.checked)"
                         <c:if test='${serviceLevelAgreement.notifyDeadlineApproaching}'>checked</c:if> />
                  <Calendar:label key="serviceLevelAgreement.notifyDeadlineApproaching"/>
               </spring:bind>
            </td>
         </tr>
         <tr>
            <th>
               <Calendar:label key="serviceLevelAgreement.notificationThreshold"/>
            </th>
            <td>
               <table>
                  <tr>
                     <th>
                        <Calendar:label key="serviceLevelAgreement.notificationThreshold.days"/>
                     </th>
                     <td>
                        <spring:bind path="serviceLevelAgreement.notificationThresholdDays">
                           <input type="text" name="${status.expression}"
                                  id="notificationThresholdDaysId" size="3"
                                  value="${status.value}"/>
                        </spring:bind>
                     </td>
                     <th>
                        <Calendar:label key="serviceLevelAgreement.notificationThreshold.hours"/>
                     </th>
                     <td>
                        <spring:bind path="serviceLevelAgreement.notificationThresholdHours">
                           <input type="text" name="${status.expression}"
                                  id="notificationThresholdHoursId" size="3"
                                  value="${status.value}"/>
                        </spring:bind>
                     </td>
                     <th>
                        <Calendar:label key="serviceLevelAgreement.notificationThreshold.minutes"/>
                     </th>
                     <td>
                        <spring:bind path="serviceLevelAgreement.notificationThresholdMinutes">
                           <select name="${status.expression}"
                                   id="notificationThresholdMinutesId" class="required">
                              <c:forEach var="minutes" items="${notificationThresholdMinutesList}">
                                 <option value="${minutes}"
                                         <c:if test='${minutes == serviceLevelAgreement.notificationThresholdMinutes}'>selected</c:if>>
                                       ${minutes}
                                 </option>
                              </c:forEach>
                           </select>
                        </spring:bind>
                     </td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr>
            <td>
               <spring:bind path="serviceLevelAgreement.notifyDeadlineApproachingForwarding">
                  <input type="checkbox" name="notifyDeadlineForwardingCheck"
                        id="notifyDeadlineForwardingCheckId"
                        <c:if test='${serviceLevelAgreement.notifyDeadlineApproachingForwarding}'>checked</c:if> />
                  <Calendar:label key="serviceLevelAgreement.notifyDeadlineApproaching.forwarding"/>
               </spring:bind>
            </td>
         </tr>
      </table>
   </td>
</tr>

<tr>
   <td>
      <table cellpadding="5">
         <tr>
            <th>
               <Calendar:label key="serviceLevelAgreement.calendar"/>
            </th>
            <td>
               <spring:bind path="serviceLevelAgreement.calendar">
                  <SELECT NAME="calendar">
                     <OPTION>Select Calendar</OPTION>
                     <c:forEach var="calendar" items="${calendars}">
                        <OPTION VALUE="${calendar.id}"
                                <c:if test='${calendar.itemName == serviceLevelAgreement.calendar.itemName}'>selected</c:if>>
                              ${calendar.itemName}
                        </OPTION>
                     </c:forEach>
                  </SELECT>
               </spring:bind>
            </td>
         </tr>
      </table>
   <td>
</tr>

<c:if test='${not empty serviceLevelAgreement.id}'>
   <tr>
      <td>
         <table width="100%">
            <tr>
               <td colspan=3>
                  <display:table name="${serviceLevelAgreement.businessProcesses}"
                                 cellspacing="0" cellpadding="0"
                                 uid="businessProcessList" pagesize="5" class="list"
                                 requestURI="" sort="list">

                     <display:column width="5" title="">
                        <button name="removeBusinessProcessName" width="16" height="16" value="" onSubmit=""
                                onclick="removeBusinessProcess(this.parentNode.parentNode.rowIndex)">
                           <img src="images/recyclebin.gif"/>
                        </button>
                     </display:column>

                     <display:column property="name.itemName" sortable="true" headerClass="sortable"
                                     titleKey="businessProcess.name"/>
                     <display:column property="type.itemName" sortable="true" headerClass="sortable"
                                     titleKey="businessProcess.type"/>
                     <display:column property="txn.itemName" sortable="true" headerClass="sortable"
                                     titleKey="businessProcess.txn"/>
                     <display:column property="description" sortable="true" headerClass="sortable"
                                     titleKey="businessProcess.description"/>
                     <display:column property="id" sortable="true" headerClass="sortable"
                                     titleKey="businessProcess.blank" style='display: none'/>
                     <display:setProperty name="paging.banner.item_name" value="Business Process"/>
                     <display:setProperty name="paging.banner.items_name" value="Business Processes"/>
                  </display:table>
               </td>
            </tr>
            <tr>
               <td>
                  <select name="selectedBusinessProcess" class="required">
                     <option value="">Select Business Process</option>
                     <c:forEach var="businessProcess" items="${unassignedBusinessProcesses}">
                        <option value="${businessProcess.id}">
                              ${businessProcess.name.itemName} - ${businessProcess.type.itemName} - ${businessProcess.txn.itemName} - ${businessProcess.step.itemName}
                        </option>
                     </c:forEach>
                  </select>
               </td>
               <td width="100%" align="left">
                  <input type="submit" value="Add" name="addBusinessProcess"/>
               </td>
            </tr>
         </table>
      </td>
   </tr>
</c:if>

<tr>
   <td>
      <table>
         <tr>
            <td></td>
            <td class="buttonBar">
               <input type="submit" class="button" name="save"
                      onclick="bCancel=false" value="<fmt:message key="button.save"/>"/>
               <input type="submit" class="button" name="delete"
                      onclick="bCancel=true;return confirmDelete('ServiceLevelAgreement')"
                      value="<fmt:message key="button.delete"/>"/>
               <input type="submit" class="button" name="cancel" onclick="bCancel=true"
                      value="<fmt:message key="button.cancel"/>"/>
            </td>
         </tr>
      </table>
   </td>
</tr>

</table>
</form>

<v:javascript formName="serviceLevelAgreement" cdata="false"
              dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript"
        src="<c:url value="/scripts/validator.jsp"/>"></script>


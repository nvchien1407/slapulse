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
<title><fmt:message key="histogramReport.title"/></title>
<content tag="heading"><fmt:message key="histogramReport.heading"/></content>
<table>
	<tr>
	<td align='left'>
		<form method="post" action="<c:url value="/histogramReport.html"/>">
            <SELECT NAME="year">
		        <c:forEach var="year" items="${years}">
						<c:choose>
							<c:when test='${year == selectedYear}'>
								<c:set var="found" value="true"/>
	    						<OPTION VALUE="${year}"  selected="selected">
									${year}
								</OPTION>
							</c:when>
							<c:otherwise>
	    						<OPTION VALUE="${year}" >
									${year}
								</OPTION>
							</c:otherwise>
						</c:choose>
		        </c:forEach>
			</SELECT>
	       <input type="submit" class="button" name="save"/> 
		</form>			
	</td></tr>

	<tr>
	<td align='left'>
		<img src="<c:out value='${graphURL}'/>" width=700 height=500 border=0"/>			
	</td></tr>
</table>

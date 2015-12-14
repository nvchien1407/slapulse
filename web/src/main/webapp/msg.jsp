<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.renewtek.service.SLAUpdatePropertiesAdapter"%>
<html>
<body>

<%
    /*
        ** For developers only ** 

        If you want to add anything to this page make sure it cannot compromise security!
    */

    String caseId = request.getParameter("caseId");
    String prop = request.getParameter("property");
    String value = request.getParameter("value");
    if (caseId != null) {
       WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
       SLAUpdatePropertiesAdapter dao = (SLAUpdatePropertiesAdapter)context.getBean("slaUpdatePropertiesDAOJMS");
       dao.lockUpdateCasePropertyAndUnlock(Long.parseLong(caseId), prop, value);
    }
%>

<form action="msg.jsp" method="GET">
    <INPUT NAME="caseId" VALUE="1900">
    <INPUT NAME="property" >
    <INPUT NAME="value" >
    <input type="submit" value="Send" />
</form>

</body>

</html>

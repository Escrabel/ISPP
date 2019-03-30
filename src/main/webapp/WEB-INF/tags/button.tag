<%--
 * cancel.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
<%@ tag language="java" body-content="empty" %>
 
 <%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
 
<%@ attribute name="code" required="true" %>
<%@ attribute name="url" required="false" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="type" required="true" %>


<%-- Definition --%>

<jstl:if test="${url == null}">
	<jstl:set var="url" value="false" />
</jstl:if>

<input name="${name}" type="${type}" value="<spring:message code="${code}" />" id="normalButton" style="background-color:#8db5df; color:white; font-weight: bold;" class="btn btn-default"  onclick="javascript: relativeRedir('${url}')" >
	


<%--
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="tag">
	<form:hidden path="id" />
	<form:hidden path="version" />


	<form:label path="name">
		<spring:message code="tag.name" />
	</form:label>	
	<form:select path="name" multiple="multiple" size="5">
		<form:options items="${tags}" itemLabel="name" itemValue="id" />
	</form:select>


	<!-- Creacion de botones -->
	<input type="submit" name="save"
		value="<spring:message code="tag.save"></spring:message>" />

	<input type="button" name="cancel"
		value="<spring:message code="tag.cancel" />"
		onclick="javascript: window.history.back();" />

</form:form>

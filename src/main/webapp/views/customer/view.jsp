<%--
 * action-2.jsp
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

<display:table name="customer" class="displaytag" id="row"
	requestURI="${requestURI}">
	<display:column>

		<p>
			<spring:message code="customer.name"></spring:message>
			:
			<jstl:out value="${row.name}"></jstl:out>
		</p>


		<p>
			<spring:message code="customer.surname"></spring:message>
			:
			<jstl:out value="${row.surName}"></jstl:out>
		</p>


		<p>
			<spring:message code="customer.postalAddress"></spring:message>
			:
			<jstl:out value="${row.postalAddress}"></jstl:out>
		</p>


		<p>
			<spring:message code="customer.phone"></spring:message>
			:
			<jstl:out value="${row.phone}"></jstl:out>
		</p>

		<p>
			<spring:message code="customer.email"></spring:message>
			:
			<jstl:out value="${row.email}"></jstl:out>
		</p>

		<%-- <p>
			<spring:message code="customer.pais"></spring:message>
			:
			<jstl:out value="${row.pais}"></jstl:out>
		</p>
--%>
		<p>
			<spring:message code="customer.ciudad"></spring:message>
			:
			<jstl:out value="${row.ciudad}"></jstl:out>
		</p>




	</display:column>

</display:table>


<form:form>
	<input type="button" name="cancel"
		value="<spring:message code="customer.back" />"
		onclick="javascript:relativeRedir('/')" />
</form:form>


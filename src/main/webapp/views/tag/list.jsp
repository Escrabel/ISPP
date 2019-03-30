<%--
 * action-1.jsp
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



<!--Creacion de la paginacion  -->

<display:table name="tags" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<!--Creacion de la tabla -->

	<spring:message code="tag.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" />

	<security:authorize access="hasRole('TATTOOIST')">
		<spring:message code="tag.deleteH" var="deleteTag"></spring:message>
		<display:column title="${deleteTag}">
			<spring:url value="tag/tattooist/delete.do" var="viewUrl">
				<spring:param name="tagId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${viewUrl}"> <spring:message code="tag.delete"></spring:message>
			</a>

		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="tag.deleteH" var="deleteTag"></spring:message>
		<display:column title="${deleteTag}">
			<spring:url value="tag/administrator/delete.do" var="viewUrl">
				<spring:param name="tagId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${viewUrl}"> <spring:message code="tag.delete"></spring:message>
			</a>

		</display:column>

		<spring:message code="tag.edit" var="edit" />
		<display:column title="${edit}">
			<spring:url value="tag/administrator/edit.do" var="editUrl">
				<spring:param name="tagId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${editUrl}"> <spring:message code="tag.edit" /></a>
		</display:column>

	</security:authorize>


</display:table>
<form:form action="${requestURI}" modelAttribute="tag">

	<security:authorize access="hasRole('TATTOOIST')">
		<input type="button" name="back"
			value="<spring:message code="tag.back"></spring:message>"
			onclick="javascript: relativeRedir('tattoo/tattooist/list.do');" />
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<input type="button" name="create"
			value="<spring:message code="tattoo.create"></spring:message>"
			onclick="javascript: relativeRedir('tag/administrator/create.do');" />

		<input type="button" name="back"
			value="<spring:message code="tag.back"></spring:message>"
			onclick="javascript: relativeRedir('tag/administrator/list.do');" />
	</security:authorize>

</form:form>
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="scripts/tattoo/TattooController.js"></script>
<div ng-controller="TattooController">
	<section class="well">
		<div class="row text-center">

			<div class="card-body">
				<h3 id="titulo2">
					<spring:message code="tattoo.ticker"></spring:message>
					:
					<jstl:out value="${tattoo.ticker}"></jstl:out>
				</h3>
				<h3>
					<jstl:out value="${tattoo.name}"></jstl:out>
				</h3>
			</div>
			<div ng-init="getImgFromTattooIdView(${tattoo.id})">
				<p>
					<img id="foto" ng-src="{{imgString}}" />
				</p>
			</div>

			<p>
			<h4 id="titulo2">
				<spring:message code="tattoo.description"></spring:message>
			</h4>
			<jstl:out value="${tattoo.description}"></jstl:out>
			</p>


			<p>

				<b><spring:message code="tattoo.price"></spring:message></b> :
				<jstl:out value="${tattoo.price}"></jstl:out>&#8364
			</p>

			<div class="card-body">
				<p>
					<b><spring:message code="tattoo.tags"></spring:message></b> :
					<jstl:forEach var="x" items="${tattoo.tags}" varStatus="loop">
					<jstl:if test="${loop.index eq 0}">
						<jstl:out value=" ${x.name}" />
					</jstl:if>
					<jstl:if test="${loop.index gt 0}">
						<jstl:out value="/ ${x.name}" />
					</jstl:if>
					</jstl:forEach>
				</p>
			</div>

			<div class="card-body">
				<p>
					<b><spring:message code="tattoo.dateUpload"></spring:message></b> :
					<spring:message code="tattoo.formatDate2" var="patternDate"></spring:message>
					<fmt:formatDate value="${tattoo.dateUpload}"
						pattern="${patternDate}" />
				</p>
			</div>

		</div>

	</section>
	<security:authorize access="hasRole('TATTOOIST')">
		<jstl:if test="${verEdit==true}">

			<center>
				<acme:button code="tattoo.edit" name="edit" type="button"
					url="tattoo/tattooist/edit.do?tattooId=${tattoo.id}" />
			</center>

		</jstl:if>

		<center>
			<acme:button code="tattoo.back" name="back" type="button"
				url="tattoo/list.do" />

		</center>
	</security:authorize>


	<security:authorize access="hasRole('CUSTOMER')">
		<center>
			<acme:button code="tattoo.contact" name="contact" type="button"
				url="message/actor/create-t.do?tattooistId=${tattoo.tattooist.id}" />

			<a class="btn btn-primary"
				href="buy/customer/buy.do?tattooId=${tattoo.id}" id="button"><span
				class="glyphicon"></span> <spring:message
					code="tattoo.buy"></spring:message></a>
		</center>
	</security:authorize>


	<!-- Cambiar url <input type="button" name="back"
		value="<spring:message code="tattoo.back"></spring:message>"
		onclick="javascript: window.history.back();" />  -->
</div>
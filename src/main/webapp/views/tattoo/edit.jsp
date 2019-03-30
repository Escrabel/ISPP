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
<jsp:useBean id="utilities" class="utilities.UtilitiesProject"
	scope="page" />

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="scripts/tattoo/TattooController.js"></script>

<div ng-controller="TattooController" ng-init="initVariables()">

 	<!-- Formulario de edit -->
	<form:form role="form" class="abs-center form-signin"
		action="${requestURI}"
		modelAttribute="tattooForm" enctype="multipart/form-data">
		
		<section id="section" class="well">
		<center>

		<form:hidden path="tattoo.id" />
		<form:hidden path="tattoo.version" />
		<form:hidden path="tattoo.ticker" />
		<form:hidden path="tattoo.dateUpload" />
		<form:hidden path="tattoo.tags" />
		<form:hidden path="tattoo.img" />

		<!-- Probando el hide -->

		<acme:textbox code="tattoo.name" path="tattoo.name" />

		<acme:textbox code="tattoo.description" path="tattoo.description" />

		<acme:textbox code="tattoo.price" path="tattoo.price" />

		<form:label path="tags">
			<spring:message code="tattoo.tags"></spring:message>
		</form:label>
		
		<form:select class="form-control" path="tags" id="inputFormText" multiple="multiple" size="5">
			<form:options items="${tags}" itemLabel="name" itemValue="id" />
		</form:select><br/>

		<%-- <acme:uploadImg code="tattoo.img" path="imgB64String" /> --%>
		<!-- <input type="text" id="imgB64String" name="imgB64String" /> -->
		
		<label for="archivoId" class="btn btn-primary btn-block btn-outlined">
		<spring:message code="tattoo.archivo" />
		</label>
		<spring:message code="tattoo.maxSizeError" var="maxSizeErrorMsg"></spring:message>
		<form:input id="archivoId" path="archivo" code="tattoo.archivo" name="archivo" type="file" accept="image/png, image/jpeg" onchange="checkSize('${maxSizeErrorMsg}')" style="display: none"/>
		</center>
		</section>

		<center>
		<acme:button code="tattoo.save" name="save" type="submit"
			url="tattoo/tattooist/list.do" />
		<security:authorize access="hasRole('TATTOOIST')">
			<acme:button code="tattoo.cancel" name="cancel" type="button"
				url="tattoo/tattooist/list.do" />

		</security:authorize>
		</center>
	</form:form>
</div>

<script>
function checkSize(messageAlert){
	var file = document.getElementById("archivoId");
	console.log(file.files[0].size);
	if(file.files[0].size > 1048576){
		alert(messageAlert);
		file.value='';
	}
}
/* $(document).on('change','input[type="file"]',function(){
	var fileSize = this.files[0].size;
	if(fileSize > 20971520){
		alert('El archivo no debe superar los 20MB');
		this.value = '';
	}
}); */
</script>
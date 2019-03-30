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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="scripts/tattoo/TattooController.js"></script>

   
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>  
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>  
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script> 

	<!-- Soy Espi, no se que carajo hacia este script aqui de la nada que daba fallo en consola y lo he comentado -->
  <!--  <script type="text/javascript"> -->


<jsp:useBean id="utilities" class="utilities.UtilitiesProject"
	scope="page" />

<spring:message code="tattoo.validarPrecio" var="validacion" />
<script type="text/javascript">
	function validaPrecio() {
		var num1 = document.getElementById('precioUno').value;
		var num2 = document.getElementById('precioDos').value;
		document.getElementById('button_validar').disabled = parseInt(num1) > parseInt(num2);

	}
</script>
<script>
$(function () { 
	$("#from").datepicker({
		dateFormat: 'yy/mm/dd',
		onClose: function (selectedDate) {
		$("#to").datepicker("option", "minDate", selectedDate);
		}
	});	
	$("#to").datepicker({
		dateFormat: 'yy/mm/dd',
		onClose: function (selectedDate) {	
		$("#from").datepicker("option", "maxDate", selectedDate);
		}
	});
});
</script>
<!-- Buscador de Tattoos -->
<div ng-controller="TattooController" class="form-inline"  ng-init="initVariables()">

	<jstl:if test="${ver==true}">
	<section id="section" class="well">
	

			<p id="colorLetras">
				<spring:message code="tattoo.filter.name"></spring:message>

				<input type="text" id="inputSearch" class="form-control"
					ng-model="filter.name" />
			</p>
			<p>
				<spring:message code="tattoo.filter.description"></spring:message>

				<textarea rows="4" cols="50" ng-model="filter.description" id="inputSearchDescription" class="form-control"></textarea>
			</p>

			<p>
				<spring:message code="tattoo.filter.localization"></spring:message>

				<input type="text" id="inputSearch" class="form-control"
					placeholder="<spring:message code="tattoo.filterString" />"
					ng-model="filter.localization" />
			</p>
			<p>
				<spring:message code="tattoo.filter.price"></spring:message>

				<input type="number" min="0"
					onkeypress="return event.charCode >= 48 && vent.charCode <= 57"
					  pattern="\d*" id="precioUno"  
					class="form-control" placeholder="12" ng-model="filter.precioOne" />
				<input id="precioDos" class="form-control" type="number"
					onkeyup="validaPrecio();" 
					min="0"   
					onkeypress="return event.charCode >= 48 && vent.charCode <= 57"
					pattern="\d+" id="precioTwoFilter" placeholder="25"
					ng-model="filter.precioTwo" />
			</p>
		
				
				

				
			
			<div class="container">
   
		    	<spring:message code="tattoo.filter.date"></spring:message>
		        
		            <div class='input-group date'>
		            
		               <input type="text" id="from" name="from" class="form-control"
		              		ng-model="filter.dateOne"/>
		                <span class="input-group-addon">
		                    <span class="glyphicon glyphicon-calendar"></span>
		                </span>
		        	</div>
		        
		            <div class='input-group date'>
		                <input type ="text" id="to" name="to" class="form-control"
							ng-model="filter.dateTwo" />
		                <span class="input-group-addon">
		                    <span class="glyphicon glyphicon-calendar"></span>
		                </span>
		            </div>
					           
				</div>
			
			<input type="button" id="button_validar"
				style="background-color: #8db5df; color: white; font-weight: bold;"
				class="btn btn-default" ng-click="searchTattoos()"
				value="<spring:message code="tattoo.filter.search" />" />
		
		</section>
		
		<br>
		<br>
	</jstl:if>

	<!-- Lista Angular -->
	<!-- Con el siguiente codigo sabremos a que listado de tattoos queremos llamar por APIRest -->
	<c:set var="rol" value="" />
	<security:authorize access="hasRole('TATTOOIST')">
		<c:set var="rol" value="tattooist" />
	</security:authorize>
	<security:authorize access="hasRole('CUSTOMER')">
		<c:set var="rol" value="customer" />
	</security:authorize>
	<!-- Obtenemos la url para saber si al tattooist le mostramos sus tatuajes o los del sistema -->
	<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}" />
	<div data-ng-init='getTattoos("${rol}","${url}")'>
		<div class="row">
			<center>
				<ul dir-paginate="tattoo in fvar_tattoos |itemsPerPage:8">

					<div class="col-lg-4 col-md-4 mb-3 img-resposive"
						ng-init="getImgFromTattooId(tattoo.id,$index)" id="tatus">
						<center>
							<div class="card h-100">
								<center>
									<img id="foto" class="card-img-top foto{{$index}}" src=""
										alt="">
								</center>
								<div class="card-body">
									<h4 style="font-weight: bold;" class="card-title">{{tattoo.name}}</h4>
									<p class="card-text">{{tattoo.price}} &#8364</p>
									<!-- 						<p class="card-text">{{tattoo.dateUpload}}</p> -->
									<spring:message code="tattoo.formatDateForAngular"
										var="fechaInternacionalizada" />
									<p class="card-text">{{tattoo.dateUpload |
										${fechaInternacionalizada}}}</p>
								</div>
						</center>
						<div class="card-footer">

							<security:authorize access="hasRole('TATTOIST')">
								<a class="btn btn-primary"
									href="tattoo/tattooist/view.do?tattooId={{tattoo.id}}"
									id="button"><span class="glyphicon glyphicon-eye-open"></span>
									<spring:message code="tattoo.view"></spring:message></a>
								<br />
								<acme:button code="tattoo.edit" name="edit" type="button"
									url="tattoo/tattooist/edit.do?tattooId={{tattoo.id}}" />
							</security:authorize>
							<security:authorize access="hasRole('CUSTOMER')">
								<a class="btn btn-primary"
									href="tattoo/customer/view.do?tattooId={{tattoo.id}}"
									id="button"><span class="glyphicon glyphicon-eye-open"></span>
									<spring:message code="tattoo.view"></spring:message></a>
							</security:authorize>
							
							<security:authorize access="hasRole('CUSTOMER')">
								<a class="btn btn-primary"
									href="buy/customer/buy.do?tattooId={{tattoo.id}}"
									id="button"><span class="glyphicon"></span>
									<spring:message code="tattoo.buy"></spring:message></a>
							</security:authorize>
							<jstl:if test="${desdeFuera == true}">
								<a class="btn btn-primary"
									href="tattoo/view.do?tattooId={{tattoo.id}}" id="button"><span
									class="glyphicon glyphicon-eye-open"></span> <spring:message
										code="tattoo.view"></spring:message></a>
								<br />
							</jstl:if>
							<br /> <br />
						</div>
					</div>
		</div>
		</ul>
		</center>
	</div>
	<center>
			<dir-pagination-controls max-size="8" direction-links="true"
				boundary-links="true"> </dir-pagination-controls>
		</center>
	</div>
	<!-- 
<display:table name="tattoos" class="displaytag" id="row" pagesize="5"
	requestURI="${requestURI}">

	<spring:message code="tattoo.ticker" var="tickerH"></spring:message>
	<display:column title="${tickerH}" property="ticker" sortable="false"></display:column>

	<spring:message code="tattoo.name" var="nameH"></spring:message>
	<display:column title="${nameH}" property="name" sortable="false"></display:column>

	<spring:message code="tattoo.description" var="descriptionH"></spring:message>
	<display:column title="${descriptionH}" property="description"
		sortable="false"></display:column>


	<spring:message code="tattoo.price" var="priceH"></spring:message>
	<display:column title="${priceH}" sortable="false">
		<jstl:out value="${row.price}"></jstl:out>
	</display:column>

	<spring:message code="tattoo.formatDate" var="patternDate"></spring:message>
	<spring:message code="tattoo.dateUpload" var="dateH"></spring:message>
	<display:column title="${dateH}" property="dateUpload"
		format="${patternDate}" sortable="true"></display:column>

	<security:authorize access="hasRole('TATTOOIST')">
		<spring:message code="tattoo.tagH" var="tagH"></spring:message>
		<display:column title="${tagH}">
			<spring:url value="tag/tattooist/list.do" var="viewUrl">
				<spring:param name="tattooId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${viewUrl}"> <spring:message code="tattoo.tags"></spring:message>
			</a>
		</display:column>

		<spring:message code="tattoo.editTitle" var="tattooEdit"></spring:message>
		<display:column title="${tattooEdit}">
			<spring:url value="tattoo/tattooist/edit.do" var="viewUrl">
				<spring:param name="tattooId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${viewUrl}"> <spring:message code="tattoo.edit"></spring:message>
			</a>

		</display:column>


		<spring:message code="tattoo.deleteH" var="deleteTattoo"></spring:message>
		<display:column title="${deleteTattoo}">
			<form:form action="tattoo/tattooist/delete.do?tattooId=${row.id}" modelAttribute="tattoo">

				<button type="submit" name="delete"
					onclick="return confirm('<spring:message code="tattoo.deleteConfir"></spring:message>')">ELIMINAR</button>
			</form:form>
		</display:column>

		<spring:message code="tattoo.viewH" var="viewH"></spring:message>
		<display:column title="${viewH}">
			<spring:url value="tattoo/tattooist/view.do" var="viewUrl">
				<spring:param name="tattooId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${viewUrl}"> <spring:message code="tattoo.view"></spring:message>
			</a>
		</display:column>
	</security:authorize>

	<jstl:if test="${desdeFuera == true}">
		<spring:message code="tattoo.viewH" var="viewH"></spring:message>
		<display:column title="${viewH}">
			<spring:url value="tattoo/view.do" var="viewUrl">
				<spring:param name="tattooId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${viewUrl}"> <spring:message code="tattoo.view"></spring:message>
			</a>
		</display:column>
	</jstl:if>

	<security:authorize access="hasRole('CUSTOMER')">
		<spring:message code="tattoo.viewH" var="viewH"></spring:message>
		<display:column title="${viewH}">
			<spring:url value="tattoo/customer/view.do" var="viewUrl">
				<spring:param name="tattooId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${viewUrl}"> <spring:message code="tattoo.view"></spring:message>
			</a>
		</display:column>

		<spring:message code="tattoo.buy" var="buyH"></spring:message>
		<display:column title="${buyH}">
			<spring:url value="buy/customer/summary.do" var="viewUrl">
				<spring:param name="tattooId" value="${row.id}"></spring:param>
			</spring:url>
			<a href="${viewUrl}"> <spring:message code="tattoo.buy"></spring:message>
			</a>
		</display:column>
	</security:authorize>
</display:table>
-->

	<security:authorize access="hasRole('TATTOOIST')">
		<jstl:if test="${verEdit==true}">
			<center>
				<acme:button code="tattoo.create" name="create" type="button"
					url="tattoo/tattooist/create.do" />
			</center>
		</jstl:if>
	</security:authorize>

</div>


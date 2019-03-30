<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<input type="text" id="busquedaCustomer" autofocus="autofocus">
<input type="button" id="botonAdd" name="add" value="<spring:message code="template.add" />" onclick="generateURL()" />

<script type="text/javascript">
	var wage = document.getElementById("busquedaCustomer");
	wage.addEventListener("keydown", function(e) {
		if (e.keyCode === 13) { //checks whether the pressed key is "Enter"
			generateURL();
		}
	});
	function generateURL() {
		window.location.href = "tattoo/tattooist/addPermisedCustomers.do?tattooId=${tattooId}&permisedCustomer=" + document.getElementById("busquedaCustomer").value;
	}
</script>
<%-- 	document.location.href= 'tattoo/tattooist/addPermisedCustomers.do?tattooId=${tattooId}&permisedCustomer=' --%>

<display:table name="permisedCustomers" class="displaytag" id="row" requestURI="${requestURI}" pagesize="5">
	<display:column>
		${row}
	</display:column>
	<display:column>
		<input type="button" name="back" value="<spring:message code="template.remove" />"
			onclick="document.location.href= 'tattoo/tattooist/removePermisedCustomers.do?tattooId=${tattooId}&permisedCustomer=${row}'"
		/>
	</display:column>
</display:table>

<br />
<br />
<input type="button" name="back" value="<spring:message code="template.back" />" onclick="document.location.href= 'tattoo/tattooist/list.do'" />


<script type="text/javascript">
	window.onload = function () {getElementById("busquedaCustomer").focus();}
</script>
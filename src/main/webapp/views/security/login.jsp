 <%--
 * login.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="j_spring_security_check"  class="form-signin" role="form" modelAttribute="credentials">
   
   <section id="section" class="well">
   <center>
	<form:label id="colorLetras" path="username">
		<spring:message  code="security.username" />
	</form:label>
	</center>
	<center>
	<form:input  id="inputFormText" class="form-control"  path="username" />
	</center>	
	<form:errors class="error" path="username" />
	<br />
	<center>
	<form:label id="colorLetras" path="password">
		<spring:message code="security.password" />
	</form:label>
	</center>
	<center>
	<form:password  id="inputFormText" class="form-control" path="password" />	
	<form:errors class="error" path="password" />
	</center>
	<br />
	
	<center>
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="security.login.failed" />
		</div> <br/>
	</jstl:if>
	</center>
	
	<center>
	<button type="submit" id="button_login" class="btn btn-lg btn-block" ><spring:message code="security.login" /></button>
	</center>
	</section>	
</form:form>



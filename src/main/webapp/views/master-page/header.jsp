<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>


<center>
	<img id="portadaLogo" 
		src="images/Logo_Oficialv3_sinfondototal.png" alt="House of Tattoo." />
</center>

<nav class="navbar navbar-default navbar-inverse container-fluid "
	style="background-color: #8db5df;">

	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse">
			<span class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>
	</div>
	<ul class="nav navbar-nav collapse navbar-collapse">

		<!-- ADMIN -->
		<security:authorize access="hasRole('ADMIN')">

			<!-- Administrator option -->
			<li class="dropdown" id="dmenu"><a
				class="dropdown-toggle navbar-brand fNiv" id="opciones_header"
				data-hover="dropdown" data-toggle="dropdown"> <span
					class="glyphicon glyphicon-briefcase"></span> <spring:message
						code="master.page.administrator" /></a>
		</security:authorize>

		<!-- CUSTOMER -->
		<security:authorize access="hasRole('CUSTOMER')">

			<!-- Customers option -->
			<li class="dropdown" id="dmenu"><a
				class="dropdown-toggle navbar-brand fNiv" id="opciones_header"
				data-hover="dropdown" data-toggle="dropdown"> <spring:message
						code="master.page.customer" /></a>
				<ul class="dropdown-menu" id="subopciones_header">



					<li><a id="subopcion_header" href="tattoo/customer/list.do">
							<span class="glyphicon glyphicon-picture"></span> <spring:message
								code="master.page.tattoo.list" />
					</a></li>

				</ul></li>
		</security:authorize>


		<!-- TATTOIST -->
		<security:authorize access="hasRole('TATTOOIST')">
			<li class="dropdown" id="dmenu"><a
				class="dropdown-toggle navbar-brand fNiv" id="opciones_header"
				data-hover="dropdown" data-toggle="dropdown"> <span
					class="glyphicon glyphicon-lock"></span> <spring:message
						code="master.page.tattooist" />
			</a>
				<ul class="dropdown-menu" id="subopciones_header">
					<li><a id="subopcion_header" href="tattoo/tattooist/list.do">
							<span class="glyphicon glyphicon-picture"></span> <spring:message
								code="master.page.tattoo.tattooist.list" />
					</a></li>

					<li><a id="subopcion_header" href="tattoo/list.do"> <span
							class="glyphicon glyphicon-picture"></span> <spring:message
								code="master.page.tattoo.list" /></a></li>

				</ul></li>
		</security:authorize>

		<!-- ANONYMOUS  -->
		<security:authorize access="isAnonymous()">
			<li><a id="opciones_header" class="fNiv navbar-brand "
				href="security/login.do"> <span
					class="glyphicon glyphicon-log-in"></span> <spring:message
						code="master.page.login" /></a></li>

			<li><a id="opciones_header" class="fNiv navbar-brand"
				href="tattoo/list.do"> <span class="glyphicon glyphicon-picture"></span>
					<spring:message code="master.page.tattoo.list" /></a></li>


		</security:authorize>

		<!-- AUTHENTICATED -->
		<security:authorize access="isAuthenticated()">








			<security:authorize access="isAuthenticated()">
				<security:authorize access="!hasRole('BANNED')">
					<li class="fNiv dropdown" id="dmenu"><a id="opciones_header"
						class="navbar-brand fNiv" href="messageFolder/actor/list.do"><span
							class="glyphicon glyphicon-envelope"></span>&nbsp;<spring:message
								code="master.page.messagerie" /> </a></li>




				</security:authorize>
			</security:authorize>


		</security:authorize>
	</ul>

	<security:authorize access="isAuthenticated()">
		<ul class="nav navbar-nav navbar-right">


			<li class="dropdown" id="dmenu"><a
				class="dropdown-toggle navbar-brand fNiv" id="opciones_header"
				data-hover="dropdown" data-toggle="dropdown"> <span
					class="glyphicon glyphicon-lock"></span> <spring:message
						code="master.page.account" />
			</a>
					<security:authorize access="hasRole('TATTOOIST')">
					<ul class="dropdown-menu" id="subopciones_header">
					
					<li><a id="subopcion_header" href="tattooist/tattooist/delete.do" onclick="return confirm('<spring:message code="master.page.delete.actor.corfim"></spring:message>')">
							<span class="glyphicon glyphicon-remove"></span>&nbsp;<spring:message
									code="master.page.delete.actor" />
					</a></li>
									
					<li><a id="subopcion_header" href="data/tattooist/generate.do" onclick="return confirm('<spring:message code="master.page.download.actor.confirm"></spring:message>')">
							<span class="glyphicon glyphicon-download-alt"></span>&nbsp;<spring:message
									code="master.page.downloadData" />
					</a></li>
									</ul>
					</security:authorize>

					<security:authorize access="hasRole('CUSTOMER')">
					<ul class="dropdown-menu" id="subopciones_header">
					
					<li><a id="subopcion_header" href="customer/customer/delete.do" onclick="return confirm('<spring:message code="master.page.delete.actor.corfim"></spring:message>')">
							<span class="glyphicon glyphicon-remove"></span>&nbsp;<spring:message
									code="master.page.delete.actor" />
					</a></li>
					<li><a id="subopcion_header" href="data/customer/generate.do" onclick="return confirm('<spring:message code="master.page.download.actor.confirm"></spring:message>')">
							<span class="glyphicon glyphicon-download-alt"></span>&nbsp;<spring:message
									code="master.page.downloadData" />
					</a></li>

						
					</ul>
					</security:authorize>

				</li>

			<li class="dropdown" id="dmenu"><a id="opciones_header"
				class="navbar-brand fNiv" href="j_spring_security_logout"><span
					class="glyphicon glyphicon-log-in"></span>&nbsp;<spring:message
						code="master.page.logout" /> </a></li>
			
		</ul>

	</security:authorize>

</nav>

</br>
<div class="container-fluid ">
	<a href="?language=es"><img class="img-fluid"
		alt="Responsive image" id="bandera"
		src="https://vignette.wikia.nocookie.net/reino-de-quito-1809/images/5/54/Bandera_espa%C3%B1ola.png/revision/latest?cb=20150508004531&path-prefix=es"></a>
	| <a href="?language=en"><img class="img-fluid"
		alt="Responsive image" id="bandera"
		src="https://i.pinimg.com/originals/99/bc/c4/99bcc439d84e544290a59fee831053c5.jpg"></a>
</div>


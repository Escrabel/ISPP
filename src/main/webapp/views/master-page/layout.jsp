<%--
 * layout.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1"><!-- Esta linea para hacerlo responsive en movil -->

<!-- Boostrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-hover-dropdown/2.2.1/bootstrap-hover-dropdown.js"></script>

<link rel="shortcut icon" href="favicon.ico"/> 

<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jmenu.js"></script>
<script type="text/javascript" src="scripts/welcomePage.js"></script>


<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.7.5/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.0/angular-messages.js"></script>
<script src="scripts/dirPagination.js"></script>
<script src="scripts/ng-table/ng-table.js"></script>
<link rel="stylesheet" href="scripts/ng-table/ng-table.css">
<script type="text/javascript" src="scripts/scriptAngular.js"></script>
<script type="text/javascript" src="scripts/directivesAngular.js"></script>

<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/jmenu.css" media="screen" type="text/css" />
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">
<link rel="stylesheet" href="styles/imgUpload.css" type="text/css">

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript">
	$(document).ready(function() {
		$("#jMenu").jMenu();
	});

	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}
	
	function relativeRedir(loc) {	
		var b = document.getElementsByTagName('base');
		if (b && b[0] && b[0].href) {
  			if (b[0].href.substr(b[0].href.length - 1) == '/' && loc.charAt(0) == '/')
    		loc = loc.substr(1);
  			loc = b[0].href + loc;
		}
		window.location.replace(loc);
	}
</script>

</head>

<body>

	<div class="container">
			<div class="abs-center">
			<center>
		<tiles:insertAttribute name="header" />
		</center>
	</div>
	</div>
	
	<div class="container">
		<div class="abs-center">
		<center><h1><tiles:insertAttribute name="title" /></h1></center>
	
		<tiles:insertAttribute name="body" />	
		
		<center>
		<jstl:if test="${message != null and vistaMensaje eq null}">
			<br />
			<span class="message"><spring:message code="${message}" /></span>
		</jstl:if>	
		<jstl:if test="${messageError != null}">
			<br />
			<span class="message"><spring:message code="${messageError}" /></span>
		</jstl:if>	
		</center>
		</div>
		</div>
	
	<center>
		<tiles:insertAttribute name="footer" />
	</center>

</body>
</html>
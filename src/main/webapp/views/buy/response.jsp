<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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
<script type="text/JavaScript">
	$(document).ready(function() {

		$('#normalButton').click(function() {
			alert("<spring:message code="buy.complete"></spring:message>");
			   location.href ="tattoo/customer/list.do";
		});
	});
</script>


<div ng-controller="TattooController"  ng-init="initVariables()">
</br></br></br></br>
<center>
<jstl:if test="${!error}">

<!--  
<div class="container-fluid">
	
    <div class="content-wrapper">	
		<div class="item-container">	
			<div class="row">	
			
				<div ng-init="getImgFromTattooId(tattoo.id,$index)" class="col-md-20">
				
					<div class="product col-md-3 service-image-left">
                    
						<img id="foto" class="card-img-top foto{{$index}}" src="" alt="">
						
						
					</div>
					
				</div>
					
				<div class="col-md-7">
					
					<div class="product-title">{{tattoo.name}}</div>
					<div class="product-desc">${tattoo.description }</div>
					<div class="product-rating"><i class="fa fa-star gold"></i> <i class="fa fa-star gold"></i> <i class="fa fa-star gold"></i> <i class="fa fa-star gold"></i> <i class="fa fa-star-o"></i> </div>
					<hr>
					<div class="product-price">${tattoo.price }&#8364</div>
					<hr>-->
					<div class="btn-group cart">
					
					<tr>
						<td align="center"></td>
					</tr>
					<tr>
					<td align="center"><img
						src="https://www.paypalobjects.com/webstatic/mktg/logo-center/logotipo_paypal_pagos.png"
						border="0" alt="Pague con PayPal" /></a> <input type="radio"
						value="Paypal" name="Paypal" checked="checked"> Paypal
		
						<!-- esta linea quitarla cuando se vaya a mejorar la vista -->
					</td>
					</tr>
					<br/> <br/>
					<center>
						<input name="button" type="button"
								value="<spring:message code="buy.finish" />" id="normalButton"
								style="background-color: #8db5df; color: white; font-weight: bold;"
								class="btn btn-default"/> 
					</div>
					</center>
					</center>
		<!--  
				</div>
				
			</div> 
		</div>
		
		</div>
		
		</div>
		</div>
-->

</jstl:if>

<jstl:if test="${error}">
	<h1 class="fab fa-cc-paypal">
		<spring:message code="buy.error"></spring:message>
	</h1>


</jstl:if>

</div>

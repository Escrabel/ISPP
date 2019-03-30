<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- Lista Angular -->
<script type="text/javascript" src="scripts/message/MessageController.js"></script>
<script>
  app.run(['$rootScope', function($rootScope) {
    $rootScope.window = window
  }])
</script>
<center>
	<div style="color: darkred;">
		<h2>
			<b> <jstl:if test="${box eq 'Bandeja de entrada'}">
					<spring:message code="folder.name.f2" />
				</jstl:if> <jstl:if test="${box eq 'Enviados'}">
					<spring:message code="folder.name.f3" />
				</jstl:if> <jstl:if test="${box eq 'Reclamaciones'}">
					<spring:message code="folder.name.f1" />
				</jstl:if> <jstl:if test="${box eq 'Papelera'}">
					<spring:message code="folder.name.f4" />
				</jstl:if> <jstl:if test="${box != 'Reclamaciones' and box != 'Bandeja de entrada' and box != 'Papelera' and box != 'Enviados'}">
					<jstl:out value="${box}" />
				</jstl:if>
			</b>
		</h2>
	</div>
</center>

<div ng-controller="MessageController" data-ng-init='getMessages("${messageFolderId}")'>

	
		<center>
			<div class="table-resposive">
				<table class="table table-striped table-bordered" style="width: 80%; height: 80%;">
					<spring:message code="message.subject" var="subject"></spring:message>
					<spring:message code="message.sender" var="sender"></spring:message>
					<spring:message code="message.see" var="see"/>
					<spring:message code="message.recipient" var="recipient"></spring:message>
					<spring:message code="message.date" var="date"></spring:message>
<%-- 					<spring:message code="message.priority" var="priority"></spring:message> --%>
					<spring:message code="message.reply" var="reply"/>
					<spring:message code="message.move" var="move"/>
					<spring:message code="message.delete" var="delete"/>
					<spring:message code="tattoo.formatDateForAngular" var="fechaInternacionalizada"/>


					<thead>
						<tr>
							<th id="dateCol" style="background: #8db5df">${date}</th>
							<th style="background-color: #8db5df">${subject}</th>
							<th style="background: #8db5df">${sender}</th>
							<!-- <th style="background: #8db5df">${recipient}</th> -->
							<th style="background: #8db5df">${see}</th>
							<!--  <th style="background: #8db5df">${reply}</th> --> 
							<!--  <th style="background: #8db5df">${move}</th>--> 
							<!-- <th style="background: #8db5df">${delete}</th>--> 
							
	

						</tr>
					</thead>
					<tbody>
					<tr dir-paginate="message in fvar_messages|itemsPerPage:5">
						<td>{{message.date | ${fechaInternacionalizada}}}</td>
						<td>{{message.subject}}</td>
						<td>{{message.actorSender.name}}</td>
						<!--  <td>{{message.actorReceiver.name}}</td>-->
						<td><a href="message/actor/details.do?messageId={{message.id}}"><input type="button" value="${see}" style="background-color: #8db5df; color: white; font-weight: bold;" class="btn btn-default"/></a></td>
						<!--  <td><a href="message/actor/create-r.do?messageId={{message.id}}"><input type="button" value="${reply}" style="background-color: #8db5df; color: white; font-weight: bold;" class="btn btn-default"/></a></td>-->
						<!--  <td><a href="message/actor/move.do?messageId={{message.id}}"><input type="button" value="${move}" style="background-color: #8db5df; color: white; font-weight: bold;" class="btn btn-default" /></a></td>-->
						<!-- <td><a href="message/actor/delete.do?messageId={{message.id}}"><input type="button" value="${delete}" style="background-color: #8db5df; color: white; font-weight: bold;" class="btn btn-default"/></a></td> -->
						</tr> 
						</tbody>
	   				</table>
			</div>
		</center> 
				


					
				

		<dir-pagination-controls max-size="5" direction-links="true"
			boundary-links="true"> </dir-pagination-controls>

	</div>

  

<%-- <center> --%>
<%-- 	<display:table name="mensajes" id="row" requestURI="message/actor/list.do" pagesize="5" class="displaytag" sort="list" defaultsort="4" --%>
<%-- 		defaultorder="descending" --%>
<%-- 	> --%>


		<!-- Action links -->

<%-- 		<spring:message code="message.subject" var="subject"></spring:message> --%>
<%-- 		<display:column property="subject" title="${subject}"></display:column> --%>
<%-- 		<spring:message code="message.sender" var="sender"></spring:message> --%>
<%-- 		<display:column property="actorSender.userAccount.username" title="${sender}"></display:column> --%>
<%-- 		<spring:message code="message.recipient" var="recipient"></spring:message> --%>
<%-- 		<display:column property="actorReceiver.userAccount.username" title="${recipient}"></display:column> --%>
<%-- 		<spring:message code="message.date" var="date"></spring:message> --%>
<%-- 		<spring:message code="message.formatDate" var="dateFormat"></spring:message> --%>
<%-- 		<display:column property="date" title="${date}" format="${dateFormat}"></display:column> --%>
<%-- 		<spring:message code="message.priority" var="priority"></spring:message> --%>
<%-- 		<display:column title="${priority}"> --%>
<%-- 			<jstl:if test="${row.priority=='LOW'}"> --%>
<!-- 				<div style="color: green;"> -->
<%-- 					<b>${row.priority}</b> --%>
<!-- 				</div> -->
<%-- 			</jstl:if> --%>	
<%-- 			<jstl:if test="${row.priority=='NEUTRAL'}"> --%>
<!-- 				<div style="color: blue;"> -->
<%-- 					<b>${row.priority}</b> --%>
<!-- 				</div> -->
<%-- 			</jstl:if> --%>
<%-- 			<jstl:if test="${row.priority=='HIGH'}"> --%>
<!-- 				<div style="color: red;"> -->
<%-- 					<b>${row.priority}</b> --%>
<!-- 				</div> -->
<%-- 			</jstl:if> --%>
<%-- 		</display:column> --%>

<%-- 		<spring:message code="message.see" var="see" /> --%>
<%-- 		<display:column title="${see}"> --%>
<%-- 			<input type="button" value="${see}" id="normalButton" style="background-color: #8db5df; color: white; font-weight: bold;" class="btn btn-default" --%>
<%-- 				onclick="document.location.href= 'message/actor/details.do?messageId=${row.id}';" --%>
<!-- 			/> -->
<%-- 		</display:column> --%>

<%-- 		<spring:message code="message.reply" var="reply" /> --%>
<%-- 		<display:column title="${reply}"> --%>
<%-- 			<input type="button" value="${reply}" id="normalButton" style="background-color: #8db5df; color: white; font-weight: bold;" class="btn btn-default" --%>
<%-- 				onclick="document.location.href= 'message/actor/create-r.do?messageId=${row.id}';" --%>
<!-- 			/> -->
<%-- 		</display:column> --%>

<%-- 		<spring:message code="message.move" var="move" /> --%>
<%-- 		<display:column title="${move}"> --%>
<%-- 			<input type="button" id="normalButton" style="background-color: #8db5df; color: white; font-weight: bold;" class="btn btn-default" value="${move}" --%>
<%-- 				onclick="document.location.href= 'message/actor/move.do?messageId=${row.id}';" --%>
<!-- 			/> -->
<%-- 		</display:column> --%>


<%-- 		<spring:message code="message.delete" var="delete" /> --%>
<%-- 		<display:column title="${delete}"> --%>
<%-- 			<input type="button" id="normalButton" style="background-color: #8db5df; color: white; font-weight: bold;" class="btn btn-default" value="${delete}" --%>
<%-- 				onclick="document.location.href= 'message/actor/delete.do?messageId=${row.id}';" --%>
<!-- 			/> -->
<%-- 		</display:column> --%>
<%-- 	</display:table> --%>
<%-- </center> --%>



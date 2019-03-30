<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- Lista Angular -->
<script type="text/javascript" src="scripts/messageFolder/MessageFolderController.js"></script>
<div ng-controller="MessageFolderController" data-ng-init='getMessageFolders()'>

	<center>
		<div class="table-resposive">
			<table class="table table-striped table-bordered" style="width: 80%; height: 80%;">
				<spring:message code="folder.name" var="name" />
				<spring:message code="folder.edit" var="edit" />
				<spring:message code="folder.messages" var="messages" />


				<thead>
					<tr>
						<th style="background-color: #8db5df">${name}</th>
						<th style="background: #8db5df">${edit}</th>
						<th style="background: #8db5df">${messages}</th>
						<%-- <th >${name}</th>
						<th >${edit}</th>
						<th>${messages}</th> --%>
					</tr>
				</thead>
				<tbody>
					<tr dir-paginate="messageFolder in fvar_messageFolders|itemsPerPage:5">
						<td><span ng-show="messageFolder.name == 'Reclamaciones'"> <spring:message code="folder.name.f1" />
						</span> <span ng-show="messageFolder.name == 'Bandeja de entrada'"> <spring:message code="folder.name.f2" />
						</span> <span ng-show="messageFolder.name == 'Enviados'"> <spring:message code="folder.name.f3" />
						</span> <span ng-show="messageFolder.name == 'Papelera'"> <spring:message code="folder.name.f4" />
						</span> <span
							ng-show="!(messageFolder.name == 'Papelera' || messageFolder.name == 'Enviados' || messageFolder.name == 'Reclamaciones' || messageFolder.name == 'Bandeja de entrada')"
						> {{messageFolder.name}} </span></td>

						<td><security:authorize access="isAuthenticated()">
								<div ng-if="messageFolder.isModificable">
									<a href="messageFolder/actor/edit.do?folderId={{messageFolder.id}}"> <spring:message code="folder.edit" />
									</a>
								</div>
							</security:authorize></td>

						<td><a href="message/actor/list.do?messageFolderId={{messageFolder.id}}"> <spring:message code="folder.messages" />
						</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</center>

	<dir-pagination-controls max-size="5" direction-links="true" boundary-links="true"> </dir-pagination-controls>

</div>

<!-- 
<div class="table row text-center">
	
			<ul dir-paginate = "messageFolder in fvar_messageFolders|itemsPerPage:5">
				<div class="col-lg-3 col-md-6 mb-4 img-resposive" >
				<div class="card h-100">
					<div class="card-body">
						<h4 id="titulo2" class="card-title">{{messageFolder.name}}</h4>
					</div>
					<div class="card-footer">
					<a href="message/actor/list.do?messageFolderId={{messageFolder.id}}"> 
						<spring:message code="folder.messages" />
						</a>
					</div>
					</div>
				</div>
   			</ul>
		
</div>
 -->


<!-- <dir-pagination-controls max-size="6" direction-links="true" boundary-links="true"> </dir-pagination-controls> -->



<%-- <display:table name="folders" id="row" requestURI="messageFolder/actor/list.do" --%>
<%-- 	pagesize="5" class="displaytag"> --%>


<!-- Action links -->



<!-- Attributes -->


<%-- 	<spring:message code="folder.name" var="name"></spring:message> --%>
<%-- 	<display:column property="name" title="${name}"></display:column> --%>

<%-- 	<security:authorize access="isAuthenticated()"> --%>
<%-- 	<spring:message code="folder.edit" var="edit"/> --%>
<%-- 	<display:column title="${edit}"> --%>
<%-- 		<jstl:if test="${row.isModificable}"> --%>
<%-- 			<a href="messageFolder/actor/edit.do?folderId=${row.id}">  --%>
<%-- 				<spring:message code="folder.edit" /> --%>
<!-- 			</a>	 -->
<%-- 		</jstl:if> --%>
<%-- 	</display:column> --%>

<%-- 	<spring:message code="folder.messages" var="messages"/> --%>
<%-- 	<display:column title="${messages}"> --%>
<%-- 		<a href="message/actor/list.do?messageFolderId=${row.id}">  --%>
<%-- 			<spring:message code="folder.messages" /><jstl:out value=" (${row.messages.size()})"></jstl:out> --%>
<!-- 		</a>	 -->
<%-- 	</display:column> --%>
<%-- 	</security:authorize> --%>


<%-- </display:table> --%>


<security:authorize access="isAuthenticated()">

	<center>
		<acme:button code="folder.create" name="create" type="button" url="messageFolder/actor/create.do" />
	</center>

</security:authorize>

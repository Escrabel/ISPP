<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<form:form action="message/actor/move.do"  role="form" class="abs-center form-signin"  modelAttribute="message">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="date" />
	<form:hidden path="actorSender" />
	<form:hidden path="actorReceiver" />
	<form:hidden path="subject" />
	<form:hidden path="body" />
<%-- 	<form:hidden path="priority" /> --%>
<center>
	<form:label path="messageFolder">
		<spring:message code="message.folder" />:
	</form:label>
	<form:select  class="form-control"  id="inputFormText2" path="messageFolder">
		<jstl:forEach items="${folders}" var="x">
			<jstl:if test="${x.name eq 'Bandeja de entrada'}">
				<spring:message code="folder.name.f2" var="f2" />
				<form:option value="${x.id}" label="${f2}" />
			</jstl:if>
			<jstl:if test="${x.name eq 'Enviados'}">
				<spring:message code="folder.name.f3" var="f3" />
				<form:option value="${x.id}" label="${f3}" />
			</jstl:if>
			<jstl:if test="${x.name eq 'Papelera'}">
				<spring:message code="folder.name.f4" var="f4" />
				<form:option value="${x.id}" label="${f4}" />
			</jstl:if>
			<jstl:if test="${x.name eq 'Reclamaciones'}">
				<spring:message code="folder.name.f1" var="f1" />
				<form:option value="${x.id}" label="${f1}" />
			</jstl:if>
			<jstl:if test="${x.name != 'Reclamaciones' and x.name != 'Bandeja de entrada' and x.name != 'Papelera' and x.name != 'Enviados'}">
				<form:option value="${x.id}" label="${x.name}" />
			</jstl:if>
		</jstl:forEach>
	</form:select>
	<form:errors cssClass="error" path="messageFolder" />
	<br />
	<br />

	<input type="submit" name="save" style="background-color: #8db5df; color: white; font-weight: bold;" class="btn btn-default"
		value="<spring:message code="message.save" />"
	/>&nbsp;
		
	<input type="button" name="cancel" value="<spring:message code="message.cancel" />" style="background-color: #8db5df; color: white; font-weight: bold;"
		class="btn btn-default" onclick="document.location.href= 'message/actor/list.do?messageFolderId=${message.messageFolder.id}';"
	/>
	<br />

</form:form>
</center>
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


<form:form action="messageFolder/actor/edit.do" role="form" class="abs-center form-signin" modelAttribute="messageFolder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actor" />
	<form:hidden path="messages" />
	<form:hidden path="isModificable" />

	<center>
		<form:label path="name">
			<spring:message code="folder.name" />:
	</form:label>
		<form:errors cssClass="error" class="form-control" path="name" />
		<form:input id="inputFolder" class="form-control" path="name" />
	</center>
	<br />

	<br />

	<center>
		<input type="submit" name="save" class="btn btn-primary" id="button" value="<spring:message code="folder.save" />" />&nbsp;



		<jstl:if test="${messageFolder.id != 0}">
			<jstl:if test="${fn:length(messageFolder.messages) eq 0}">
				<input type="submit" id="normalButton" style="background-color:#8db5df; color:white; font-weight: bold;" class="btn btn-default"  name="delete" value="<spring:message code="folder.delete" />"
					onclick="return confirm('<spring:message code="folder.confirm.delete" />')"
				/>&nbsp;
				
		</jstl:if>
			<jstl:if test="${fn:length(messageFolder.messages) gt 0}">
				<input id="normalButton" style="background-color:#8db5df; color:white; font-weight: bold;" class="btn btn-default"  type=button name="delete" value="<spring:message code="folder.delete" />"
					onclick="return alert('<spring:message code="folder.confirm.undelete" />')"
				/>&nbsp;
		 
				</jstl:if>
		</jstl:if>
		<input type="button" name="cancel" id="button" class="btn btn-primary" value="<spring:message code="folder.cancel" />"
			onclick="document.location.href= 'messageFolder/actor/list.do';"
		/> <br />
	</center>
</form:form>

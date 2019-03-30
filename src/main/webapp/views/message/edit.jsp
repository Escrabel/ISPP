<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form:form action="message/actor/edit-${tipo}.do" role="form" class="abs-center form-signin" modelAttribute="message">
		<section id="section" class="well">
		<center>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="date"/>
	<form:hidden path="actorSender"/>
	<form:hidden path="actorReceiver"/>
	<form:hidden path="messageFolder"/>
	<form:hidden path="buyTicker"/>
	
	<spring:message code="message.recipient" />: <b><jstl:out value="${message.actorReceiver.userAccount.username}"/></b>
	
	<br/><br/>
	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<form:input id="inputFormText" class="form-control" path="subject" />
	<form:errors class="form-control"  cssClass="error" path="subject" />
	<br/><br/>
	
	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label><br/>
	<form:textarea id="inputFormText" class="form-control" path="body" cols="70" rows="10" style="resize: vertical;"/>
	<form:errors class="form-control"  cssClass="error" path="body" />
	
		
<%-- 	<form:label path="priority"> --%>
<%-- 		<spring:message code="message.priority" />: --%>
<%-- 	</form:label> --%>
<%-- 	<form:select path="priority"> --%>
<%-- 		<form:options id="inputFormText"  class="form-control" items="${priorities}"/> --%>
<%-- 	</form:select> --%>
<%-- 	<form:radiobutton path="priority" id="LOW"/> --%>
	
<%-- 	<form:radiobutton path="priority" id="MEDIUM"/> --%>
	
<%-- 	<form:radiobutton path="priority" id="HIGH"/> --%>
<%-- 	<form:errors cssClass="error" path="priority" /> --%>
	<br />
	<br/>
	
	<input id="normalButton" style="background-color:#8db5df; color:white; font-weight: bold;" class="btn btn-default"  type="submit" name="send-${tipo}"
		value="<spring:message code="message.send" />" />&nbsp;
	<input id="normalButton" style="background-color:#8db5df; color:white; font-weight: bold;" class="btn btn-default"  type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="document.location.href= 'messageFolder/actor/list.do'" />
	<br />
</center>
</section>
</form:form>
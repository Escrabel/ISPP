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


<section id="section" class="well">
		<center>
	<b><jstl:out value=' ${message.actorReceiver.userAccount.username}'/></b></br>
	<spring:message code="message.to" /><b><jstl:out value=': ${message.actorSender.userAccount.username}'/></b></br>
<%-- 	<li><spring:message code="message.priority" /><b><jstl:out value=': ${message.priority}'/></b></li> --%>
	<spring:message code="message.date" /><b><jstl:out value=': ${message.date}'/></b></br>
	<spring:message code="message.subject" /><b><jstl:out value=': ${message.subject}'/></b></br>
	<spring:message code="message.body" /><b>:</b><br/><textarea id="mytextarea"><jstl:out value='${message.body}'/></textarea>
</center>
</section>


<center>

<a class="btn btn-primary"href="message/actor/create-r.do?messageId=${message.id}"
id="button"><spring:message code="message.reply"></spring:message></a>

<a class="btn btn-primary" href="message/actor/move.do?messageId=${message.id}" id="button">
<spring:message code="message.move"></spring:message></a>

<a class="btn btn-primary"href="message/actor/delete.do?messageId=${message.id}"
id="button"><spring:message code="message.delete"></spring:message></a>
						
</center>

<script type="text/javascript">
var textarea = document.getElementById("mytextarea");
textarea.style.border = 'none';
textarea.style.outline = 'none';
textarea.style.borderColor = 'Transparent';
textarea.style.resize = 'none';
textarea.style.width = '70%';
textarea.style.height = textarea.scrollHeight + "px";
</script>

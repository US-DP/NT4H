<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ attribute name="loggedPlayer" required="true" rtexprvalue="true" type="org.springframework.samples.nt4h.player.Player"%>
<%@ attribute name="chat" required="true" rtexprvalue="true" type="org.springframework.samples.nt4h.message.Message"%>

<div class="row">
    <h2>Chatea</h2>
    <div class="chatGroup"></div>
    <c:if test="${!loggedPlayer.isNew()}">
        <form:form modelAttribute="chat" class="form-horizontal" action="/messages/game">
            <nt4h:inputField label="Content" name="content"/>
        </form:form>
    </c:if>
    <c:if test="${loggedPlayer.isNew()}">
        <a href="/turns">Reload</a>
    </c:if>
</div>
<script src="/resources/js/chatGroup.js" type="module"></script>
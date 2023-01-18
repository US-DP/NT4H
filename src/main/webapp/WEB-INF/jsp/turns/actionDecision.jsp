<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Action decision">
    <jsp:attribute name="customScript">
        <script src="/resources/js/radioButtom.js" type="module"></script>
    <script src="/resources/js/currentTurn.js" type="module"></script>
    </jsp:attribute>
    <jsp:body>
        <h2>Action decision</h2>
        <h1>Turno del jugador ${currentPlayer}</h1>
        <c:set var="evasion" value="/resources/images/rajoy.png"/>
        <c:set var="attack" value="/resources/images/espada.png"/>
        <c:if test="${!loggedPlayer.isNew()}">
            <nt4h:startPlayer evasion="${evasion}" attack="${attack}" newTurn="${newTurn}" turns="${turns}"/>
        </c:if>
        <c:if test="${loggedPlayer.isNew()}">
            <nt4h:startSpectator evasion="${evasion}" attack="${attack}"/>
        </c:if>
        <hr>
        <nt4h:chatGroup loggedPlayer="${loggedPlayer}" chat="${chat}"/>
        <nt4h:continue/>
    </jsp:body>
</nt4h:layout>

<style>
    .pointer {
        display: flex;
        justify-content: center;
    }
</style>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="lobby">
    <jsp:attribute name="customScript">
        <script src="/resources/js/playersInLobby.js" type="module"></script>
    </jsp:attribute>
    <jsp:body>
        <h2>Game Lobby</h2>
        <div class="ready"></div>
        <div>
            <c:if test="${loggedPlayer.isNew()}">
                <h1>You are watching the game!</h1>
            </c:if>
            <c:if test="${loggedPlayer.ready}">
                <h1>You are ready!</h1>
            </c:if>
            <c:if test="${!loggedPlayer.ready && !loggedPlayer.isNew()}">
                <a href="/games/heroSelect">Add hero!</a>
            </c:if>
        </div>
        <div class="next"></div>
        <hr>
        <nt4h:chatGroup loggedPlayer="${loggedPlayer}" chat="${chat}"/>
    </jsp:body>
</nt4h:layout>

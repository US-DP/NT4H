<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Reestablishment Phase">
    <body class="background">
    <h1>Reestablishment Phase</h1>
    <h2>${currentPlayer}`s Turn</h2>
    <nt4h:playerInfo currentPlayer="${currentPlayer}"/>
    <nt4h:enemySpectator orcs="${game.actualOrcs}"/>
    <c:if test="${!loggedPlayer.isNew()}">
        <form:form modelAttribute="newTurn" class="form-horizontal" id="choose-phases-form">
            <c:out value="In hand: ${currentPlayer.deck.inHand.size()} - In deck: ${currentPlayer.deck.inDeck.size()} - In Discard: ${currentPlayer.deck.inDiscard.size()}"/>
            <nt4h:abilityPlayer abilities="${currentPlayer.deck.inHand}" j="0"/>
            <c:if test="${currentPlayer.deck.inHand.size() > 0}">
                <button class="btn btn-default" type="submit">Discard Ability</button>
            </c:if>
        </form:form>
    </c:if>
    <c:if test="${loggedPlayer.isNew()}">
        <div class="container">
            <c:out value="In hand: ${currentPlayer.deck.inHand.size()} - In deck: ${currentPlayer.deck.inDeck.size()} - In Discard: ${currentPlayer.deck.inDiscard.size()}"/>
            <nt4h:abilitySpectator abilities="${currentPlayer.deck.inHand}"/>
        </div>
    </c:if>
    <hr>
    <nt4h:chatGroup loggedPlayer="${loggedPlayer}" chat="${chat}"/>
    <nt4h:continue/>
</nt4h:layout>

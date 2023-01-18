<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Hero Attack Action">
    <jsp:attribute name="customScript">
        <script type="text/javascript">
            if (${game.currentTurn.phase.toString()=='enemyAttack'})
                alert("The orcs makes you " + ${damage} +".");
        </script>
    </jsp:attribute>
    <jsp:body>
        <h1>${game.currentPlayer}`s Turn</h1>
        <nt4h:playerInfo currentPlayer="${currentPlayer}"/>
        <c:if test="${!loggedPlayer.isNew()}">
            <form:form modelAttribute="newTurn" class="form-horizontal" id="choose-phases-form">
                <nt4h:enemyPlayer orcs="${game.actualOrcs}"/>
                <div class="container">
                    <c:out value="In hand: ${currentPlayer.deck.inHand.size()} - In deck: ${currentPlayer.deck.inDeck.size()} - In Discard: ${currentPlayer.deck.inDiscard.size()}"/>
                    <nt4h:abilityPlayer abilities="${currentPlayer.deck.inHand}"/>
                </div>
                <c:if test="${game.actualOrcs.size()!=0 && currentPlayer.deck.inHand.size()!=0 && game.currentTurn.phase.toString()!='enemyAttack'}">
                    <button class="btn btn-default" type="submit">Attack</button>
                </c:if>
            </form:form>
        </c:if>
        <c:if test="${logggedPlayer.isNew() }">
            <nt4h:enemySpectator orcs="${game.actualOrcs}"/>
            <div class="container">
                <c:out value="In hand: ${currentPlayer.deck.inHand.size()} - In deck: ${currentPlayer.deck.inDeck.size()} - In Discard: ${currentPlayer.deck.inDiscard.size()}"/>
                <nt4h:abilitySpectator abilities="${currentPlayer.deck.inHand}"/>
            </div>
        </c:if>
        <hr>
        <nt4h:chatGroup loggedPlayer="${loggedPlayer}" chat="${chat}"/>
        <nt4h:continue/>
    </jsp:body>
</nt4h:layout>



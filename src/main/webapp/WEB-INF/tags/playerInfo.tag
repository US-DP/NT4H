<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="currentPlayer" required="true" rtexprvalue="true" type="org.springframework.samples.nt4h.player.Player" %>

<div class="container">
    <c:if test="${currentPlayer.health > 0}">
        <c:forEach var="i" begin="0" end="${currentPlayer.health-1}">
            <img src="/resources/images/heart_hero.gif" width="50" height="50">
        </c:forEach>
    </c:if>
    <c:if test="${currentPlayer.health <= 0}">
        <p>You are dead!</p>
    </c:if>
    <p style="font-size:5rem">
        <img src="/resources/images/gloria.gif" width="50" height="50"> ${currentPlayer.statistic.glory}
        <img src="/resources/images/coin.gif" width="50" height="50"> ${currentPlayer.statistic.gold}
    </p>
</div>
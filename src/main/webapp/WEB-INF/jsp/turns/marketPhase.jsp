<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>

<nt4h:layout pageName="Market phase">
    <h1>Market phase</h1>
    <h1>${currentPlayer}`s Turn</h1>
    <h2>Buy a product</h2>
    <nt4h:playerInfo currentPlayer="${currentPlayer}"/>
    <c:if test="${!loggedPlayer.isNew()}">
        <nt4h:productPlayer productsOnSale="${productsOnsale}" newTurn="${newTurn}"/>
    </c:if>
    <c:if test="${loggedPlayer.isNew()}">
        <nt4h:productSpectator productsOnSale="${productsOnSale}"/>
    </c:if>
    <hr>
    <nt4h:chatGroup loggedPlayer="${loggedPlayer}" chat="${chat}"/>
    <nt4h:continue/>
</nt4h:layout>

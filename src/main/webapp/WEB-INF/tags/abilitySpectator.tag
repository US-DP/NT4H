<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ attribute name="abilities" required="true" rtexprvalue="true" type="java.util.List"%>

<div class="container">
    <c:if test="${abilities.size()!=0}">
        <c:forEach var="i" begin="0" end="${abilities.size()-1}">
            <c:set var="abilityInGame" value="${abilities.inHand[i]}" scope="page"/>
            <div class="col-sm-2">
                <img src="${abilities.frontImage}" width="90%" height="90%">
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${abilities.size()==0}">
        <div class="display: flex; justify-content: center;">
            <c:out value="You have no abilities in your hand."/>
        </div>
    </c:if>
</div>
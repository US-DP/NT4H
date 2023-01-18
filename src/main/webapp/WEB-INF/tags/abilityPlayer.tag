<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ attribute name="abilities" required="true" rtexprvalue="true" type="java.util.List"%>


<div class="container">
    <c:if test="${abilities.size() != 0}">
        <div class="pointer">
            <c:forEach var="i" begin="0" end="${abilities.size()-1}">
                <c:set var="abilityInGame" value="${abilities[i]}" scope="page"/>

                <div class="col-sm-2">
                    <nt4h:radioButtom name="currentAbility" element="${abilityInGame.id}"
                                      frontImage="${abilityInGame.ability.frontImage}" i="${i}1"
                                      image="/resources/images/muszka.png"/>
                </div>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${abilities.size()==0}">
        <div class="display: flex; justify-content: center;">
            <c:out value="You have no abilities in your hand."/>
        </div>
    </c:if>
</div>

<script src="/resources/js/radioButtom.js" type="module"></script>

<style>
    .card-img-top {
        width: 100%;
        padding-top: 2rem;
        padding-bottom: 2rem;
    }

    .pointer {
        display: flex;
        justify-content: center;
    }
</style>
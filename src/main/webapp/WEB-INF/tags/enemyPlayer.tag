<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ attribute name="orcs" required="true" rtexprvalue="true" type="java.util.List"%>

<div class="container">
    <c:if test="${orcs.size()!=0}">
        <div class="pointer">
            <c:forEach var="i" begin="0" end="${orcs.size()-1}">
                <c:set var="orc" value="${orcs[i]}" scope="page"/>
                <div class="row">
                    <div class="col-8">
                        <p style="font-size:2rem">
                            <c:if test="${!orc.isNightLord()}">
                                <img src="/resources/images/heart_orc.gif" width="50"
                                     height="50"> ${orc.actualHealth}
                            </c:if>
                            <c:if test="${orc.isNightLord()}">
                                <img src="/resources/images/heart_nightlord.gif" width="50"
                                     height="50"> ${orc.actualHealth}
                            </c:if>
                        </p>
                    </div>
                </div>
                <div class="col-sm-2">
                    <nt4h:radioButtom name="currentEnemy" element="${orc.id}"
                                      frontImage="${orc.enemy.frontImage}" i="${i}0"
                                      image="/resources/images/muszka.png"/>
                </div>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${orcs.size()==0}">
        <div class="display: flex; justify-content: center;">
            <c:out value="There are no enemies in the battle."/>
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
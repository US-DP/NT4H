<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ attribute name="productsOnSale" required="true" rtexprvalue="true" type="java.util.List"%>
<%@ attribute name="newTurn" required="true" rtexprvalue="true" type="org.springframework.samples.nt4h.turn.Turn"%>


<form:form modelAttribute="newTurn" class="form-horizontal" id="product-selection-form">
    <div class="container">
        <c:if test="${productsOnSale.size()!=0}">
            <c:out value="Products stored: ${productsOnSale.size()}" />
            <div class="pointer">
                <c:forEach var="i" begin="0" end="${productsOnSale.size()-1}">
                    <c:set var="productInGame" value="${productsOnSale[i]}" scope="page"/>
                    <div class="col-sm-2">
                        <nt4h:radioButtom name="currentProduct" element="${productInGame.id}"
                                          frontImage="${productInGame.product.frontImage}" i="${i}0"
                                          image="/resources/images/muszka.png"/>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${productsOnSale.size()==0}">
            <div class="display: flex; justify-content: center;">
                <c:out value="No products on sale."/>
            </div>
        </c:if>
    </div>
    <c:if test="${productsOnSale.size()!=0}">
        <button class="btn btn-default" type="submit">buy Product</button>
    </c:if>
</form:form>

<script src="/resources/js/radioButtom.js" type="module"></script>

<style>
    .pointer {
        display: flex;
        justify-content: center;
    }
</style>
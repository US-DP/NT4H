<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="productsOnSale" required="true" rtexprvalue="true" type="java.util.List"%>

<div class="container">
    <c:if test="${productsOnSale.size()!=0}">
        <div style="display: flex;justify-content: center;">
            <c:forEach var="i" begin="0" end="${productsOnSale.size()-1}">
                <c:set var="productInGame" value="${productsOnSale[i]}" scope="page"/>
                <div class="col-sm-2">
                    <img src="${productInGame.product.frontImage}" width="90%" height="90%">
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
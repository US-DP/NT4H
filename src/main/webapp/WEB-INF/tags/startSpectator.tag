<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ attribute name="evasion" required="true" rtexprvalue="true" %>
<%@ attribute name="attack" required="true" rtexprvalue="true" %>

<div style="display: flex; justify-content: center;">
    <div class="col-sm-2">
        <img src="${evasion}">
    </div>
    <div class="col-sm-2">
        <img src="${attack}">
    </div>
</div>
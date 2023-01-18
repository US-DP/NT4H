<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nt4h" tagdir="/WEB-INF/tags" %>
<%@ attribute name="evasion" required="true" rtexprvalue="true" %>
<%@ attribute name="attack" required="true" rtexprvalue="true" %>
<%@ attribute name="newTurn" required="true" rtexprvalue="true" type="org.springframework.samples.nt4h.turn.Turn"%>
<%@ attribute name="turns" required="true" rtexprvalue="true" type="java.util.List"%>

<form:form modelAttribute="newTurn" class="form-horizontal" id="choose-phases-form">
    <div class="pointer">
        <div class="col-sm-2">
            <nt4h:radioButtom name="phase" element="${turns[0]}" frontImage="${evasion}" i="00" image="/resources/images/muszka.png"/>
        </div>
        <div class="col-sm-2">
            <nt4h:radioButtom name="phase" element="${turns[1]}" frontImage="${attack}" i="10" image="/resources/images/muszka.png"/>
        </div>
    </div>
    <button class="btn btn-default" type="submit">Action chosen</button>
</form:form>

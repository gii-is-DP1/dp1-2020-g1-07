<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>



<petclinic:layout pageName="slotgames">
    <jsp:body>
        <h2>Slot Game</h2>


        <form:form modelAttribute="slotgame" class="form-horizontal" action="/slotgames/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Jackpot" name="jackpot"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="slotgameId" value="${slotgame.id}"/>
                    <button class="btn btn-default" type="submit">Add Slot Game</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
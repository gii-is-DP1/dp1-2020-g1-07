<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="games">
    <jsp:body>
        <h2>Game</h2>


        <form:form modelAttribute="game" class="form-horizontal" action="/games/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                <div class="control-group">
                	<petclinic:inputField label="Max Players" name="maxPlayers"/>
                </div>
                <div class="control-group">
                	<petclinic:selectField label="Game Type" name="gametype" names="${gametypes}" size="1"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="gameId" value="${game.id}"/>
                    <button class="btn btn-default" type="submit">Add Game</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
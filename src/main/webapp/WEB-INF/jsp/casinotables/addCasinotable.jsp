<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="casinotables">
    <jsp:body>
        <h2>Casino Table</h2>


        <form:form modelAttribute="casinotable" class="form-horizontal" action="/casinotables/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Game" name="game"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="casinotableId" value="${casinotable.id}"/>
                    <button class="btn btn-default" type="submit">Add Casino Table</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
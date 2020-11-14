<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="clients">
    <jsp:body>
        <h2>Client</h2>


        <form:form modelAttribute="client" class="form-horizontal" action="/clients/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Dni" name="dni"/>
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="phone_number" name="phone_number"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="clientId" value="${client.id}"/>
                    <button class="btn btn-default" type="submit">Add Client</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="maintenanceWorkers">
    <jsp:body>
        <h2>New Maintenance Worker</h2>
        <form:form modelAttribute="maintenanceWorker" class="form-horizontal" action="/maintenanceWorkers/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Dni" name="dni"/>
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Phone Number" name="phone_number"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="maintenanceWorkerId" value="${maintenanceWorker.id}"/>
                    <button class="btn btn-default" type="submit">Add Maintenance Worker</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
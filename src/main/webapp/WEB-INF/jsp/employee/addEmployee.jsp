<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="employee">
    <jsp:body>
        <h2>Employee Table</h2>
        <form:form modelAttribute="employee" class="form-horizontal" action="/employee/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Dni" name="dni"/>
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="PhoneNumber" name="phoneNumber"/>         
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="employeeId" value="${employee.id}"/>
                    <button class="btn btn-default" type="submit">Add Employee</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
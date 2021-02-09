<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <jsp:body>
        <h2>New User</h2>
        <c:out value="${error}"></c:out>
        <form:form modelAttribute="client" class="form-horizontal" action="/users/saveclient">
            <div class="form-group has-feedback">
             	<petclinic:inputField label="Dni" name="dni"/>
             	<petclinic:inputField label="Name" name="name"/>
             	<petclinic:inputField label="Phone Number" name="phone_number"/>
                <petclinic:inputField label="Username" name="user.username"/>
                <petclinic:inputField label="Password" name="user.password"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="clientId" value="${client.id}"/>
                    <button class="btn btn-default" type="submit">Register</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
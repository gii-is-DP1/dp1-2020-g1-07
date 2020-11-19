<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="waiters">
    <jsp:body>
        <h2>New waiter</h2>
        <form:form modelAttribute="waiter" class="form-horizontal" action="/waiters/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Dni" name="dni"/>
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Phone Number" name="phoneNumber"/>
                <div class="control-group">
                    <petclinic:selectField label="Shift" name="shift" names="${shifts}" size="1"/>
                </div>      
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="waiterId" value="${waiter.id}"/>
                    <button class="btn btn-default" type="submit">Add Waiter</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
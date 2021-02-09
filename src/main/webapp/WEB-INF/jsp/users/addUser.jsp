<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <jsp:body>
        <h2>New User</h2>
        <form:form modelAttribute="employee" class="form-horizontal" action="/users/save">
            <div class="form-group has-feedback">
             	Employee <select name="dni">
                		<c:forEach var="emp" items="${emps}">
                			<option value="${emp.dni}">${emp.dni}</option>
                		</c:forEach>
                	</select>
                
                <petclinic:inputField label="Username" name="user.username"/>
                <petclinic:inputField label="Password" name="user.password"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="employeeId" value="${employee.id}"/>
                    <input type="hidden" name="name" value="nombre"/>
                    <input type="hidden" name="phone_number" value="111222333"/>
                    <button class="btn btn-default" type="submit">Register</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
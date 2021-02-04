<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <jsp:body>
        <h2>New User</h2>
        <form:form modelAttribute="user" class="form-horizontal" action="/users/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Username" name="username"/>
                <petclinic:inputField label="Password" name="password"/>
                Role <select id="role" name="role">
		            <option value="admin">Administrator</option>
		            <option value="employee">Employee</option>
		            <option value="client">Client</option>
		        </select>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                <input type="hidden" name="origin" value="admin"/>
                    <input type="hidden" name="userId" value="${user.username}"/>
                    <button class="btn btn-default" type="submit">Add User</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
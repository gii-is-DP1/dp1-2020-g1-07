<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="employees">
    <jsp:body>
        <h2>Update employee</h2>
        <script>
        function chgAction(){
	        var frm = document.getElementById('update') || null;
	        if(frm){
	           frm.action = "/employees/"+"${employee.id}" +"/edit";}}
        </script>
        <form:form modelAttribute="employee" class="form-horizontal"
        action="/employees/{employeeId}/edit" onsubmit = "chgAction()" id = "update">
        
            <div class="form-group has-feedback">
                <petclinic:inputField label="Dni" name="dni"/>
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Phone Number" name="phone_number"/>     
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Update Employee</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
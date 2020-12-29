<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="waiters">
    <jsp:body>
        <h2>Update waiter</h2>
        <script>
        function chgAction(){
	        var frm = document.getElementById('update') || null;
	        if(frm){
	           frm.action = "/waiters/"+"${waiter.id}" +"/edit";}}
        </script>
        <form:form modelAttribute="waiter" class="form-horizontal"
        action="/waiters/{waiterId}/edit" onsubmit = "chgAction()" id = "update">
        
            <div class="form-group has-feedback">
                <petclinic:inputField label="Dni" name="dni"/>
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Phone Number" name="phone_number"/>     
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Update Waiter</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="clients">
    <jsp:body>
        <h2>Edit table</h2>

		<script>
    	function chgAction()
    		{
        
        var frm = document.getElementById('id') || null;
        if(frm) {
           frm.action = "/clients/"+"${client.id}"+"/edit";
        }

   			 }
    	</script>
			
        <form:form modelAttribute="client" class="form-horizontal" action="/clients/{clientId}/edit" onsubmit = "chgAction()" id = "id">
       
            <div class="form-group has-feedback">
               <div class="form-group has-feedback">
                <petclinic:inputField label="Dni" name="dni"/>
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Phone Number" name="phone_number"/>
            	</div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                    <button class="btn btn-default" type="submit">Update client</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
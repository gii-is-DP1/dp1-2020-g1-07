<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="slotmachines">
    <jsp:body>
        <h2>Edit table</h2>

		<script>
    	function chgAction()
    		{
        
        var frm = document.getElementById('id') || null;
        if(frm) {
        	frm.action = "/slotmachines/"+"${slotMachine.id}"+"/edit";
        }

   			}
    	</script>
			
        <form:form modelAttribute="slotMachine" class="form-horizontal" action="/slotmachines/{slotmachineId}/edit" onsubmit = "chgAction()" id = "id">
       
            <div class="form-group has-feedback">
            	<div class="control-group">
                	<petclinic:selectField label="Slot Game" name="slotgame" names="${slotgames}" size="1"/>
                </div>
                <div class="control-group">
                	<petclinic:selectField label="Status" name="status" names="${statuses}" size="1"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                    <button class="btn btn-default" type="submit">Update Slot Machine</button>
                </div>
            </div>
        </form:form>
        
    </jsp:body>

</petclinic:layout>
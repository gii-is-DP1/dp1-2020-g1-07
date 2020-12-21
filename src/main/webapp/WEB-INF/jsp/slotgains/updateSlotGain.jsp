<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="slotgains">
    <jsp:body>
        <h2>Edit Slot Gain</h2>

		<script>
    	function chgActionSh()
    		{
        
        var frm = document.getElementById('id') || null;
        if(frm) {
           frm.action = "/slotgains/"+"${slotGain.id}"+"/edit";
        }

   			 }
    	</script>
			
       <form:form modelAttribute="slotGain" class="form-horizontal" action="/slotgains/{slotGainId}/edit" onsubmit = "chgActionSh()" id = "id">
       
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Date" name="date"/>
                <petclinic:inputField label="Amount" name="amount"/>
                <div class="control-group">
            	<petclinic:selectField label="Assign to slot machine" name="slotMachine" names="${slotMachines}" size="1"/>
            	</div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Update Slot Gain</button>
                </div>
            </div>
            
        </form:form>

        
    </jsp:body>

</petclinic:layout>
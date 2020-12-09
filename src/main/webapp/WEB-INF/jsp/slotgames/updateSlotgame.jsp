<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="slotgames">
    <jsp:body>
        <h2>Edit Slot Game</h2>

		<script>
    	function chgAction()
    		{
        
        var frm = document.getElementById('id') || null;
        if(frm) {
        	frm.action = "/slotgames/"+"${slotgame.id}"+"/edit";
        }

   			 }
    	</script>
			
       <form:form modelAttribute="slotgame" class="form-horizontal" action="/slotgames/{slotgameId}/edit" onsubmit = "chgAction()" id = "id">
       
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Jackpot" name="jackpot"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Update Slot Game</button>
                </div>
            </div>
            
        </form:form>

        
    </jsp:body>

</petclinic:layout>
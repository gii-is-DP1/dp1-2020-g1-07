<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="casinotables">
    <jsp:body>
        <h2>Edit table</h2>
		<script>
    	function chgAction()
    		{
        
        var frm = document.getElementById('joseluis') || null;
        if(frm) {
           frm.action = "/casinotables/"+"${casinotable.id}"+"/edit";
        }
            
            


   			 }
    	</script>
			
        <form:form modelAttribute="casinotable" class="form-horizontal" action="/casinotables/{casinotableId}/edit" onsubmit = "chgAction()" id = "joseluis">
       
            <div class="form-group has-feedback">
            	<div class="control-group">
                	<petclinic:selectField label="Game Type" name="gametype" names="${gametypes}" size="1"/>
                </div>
                <div class="control-group">
                	<petclinic:selectField label="Game" name="game" names="${games}" size="1"/>
                </div>
                <div class="control-group">
                	<petclinic:selectField label="Skill Level" name="skill" names="${skills}" size="1"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                    <button class="btn btn-default" type="submit">Update Casino Table</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
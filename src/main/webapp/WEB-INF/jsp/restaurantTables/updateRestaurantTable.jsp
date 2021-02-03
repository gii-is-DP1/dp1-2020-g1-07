<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="restaurantTables">
    <jsp:body>
        <h2>Edit Restaurant Table</h2>

		<script>
    	function chgActionSh()
    		{
        
        var frm = document.getElementById('id') || null;
        if(frm) {
           frm.action = "/restaurantTables/"+"${restaurantTable.id}"+"/edit";
        }

   			 }
    	</script>
			
       <form:form modelAttribute="restaurantTable" class="form-horizontal" action="/restaurantTables/{restaurantTableId}/edit" onsubmit = "chgActionSh()" id = "id">
       
            <div class="form-group has-feedback">
                <div class="control-group">
                	<petclinic:selectField label="Waiter" name="waiter" names="${waiters}" size="1"/>
                </div>
                <petclinic:inputField label="Size" name="size"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Update Restaurant Table</button>
                </div>
            </div>
            
        </form:form>

        
    </jsp:body>

</petclinic:layout>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="menus">
    <jsp:body>
        <h2>Edit table</h2>

		<script>
    	function chgAction()
    		{
        
        var frm = document.getElementById('id') || null;
        if(frm) {
           frm.action = "/menus/"+"${menu.id}"+"/edit";
        }

   			 }
    	</script>
			
        <form:form modelAttribute="menu" class="form-horizontal" action="/menus/{menuId}/edit" onsubmit = "chgAction()" id = "id">
       
            <div class="form-group has-feedback">
               <petclinic:inputField label="Date" name="date"/>
            	 <!--  PRIMER PLATO  -->
                <div class="control-group">
                	<petclinic:selectField label="First dish" name="first_dish" names="${first_dishes}" size="1"/>
                </div>
                <!--  SEGUNDO PLATO  -->
                <div class="control-group">
                	<petclinic:selectField label="Second dish" name="second_dish" names="${second_dishes}" size="1"/>
                </div>
				<!--  POSTRE  -->
                <div class="control-group">
                	<petclinic:selectField label="Dessert" name="dessert" names="${desserts}" size="1"/>
                </div>
                <div class="control-group">
                	<petclinic:selectField label="Shift" name="shift" names="${shifts}" size="1"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                    <button class="btn btn-default" type="submit">Update menu</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
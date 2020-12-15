<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="schedules">
    <jsp:body>
        <h2>Edit Schedule</h2>

		<script>
    	function chgActionSh()
    		{
        
        var frm = document.getElementById('id') || null;
        if(frm) {
           frm.action = "/schedules/"+"${schedule.id}"+"/edit";
        }

   			 }
    	</script>
			
       <form:form modelAttribute="schedule" class="form-horizontal" action="/schedules/{scheduleId}/edit" onsubmit = "chgActionSh()" id = "id">
       
            <div class="form-group has-feedback">
            	<div style="display: none">
            	     <petclinic:inputField label="Date" name="date" />
            	</div>
                <div class="control-group" style="display: none">
                	<petclinic:selectField label="Select Employee" name="emp" names="${employees_dnis}" size="1"/>
                </div>
                <div class="control-group">
                	<petclinic:selectField  label="Shift" name="shift" names="${shifts}" size="1"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Update Schedule</button>
                </div>
            </div>
            
        </form:form>

        
    </jsp:body>

</petclinic:layout>
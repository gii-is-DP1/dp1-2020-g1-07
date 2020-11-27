<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="schedules">
	<jsp:attribute name="customScript">
		<script>
			$(function () {
				$("#date").datepicker({dateFormat: 'yy/mm/dd'});
			});
		</script>
	</jsp:attribute>
    <jsp:body>
    
        <h2>Modify Schedule</h2>

        <form:form modelAttribute="schedule" class="form-horizontal" action="/schedules/save">
            <div class="form-group has-feedback">
            
                <petclinic:inputField label="Date" name="date"/>
                <div class="control-group">
                	<petclinic:selectField label="Select Employee" name="emp" names="${employees_ids}" size="1"/>
                </div>
                <div class="control-group">
                	<petclinic:selectField label="Shift" name="shift" names="${shifts}" size="1"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="schedules_Id" value="${schedules.id}"/>
                    <button class="btn btn-default" type="submit">Add Shift</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="events">
	<jsp:attribute name="customScript">
		<script>
			$(function () {
				$("#date").datepicker({dateFormat: 'yy/mm/dd'});
			});
		</script>
	</jsp:attribute>
    <jsp:body>
        <h2>Edit event</h2>

		<script>
    	function chgAction()
    		{

        var frm = document.getElementById('reminder') || null;
        if(frm) {
           frm.action = "/events/"+"${event.id}"+"/edit";
        }
		}
    	</script>
			
        <form:form modelAttribute="event" class="form-horizontal" action="/events/{eventId}/edit" onsubmit = "chgAction()" id = "reminder">
       
           <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                <div class="control-group">
                	<petclinic:inputField label="Date" name="date"/>
                </div>
                <div class="control-group">
                	<petclinic:selectField label="Show Type" name="showtype_id" names="${showtypes}" size="1"/>
                </div>
                
                
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                    <button class="btn btn-default" type="submit">Update Event</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
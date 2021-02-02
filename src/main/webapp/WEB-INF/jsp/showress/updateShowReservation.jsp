<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="showress">
    <jsp:body>
        <h2>Edit Show Reservation</h2>

		<script>
    	function chgActionSh()
    		{
        
        var frm = document.getElementById('id') || null;
        if(frm) {
           frm.action = "/showress/"+"${showres.id}"+"/edit";
        }

   			 }
    	</script>
			
       <form:form modelAttribute="showres" class="form-horizontal" action="/showress/{showresId}/edit" onsubmit = "chgActionSh()" id = "id">
            <div class="form-group has-feedback">
                <div class="control-group">
                 Event <select id="event" name="event">
                	<c:forEach var="e" items="${events}">
		            	<option value="${e.id}">${e.name}</option>
		            </c:forEach>
		        </select>
		        <petclinic:inputField label="Seats" name="seats"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Update Show Reservation</button>
                </div>
            </div>
            
        </form:form>

        
    </jsp:body>

</petclinic:layout>
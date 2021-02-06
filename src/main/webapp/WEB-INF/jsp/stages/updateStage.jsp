<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="stages">
<jsp:attribute name="customScript">
		<script>
			$(function () {
				$("#date").datepicker({dateFormat: 'yy/mm/dd'});
			});
		</script>
	</jsp:attribute>
    <jsp:body>
        <h2>Edit Stage</h2>

		<script>
    	function chgAction()
    		{

        var frm = document.getElementById('reminder') || null;
        if(frm) {
           frm.action = "/stages/"+"${stage.id}"+"/edit";
        }
		}
    	</script>
			
        <form:form modelAttribute="stage" class="form-horizontal" action="/stages/{stageId}/edit" onsubmit = "chgAction()" id = "reminder">
       
           <div class="form-group has-feedback">
                <petclinic:inputField label="Capacity" name="capacity"/>
             
                
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                    <button class="btn btn-default" type="submit">Update Stage</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
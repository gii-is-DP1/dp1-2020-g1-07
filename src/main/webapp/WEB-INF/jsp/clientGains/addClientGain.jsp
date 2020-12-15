<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="cgains">
	<jsp:attribute name="customScript">
		<script>
			$(function () {
				$("#date").datepicker({dateFormat: 'yy/mm/dd'});
			});
		</script>
	</jsp:attribute>
    <jsp:body>
    
        <h2>Add Client Gain</h2>

        <form:form modelAttribute="cgain" class="form-horizontal" action="/cgains/save">
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Amount" name="amount"/>
                <petclinic:inputField label="Date" name="date"/>
                <div class="control-group">
                	<petclinic:selectField label="Select Client" name="dni" names="${clients_dni}" size="1"/>
                </div>
                <div class="control-group">
                	<petclinic:selectField label="Game" name="game" names="${games}" size="1"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="cgainId" value="${cgain.id}"/>
                    <button class="btn btn-default" type="submit">Add Gain</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
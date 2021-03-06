<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="add events">
	<jsp:attribute name="customScript">
		<script>
			$(function () {
				$("#date").datepicker({dateFormat: 'yy/mm/dd'});
			});
		</script>
	</jsp:attribute>
    <jsp:body>
        <h2>New event</h2>


        <form:form modelAttribute="event" class="form-horizontal" action="/events/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                <div class="control-group">
                    <petclinic:inputField label="Date" name="date"/>
                </div>
                <div class="control-group">
                    <petclinic:selectField label="Show Type" name="showtype_id" names="${showtypes}" size="1"/>
                </div>
                <div class="control-group">
                    <petclinic:selectField label="Stage" name="stage_id" names="${stages}" size="1"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="eventId" value="${event.id}"/>
                    <button class="btn btn-default" type="submit">Add event</button>
                </div>
            </div>
        </form:form>


    </jsp:body>

</petclinic:layout>
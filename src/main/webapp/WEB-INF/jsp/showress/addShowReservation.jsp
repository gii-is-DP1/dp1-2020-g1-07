<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="showress">
    <jsp:body>
    
        <h2>Add Show Reservation</h2>

        <form:form modelAttribute="showReservation" class="form-horizontal" action="/showress/save">
            <div class="form-group has-feedback">
               	<div class="control-group">
                	<petclinic:selectField label="Event" name="event" names="${events}" size="1"/>
                </div>
		        <petclinic:inputField label="Seats" name="seats"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="showresId" value="${showReservation.id}"/>
                    <button class="btn btn-default" type="submit">Add Reservation</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
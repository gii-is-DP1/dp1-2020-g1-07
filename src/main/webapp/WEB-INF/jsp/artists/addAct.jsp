<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="artists">
    <jsp:body>
        <h2>New Event to act in</h2>
        <form:form modelAttribute="event" class="form-horizontal" action="save">
            <div class="form-group has-feedback">
                <div class="control-group">
                	<petclinic:selectField label="Event" name="name" names="${eventsNames}" size="1"/>
                </div>   
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Add Act</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
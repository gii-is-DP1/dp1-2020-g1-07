<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>



<petclinic:layout pageName="restauranttables">
    <jsp:body>
        <h2>Restaurant Tables</h2>
        <form:form modelAttribute="restaurantTable" class="form-horizontal" action="/restauranttables/save">
            <div class="form-group has-feedback">
                 <div class="control-group">
                	<petclinic:selectField label="Waiter" name="waiter" names="${waiters}" size="1"/>
                </div>
        <petclinic:inputField label="Size" name="size"/>
       </div>
        <div class="form-group">
        	<div class="col-sm-offset-2 col-sm-10">
            	<input type="hidden" name="restaurantTableId" value="${restaurantTable.id}"/>
                    <button class="btn btn-default" type="submit">Add Restaurant Table</button>
           	</div>
        </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
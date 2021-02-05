<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="waiters">
    <jsp:body>
        <h2>New table to serve</h2>
        <form:form modelAttribute="restaurantTable" class="form-horizontal" action="save">
            <div class="form-group has-feedback">
                <div class="control-group">
                	<petclinic:selectField label="Restaurant Table" name="id" names="${restaurantTablesIds}" size="1"/>
                </div>   
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Add Serve</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
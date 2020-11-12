<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="dishes">
    <jsp:body>
        <h2>Dish</h2>


        <form:form modelAttribute="dish" class="form-horizontal" action="/dishes/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                <div class="control-group">
                	<petclinic:selectField label="Dish Course" name="dish_course" names="${dish_courses}" size="1"/>
                </div>
                <div class="control-group">
                	<petclinic:selectField label="Shift" name="shift" names="${shifts}" size="1"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="dishId" value="${dish.id}"/>
                    <button class="btn btn-default" type="submit">Add Dish</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
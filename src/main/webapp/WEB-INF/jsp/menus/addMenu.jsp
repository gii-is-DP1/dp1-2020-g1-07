<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="menus">
	<jsp:attribute name="customScript">
		<script>
			$(function () {
				$("#date").datepicker({dateFormat: 'yy/mm/dd'});
			});
		</script>
	</jsp:attribute>
    <jsp:body>
        <h2>Menu</h2>


        <form:form modelAttribute="menu" class="form-horizontal" action="/menus/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Date" name="date"/>
            	 <!--  PRIMER PLATO  -->
                <div class="control-group">
                	<petclinic:selectField label="First dish" name="first_dish" names="${dishes}" size="1"/>
                </div>
                <!--  SEGUNDO PLATO  -->
                <div class="control-group">
                	<petclinic:selectField label="Second dish" name="second_dish" names="${dishes}" size="1"/>
                </div>
				<!--  POSTRE  -->
                <div class="control-group">
                	<petclinic:selectField label="Dessert" name="third_dish" names="${dishes}" size="1"/>
                </div>
                <div class="control-group">
                	<petclinic:selectField label="Shift" name="shift" names="${shifts}" size="1"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="menuId" value="${menu.id}"/>
                    <button class="btn btn-default" type="submit">Add Menu</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
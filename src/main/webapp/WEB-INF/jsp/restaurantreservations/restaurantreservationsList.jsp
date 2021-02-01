<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
	$('#comboboxDates').change(function(){
		var valDate = $(this).val();
		$.ajax({
			type: 'GET',
			url: '${pageContext.request.contextPath}/restaurantreservations/' + valDate,
			success: function(result){
				var result = JSON.parse(result)
				var s1= '';
				for(var i = 0; i < result.length; i++){
					s1 += '<tr> <td> ' + result[i].restaurant_table.id + ' </td>'
					+ '<td>' + result[i].client.name + '</td>'
					+ '<td>' + result[i].time_interval.name + '</td>'
					+ '<td> <spring:url value="/restaurantreservations/delete/{restaurantreservationId}" var="restaurantreservationUrl">'
					+ '<spring:param name="restaurantreservationId" value="${restaurantreservation.id}"/> </spring:url> <a href="${fn:escapeXml(restaurantreservationUrl)}">Delete</a> </td> '
					+ '<td> <spring:url value="/restaurantreservations/{restaurantreservationId}/edit" var="editUrl"> '
					+ '<spring:param name="restaurantreservationId" value="${restaurantreservation.id}"/> </spring:url> <a href="${fn:escapeXml(editUrl)}">Update</a> </td> </tr>';
				}
				$('#reservations').html(s1);
			}
		});
	});
});
</script>

<petclinic:layout pageName="restaurantreservations">
    <h2>Restaurant Reservations</h2>
    
    <div class="control-group">
    	Dates <select id="comboboxDates" name="date">
        	<option selected>Selecciona fecha</option>
        	<c:forEach var="date" items="${dates}">
            	<option value="${date}">${date}</option>
        	</c:forEach>
        </select>
    </div>

    <table id="restaurantReservationsTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 200px;">Table</th>
            <th style="width: 200px;">Client</th>
            <th style="width: 200px;">Interval</th>
            <th>Actions</th>
            <th></th>
        </tr>
        </thead>
        <tbody id="reservations"></tbody>
    </table>    
</petclinic:layout>
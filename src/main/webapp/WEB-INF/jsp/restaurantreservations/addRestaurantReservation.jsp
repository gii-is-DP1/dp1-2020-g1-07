<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
	$('#date').change(function(){
		var timeIntervalcom = document.getElementById("comboboxTimeInterval");
		timeIntervalcom.value = "";
	})
	
	$('#comboboxTimeInterval').change(function(){
		var date = $("#date").val();
		var timeIntervalId = $(this).val();
		$.ajax({
			type: 'GET',
			url: '${pageContext.request.contextPath}/restaurantreservations/new/loadDinersByTimeInterval/' + timeIntervalId + '/' + date,
			success: function(result){
				var result = JSON.parse(result);
				var s1= '';
				for(var i = 0; i < result.length; i++){
					s1 += '<option value="' + result[i].id + '">' + result[i].size + '</option>';
				}
				$('#comboboxDiners').html(s1);

			}
		});
	});
});
</script>

<petclinic:layout pageName="restaurantreservations">
	<jsp:attribute name="customScript">
		<script>
			$(function () {
				$("#date").datepicker({dateFormat: 'yy-mm-dd'});
			});
		</script>
	</jsp:attribute>
    <jsp:body>
        <h2>Restaurant Reservation</h2>


        <form:form modelAttribute="restaurantReservation" class="form-horizontal" action="/restaurantreservations/save">
            <div class="form-group has-feedback">
            	<input type="hidden" name="client" value="${client.dni}"/>
                <div class="control-group">
                    <petclinic:inputField label="Date" name="date"/>
                </div>
                <div class="control-group">
                	Time Interval <select id="comboboxTimeInterval" name="timeInterval">
                		<option value="">Select Time Interval</option>
                		<c:forEach var="timeinterval" items="${time_intervals}">
                			<option value="${timeinterval.id}">${timeinterval.name}</option>
                		</c:forEach>
                	</select>
                </div>
                <div class="control-group">
                	Diners <select id="comboboxDiners" name="restauranttable"></select>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="restaurantReservationId" value="${restaurantReservation.id}"/>
                    <button class="btn btn-default" type="submit">Add Reservation</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>
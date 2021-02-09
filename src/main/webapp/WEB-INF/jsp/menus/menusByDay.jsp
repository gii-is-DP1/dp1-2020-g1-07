<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
	$('#comboboxDates').change(function(){
		var valDate = $(this).val();
		$.ajax({
			type: 'GET',
			url: '${pageContext.request.contextPath}/menus/byDay/' + valDate,
			success: function(result){
				var result = JSON.parse(result)
				var s1= '';
				for(var i = 0; i < result.length; i++){
					s1 += '<div> <br> <b>' + result[i].first_dish.shift.name
					+ '</b> <br>' + result[i].first_dish.name
					+ '<br>' + result[i].second_dish.name
					+ '<br>' + result[i].dessert.name
					+ '<br> </div>';
				}
				$('#tableMenus').html(s1);
			}
		});
	});
});
</script>

<petclinic:layout pageName="menusByDay">

	<body class="restaurant">
	
 <div style="
    height: 80%;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;"> 
    <div style="
    background-color: #fcd5ca;
    padding: 30px;
    border-radius: 30px;
    display: flex;
    flex-direction: column;
    ">
        <h2>Menus</h2>
        
        <div class="control-group">
        	Dates <select id="comboboxDates" name="date">
        	<option selected>Selecciona fecha</option>
            <c:forEach var="date" items="${dates}">
            	<option value="${date}">${date}</option>
            </c:forEach>
            </select>
        </div>
        
        <div id="tableMenus" ></div>    
        
        <div class="form-group" style="margin-top: 20px;">
		    	<form method="get" action="/restaurantreservations/new">
		    		<button class="btn btn-default" type="submit">Book a table</button>
				</form>
		</div>
		
		<div class="form-group" style="margin-top: -5px;">
		    	<form method="get" action="/restaurantreservations">
		    		<button class="btn btn-default" type="submit">My reservations </button>
				</form>
		</div>
	</div>
	</div>
	</body>
</petclinic:layout>
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
			url: '${pageContext.request.contextPath}/events/byDay/' + valDate,
			success: function(result){
				var result = JSON.parse(result)
				var s1= '';
				for(var i = 0; i < result.length; i++){
					s1 += '<div> <br> <b>' + result[i].name
					+ '</b> <br> Showtype: ' + result[i].showtype_id.name
					+ '<br> Stage number: ' + result[i].stage_id.id
					+ '<br> Artist/Group: ' + result[i].artist_id.name
					+ '<br> </div>';
				}
				$('#tableEvents').html(s1);
			}
		});
	});
});
</script>

<petclinic:layout pageName="eventsByDay">
    <jsp:body>
        <h2>Events</h2>
        
        <div class="control-group">
        	Dates <select id="comboboxDates" name="date">
        	<option selected>Selecciona fecha</option>
            <c:forEach var="date" items="${dates}">
            	
            	<option value="${date}">${date}</option>
            </c:forEach>
            </select>
        </div>
        
        <div id="tableEvents"></div>    
    </jsp:body>

</petclinic:layout>
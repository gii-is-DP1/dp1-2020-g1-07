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
			url: '${pageContext.request.contextPath}/finance/' + valDate,
			success: function(result){
				var result = JSON.parse(result)
				var s1= '<h2> <br> <b> Slot Machines Data: </b> </h2>';
				for(var i = 0; i < result.length; i++){ 
					if(typeof result[i].SlotGains==="undefined"){
						s1 += '<div> <b> No Data Found. </b>';
						break;
					}else{
						s1 += '<div> <b> Slot Machine ID: </b>' + result[i].SlotId 
						+ '<br> <b>Gains: </b> ' + result[i].SlotGains
						+ '<br> </div>';
					}
				}
				 s1+= '<h2> <br> <b> Casino Tables Data: </b> </h2>';
				for(var i = 0; i < result.length; i++){      
					if(typeof result[i].TableGains==="undefined"){
						s1 += '<div> <b> No Data Found. </b>';
						break;
					}else{
						s1 += '<div> <b> Casino Table ID: </b>' + result[i].TableId
						+ '<br> <b>Gains: </b> ' + result[i].TableGains
						+ '<br> </div>';
					}
				}
				$('#finance').html(s1);
			}
		});
	});
});
</script>

<petclinic:layout pageName="finance">
    <jsp:body>
        <h2>Finance</h2>
        
        <div class="control-group">
        	Dates <select id="comboboxDates" name="date">
        	<option selected>Selecciona fecha</option>
            <c:forEach var="date" items="${dates}">
            	
            	<option value="${date}">${date}</option>
            </c:forEach>
            </select>
        </div>
        
        <div id="finance"></div>    
    </jsp:body>

</petclinic:layout>
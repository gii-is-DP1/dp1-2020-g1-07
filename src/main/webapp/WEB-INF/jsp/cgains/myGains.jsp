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
			url: '${pageContext.request.contextPath}/cgains/user/' + valDate,
			success: function(result){
				var result = JSON.parse(result)
				var s1= '';
				for(var i = 0; i < result.length; i++){
					s1 += '<div> <br>' + result[i].date
					+ '<br>' + result[i].amount
					+ '<br>' + result[i].game
					+ '<br> </div>';
				}	
				$('#tableGains').html(s1);
			}
		});
	});
});
</script>

<petclinic:layout pageName="myGains">
    <h2>Your Gains</h2>
    <div class="control-group">
        	<select id="comboboxDates" name="date">
        	<option selected>Select week</option>
	            <c:forEach var="date" items="${dates}">
	            	<option value="${date.monday}">${date.text}</option>
	            </c:forEach>
            </select>
        </div>
    <div id="tableGains"></div>    
</petclinic:layout>
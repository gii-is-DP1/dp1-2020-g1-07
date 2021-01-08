<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
<link href="mdtimepicker.css" rel="stylesheet">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="mdtimepicker.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
	$('#comboboxGameType').change(function(){
		var gametypeId = null;
		var gametypeName = $(this).val();
		if(gametypeName=="Roulette"){
			gametypeId=1;
		}else if(gametypeName=="Cards"){
			gametypeId=2;
		}else{
			gametypeId=3;
		}
		$.ajax({
			type: 'GET',
			url: '${pageContext.request.contextPath}/casinotables/new/loadGamesByGameType/' + gametypeId,
			success: function(result){
				var result = JSON.parse(result);
				var s1= '';
				for(var i = 0; i < result.length; i++){
					s1 += '<option value="' + result[i].name + '">' + result[i].name + '</option>';
				}
				$('#comboboxGame').html(s1);

			}
		});
	});
});
</script>


<petclinic:layout pageName="casinotables">
	<jsp:attribute name="customScript">               <!-- Preguntar a la profe (poner un formato de entrada mas cómodo, errores con el id) con jQuery-->
		<script>
			$(function () {
			  $('#startTime').mdtimepicker();
			});

		</script>
	</jsp:attribute>
	<jsp:attribute name="customScript">
		<script>
			$(function () {
			  $('#endingTime').mdtimepicker();
			});

		</script>
	</jsp:attribute>
	<jsp:attribute name="customScript">
		<script>
			$(function () {
				$("#date").datepicker({dateFormat: 'yy/mm/dd'});
			});
		</script>
	</jsp:attribute>
    <jsp:body>
        <h2>New table</h2>
        <form:form modelAttribute="casinotable" class="form-horizontal" action="/casinotables/save">
            <div class="form-group has-feedback">
           	    <petclinic:inputField label="Name" name="name"/>
           	    <petclinic:inputField label="Date" name="date"/>
            	<petclinic:inputField label="Start Time" name="startTime"/>
            	<petclinic:inputField label="Ending Time" name="endingTime"/>
            	<div class="control-group">
                	GameType <select id="comboboxGameType" name="gametype">
                		<option value="">Select Game type</option>
                		<c:forEach var="gametype" items="${gametypes}">
                			<option value="${gametype.name}">${gametype.name}</option>
                		</c:forEach>
                	</select>
                </div>
                <div class="control-group">
                	Game <select id="comboboxGame" name="game"></select>
                </div>
                <div class="control-group">
                	<petclinic:selectField label="Skill Level" name="skill" names="${skills}" size="1"/>
                </div>
             </div>   
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="casinotableId" value="${casinotable.id}"/>
                    <button class="btn btn-default" type="submit">Add Casino Table</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>





    	
    	
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>


<script type="text/javascript">
$(document).ready(function(){
	
	$('#comboboxGameType').change(function(){
		var gametypeId = null;
		var gametypeName = $(this).val();
		if(gametypeName=="Roullete"){
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

    <jsp:body>
        <h2>New table</h2>


        <form:form modelAttribute="casinotable" class="form-horizontal" action="/casinotables/save">
            <div class="form-group has-feedback">
            	<div class="control-group">
                	GameType <select id="comboboxGameType" name="gametype">
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





    	
    	
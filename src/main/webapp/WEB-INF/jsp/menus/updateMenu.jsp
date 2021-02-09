<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>


<script type="text/javascript">
$(document).ready(function(){
	
	$('#comboboxShift').change(function(){
		var shiftId = null;
		var shiftName = $(this).val();
		if(shiftName=="Day"){
			shiftId=1;
		}else if(shiftName=="Afternoon"){
			shiftId=2;
		}else{
			shiftId=3;
		}
		$.ajax({
			type: 'GET',
			url: '${pageContext.request.contextPath}/menus/new/loadDishesByShift/' + shiftId,
			success: function(result){
				var splits = result.split('#');
				var json1 = JSON.parse(splits[0]);
				var json2 = JSON.parse(splits[1]);
				var json3 = JSON.parse(splits[2]);
				var s1= '';
				for(var i = 0; i < json1.length; i++){
					s1 += '<option value="' + json1[i].name + '">' + json1[i].name + '</option>';
				}
				$('#comboboxFirstDish').html(s1);
				var s2= '';
				for(var i = 0; i < json2.length; i++){
					s2 += '<option value="' + json2[i].name + '">' + json2[i].name + '</option>';
				}
				$('#comboboxSecondDish').html(s2);
				var s3= '';
				for(var i = 0; i < json3.length; i++){
					s3 += '<option value="' + json3[i].name + '">' + json3[i].name + '</option>';
				}
				$('#comboboxDessert').html(s3);
			}
		});
	});
});
</script>

<petclinic:layout pageName="menus">
	
    	
	<jsp:attribute name="customScript">
		<script>
			$(function () {
				$("#date").datepicker({dateFormat: 'yy/mm/dd'});
			});
		</script>
	</jsp:attribute>
    <jsp:body>
        <h2>Edit Menu</h2>
		<script>
    	function chgAction()
    		{
        
        var frm = document.getElementById('id') || null;
        if(frm) {
           frm.action = "/menus/edit/"+"${menu.id}";
        }

   			 }
    	</script>
    	<c:out value="${error}"></c:out>
		<form:form modelAttribute="menu" class="form-horizontal" action="/menus/edit/{menuId}" onsubmit = "chgAction()" id = "id">
        
            <div class="form-group has-feedback">
                <petclinic:inputField label="Date" name="date"/>
            	 <!--  PRIMER PLATO  -->
                <div class="control-group">
                	First Dish <select id="comboboxFirstDish" name="first_dish"></select>
                </div>
                <!--  SEGUNDO PLATO  -->
                <div class="control-group">
                	Second Dish <select id="comboboxSecondDish" name="second_dish"></select>
                </div>
				<!--  POSTRE  -->
                <div class="control-group">
                	Dessert <select id="comboboxDessert" name="dessert"></select>
                </div>
                <div class="control-group">
                	Shift <select id="comboboxShift" name="shift">
                		<c:forEach var="shift" items="${shifts}">
                			<option value="${shift.name}">${shift.name}</option>
                		</c:forEach>
                	</select>
                	<!-- <petclinic:selectField label="Shift" name="shift" names="${shifts}" size="1"/> -->
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


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<!-- La primera funcion es para seleccionar los dates del selector en cada fila-->
<script type="text/javascript">

function getDates(idCT){
	var s1= '<option value=""> Seleccione fecha </option>';
	var clientgains = JSON.parse('${clientgains}');
	for(var clientgain of clientgains){
		if(clientgain.tableId==idCT) s1 += '<option value="' + clientgain.amount + '">' + clientgain.date + '</option>';
	}
	$('#comboboxDate'+idCT.toString()).html(s1);
}


$(document).ready(function(){
});
</script>

<petclinic:layout pageName="casinotables">
    <h2>Casino Tables</h2>

    <table id="casinotablesTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Id</th>
        	<th style="width: 150px;">Name</th>
            <th style="width: 150px;">Game</th>
            <th style="width: 200px;">GameType</th>
            <th style="width: 200px;">Skill Level</th>
            <th style="width: 200px;">Date</th>
            <th style="width: 200px;"> Start Time</th>
            <th style="width: 200px;"> Ending Time</th>
            <th style="width: 200px;">Gain Date</th>
            <th style="width: 200px;">Amount</th>
            <th>Actions</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${casinotables}" var="casinotable">
            <tr>
                <td>
                    <c:out value="${casinotable.id}"/>
                </td>
                <td>
                    <c:out value="${casinotable.name}"/>
                </td>
                <td>
                    <c:out value="${casinotable.game.name}"/>
                </td>
                <td>
                    <c:out value="${casinotable.gametype}"/>
                </td>
                <td>
                    <c:out value="${casinotable.skill}"/>
                </td>
                <td>
                    <c:out value="${casinotable.date}"/>
                </td>
                 <td>
                    <c:out value="${casinotable.startTime}"/>
                </td>
                <td>
                    <c:out value="${casinotable.endingTime}"/>
                </td>
                <td>
                    <select id="comboboxDate${casinotable.id}" name="date">
                    </select>
                    <script>
                    	getDates(${casinotable.id});
                    	var select = document.getElementById("comboboxDate${casinotable.id}");
                    	select.addEventListener("change", function(){
                    		var valAmount = $(this).val().toString();
                    		var s1 = '';
                    		s1 += '<p>' + valAmount + '</p>';
                    		$('#amount${casinotable.id}').html(s1);
                    	});
                    </script>
                </td>
                <td id="amount${casinotable.id}">
                    <!-- <c:out value="${slotMachine.status}"/> -->
                </td>
                <td>
                	<spring:url value="/casinotables/delete/{casinotableId}" var="deleteUrl">
                        <spring:param name="casinotableId" value="${casinotable.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
                </td> 
                 <td>
                	<spring:url value="/casinotables/{casinotableId}/edit" var="editUrl">
                        <spring:param name="casinotableId" value="${casinotable.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-group">
    	<form method="get" action="/casinotables/new">
    		<button class="btn btn-default" type="submit">Add new table</button>
		</form>
	</div>
    
</petclinic:layout>
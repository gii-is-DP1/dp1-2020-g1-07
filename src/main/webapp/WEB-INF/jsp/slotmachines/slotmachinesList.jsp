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

function getDates(idSM){
	var s1= '';
	var slotgains = JSON.parse('${slotgains}');
	for(var slotgain of slotgains){
		if(slotgain.slotMachine==idSM) s1 += '<option value="' + slotgain.id + '">' + slotgain.date + '</option>';
	}
	$('#comboboxDate'+idSM.toString()).html(s1);
}

$(document).ready(function(){
	
});
</script>

<petclinic:layout pageName="slotmachines">
    <h2>Slot Machines</h2>

    <table id="slotmachines" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Id</th>
            <th style="width: 200px;">Game</th>
            <th style="width: 200px;">Status</th>
            <th style="width: 200px;">Gain Date</th>
            <th style="width: 200px;">Amount</th>
            <th>Actions</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${slotMachines}" var="slotMachine">
            <tr>
                <td>
                    <c:out value="${slotMachine.id}"/>
                </td>
                <td>
                    <c:out value="${slotMachine.slotgame.name}"/>
                </td>
                <td>
                    <c:out value="${slotMachine.status}"/>
                </td>
                <td>
                    <select id="comboboxDate${slotMachine.id}" name="date"></select>
                    <script>getDates(${slotMachine.id});</script>
                </td>
                <td>
                    <c:out value="${slotMachine.status}"/>
                </td>
            <td>
                	<spring:url value="/slotmachines/delete/{slotMachineId}" var="slotMachineUrl">
                        <spring:param name="slotMachineId" value="${slotMachine.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(slotMachineUrl)}">Delete</a>
                </td>
            
            <td>
                	<spring:url value="/slotmachines/{slotMachineId}/edit" var="editUrl">
                        <spring:param name="slotMachineId" value="${slotMachine.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
        <div class="form-group">
    	<form method="get" action="/slotmachines/new">
    		<button class="btn btn-default" type="submit">Add new slot machine</button>
		</form>
	</div>
    
</petclinic:layout>
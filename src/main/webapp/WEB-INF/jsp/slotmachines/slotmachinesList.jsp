<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="slotmachines">
    <h2>Slot Machines</h2>

    <table id="slotmachines" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Id</th>
            <th style="width: 200px;">Game</th>
            <th style="width: 200px;">Status</th>
            <th>Actions</th>
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
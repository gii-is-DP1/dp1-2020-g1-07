<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="maintenanceWorkers">
    <h2>Maintenance Worker</h2>

    <table id="maintenanceWorkersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Dni</th>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Phone Number</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${maintenanceWorkers}" var="maintenanceWorker">
            <tr>
                <td>
                    <c:out value="${maintenanceWorker.dni}"/>
                </td>
                <td>
                    <c:out value="${maintenanceWorker.name}"/>
                </td>
                <td>
                    <c:out value="${maintenanceWorker.phone_number}"/>
                </td>
                <td>
                	<spring:url value="/maintenanceWorkerss/delete/{maintenanceWorkersId}" var="deleteUrl">
                        <spring:param name="maintenanceWorkersId" value="${maintenanceWorkers.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
                </td>
                <td>
                    <spring:url value="/maintenanceWorkerss/{maintenanceWorkersId}/edit" var="editUrl">
                        <spring:param name="maintenanceWorkersId" value="${maintenanceWorkers.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-group">
    	<form method="get" action="/maintenanceWorkers/new">
    		<button class="btn btn-default" type="submit">Add new maintenance worker</button>
		</form>
	</div>
</petclinic:layout>
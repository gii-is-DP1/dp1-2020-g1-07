<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clients">
    <h2>Clients</h2>

    <table id="clientsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Dni</th>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Phone Number</th>
            <th>Actions</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clients}" var="client">
            <tr>
                <td>
                    <c:out value="${client.dni}"/>
                </td>
                <td>
                    <c:out value="${client.name}"/>
                </td>
                <td>
                    <c:out value="${client.phone_number}"/>
                </td>
            <td>
                	<spring:url value="/clients/delete/{clientId}" var="clientUrl">
                        <spring:param name="clientId" value="${client.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clientUrl)}">Delete</a>
                </td> 
                
                <td>
                	<spring:url value="/clients/{clientId}/edit" var="editUrl">
                        <spring:param name="clientId" value="${client.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-group">
    	<form method="get" action="/clients/new">
    		<button class="btn btn-default" type="submit">Add new client</button>
		</form>
		</div>
</petclinic:layout>
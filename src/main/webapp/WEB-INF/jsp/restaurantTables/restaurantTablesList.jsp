<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<petclinic:layout pageName="restaurantTables">
    <h2>Restaurant Tables</h2>

    <table id="restaurantTables" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Waiter</th>
            <th style="width: 200px;">Size</th>
            <th>Actions</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${restaurantTables}" var="restaurantTable">
            <tr>
                <td>
                    <c:out value="${restaurantTable.waiter.name}"/>
                </td>
                <td>
                    <c:out value="${restaurantTable.size}"/>
                </td>
            <td>
                	<spring:url value="/restaurantTables/delete/{restaurantTableId}" var="deleteUrl">
                        <spring:param name="restaurantTableId" value="${restaurantTable.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
                </td>
            
            <td>
                	<spring:url value="/restaurantTables/{restaurantTableId}/edit" var="editUrl">
                        <spring:param name="restaurantTableId" value="${restaurantTable.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
        <div class="form-group">
    	<form method="get" action="/restaurantTables/new">
    		<button class="btn btn-default" type="submit">Add new restaurant table</button>
		</form>
	</div>
    
</petclinic:layout>
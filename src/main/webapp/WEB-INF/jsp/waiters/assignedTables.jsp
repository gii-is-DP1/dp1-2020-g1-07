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
            <th style="width: 200px;">ID</th>
            <th style="width: 200px;">Size</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${restaurantTables}" var="restaurantTable">
            <tr>
                <td>
                    <c:out value="${restaurantTable.id}"/>
                </td>
                <td>
                    <c:out value="${restaurantTable.size}"/>
                </td>
                <td>
                	<spring:url value="/waiters/serves/{waiterId}/delete/{restaurantTableId}" var="restaurantTableUrl">
                        <spring:param name="restaurantTableId" value="${restaurantTable.id}"/>
                        <spring:param name="waiterId" value="${waiter.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(restaurantTableUrl)}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
        <div class="form-group">
        <spring:url value="/waiters/serves/{waiterId}/new" var="newUrl">
                        <spring:param name="waiterId" value="${waiter.id}"/>
        </spring:url>
    	<a class="btn btn-default" href="${fn:escapeXml(newUrl)}">Add new table to serve</a>
	</div>
    
</petclinic:layout>
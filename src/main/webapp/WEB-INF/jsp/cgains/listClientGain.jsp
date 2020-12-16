<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cgains">
    <h2>Client Gains</h2>

    <table id="clientgainsTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Client</th>
            <th style="width: 150px;">Amount</th>
            <th style="width: 200px;">Date</th>
            <th style="width: 200px;">Game</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cgains}" var="cgain">
            <tr>
                <td>
                    <c:out value="${cgain.dni}"/>
                </td>
                <td>
                    <c:out value="${cgain.amount}"/>
                </td>
                <td>
                    <c:out value="${cgain.date}"/>
                </td>
                <td>
                    <c:out value="${cgain.game.name}"/>
                </td>
                <td>
                	<spring:url value="/cgains/delete/{cgainId}" var="deleteUrl">
                        <spring:param name="cgainId" value="${cgain.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
                </td> 
                 <td>
                	<spring:url value="/cgains/{cgainId}/edit" var="editUrl">
                        <spring:param name="cgainId" value="${cgain.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-group">
    	<form method="get" action="/cgains/new">
    		<button class="btn btn-default" type="submit">Add new client gain</button>
		</form>
	</div>
    
</petclinic:layout>
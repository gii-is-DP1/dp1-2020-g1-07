<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="showress">
    <h2>My Show Reservations</h2>

    <table id="showresTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Event</th>
            <th style="width: 200px;">Seats</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${showress}" var="s">
            <tr>
                <td>
                    <c:out value="${s.event.name}"/>
                </td>
                <td>
                    <c:out value="${s.seats}"/>
                </td>
                <td>
                	<spring:url value="/showress/delete/{showresId}" var="deleteUrl">
                        <spring:param name="showresId" value="${s.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
                </td> 
                 <td>
                	<spring:url value="/showress/{showresId}/edit/{clientId}" var="editUrl">
                        <spring:param name="showresId" value="${s.id}"/>
                        <spring:param name="clientId" value="${s.client.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-group">
    	<form method="get" action="/showress/new">
    		<button class="btn btn-default" type="submit">Add new show reservation</button>
		</form>
	</div>
	    <div class="form-group">
		    	<form method="get" action="/events/byDay">
		    		<button class="btn btn-default" type="submit">Event list</button>
				</form>
		</div>
    
</petclinic:layout>
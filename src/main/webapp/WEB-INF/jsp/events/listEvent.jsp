<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="events">
    <h2>Events</h2>

    <table id="eventsTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Id</th>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Date</th>
            <th style="width: 200px;">Show Type</th>
            <th style="width: 200px;">Artist/Group</th>
            <th style= "width: 200px;">Stage</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${events}" var="event">
            <tr>
            	<td>
            		<c:out value="${event.id}"/>
            	</td>
                <td>
                    <c:out value="${event.name}"/>
                </td>
                <td>
                    <c:out value="${event.date}"/>
                </td>
                <td>
                    <c:out value="${event.showtype_id.name}"/>
                </td>
                <td>
                    <c:out value="${event.artist_id.name}"/>
                </td>
                <td>
                    <c:out value="${event.stage_id.id}"/>
                </td>
            <td>
                	<spring:url value="/events/delete/{eventId}" var="eventUrl">
                        <spring:param name="eventId" value="${event.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(eventUrl)}">Delete</a>
                </td> 
                <td>
                	<spring:url value="/events/{eventId}/edit" var="editUrl">
                        <spring:param name="eventId" value="${event.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-group">
    	<form method="get" action="/events/new">
    		<button class="btn btn-default" type="submit">Add new event</button>
		</form>
	</div>
    
</petclinic:layout>
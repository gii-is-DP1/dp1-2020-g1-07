<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<petclinic:layout pageName="events">
    <h2>Events</h2>

    <table id="events" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Date</th>
            <th style="width: 200px;">ShowType</th>
            <th style="width: 200px;">Stage</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${actedEvents}" var="event">
            <tr>
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
                    <c:out value="${event.stage_id.id}"/>
                </td>
                <td>
                	<spring:url value="/artists/acts/{artistId}/delete/{eventId}" var="eventUrl">
                        <spring:param name="eventId" value="${event.id}"/>
                        <spring:param name="artistId" value="${artist.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(eventUrl)}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
        <div class="form-group">
        <spring:url value="/artists/acts/{artistId}/new" var="newUrl">
                        <spring:param name="artistId" value="${artist.id}"/>
        </spring:url>
    	<a class="btn btn-default" href="${fn:escapeXml(newUrl)}">Add new event to prepare</a>
	</div>
    
</petclinic:layout>
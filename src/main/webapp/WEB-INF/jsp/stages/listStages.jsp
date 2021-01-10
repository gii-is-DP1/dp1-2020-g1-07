<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="stages">
    <h2>Events</h2>

    <table id="stagesTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Id</th>
            <th style="width: 150px;">Capacity</th>
            <th style="width: 200px;">Event</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${stages}" var="stage">
            <tr>
            	<td>
            		<c:out value="${stage.id}"/>
            	</td>
                <td>
                    <c:out value="${stage.capacity}"/>
                </td>
                <td>
                    <c:out value="${stage.event_id.name}"/>
                </td>
            <td>
                	<spring:url value="/stages/delete/{stageId}" var="eventUrl">
                        <spring:param name="stageId" value="${stage.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(eventUrl)}">Delete</a>
                </td> 
                <td>
                	<spring:url value="/stages/{stageId}/edit" var="editUrl">
                        <spring:param name="stageId" value="${stage.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-group">
    	<form method="get" action="/stages/new">
    		<button class="btn btn-default" type="submit">Add new stage</button>
		</form>
	</div>
    
</petclinic:layout>
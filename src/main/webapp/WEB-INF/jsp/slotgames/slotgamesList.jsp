<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="slotgames">
    <h2>Slot Games</h2>

    <table id="slotgamesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Jackpot</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${slotgames}" var="slotgame">
            <tr>
                <td>
                    <c:out value="${slotgame.name}"/>
                </td>
                <td>
                    <c:out value="${slotgame.jackpot}"/>
                </td>
            <td>
                	<spring:url value="/slotgames/delete/{slotgameId}" var="slotgameUrl">
                        <spring:param name="slotgameId" value="${slotgame.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(slotgameUrl)}">Delete</a>
                </td>
            
            <td>
                	<spring:url value="/slotgames/{slotgameId}/edit" var="editUrl">
                        <spring:param name="slotgameId" value="${slotgame.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
        <div class="form-group">
    	<form method="get" action="/slotgames/new">
    		<button class="btn btn-default" type="submit">Add new slot game</button>
		</form>
	</div>
    
</petclinic:layout>
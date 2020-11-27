<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h2>Games</h2>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Id</th>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">MaxPlayers</th>
            <th style="width: 200px;">GameType</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <tr>
            	<td>
            		<c:out value="${game.id}"/>
            	</td>
                <td>
                    <c:out value="${game.name}"/>
                </td>
                <td>
                    <c:out value="${game.maxPlayers}"/>
                </td>
                <td>
                    <c:out value="${game.gametype.name}"/>
                </td>
            <td>
                	<spring:url value="/games/delete/{gameId}" var="gameUrl">
                        <spring:param name="gameId" value="${game.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(gameUrl)}">Delete</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-group">
    	<form method="get" action="/games/new">
    		<button class="btn btn-default" type="submit">Add new game</button>
		</form>
	</div>
    
</petclinic:layout>
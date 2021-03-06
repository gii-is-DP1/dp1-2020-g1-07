<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

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
            <security:authorize access="hasAnyAuthority('admin, employee')">
            <th>Actions</th>
            </security:authorize>
            
     
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
           
            	<security:authorize access="hasAnyAuthority('admin, employee')">
            
           		<td>
                	<spring:url value="/games/delete/{gameId}" var="gameUrl">
                        <spring:param name="gameId" value="${game.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(gameUrl)}">Delete</a>
                </td> 
                <td>
                	<spring:url value="/games/{gameId}/edit" var="editUrl">
                        <spring:param name="gameId" value="${game.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td>
                
               	</security:authorize>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-group">
    	<form method="get" action="/casinotables/index">
    		<button class="btn btn-default" type="submit">Return to index</button>
		</form>
	</div>
	
	<security:authorize access="hasAnyAuthority('admin, employee')">
	
    <div class="form-group">
    	<form method="get" action="/games/new">
    		<button class="btn btn-default" type="submit">Add new game</button>
		</form>
	</div>
    </security:authorize>
    
</petclinic:layout>
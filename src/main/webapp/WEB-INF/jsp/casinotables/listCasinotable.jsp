<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="casinotables">
    <h2>Casinotables</h2>

    <table id="casinotablesTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Id</th>
            <th style="width: 150px;">Game</th>
            <th style="width: 200px;">GameType</th>
            <th style="width: 200px;">Skill Level</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${casinotables}" var="casinotable">
            <tr>
                <td>
                    <c:out value="${casinotable.id}"/>
                </td>
                <td>
                    <c:out value="${casinotable.game_id}"/>
                </td>
                <td>
                    <c:out value="${casinotable.gametype_id}"/>
                </td>
                <td>
                    <c:out value="${casinotable.skill_id}"/>
                </td>
                <td>
                	<spring:url value="/casinotables/delete/{casinotableId}" var="casinotableUrl">
                        <spring:param name="casinotableId" value="${casinotable.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(casinotableUrl)}">Delete</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-group">
    	<form method="get" action="/casinotables/new">
    		<button class="btn btn-default" type="submit">Add new game</button>
		</form>
	</div>
    
</petclinic:layout>
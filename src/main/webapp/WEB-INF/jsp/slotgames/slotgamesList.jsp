<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<petclinic:layout pageName="slotgames">
    <h2>Slot Games</h2>

    <table id="slotgamesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Jackpot</th>
            
            <security:authorize access="hasAuthority('admin')">
            <th>Actions</th>
            </security:authorize>
            
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
           	<security:authorize access="hasAuthority('admin')">
                
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
	
	<security:authorize access="hasAuthority('admin')">
        <div class="form-group">
    	<form method="get" action="/slotgames/new">
    		<button class="btn btn-default" type="submit">Add new slot game</button>
		</form>
	</div>
	</security:authorize>
    
</petclinic:layout>
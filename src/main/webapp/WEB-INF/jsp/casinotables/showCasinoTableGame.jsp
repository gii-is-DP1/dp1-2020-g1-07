<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="showCasinoTablesGame">
    <h2>Table Games</h2>

    <table id="casinotablesTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Name</th>
            <th style="width: 150px;">Game</th>
            <th style="width: 200px;">GameType</th>
            <th style="width: 200px;">Skill Level</th>
            <th style="width: 200px;">Start Time</th>
            <th style="width: 200px;">Ending Time</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${casinotables}" var="casinotable">
            <tr>
                <td>
                    <c:out value="${casinotable.name}"/>
                </td>
                <td>
                    <c:out value="${casinotable.game.name}"/>
                </td>
                <td>
                    <c:out value="${casinotable.gametype}"/>
                </td>
                <td>
                    <c:out value="${casinotable.skill}"/>
                </td>
                 <td>
                    <c:out value="${casinotable.startTime}"/>
                </td>
                <td>
                    <c:out value="${casinotable.endingTime}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
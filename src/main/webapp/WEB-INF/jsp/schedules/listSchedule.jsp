<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="schedules">
    <h2>Schedules</h2>

    <table id="schedulesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Employee</th>
            <th style="width: 200px;">Date</th>
            <th style="width: 200px;">Shift</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${schedules}" var="schedules">
            <tr>
                <td>
                    <c:out value="${schedules.emp}"/>
                </td>
                <td>
                    <c:out value="${schedules.date}"/>
                </td>
                <td>
                    <c:out value="${schedules.shift}"/>
                </td> 
                 <td>
                	<spring:url value="/schedules/delete/{schedulesId}" var="schedulesUrl">
                        <spring:param name="menuId" value="${schedules.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(schedulesUrl)}">Delete</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
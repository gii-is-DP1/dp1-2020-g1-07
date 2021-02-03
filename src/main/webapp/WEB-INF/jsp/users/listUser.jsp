<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="users">
    <h2>User</h2>

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Username</th>
            <th style="width: 200px;">Role</th>
            <th style="width: 200px;">DNI</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
            <tr>
            	<c:forEach items="${admins}" var="admin">
	                <td>
	                    <c:out value="${admin.user.username}"/>
	                </td>
	                <td>
	                    <c:out value="Admin"/>
	                </td>
	                <td>
	                    <c:out value="${admin.dni}"/>
	                </td>
	                <td>
	                	<spring:url value="/users/delete/{userId}" var="deleteUrl">
	                        <spring:param name="userId" value="${admin.user.username}"/>
	                    </spring:url>
	                    
	                    <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
	                </td>
	                <td>
	                    <spring:url value="/users/{userId}/edit" var="editUrl">
	                        <spring:param name="userId" value="${admin.user.username}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(editUrl)}">Update</a>
	                </td>
                </c:forEach>
                
                <c:forEach items="${employees}" var="emp">
	                <td>
	                    <c:out value="${emp.user.username}"/>
	                </td>
	                <td>
	                    <c:out value="Employee"/>
	                </td>
	                <td>
	                    <c:out value="${emp.dni}"/>
	                </td>
	                <td>
	                	<spring:url value="/users/delete/{userId}" var="deleteUrl">
	                        <spring:param name="userId" value="${emp.user.username}"/>
	                    </spring:url>
	                    
	                    <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
	                </td>
	                <td>
	                    <spring:url value="/users/{userId}/edit" var="editUrl">
	                        <spring:param name="userId" value="${emp.user.username}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(editUrl)}">Update</a>
	                </td>
                </c:forEach>
                
                <c:forEach items="${clients}" var="client">
	                <td>
	                    <c:out value="${client.user.username}"/>
	                </td>
	                <td>
	                    <c:out value="Employee"/>
	                </td>
	                <td>
	                    <c:out value="${client.dni}"/>
	                </td>
	                <td>
	                	<spring:url value="/users/delete/{userId}" var="deleteUrl">
	                        <spring:param name="userId" value="${client.user.username}"/>
	                    </spring:url>
	                    
	                    <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
	                </td>
	                <td>
	                    <spring:url value="/users/{userId}/edit" var="editUrl">
	                        <spring:param name="userId" value="${client.user.username}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(editUrl)}">Update</a>
	                </td>
                </c:forEach>
            </tr>
        </tbody>
    </table>
    
</petclinic:layout>
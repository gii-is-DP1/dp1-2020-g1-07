<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="administrators">
    <h2>Administrator</h2>

    <table id="administratorsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Dni</th>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Phone Number</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${administrators}" var="administrator">
            <tr>
                <td>
                    <c:out value="${administrator.dni}"/>
                </td>
                <td>
                    <c:out value="${administrator.name}"/>
                </td>
                <td>
                    <c:out value="${administrator.phone_number}"/>
                </td>
                <td>
                	<spring:url value="/administrators/delete/{administratorId}" var="administratorUrl">
                        <spring:param name="administratorId" value="${administrator.id}"/>
                    </spring:url>
                    
                    <a href="${fn:escapeXml(administratorUrl)}">Delete</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
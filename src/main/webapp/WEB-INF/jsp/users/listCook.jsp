<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cooks">
    <h2>Cook</h2>

    <table id="cooksTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Dni</th>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Phone Number</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cooks}" var="cook">
            <tr>
                <td>
                    <c:out value="${cook.dni}"/>
                </td>
                <td>
                    <c:out value="${cook.name}"/>
                </td>
                <td>
                    <c:out value="${cook.phone_number}"/>
                </td>
                <td>
                	<spring:url value="/cooks/delete/{cookId}" var="deleteUrl">
                        <spring:param name="cookId" value="${cook.id}"/>
                    </spring:url>
                    
                    <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
                </td>
                <td>
                    <spring:url value="/cooks/{cookId}/edit" var="editUrl">
                        <spring:param name="cookId" value="${cook.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
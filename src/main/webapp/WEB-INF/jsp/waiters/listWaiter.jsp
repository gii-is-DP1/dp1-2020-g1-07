<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="waiters">
    <h2>Waiter</h2>

    <table id="waitersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Dni</th>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Phone Number</th>
            <th>Actions</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${waiters}" var="waiter">
            <tr>
                <td>
                    <c:out value="${waiter.dni}"/>
                </td>
                <td>
                    <c:out value="${waiter.name}"/>
                </td>
                <td>
                    <c:out value="${waiter.phone_number}"/>
                </td>
                <td>
                	<spring:url value="/waiters/delete/{waiterId}" var="deleteUrl">
                        <spring:param name="waiterId" value="${waiter.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
                </td>
                <td>
                    <spring:url value="/waiters/{waiterId}/edit" var="editUrl">
                        <spring:param name="waiterId" value="${waiter.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td>
                <td>
                    <spring:url value="/waiters/serves/{waiterId}" var="serveUrl">
                        <spring:param name="waiterId" value="${waiter.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(serveUrl)}">Serves</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
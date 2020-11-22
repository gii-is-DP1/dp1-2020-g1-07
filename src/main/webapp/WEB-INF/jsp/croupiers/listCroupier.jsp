<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="croupiers">
    <h2>Croupier</h2>

    <table id="croupiersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Dni</th>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Phone Number</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${croupiers}" var="croupier">
            <tr>
                <td>
                    <c:out value="${croupier.dni}"/>
                </td>
                <td>
                    <c:out value="${croupier.name}"/>
                </td>
                <td>
                    <c:out value="${croupier.phone_number}"/>
                </td>
                <td>
                	<spring:url value="/croupiers/delete/{croupierId}" var="croupierUrl">
                        <spring:param name="croupierId" value="${croupier.id}"/>
                    </spring:url>
                    
                    <a href="${fn:escapeXml(croupierUrl)}">Delete</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
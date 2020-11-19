<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="chefs">
    <h2>Chef</h2>

    <table id="chefsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Dni</th>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Phone Number</th>
            <th style="width: 200px;">Shift</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${chefs}" var="chef">
            <tr>
                <td>
                    <c:out value="${chef.dni}"/>
                </td>
                <td>
                    <c:out value="${chef.name}"/>
                </td>
                <td>
                    <c:out value="${chef.phone_number}"/>
                </td>
                <td>
                    <c:out value="${chef.shift.name}"/>
                </td>
                <td>
                	<spring:url value="/chefs/delete/{chefId}" var="chefUrl">
                        <spring:param name="chefId" value="${chef.id}"/>
                    </spring:url>
                    
                    <a href="${fn:escapeXml(chefUrl)}">Delete</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
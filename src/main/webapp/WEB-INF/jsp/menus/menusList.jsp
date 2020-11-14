<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="menus">
    <h2>Menus</h2>

    <table id="menusTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 200px;">Date</th>
            <th style="width: 200px;">First Dish</th>
            <th style="width: 200px;">Second Dish</th>
            <th style="width: 200px;">Dessert</th>
            <th style="width: 200px;">Shift</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${menus}" var="menu">
            <tr>
                <td>
                	<c:out value="${menu.date}"/>
                </td>
                <td>
                    <c:out value="${menu.first_dish.name}"/>
                </td>
                <td>
                    <c:out value="${menu.second_dish.name}"/>
                </td>
                <td>
                    <c:out value="${menu.dessert.name}"/>
                </td>
                 <td>
                    <c:out value="${menu.shift.name}"/>
                </td>
            <td>
                	<spring:url value="/menus/delete/{menuId}" var="menuUrl">
                        <spring:param name="menuId" value="${menu.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(menuUrl)}">Delete</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
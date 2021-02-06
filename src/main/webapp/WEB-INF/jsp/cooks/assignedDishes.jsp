<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<petclinic:layout pageName="dishes">
    <h2>Dishes</h2>

    <table id="dishes" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">DishCourse</th>
            <th style="width: 200px;">Shift</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${preparedDishes}" var="dish">
            <tr>
                <td>
                    <c:out value="${dish.name}"/>
                </td>
                <td>
                    <c:out value="${dish.dish_course.name}"/>
                </td>
                <td>
                    <c:out value="${dish.shift.name}"/>
                </td>
                <td>
                	<spring:url value="/cooks/prepares/{cookId}/delete/{dishId}" var="dishUrl">
                        <spring:param name="dishId" value="${dish.id}"/>
                        <spring:param name="cookId" value="${cook.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(dishUrl)}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
        <div class="form-group">
        <spring:url value="/cooks/prepares/{cookId}/new" var="newUrl">
                        <spring:param name="cookId" value="${cook.id}"/>
        </spring:url>
    	<a class="btn btn-default" href="${fn:escapeXml(newUrl)}">Add new dish to prepare</a>
	</div>
    
</petclinic:layout>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="myschedule">
    <h2>Your Schedule</h2>

    <table id="schedulesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Date</th>
            <th style="width: 200px;">Shift</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userschedules}" var="schedule">
            <tr>
                <td>
                    <c:out value="${schedule.date}"/>
                </td>
                <td>
                    <c:out value="${schedule.shift}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</petclinic:layout>
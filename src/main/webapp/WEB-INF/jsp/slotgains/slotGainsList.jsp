<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="slotgains">
    <h2>Slot Gains</h2>

    <table id="slotgainsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Date</th>
            <th style="width: 200px;">Amount</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${slotgains}" var="slotGain">
            <tr>
                <td>
                    <c:out value="${slotGain.date}"/>
                </td>
                <td>
                    <c:out value="${slotGain.amount}"/>
                </td>
            <td>
                	<spring:url value="/slotgains/delete/{slotGainId}" var="slotGainUrl">
                        <spring:param name="slotGainId" value="${slotGain.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(slotGainUrl)}">Delete</a>
                </td>
            
            <td>
                	<spring:url value="/slotgains/{slotGainId}/edit" var="editUrl">
                        <spring:param name="slotGainId" value="${slotGain.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
        <div class="form-group">
    	<form method="get" action="/slotgains/new">
    		<button class="btn btn-default" type="submit">Add new slot gain</button>
		</form>
	</div>
    
</petclinic:layout>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="artists">
    <h2>Artist</h2>

    <table id="artistsTable" class="table table-striped">
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
        <c:forEach items="${artists}" var="artist">
            <tr>
                <td>
                    <c:out value="${artist.dni}"/>
                </td>
                <td>
                    <c:out value="${artist.name}"/>
                </td>
                <td>
                    <c:out value="${artist.phone_number}"/>
                </td>
                <td>
                	<spring:url value="/artists/delete/{artistId}" var="deleteUrl">
                        <spring:param name="artistId" value="${artist.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
                </td>
                <td>
                    <spring:url value="/artists/{artistId}/edit" var="editUrl">
                        <spring:param name="artistId" value="${artist.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td>
                <td>
                    <spring:url value="/artists/acts/{artistId}" var="actUrl">
                        <spring:param name="artistId" value="${artist.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(actUrl)}">Acts</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-group">
    	<form method="get" action="/artists/new">
    		<button class="btn btn-default" type="submit">Add new artist</button>
		</form>
	</div>
</petclinic:layout>
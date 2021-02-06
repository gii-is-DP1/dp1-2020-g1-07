<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<petclinic:layout pageName="financeList">
    <h2>Finance summary</h2>
    
    <div class="control-group">
    	Dates <select id="comboboxDates" name="date">
        	<option selected>Selecciona fecha</option>
        	<c:forEach var="date" items="${dates}">
            	<option value="${date}">${date}</option>
        	</c:forEach>
        </select>
    </div>

    <table id="restaurantReservationsTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 200px;">Table</th>
            <th style="width: 200px;">Client</th>
            <th style="width: 200px;">Interval</th>
            <th>Actions</th>
            <th></th>
        </tr>
        </thead>
        <tbody id="reservations"></tbody>
    </table>    
</petclinic:layout>
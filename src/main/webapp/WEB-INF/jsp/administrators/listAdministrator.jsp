<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="administrators">
    <h2>Administrator</h2>

    <table id="administratorsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Dni</th>
            <th style="width: 200px;">Name</th>
            <th style="width: 200px;">Phone Number</th>
            <th>Actions</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${administrators}" var="administrator">
            <tr>
                <td>
                    <c:out value="${administrator.dni}"/>
                </td>
                <td>
                    <c:out value="${administrator.name}"/>
                </td>
                <td>
                    <c:out value="${administrator.phone_number}"/>
                </td>
                <td>
                	<spring:url value="/administrators/delete/{administratorId}" var="deleteUrl">
                        <spring:param name="administratorId" value="${administrator.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
                </td>
                <td>
                    <spring:url value="/administrators/{administratorId}/edit" var="editUrl">
                        <spring:param name="administratorId" value="${administrator.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(editUrl)}">Update</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="content" style=" display: flex; flex-direction: column;"> 
        <h3>Managing Casino Tables and Games</h3>
    
	    <div class="caselement">
	    	<div class="form-group">
	    	<form method="get" action="/croupiers">
	    		<button class="btn btn-default" type="submit">Croupiers</button>
			</form>
		</div>
	    <div class="form-group">
	    	<form method="get" action="/slotgains">
	    		<button class="btn btn-default" type="submit">Slot Gains</button>
			</form>
		</div>
		<div class="form-group">
	    	<form method="get" action="/slotgames">
	    		<button class="btn btn-default" type="submit">Slot Games</button>
			</form>
		</div>
		<div class="form-group">
	    	<form method="get" action="/slotmachines">
	    		<button class="btn btn-default" type="submit">Slot Machines</button>
			</form>
		</div>
	    <div class="form-group">
	    	<form method="get" action="/casinotables">
	    		<button class="btn btn-default" type="submit">Casino Tables</button>
			</form>
		</div>
	    <div class="form-group">
	    	<form method="get" action="/games">
	    		<button class="btn btn-default" type="submit">Games</button>
			</form>
		</div>
		</div>
		<h3>Managing the restaurant</h3>
		
		<div class="caselement">
			<div class="form-group">
		    	<form method="get" action="/dishes">
		    		<button class="btn btn-default" type="submit">Dishes</button>
				</form>
			</div>
			<div class="form-group">
		    	<form method="get" action="/menus">
		    		<button class="btn btn-default" type="submit">Menus</button>
				</form>
			</div>
			<div class="form-group">
		    	<form method="get" action="/restaurantreservations">
		    		<button class="btn btn-default" type="submit">Restaurant Reservation</button>
				</form>
			</div>
			<div class="form-group">
		    	<form method="get" action="/restaurantTables">
		    		<button class="btn btn-default" type="submit">Restaurant Tables</button>
				</form>
			</div>
			<div class="form-group">
		    	<form method="get" action="/cooks">
		    		<button class="btn btn-default" type="submit">Cooks</button>
				</form>
			</div>
			<div class="form-group">
		    	<form method="get" action="/chefs">
		    		<button class="btn btn-default" type="submit">Chefs</button>
				</form>
			</div>
			<div class="form-group">
		    	<form method="get" action="/waiters">
		    		<button class="btn btn-default" type="submit">Waiters</button>
				</form>
			</div>
		</div>
		<h3>Managing events</h3>
		
		<div class="caselement"> 
			<div class="form-group">
		    	<form method="get" action="/artists">
		    		<button class="btn btn-default" type="submit">Artists</button>
				</form>
			</div>
			<div class="form-group">
		    	<form method="get" action="/showress">
		    		<button class="btn btn-default" type="submit">Show Reservation</button>
				</form>
			</div>
		
			<div class="form-group">
		    	<form method="get" action="/stages">
		    		<button class="btn btn-default" type="submit">Stages</button>
				</form>
			</div>
			
			<div class="form-group">
		    	<form method="get" action="/events">
		    		<button class="btn btn-default" type="submit">Events</button>
				</form>
			</div>
		</div>
		<h3>Managing employees and finances</h3>
		<div class="caselement">
		    <div class="form-group">
		    	<form method="get" action="/clients">
		    		<button class="btn btn-default" type="submit">Clients</button>
				</form>
			</div>
			<div class="form-group">
		    	<form method="get" action="/employees">
		    		<button class="btn btn-default" type="submit">Employees</button>
				</form>
			</div>
			<div class="form-group">
		    	<form method="get" action="/finance">
		    		<button class="btn btn-default" type="submit">Finance</button>
				</form>
			</div>
			<div class="form-group">
		    	<form method="get" action="/maintenanceWorkers">
		    		<button class="btn btn-default" type="submit">Maintenance</button>
				</form>
			</div>
			<div class="form-group">
		    	<form method="get" action="/schedules">
		    		<button class="btn btn-default" type="submit">Schedules</button>
				</form>
			</div>
			<div class="form-group">
		    	<form method="get" action="/users">
		    		<button class="btn btn-default" type="submit">Users</button>
				</form>
			</div>
		</div>
	</div>
	
</petclinic:layout>
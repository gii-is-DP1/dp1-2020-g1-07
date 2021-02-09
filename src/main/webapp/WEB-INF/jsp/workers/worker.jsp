<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="workerPad">
    <h2>Workers</h2>
    <div class="content" style=" display: flex; flex-direction: column;"> 
        <h3>Schedules</h3>
	    <div class="caselement">
	    <div class="form-group">
	    	<form method="get" action="/schedules/user">
	    		<button class="btn btn-default" type="submit">Schedules</button>
			</form>
		</div>
		</div>
		
		<h3>Managing the restaurant</h3>
		
		<div class="caselement">
			<div class="form-group">
		    	<form method="get" action="/restaurantTables">
		    		<button class="btn btn-default" type="submit">Restaurant Tables</button>
				</form>
			</div>
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
		    	<form method="get" action="/menus/byDay">
		    		<button class="btn btn-default" type="submit">Actual Menu</button>
				</form>
			</div>
			
		</div>
		<h3>Managing Events</h3>
		
		<div class="caselement"> 
			<div class="form-group">
		    	<form method="get" action="/events/byDay">
		    		<button class="btn btn-default" type="submit">Event</button>
				</form>
			</div>
			
		</div>
		<h3>Managing Casino Tables</h3>
		<div class="caselement"> 
			<div class="form-group">
		    	<form method="get" action="/casinotables">
		    		<button class="btn btn-default" type="submit">Casino Tables</button>
				</form>
			</div>
		<div class="caselement"> 
			<div class="form-group">
		    	<form method="get" action="/cgains">
		    		<button class="btn btn-default" type="submit">Gains</button>
				</form>
			</div>
			
		</div>
		
		</div>
	
</petclinic:layout>
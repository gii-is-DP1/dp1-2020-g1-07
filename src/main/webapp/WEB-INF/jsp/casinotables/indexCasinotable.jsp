<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="casinotables">
	<body class="gambling">
    <div style="
    height: 80%;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 25%;"> 
    
    <div style="
    background-color: #f2dfdc;
    padding: 30px;
    border-radius: 30px;
    display: flex;
    flex-direction: column;
    ">
    
    <h2>Where do you want to go?</h2>
	<div style="display: flex;">
	
	<div style="display: flex; flex-direction: center; justify-content: space-around; width:470px; margin-top: 7px">
    <div class="form-group">
    	<form method="get" action="/games">
    		<button class="btn btn-default" type="submit">View table games</button>
		</form>
	</div>
	
	<div class="form-group">
    	<form method="get" action="/slotgames">
    		<button class="btn btn-default" type="submit">View slot games</button>
		</form>
	</div>
	
	<div class="form-group">
    	<form method="get" action="/cgains/user">
    		<button class="btn btn-default" type="submit">View your gains</button>
		</form>
	</div>
	</div>
	</div>
    </div>
    </div>
    </body>
</petclinic:layout>
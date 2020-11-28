<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
	<div class="fullpage"> 
    <div class="row">
        <div class="col-md-12">
        </div>
        
    </div>
    <div class = "row">
    	<h1 class="tuvis"> Tuvi's casino </h1>
    	<a style="font-size:50px;" class = "enlace" href = "/casinotables" > Delete table Link</a><br>
   		<a style="font-size:50px;" class = "enlace" href = "/casinotables/new" > Add table link</a>
    </div>
    </div>
</petclinic:layout>

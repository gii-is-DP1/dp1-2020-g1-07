<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="cgains">
    <jsp:body>
        <h2>Edit Client Gain</h2>

		<script>
    	function chgActionSh()
    		{
        
        var frm = document.getElementById('id') || null;
        if(frm) {
           frm.action = "/cgains/"+"${cgain.id}"+"/edit";
        }

   			 }
    	</script>
			
       <form:form modelAttribute="cgain" class="form-horizontal" action="/cgains/{cgainId}/edit" onsubmit = "chgActionSh()" id = "id">
            <div class="form-group has-feedback">
            	<petclinic:inputField label="Amount" name="amount"/>
                <petclinic:inputField label="Date" name="date"/>
                <div class="control-group">
                 Client <select id="client" name="client">
                	<c:forEach var="c" items="${clients}">
		            	<option value="${c.dni}">${c.dni}</option>
		            </c:forEach>
		        </select>
                </div>
                <div class="control-group">
                Game <select id="game" name="game">
	                <c:forEach var="g" items="${games}">
		            	<option value="${g.name}">${g.name}</option>
		            </c:forEach>
		        </select>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Update Client Gain</button>
                </div>
            </div>
            
        </form:form>

        
    </jsp:body>

</petclinic:layout>
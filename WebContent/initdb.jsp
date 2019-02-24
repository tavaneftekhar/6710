<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="head-includes.html"%>
  <title>Home</title>
</head>
<body>
	<%@include file="nav-includes.html"%>
  
  <div class="container">
    <div class="row">
      <div class="col-sm-12 col-md-6 offset-md-3">
        
        <%
	    if (request.getAttribute("databaseInitialized") == "true") {
	        out.println("<div class=\"alert alert-success\"><big><strong>Success!</strong> Database initialized.</big></div>");
	    } else {
	    	out.println("<div class=\"alert alert-danger\"><big><strong>Uh oh!</strong> Database not initialized.</big></div>");
	    }
	%>

      </div>
    </div>
  </div>

</body>
</html>

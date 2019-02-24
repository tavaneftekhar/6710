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
			    if (session.getAttribute("loggedIn") == Boolean.TRUE) {
			        %>
			        <div class="alert alert-info"><h3 class='m-0 font-weight-bold'>Welcome Back!</h3></div>
			        <a href="logout">Log out</a>
			        <%
			    } else {
			    	%>
			    	<div class="card shadow mb-4">
          <div class="card-header py-3">
         <h3 class='m-0 font-weight-bold'>Please log in</h3>
          </div>
          <div class="card-body">
            <form action="login" method="POST">
              <div class="form-group">
                <input type="text" class="form-control" id="username" name="username" placeholder="Username">
              </div>
              <div class="form-group">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
              </div>
              <input type="submit" name="submit" value="Login" class="btn btn-primary btn-user btn-block">
            </form>
            <hr>
            <div class="text-center">
              <a class="small" href="register.jsp">Create an Account!</a>
            </div>

          </div>
          </div>
			    	<%
			    }
			%>
            
        
      </div>
    </div>
  </div>

</body>
</html>

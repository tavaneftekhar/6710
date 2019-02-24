<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="head-includes.html"%>
  <title>Create your account</title>
</head>
<body>
  <%@include file="nav-includes.html"%>
  <div class="container">
    <div class="row">
      <div class="col-sm-12 col-md-6 offset-md-3">
        
      
			    	<div class="card shadow mb-4">
          <div class="card-header py-3">
         <h3 class='m-0 font-weight-bold'>Create your account</h3>
          </div>
          <div class="card-body">
            <form action="register-user" method="POST">
            <div class="form-group">
                <input type="text" class="form-control" name="first_name" placeholder="First name" required>
              </div>
              <div class="form-group">
                <input type="text" class="form-control" name="last_name" placeholder="Last name" required>
              </div>
              <div class="form-group">
                <input type="text" class="form-control" name="email" placeholder="Email" required>
              </div>
              <div class="form-group">
                <input type="number" class="form-control" name="age" placeholder="Age" min="1" max="200" required>
              </div>
              <div class="form-group">
              <div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" name="gender" id="gender1" value="M" required>
				  <label class="form-check-label" for="gender1">Male</label>
			  </div>
			  <div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" name="gender" id="gender2" value="F">
				  <label class="form-check-label" for="gender2">Female</label>
			  </div>
			  </div>
			  <hr>
              <div class="form-group">
                <input type="text" class="form-control" id="username" name="username" placeholder="Username" required>
              </div>
              <div class="form-group">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
              </div>
              <input type="submit" name="submit" value="Register" class="btn btn-primary btn-user btn-block">
            </form>
            <hr>
            <div class="text-center">
              Already have an account? <a class="small" href="home">Login</a>
            </div>

          </div>
          </div>
	
        
      </div>
    </div>
  </div>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="head-includes.html"%>
  <title>Home</title>
</head>
<body>
  <nav class="navbar navbar-expand navbar-dark bg-primary topbar mb-5 static-top shadow">

    <a class="navbar-brand" href="#">JokeyJokes</a>

    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="#">All Jokes</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Your Reviews</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Favorite Friends</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Favorite Jokes</a>
      </li>
    </ul>
    <ul class="navbar-nav ml-auto">
      <button class="btn btn-danger" type="submit">Initialize Database</button>
    </ul>

  </nav>
  <div class="container">
    <div class="row">
      <div class="col-sm-12 col-md-6 offset-md-3">
        <div class="card shadow mb-4">
          <div class="card-header py-3">
            <h3 class="m-0 font-weight-bold">Welcome Back!</h3>
          </div>
          <div class="card-body">
            <form>
              <div class="form-group">
                <input type="email" class="form-control" id="usernameInput" name="usernameInput" placeholder="Username">
              </div>
              <div class="form-group">
                <input type="password" class="form-control" id="passwordInput" name="passwordInput" placeholder="Password">
              </div>
              <a href="index.jsp" class="btn btn-primary btn-user btn-block">
                Login
              </a>
            </form>
            <hr>
            <div class="text-center">
              <a class="small" href="register.jsp">Create an Account!</a>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>

</body>
</html>

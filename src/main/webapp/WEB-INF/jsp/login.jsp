<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>SIMS | Log in</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="stylesheet" href="<%=request.getRemoteHost() %>/sims-login-service/bootstrap.min.css">
  <link rel="stylesheet" href="<%=request.getRemoteHost() %>/sims-login-service/login1.css">
  <link rel="stylesheet" href="<%=request.getRemoteHost() %>/sims-login-service/AdminLTE.min.css"/>
  <link rel="stylesheet" href="<%=request.getRemoteHost() %>/sims-login-service/custonCSS.css"/>

</head>
<body class="hold-transition login-page">
<div class="wrapper row-offcanvas row-offcanvas-left show-div login-background login-background">
  <aside class="right-side">
    <section class="content-header hide-div">
    </section>
    <div id="alerts-container" class="hide-div login-background">
    </div>

    <section class="content ng-scope login-background" ng-view="" ng-class="{'login-background':title ==='login'}">
      <div class="container ng-scope">


        <form class="form-signin" action="/login" method="post">

          <h1 class="form-signin-heading text-muted">SIMS</h1>
          <input type="text" class="form-control" name="username" id="username" placeholder="Username" required=""
                 autofocus="" >
          <input type="password" class="form-control" name="password" id="password" placeholder="Password" required=""
          >
          <button class="btn btn-lg btn-primary btn-block" type="submit" id="login_button" >
            Sign In
          </button>
          <input type="hidden" name="target" value="TARGET">

        </form>

      </div>
    </section>
  </aside>
</div>
</div>

</body>
</html>
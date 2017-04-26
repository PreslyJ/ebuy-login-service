<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>SIMS | Log in</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <style>

    .login input {
      padding:5px;
      margin:5px;
    }
    .login-background {
      background-color: #2c3e50 !important;
    }

    h1, h2, h3, h4, h5, h6, .h1, .h2, .h3, .h4, .h5, .h6 {
      font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
      font-weight: 500;
      line-height: 1.1;
      color: inherit;
    }
    .form-control {
      display: block;
      width: 100%;
      height: 34px;
      padding: 6px 12px;
      font-size: 14px;
      line-height: 1.428571429;
      color: #555;
      vertical-align: middle;
      background-color: #fff;
      background-image: none;
      border: 1px solid #ccc;
      border-radius: 4px;
      -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
      box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
      -webkit-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
      transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s
    }
    .login-page{
      background-color:#2c3e50 ;
      height: 100%;
      position: relative;
      overflow: hidden;
    }
    .form-signin {
      max-width: 280px;
      padding: 15px;
      margin: 17px auto 0;

    }
    .form-signin .form-signin-heading, .form-signin {
      margin-bottom: 10px;
    }
    .form-signin .form-control {
      position: relative;
      font-size: 16px;
      height: auto;
      padding: 10px;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
    }
    .form-signin .form-control:focus {
      z-index: 2;
    }
    .form-signin input[type="text"] {
      margin-bottom: -1px;
      border-bottom-left-radius: 0;
      border-bottom-right-radius: 0;
      border-top-style: solid;
      border-right-style: solid;
      border-bottom-style: none;
      border-left-style: solid;
      border-color: #000;
    }
    .form-signin input[type="password"] {
      margin-bottom: 10px;
      border-top-left-radius: 0;
      border-top-right-radius: 0;
      border-top-style: none;
      border-right-style: solid;
      border-bottom-style: solid;
      border-left-style: solid;
      border-color: #000;
    }
    .form-signin-heading {
      color: #fff;
      text-align: center;
      text-shadow: 0 2px 2px rgba(0,0,0,0.5);
    }
    .btn-primary:hover, .btn-primary:focus, .btn-primary:active, .btn-primary.active, .open .dropdown-toggle.btn-primary {
      color: #fff;
      background-color: #3276b1;
      border-color: #285e8e;
    }
    .btn-block {
      display: block;
      width: 100%;
      padding-right: 0;
      padding-left: 0;
    }
    .btn-lg {
      padding: 10px 16px;
      font-size: 18px;
      line-height: 1.33;
      border-radius: 6px;
    }
    .btn-primary {
      color: #fff;
      background-color: #428bca;
      border-color: #357ebd;
    }
  </style>

</head>
<body class="hold-transition login-page">
<div class="wrapper login-background">

    <div class="container ng-scope">

      <div class="login-c">
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


</div>
</div>

</body>

<script>
    var req = new XMLHttpRequest();
    req.open('GET', document.location, false);
    req.send(null);
    var target = req.getResponseHeader('X-Target').toLowerCase();
    document.getElementsByName("target")[0].value =target;

</script>
</html>
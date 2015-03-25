<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<html>
  <head>
    <c:import url="../head.jsp"></c:import>
  </head>
  <body class="login-page">
  
  <c:import url="../message-div.jsp"></c:import>

    <div class="login-box">
      <div class="login-logo">
        <b>You</b>APP
      </div><!-- /.login-logo -->
      <div class="login-box-body">
        <p class="login-box-msg">Sign in to start your session</p>
        <form  id="loginForm" method="post" >
          <div class="form-group has-feedback">
            <input name="user.userName" type="text" class="form-control" placeholder="Email"/>
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input name="user.password" type="password" class="form-control" placeholder="Password"/>
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <div class="row">
            <div class="col-xs-8">    
              <div class="checkbox icheck">
                <label>
                  <input type="checkbox"  name="remember" > Remember Me
                </label>
              </div>                        
            </div><!-- /.col -->
            <div class="col-xs-4">
              <button type="submit" class="btn btn-primary btn-block btn-flat" 
              >Sign In</button>
            </div><!-- /.col -->
          </div>
        </form>

        <a href="#">I forgot my password</a><br>
        <a href="javascript:void(0)" 
        onclick='httpRELOAD("/login.loginaction/toRegister")'
        class="text-center"  >Register a new membership</a>

      </div><!-- /.login-box-body -->
    </div><!-- /.login-box -->
    <script type="text/javascript">
      $(function () {
        $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
        });
      });
      
		$(function (){
			 $("#loginForm").validate({
			rules: {
			   'user.userName': {
				   required: true
			   },
			   'user.password': {
				   required: true
			   }
			  },
			  submitHandler:function(form){
				  httpPOST('/login.loginaction/login', form.id);
		        } 
			    });
		});
      
      
    </script>
    
    <script type="text/javascript">
    
    </script>
    
  </body>
</html>
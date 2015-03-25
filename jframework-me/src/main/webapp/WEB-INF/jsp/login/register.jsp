<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<html>
  <head>
  <c:import url="../head.jsp"></c:import>
  </head>
  <body class="register-page">
  
  <c:import url="../message-div.jsp"></c:import>
  
    <div class="register-box">
      <div class="register-logo">
        <a href="../../index2.html"><b>You</b>APP</a>
      </div>

      <div class="register-box-body">
        <p class="login-box-msg">Register a new membership</p>
        <form id ="registerForm" method="post">
          <!-- 
          <div class="form-group has-feedback">
            <input type="text" class="form-control" placeholder="Full name"/>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
          </div>
           -->
          <div class="form-group has-feedback">
            <input type="text" class="form-control" placeholder="Email"  
            	name="user.userName"
            />
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="password" class="form-control" placeholder="Password" 
            	name="user.password"
            />
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="password" class="form-control" placeholder="Retype password" 
            	name="user.retypePassword"
            />
            <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
          </div>
          <div class="row">
            <div class="col-xs-8">    
              <div class="checkbox icheck">
                <label>
                  <input type="checkbox"> I agree to the <a href="#">terms</a>
                </label>
              </div>                        
            </div><!-- /.col -->
            <div class="col-xs-4">
              <button type="submit"  class="btn btn-primary btn-block btn-flat" 
              >Register</button>
            </div><!-- /.col -->
          </div>
        </form>        
        <a href="javascript:void(0)" class="text-center"
        onclick='httpRELOAD("/login.loginaction/toLogin")'
        >I already have a membership</a>
      </div><!-- /.form-box -->
    </div><!-- /.register-box -->
    <script>
      $(function () {
        $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
        });
      });
      
      

		$(function (){
			 $("#registerForm").validate({
					rules: {
					   'user.userName': {
						   required: true
					   },
					   'user.password': {
						   required: true
					  },
					  'user.retypePassword': {
						   required: true
					  }
					},
				  submitHandler:function(form){
					  httpPOST('/login.loginaction/register', form.id);
			        } 
			    });
			});
    </script>
  </body>
</html>
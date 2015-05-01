
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            User
	            <small>Create</small>
	          </h1>
	          <ol class="breadcrumb">
	            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
	            <li class="active">User</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
					
				<form role="form"  id="createUserForm">
	                  <div class="box-body">
	                    <div class="form-group">
	                      <label for="exampleInputEmail1">User Name</label>
	                      <input type="email" class="form-control" 
	                      	name="user.userName"
	                      	id="exampleInputEmail1" placeholder="Enter email">
	                    </div>
	                    <div class="form-group">
	                      <label for="exampleInputPassword1">Password</label>
	                      <input type="password" class="form-control" 
	                      	name="user.password"
	                      id="exampleInputPassword1" placeholder="Password">
	                    </div>
	                    <!-- 
	                    <div class="form-group">
	                      <label for="exampleInputFile">File input</label>
	                      <input type="file" id="exampleInputFile">
	                      <p class="help-block">Example block-level help text here.</p>
	                    </div>
	                    <div class="checkbox">
	                      <label>
	                        <input type="checkbox"> Check me out
	                      </label>
	                    </div>
	                     -->
	                  </div><!-- /.box-body -->

	                  <div class="box-footer">
	                    <button type="button" class="btn btn-primary"  onclick="execute();">Submit</button>
	                  </div>
                </form>
		
	        </section>

	<script type="text/javascript">
		function execute(){
			submitPOST('/login.loginaction/createUser', 'createUserForm');
		}
	</script>

</div>
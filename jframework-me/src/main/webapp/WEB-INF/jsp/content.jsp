
<div id="youapp-content">
		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            Dashboard
	            <small>Control panel</small>
	          </h1>
	          <ol class="breadcrumb">
	            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
	            <li class="active">Dashboard</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
	        	
	        	<form  id="form1"  method ="post"  enctype ="multipart/form-data">
	        		<input type="text"  name="userName" />
	        		<input type="text"  name="age" />
	        		<input type ="file" name ="posterUrlUploadPath"  id ="posterUrlUploadPath"
	        		  title ="uploadImage" />  
	        		<input type="button"  value='Commit'   onclick='submitPOST("/login.loginaction/view","form1")' />
	        	</form>
	        </section>

</div>
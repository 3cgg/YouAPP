<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
       
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            User
	            <small>Upload</small>
	          </h1>
	          <ol class="breadcrumb">
	            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
	            <li class="active">User</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
				<c:import url="../message-success.jsp"></c:import>
				
				<form role="form"  id="imageUploadForm">
	                  <div class="box-body">
	                   
	                    <div class="form-group">
	                      <label for="exampleInputFile"></label>
	                      <input type="file"  id="userImage"  name="userImage">
	                      <p class="help-block">请选择头像文件（.JPG）</p>
	                    </div>
	                    
	                  </div><!-- /.box-body -->

	                  <div class="box-footer">
	                    <button type="submit" class="btn btn-primary" >Submit</button>
	                    <button type="button" class="btn btn-primary"  onclick="donwload();">Download</button>
	                  </div>
                </form>
				
	        </section>

	<script type="text/javascript">
	$(function (){
		 $("#imageUploadForm").validate({
		rules: {
		   'userImage': {
		   }
		  },
		  submitHandler:function(form){
			  submitPOST('/user.useraction/uploadImage', form.id);
	        } 
		    });
	});
	</script>
	<script type="text/javascript">
	
	function donwload(){
		GETDownload("/user.useraction/downloadImage");
	}
	
	</script>
</div>
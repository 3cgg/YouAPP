<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">

		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            体重录入
	            <small>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='httpGET("/weight.weightaction/toNavigate")'
	                ><i class=" fa ion-ios-undo"></i></a>
	            </small>
	          </h1>
	          <ol class="breadcrumb">
	            <li>
	            <a 
	            	href="javascript:void(0)" 
	                onclick='httpGET("/weight.weightaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li class="active">体重录入</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
				<c:import url="../message-success.jsp"></c:import>
									
				<form role="form"  id="recordWeightForm">
	                  <div class="box-body">
	                    <div class="form-group">
	                      <label for="weight">体重（KG）</label>
	                      <input type="text" class="form-control" 
	                      	name="weight.weight"
	                      	id="weight" placeholder="Weight : KG">
	                    </div>
	                    <div class="form-group">
	                      <label for="record-time">日期</label>
		                    <div class="input-group">
		                      <div class="input-group-addon">
		                        <i class="fa fa-calendar"></i>
		                      </div>
		                      <input id="record-time" type="text" 
		                      name="weight.recordTime" 
		                      class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask/>
		                    </div><!-- /.input group -->
	                    </div>
	                    
	                    <div class="form-group">
	                      <label for="weight-desc">备注</label>
	                      <textarea  class="form-control" 
	                      	name="weight.description" rows="3"
	                      	id="weight-desc" placeholder="备注" ></textarea>
	                    </div>
	                    
	                  </div><!-- /.box-body -->

	                  <div class="box-footer">
	                    <button type="submit" class="btn btn-primary"  >Submit</button>
	                  </div>
                </form>
		
	        </section>

	<script type="text/javascript">
	
		$(function (){
			$("#record-time").inputmask("yyyy-mm-dd", {"placeholder": "yyyy-mm-dd"});
		});
		
		$(function (){
			 $("#recordWeightForm").validate({
			rules: {
			   'weight.weight': {
				   required: true,
				   number:true,
				   max:100
			   }
			  },
			  submitHandler:function(form){
				  httpPOST('/weight.weightaction/recordWeight', form.id);
		        } 
			    });
		});
		
	</script>

</div>
	 <!-- Main content -->
    <section class="content"  id="add${modelModel.simpleClassName}Section" >
    	<div class="box box-info">
	        <div class="box-footer">
	          <button type="button" class="btn btn-primary"  id="add${modelModel.simpleClassName}Btn" >提交</button>
	          <button type="button" class="btn btn-primary"
	          onclick='$(this).goView("${uiAddModel.uiContext.listFilePath}");'
	          >返回</button>
	        </div> 
      </div>
    	
    	<div class="box box-info">
	    	<!--   .box-header   -->
	        <div class="box-header with-border">
	          <h3 class="box-title">新增</h3>
	          <div class="box-tools pull-right">
	          </div>
	        </div>
	        <!-- /.box-header -->
	        
	        <div class="box-body">
	        
	        <form role="form"  id="add${modelModel.simpleClassName}Form"  class="form-horizontal">
    		<#list uiAddModel.addFields as modelField>
              	<#if modelField_index % 2=0>
              	<div class="form-group">
              	</#if>
              	
              	<#if !(modelField.virtual)>
              	   <label for="${modelField.property}" class="col-sm-1 control-label">${modelField.label}</label>
                   <div class="col-sm-${modelField.colNum}">
                   <#if modelField.fieldSpec.fieldType="textarea"> 
                    <textarea  id="${modelField.property}" name="${modelField.property}" class="form-control" rows="3" ></textarea>
                    </#if>
                    <#if modelField.fieldSpec.fieldType="text"> 
                    <input  id="${modelField.property}" name="${modelField.property}" type="text" class="form-control"></input>
                    </#if>
                  </div>
              	</#if>
              	  
              	<#if modelField_index % 2=1>
              	</div>
              	</#if>
					
				</#list>
    		</form>
	          <!-- /.row -->
	        </div>
      </div>
    </section>
	
	
	<script type="text/javascript">
		
		$_youapp.ready(function (){
			
			var page=$.extend({
	    		root:$("#add${modelModel.simpleClassName}Section"),
	    		model:{
	    		}
	    	},$_youapp.pageTemplate);
			
			page.root.find("#add${modelModel.simpleClassName}Form").validate({
					rules: {
					<#assign ind=0 />
						<#list uiAddModel.addFields as modelField>
						<#if !(modelField.virtual)>
						<#if ind!=0>
						,
						</#if>
						'${modelField.property}': {
							   required: ${modelField.fieldSpec.required},
							   maxlength:${modelField.fieldSpec.maxLength?c}
						   }
						<#assign ind=ind+1 />   	
						</#if>		
						</#list>
					  },
					  submitHandler:function(form){
						  page.submitForm({
							  url:'${uiAddModel.uiContext.saveMethodUrl}',
							  formSelector:form,
							  success:function(data){
								  page.root.goView('${uiAddModel.uiContext.listFilePath}');
							  }
							});
					 }
				});
			page.root.find('#add${modelModel.simpleClassName}Btn').on('click',function(){
				page.root.find("#add${modelModel.simpleClassName}Form").submit();
			});	
		});
	</script>



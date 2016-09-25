	 <!-- Main content -->
    <section class="content"  id="edit${modelModel.simpleClassName}Section"  ms-controller="${modelModel.variableName}_edit">
    	<div class="box box-info">
	        <div class="box-footer">
	          <button type="submit" class="btn btn-primary"  id="edit${modelModel.simpleClassName}Btn"  >提交</button>
	          <button type="button" class="btn btn-primary"
	          onclick='$(this).goView("${uiEditModel.uiContext.listFilePath}");'
	          >返回</button>
	        </div> 
      </div>
    	
    	<div class="box box-info">
	    	<!--   .box-header   -->
	        <div class="box-header with-border">
	          <h3 class="box-title">修改</h3>
	          <div class="box-tools pull-right">
	          </div>
	        </div>
	        <!-- /.box-header -->
	        
	        <div class="box-body">
	        <form role="form"  id="edit${modelModel.simpleClassName}Form"  class="form-horizontal">
    		<#list uiEditModel.editFields as modelField>
              	<#if modelField_index % 2=0>
              	<div class="form-group">
              	</#if>
              	
              	<#if !(modelField.virtual)>
              	   <label for="${modelField.property}" class="col-sm-1 control-label">${modelField.label}</label>
                   <div class="col-sm-${modelField.colNum}">
                   <#if modelField.fieldSpec.fieldType="textarea"> 
                    <textarea id="${modelField.property}" name="${modelField.property}" ms-attr-value="data.${modelField.property}"  class="form-control" rows="3" ></textarea>
                    </#if>
                    <#if modelField.fieldSpec.fieldType="text"> 
                    <input id="${modelField.property}" name="${modelField.property}" ms-attr-value="data.${modelField.property}"   type="text"  class="form-control" >
                    </#if>
                  </div>
              	</#if>
              	  
              	<#if modelField_index % 2=1>
              	</div>
              	</#if>
					
			</#list>
			
			<#list uiEditModel.hiddenFields as modelField>
			<input type="hidden"  name="${modelField.property}"  ms-attr-value="data.${modelField.property}"  />
			</#list>
    		
    		</form>
	          <!-- /.row -->
	        </div>
      </div>
      
    
    </section>
	<script type="text/javascript">
    
    $_youapp.ready(function(){
    	
    	var page=$.extend({
    		root:$("#edit${modelModel.simpleClassName}Section"),
    		model:{
    			vm:avalon.define({
    		        $id: "${modelModel.variableName}_edit",
    		        data: {}
    		    })
    		}
    	},$_youapp.pageTemplate);
    	
    	avalon.scan(page.root[0], page.model.vm);  // void braces
    	
    	function getId(){
        	return page.root.getViewParam().id;
        }
    	
    	page.ajaxGet({
			  url:'${uiEditModel.uiContext.getMethodUrl}',
			  formData:{'id':getId()},
			  success:function(data){
				  page.model.vm.data=data;
		  		  avalon.scan(page.root[0], page.model.vm);
			  }
		});
    	
    	page.root.find("#edit${modelModel.simpleClassName}Form").validate({
 			rules: {
 			<#assign ind=0 />
 				<#list uiEditModel.editFields as modelField>
				<#if !(modelField.virtual)>
				<#if ind!=0>
				,
				</#if>
				'${modelField.property}': {
					   required: ${modelField.fieldSpec.required},
					   maxlength:${modelField.fieldSpec.maxLength}
				   }
				<#assign ind=ind+1 />   	
				</#if>		
				</#list>
 			  },
 			  submitHandler:function(form){
 				  page.submitForm({
 					  url:'${uiEditModel.uiContext.updateMethodUrl}',
 					  formSelector:form,
 					  success:function(data){
 						  page.root.goView('${uiEditModel.uiContext.listFilePath}');
 					  }
 					});
 			  
 			  } 
 		});
    	page.root.find('#edit${modelModel.simpleClassName}Btn').on('click',function(){
			page.root.find("#edit${modelModel.simpleClassName}Form").submit();
		});
    });
    </script>
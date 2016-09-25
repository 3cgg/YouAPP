	 <!-- Main content -->
    <section class="content"   id="view${modelModel.simpleClassName}Section"   ms-controller="${modelModel.variableName}_view">
    
    	<div class="box box-info">
	        <div class="box-footer">
	          <button type="button" class="btn btn-primary"
	          onclick='$(this).goView("${uiViewModel.uiContext.listFilePath}");'
	          >返回</button>
	        </div> 
      </div>
    	
    	<div class="box box-info">
	    	<!--   .box-header   -->
	        <div class="box-header with-border">
	          <h3 class="box-title">明细</h3>
	          <div class="box-tools pull-right">
	          </div>
	        </div>
	        <!-- /.box-header -->
	        
	        <div class="box-body form-horizontal">
	        <#list uiViewModel.viewFields as modelField>
              	<#if modelField_index % 2=0>
              	<div class="form-group">
              	</#if>
              	
              	<#if !(modelField.virtual)>
              	   <label for="${modelField.property}" class="col-sm-1 control-label">${modelField.label}</label>
                   <div class="col-sm-${modelField.colNum}">
                   <span id="${modelField.property}"  class="form-control label-form-control">{{data.${modelField.property}}}</span>
                  </div>
              	</#if>
              	  
              	<#if modelField_index % 2=1>
              	</div>
              	</#if>
					
			</#list>
	        </div>
      </div>
    </section>
    <script type="text/javascript">
    
    $_youapp.ready(function(){
    	
    	var page=$.extend({
    		root:$("#view${modelModel.simpleClassName}Section"),
    		model:{
    			vm:avalon.define({
    		        $id: "${modelModel.variableName}_view",
    		        data: {}
    		    })
    		}
    	},$_youapp.pageTemplate);
    	
    	avalon.scan(page.root[0], page.model.vm);  // void braces
    	
    	function getId(){
        	return page.root.getViewParam().id;
        }
    	
    	page.ajaxGet({
			  url:'${uiViewModel.uiContext.getMethodUrl}',
			  formData:{'id':getId()},
			  success:function(data){
				  page.model.vm.data=data;
		  		  avalon.scan(page.root[0], page.model.vm);
			  }
			});
    });
    </script>
	


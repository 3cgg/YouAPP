	 <!-- Main content -->
    <section class="content"   id="search${modelModel.simpleClassName}Section"   ms-controller="${modelModel.variableName}_view">
      
      <div class="box box-info">
      	<div class="box-header with-border">
          <h3 class="box-title"></h3>
          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
          </div>
        </div>
              <div class="box-body">
              <form class="form-horizontal"  id="search${modelModel.simpleClassName}Form">
              	
              	<#list uiListModel.criteriaFields as modelField>
              	<#if modelField_index % 2=0>
              	<div class="form-group">
              	</#if>
              	  <label for="${modelField.property}" class="col-sm-1 control-label">${modelField.label}</label>
                  <div class="col-sm-${modelField.colNum}">
                    <input type="text"  name="${modelField.property}" class="form-control" id="${modelField.property}">
                  </div>
              	<#if modelField_index % 2=1>
              	</div>
              	</#if>
					
				</#list>
                </form>
              </div>
          </div>
          <!-- /.box -->
      
    	
    	<div class="box box-info">
	    	<!--   .box-header   -->
	        <div class="box-header with-border">
	          <button type="submit" class="btn btn-primary btn-sm"
	          onclick='$(this).goView("${uiListModel.uiContext.addFilePath}");'
	          >新增</button>
	          <div class="box-tools pull-right">
	            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	          </div>
	          
	        </div>
	        <!-- /.box-header -->
	        <div class="box-body" >
	          <table id="${modelModel.variableName}ListTable" 
	          		class="" 
	          			cellspacing="0" width="100%">
	            <thead>
					<tr>
						<#if uiListModel.checkbox>
		              	<th></th>
		              	</#if>
						
						<#list uiListModel.tableFields as modelField>
						<th>${modelField.label}</th>
						</#list>
						
					</tr>
				</thead>
            </table>
	          
	          
	          <!-- /.row -->
	        </div>
      </div>
    
    </section>
    <script type="text/javascript">
    $_youapp.ready(function() {
    	
    	var page=$.extend({
    		root:$("#search${modelModel.simpleClassName}Section"),
    		model:{
    			vm:avalon.define({
    		        $id: "${modelModel.variableName}_view",
    		        data: {}
    		    })
    		}
    	},$_youapp.pageTemplate);
    	
    	page.listTable=page.root.find('#${modelModel.variableName}ListTable').initDataTable({
			url:"${uiListModel.uiContext.pageMethodUrl}",
			urlDataFn:function(){
				return page.root.find("#search${modelModel.simpleClassName}Form").serializeObj();
			},
			lengthChange:false,
			checkbox:true,
			ops:{
				view:function(id,rowData){
                    page.root.find('#${modelModel.variableName}ListTable').goView('${uiListModel.uiContext.viewFilePath}',{"id":id});
				},
				edit:function(id,rowData){
					page.root.find('#${modelModel.variableName}ListTable').goView('${uiListModel.uiContext.editFilePath}',{"id":id});
				},
				del:function(id,rowData){
					page.ajaxGet({
						  url:'${uiListModel.uiContext.deleteByIdMethodUrl}',
						  formData:{'id':id},
						  success:function(data){
							  page.listTable.ajax.reload();
						  }
						});
				}
			},
			columns : [
			
			<#list uiListModel.tableFields as modelField>
			{
				"data" : "${modelField.property}",
				"width": "${modelField.width}"
			}
			<#if (modelField_has_next)!>
			,
			</#if>			
			</#list>
			]
		});
		
		page.root.find("#search${modelModel.simpleClassName}Btn").on("click",function(){
			page.listTable.ajax.reload();
		})
		
	});
	</script>


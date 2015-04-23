<!-- class="youapp-popover-mark" 
	                  data-youappDelete="/weight.weightaction/deleteWeight"
	                  data-youappView="/weight.weightaction/toViewWeight" -->
<div id="youapp-popover-id"  style="display: none">
									<a class="btn btn-app"  id="youapp-popover-id-view"  >
					                    <i class="fa fa-edit"></i> View
					                  </a>
					                  <a class="btn btn-app"  id="youapp-popover-id-delete" >
					                    <i class="fa fa-trash-o"></i> Delete
					                  </a>
					</div>

<script type="text/javascript">
//popover statement begin. 
$(function () {
	var $tooltipEles=$('.youapp-popover-mark');
	$tooltipEles.popover(
		{
			trigger:'manual',
			html:true,
			container:'body',
			placement:'bottom',
			content: function showContent(){
				var $this=$(this);
				var recordId=$this.attr('id');
				var deleteURL=$this.attr('data-youappDelete');
				var viewURL=$this.attr('data-youappView');
				
				var param=$this.attr('data-youappParam');
				var extension="";
				if(param!=null&&param!=""){
					try{
						if(param.lastChar()!=')'){
							// eval function 
							extension=eval(param);
						}
						else{
							extension=param;
						}
					}catch(e){
						
					}
				}
				var targetParam='id='+recordId
				+(extension!=null&&extension!='' ? ('&'+extension):'');
				
				var $popover=$('#youapp-popover-id');
				var $view=$popover.find('#youapp-popover-id-view');
				$view.attr('id',$view.attr('id')+"-"+recordId);
				$view.attr('onclick', function (){
					return 'httpGET("'+viewURL+'", "'+targetParam+'")';
				});
				var $delete=$popover.find('#youapp-popover-id-delete');
				$delete.attr('id',$delete.attr('id')+"-"+recordId);
				$delete.attr('onclick', function (){
					return 'deleteRecordWithConfirmOnHTTPGET("'+deleteURL+'", "'+targetParam+'")';
				});
				return $popover.html();
			}
			
		}	  
	  
	  ).click(function (event) {
            event.stopPropagation();
            $('.youapp-popover-mark').not(this).removeClass('youapp-simeple-table-div').popover('hide');
            $(this).addClass('youapp-simeple-table-div').popover('show');
        });
	
	$(document).click(function () {
        $('.youapp-popover-mark').popover('hide');
    })

	  $tooltipEles.on('show.bs.popover', function () {
		});
	$tooltipEles.on('shown.bs.popover', function () {
		//alert('shown');
	});
	  $tooltipEles.on('hidden.bs.popover', function () {
		  	var $this=$(this);
		});
	  $tooltipEles.on('hide.bs.popover', function () {
		  	var $this=$(this);
		});
	});
	// popover statement end. 

</script>

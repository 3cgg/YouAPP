<div  id="youapp-alert-warning" class="alert alert-warning alert-dismissable" style="display: none;">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <h4><i class="icon fa fa-warning"></i> Alert!</h4>
                    <span id="youapp-alert-warning-message"></span>
                  </div>
<script type="text/javascript">
function warningMessageAlert(error,element){
	try{
		$('#youapp-alert-warning-message').text(error.text());
		$('#youapp-alert-warning').show();
	}catch (Exception) {
		
	}
}
</script>
<div  id="youapp-alert-warning" class="alert alert-warning alert-dismissable hidden" >
                    <button type="button" class="close"  aria-hidden="true"  onclick="hiddenWarningMsgAlert();return false;">&times;</button>
                    <h4><i class="icon fa fa-warning"></i> Alert!</h4>
                    <span id="youapp-alert-warning-message"></span>
                  </div>
<script type="text/javascript">
function warningMessageAlert(error,element){
	try{
		
		if(typeof(error)==="string"){
			$('#youapp-alert-warning-message').text(error);
		}
		else{
			$('#youapp-alert-warning-message').text(error.text());
		}
		var $alertWarning=$('#youapp-alert-warning');
		$alertWarning.addClass("visible");
		$alertWarning.removeClass("hidden");
	}catch (e) {
		Console(e);
	}
}


function hiddenWarningMsgAlert(){
	try{
		var $alertWarning=$('#youapp-alert-warning');
		$alertWarning.removeClass("visible");
		$alertWarning.addClass("hidden");
	}catch(e){
		Console(e);
	}
}

</script>


var serviceHost = "http://127.0.0.1:8686/youapp/web/service/dispatch";


function success_jsonpCallback(data) {

	//alert('success_jsonpCallback-->' + data['status']);

}

$.validator.setDefaults({
	errorPlacement:function(error,element) {  
    	error.addClass('youapp-error');
		error.appendTo(element.parent("div"));
   }
});

function deleteRecordWithConfirmOnGTTPGET(url,guid){
	$.confirm({
        text: "你确定删除？",
        confirm: function(button) {
            //alert("You just confirmed.");
            httpGET(url, 'id='+guid); 
        },
        cancel: function(button) {
            //alert("You cancelled.");
        }
    });
};

function renderXML(xml){
	var render = analyze(xml);
	var ac=render[0];
	if('url'==ac){
		window.location.href=serviceHost+render[1];
	}
	else if('youapp-error'==ac){
		$('#' + render[0]).html(render[1]);
		var $modal=$('#youapp-error-modal');
		$modal.modal({
			  keyboard: true
			});
		$modal.modal('show');
	}
	else if('youapp-warning'==ac){
		$('#' + render[0]).html(render[1]);
		var $modal=$('#youapp-warning-modal');
		$modal.modal({
			  keyboard: true
			});
		$modal.modal('show');
	}
	else if('youapp-success'==ac){
		$('#' + render[0]).html(render[1]);
		var $modal=$('#youapp-success-modal');
		$modal.modal({
			  keyboard: true
			});
		$modal.modal('show');
	}else{
		$('#' + render[0]).html(render[1]);
	}
	
}

function action(url, param) {
	var a = window.location;
	$.ajax({
		type : "get",
		async : true,
		url : url, //?html=&service=
		//dataType : "jsonp", 
		data : param,
		//jsonp: "callbackKey", 
		//jsonpCallback:'success_jsonpCallback', 
		success : function(xml) {
			try {
				renderXML(xml);
			} catch (Exception) {
				//alert('error');
			}
		},
		error : function(data) {
			//alert('fail');
		}
	});
}

function httpGET(url, parameter) {
	action(serviceHost + url, parameter);
}

function httpPOST(url, formId) {
	var options = {
			type: 'POST',  
            url: serviceHost+url,
            success:function (xml){
            	renderXML(xml);
            },
			 error: function(XmlHttpRequest, textStatus, errorThrown){  
		         
		     } 
        };
	$('#'+formId).ajaxSubmit(options);
}

function httpRELOAD(url, parameter){
	window.location.href=serviceHost+url;
	return false;
}


function analyze(html) {
	var s = html;
	var render = new Array(2);
	var targetDiv = '';
	var targetDivStart = 0;
	var targetDivEnd = 0;
	var start = 0;
	for (var i = 0; i < s.length; i++) {
		var chr = s.charAt(i);
		if (('\'' == chr || '"' == chr) && targetDivStart == 0) {
			targetDivStart = i;
		}

		if (i > targetDivStart && targetDivStart != 0) {
			if ('\'' == chr || '"' == chr) {
				targetDivEnd = i;
			}
			if (targetDivEnd == 0) {
				targetDiv = targetDiv + chr;
			}
		}
		if ('>' == s.charAt(i)) {
			start = i + 1;
			break;
		}
	}
	var end = 0;
	for (var i = s.length; i > 0; i--) {
		if ('<' == s.charAt(i)) {
			end = i;
			break;
		}
	}

	var content = s.substring(start, end);

	//alert('targetDiv='+targetDiv);
	//alert('content='+content);
	render[0] = targetDiv;
	render[1] = content;
	return render;
}

// initialize date validator of default date format "yyyy-mm-dd hh:mm:ss"
$(
		$.validator.addMethod("youappdate", function(value, element, params) {
			var valid=false;
			if("yyyy-mm-dd hh:mm:ss"==params["format"]){
			 valid=/^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])[ ](0?[0-9]|[12][0-9])[:](0?[0-9]|[1-5][0-9])[:](0?[0-9]|[1-5][0-9])$/.test( value );
			}
			return valid;
			}, $.validator.format("the date format must be yyyy-mm-dd hh:mm:ss"))
);

//initialize default date format mask "yyyy-mm-dd hh:mm:ss"
$(
		$.extend($.inputmask.defaults.aliases, {
			'yyyy-mm-dd hh:mm:ss': {
			mask: "y-1-2 h:s:s",
			        placeholder: "yyyy-mm-dd 00:00:00",
			        separator: '-',
			        alias: "yyyy/mm/dd"
			}
			})		
);


function success_jsonpCallback(data) {

	//alert('success_jsonpCallback-->' + data['status']);

}

$.validator.setDefaults({
	errorPlacement:function(error,element) {  
    	error.addClass('youapp-error');
		error.appendTo(element.parent("div"));
   }
});

function deleteRecordWithConfirmOnHTTPGET(url,param,fnSuccess,fnError){
	$.confirm({
        text: "你确定删除？",
        confirm: function(button) {
            //alert("You just confirmed.");
            GET(url, param,fnSuccess,fnError); 
        },
        cancel: function(button) {
            //alert("You cancelled.");
        }
    });
};

function renderXML(render){
	
	var ac=render[0];
	if('url'==ac){
		window.location.href=_GLOBAL_serviceHost+render[1];
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

function POST(url, param,fnSuccess,fnError) {
	
	if(typeof(fnSuccess)=="undefined"){
		fnSuccess=function(render) {
			try {
				renderXML(render);
			} catch (Exception) {
				//alert('error');
			}
		};
	}
	// add method proxy on request method.
	var customfnSuccess=fnSuccess;
	fnSuccess=function proxyOnCallback(reponse){
		var result = analyze(reponse);
		if(result.type=="XML"){
			renderXML(result.data);
		}
		else{
			customfnSuccess(result.data);
		}
	};
	
	if(typeof(fnError)=="undefined"){
		fnError=function(data) {
			alert(data);
		};
	};
	
	$.ajax({
		type : "post",
		async : true,
		url : _GLOBAL_serviceHost +url, //?html=&service=
		//dataType : "jsonp", 
		data : param,
		//jsonp: "callbackKey", 
		//jsonpCallback:'success_jsonpCallback', 
		success : fnSuccess,
		error : fnError
	});
}



function GET(url, parameter,fnSuccess,fnError) {
	
	if(typeof(fnSuccess)=="undefined"){
		fnSuccess=function(render) {
			try {
				renderXML(render);
			} catch (Exception) {
				//alert('error');
			}
		};
	}
	// add method proxy on request method.
	var customfnSuccess=fnSuccess;
	fnSuccess=function proxyOnCallback(reponse){
		var result = analyze(reponse);
		if(result.type=="WHOLE-HTML"){
			$(window.document).html(result.data);
		}
		else if(result.type=="XML"){
			renderXML(result.data);
		}
		else{
			customfnSuccess(result.data);
		}
	};
	
	if(typeof(fnError)=="undefined"){
		fnError=function(data) {
			alert(data);
		};
	};

	$.ajax({
		type : "get",
		async : true,
		url : _GLOBAL_serviceHost + url, //?html=&service=
		//dataType : "jsonp", 
		data : parameter,
		//jsonp: "callbackKey", 
		//jsonpCallback:'success_jsonpCallback', 
		success : fnSuccess,
		error : fnError
	});
	
}

function submitPOST(url, formId,fnSuccess,fnError) {
	
	if(typeof(fnSuccess)=="undefined"){
		fnSuccess=function(render) {
			try {
				renderXML(render);
			} catch (e ) {
				//alert('error');
			}
		};
	}
	// add method proxy on request method.
	var customfnSuccess=fnSuccess;
	fnSuccess=function proxyOnCallback(reponse){
		var result = analyze(reponse);
		if(result.type=="XML"){
			renderXML(result.data);
		}
		else{
			customfnSuccess(result.data);
		}
	};
	
	if(typeof(fnError)=="undefined"){
		fnError=function(XmlHttpRequest, textStatus, errorThrown){  
	         
	     };
	};
	
	var options = {
			type: 'POST',  
            url: _GLOBAL_serviceHost+url,
            success:fnSuccess,
			 error: fnError
        };
	$('#'+formId).ajaxSubmit(options);
}

function httpRELOAD(url, parameter){
	window.location.href=_GLOBAL_serviceHost+url;
	return false;
}

function Result(){
	this.type; // JSON, XML or WHOLE-HTML
	this.data;
}

function analyze(html) {
	var result=new Result();
	try{
	 var data=$.parseJSON(html)
	 result.type="JSON";
	 result.data=data;
	 return result;
	}catch(e){
		;
	}
	
	if(isWholeHTML(html)){
		result.type="WHOLE-HTML";
		result.data=html;
		return result;
	}
	
	
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
	
	result.type="XML";
	result.data=render;
	return result;
}

function isWholeHTML(html){
	return /<html[ ]*>/gi.test(html) 
				&& (/<\/html[ ]*>/gi.test(html))
				&& (/<\/html[ ]*>/gi.test(html))
				&& (/<body.*\s*>$/gim.test(html)) 
				&& (/<\/body[ ]*>/gi.test(html));
}



String.prototype.trim=function (){
	return $.trim(this);
}

String.prototype.lastChar=function(){
	return this.substring(this.length-1);
}

String.prototype.trimStartWith=function(chr){
	var matches=false;
	var temp=this.trim();
	if(temp.length>0){
		if(temp.charAt(0)==chr){
			matches=true;
		}
	}
	return matches;
}

function Console(message){
	try{
		if(console){
			console.log(message)
		}
	}catch(e){
		
	}
}

function GETDownload(url, param){
	window.open(_GLOBAL_serviceHost + url+"?"+param);
}












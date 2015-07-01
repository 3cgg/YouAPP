/**
 * 
 */
function success_jsonpCallback(data) {

}

function action(url, param) {
	var i=window.location;
	$.ajax({
		type : "get",
		async : true,
		url : url, //?html=&service=
		dataType : "jsonp",
		data : param,
		jsonp : "callbackFunName",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(默认为:callback) 
		jsonpCallback : "success_jsonpCallback",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名 
		success : function(json) {
			$("#content").html(json['template']);
		},
		error : function(data) {
			alert('fail');
		}
	});
}

function action(url,param) {

	action(
			"http://localhost:8686/youapp?html=D:/java_/JFramework/trunk/jframework-bootstrap/page/html/nex.html&service=beanName.methodLLLL",
			"");
}

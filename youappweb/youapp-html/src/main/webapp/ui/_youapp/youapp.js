(function(){
	function Layout($dom){
		var $htl=$dom;
		this.dataHtmlUrls=function(){
			var all=$_util.newList();
			if($htl.attr("data-htmlurl")){
				all.add($htl);
			}
			var $foundHtmlUrl=$htl.find('[data-htmlurl]');
			if($foundHtmlUrl.length>0){
				for(var i=0;i<$foundHtmlUrl.length;i++){
					all.add($($foundHtmlUrl[i]));
				}
			}
			return all.removeNull();
		}
		
		this.requset=function(){
			var $allHtmlUrlls=this.dataHtmlUrls();
			for(var i=0;i<$allHtmlUrlls.length;i++){
				var $htmlUrl=$($allHtmlUrlls[i]);
				var layoutId=$htmlUrl.data('layoutid');
				var htmlUrl=$htmlUrl.data('htmlurl');
				var requsetVO={
						layoutId:layoutId,
						htmlUrl:htmlUrl
				}
				$_util.ajaxGet({
					url:$_config.getHtmlEndpoint(),
					data:{data:$_util.json(requsetVO)},
					success:function(data){
						var resp=JSON.parse(data);
						var layout=new Layout($(resp.html));
						layout.draw(resp.htmlDef.layoutId);
					},
					error:function(data){
//						var layout=new Layout(data);
//						layout.draw();
					}
					})
			}
		}
		
		this.appendTo=function(layoutId){
			var $target=$(document).find('[data-layoutid="'+layoutId+'"]');
			$htl.appendTo($target);
		}
		
		this.draw=function(layoutId){
			this.appendTo(layoutId);
			this.requset();
		}
	}
	
	
	function LayoutDraw(){
		this.draw=function(layoutId,data){
			var layout=new Layout(data);
			layout.draw(layoutId);
		}
	}
	
	window.$_layout=new LayoutDraw();
	window.$_layout.draw('body',$('body'));
})(window);

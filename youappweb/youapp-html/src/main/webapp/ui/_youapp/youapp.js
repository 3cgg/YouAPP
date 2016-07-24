(function(){
	function Layout($dom){
		var $htl=$dom;
		this.dataHtmlUrls=function(){
			var all=$_youapp.$_util.newList();
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
				$_youapp.$_util.ajaxGet({
					url:$_youapp.$_config.getHtmlEndpoint(),
					data:{data:$_youapp.$_util.json(requsetVO)},
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
			if(layoutId){
				this.appendTo(layoutId);
			}
			this.requset();
		}
	}
	
	
	function LayoutDraw(){
		this.draw=function(layoutId,data){
			var layout=new Layout(data);
			layout.draw(layoutId);
		}
		/*
		 * $_youapp.$_layout.drawTab('Me','MMMEEE','ui/pages/default.html','layoutTab')
		 * */
		this.drawTab=function(id,title,htmlurl,tabDomId){
			if($_youapp.$tabs[tabDomId].exists(id)){
				$_youapp.$tabs[tabDomId].active(id);
				return ;
			}
			var tabContent=new TabContent($('#'+tabDomId));
			tabContent.draw(id,title,htmlurl);
			$_youapp.$tabs.tabDomId=$('#'+tabDomId).tabulous({
				effect: 'scaleUp'
			});
			$_youapp.$tabs.tabDomId.active(id);
		}
	}
	
	
	function TabContent($tabDom){
		var $htl=$tabDom;
		this.getMenu=function(id,title){
			return $('<li><a id="'+id+'"   href="#'+id+'" title="'+title+'">'+title+'</a></li>');
		}
		
		this.getSlice=function(id,title,htmlurl){
			return $('<div id="'+id+'">'
					+'<div data-layoutId="tab-'+id+'" data-htmlUrl="'+htmlurl+'"   style="height: auto;"></div>'
					+'</div>');
		}
		
		this.draw=function(id,title,htmlurl){
			
			var menu=this.getMenu(id,title);
			var sliceView=this.getSlice(id,title,htmlurl);
			menu.insertAfter($htl.children('ul.tabul').find('li:last'));
			sliceView.appendTo($htl.children('div.tabcontainer'));
			$_youapp.$_layout.draw(null,sliceView);
		}
		
	}
	
	
	function DataExchange(){
		this.ajaxGet=function(options){
			var ajax=new Ajax();
			ajax.request($.extend({},options,{type:'GET'}));
		}
		
		this.ajaxPost=function(options){
			var ajax=new Ajax();
			ajax.request($.extend({},options,{type:'POST'}));
		}
	}
	
	function HtmlExchange(){
		this.ajaxGet=function(options){
			var ajax=new Ajax();
			ajax.request($.extend({},options,{type:'GET'}));
		}
		
		this.newTab=function($aDom){
			/*
			 * data-url="ui/pages/tabs.html"
            data-urldata=""
            data-tabid="layoutTab"
            data-tabmenuid="tabDemo"
            data-tabmenutitle="Tab-Demo"
			 */
			$a=$($aDom);
			var url=$a.data("url");
			var urldata=$a.data("urldata");
			var tabid=$a.data("tabid");
			var tabmenuid=$a.data("tabmenuid");
			var tabmenutitle=$a.data("tabmenutitle");
			$_youapp.$_layout.drawTab(tabmenuid,tabmenutitle,url,tabid);
		}
	}
	
	window.$_youapp.$_layout=new LayoutDraw();
	window.$_youapp.$_layout.draw('body',$('body'));
	window.$_youapp.$_data=new DataExchange();
	window.$_youapp.$_html=new HtmlExchange();
})(window);

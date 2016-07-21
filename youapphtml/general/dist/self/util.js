function List(){
				  			this.arrys=[];
				  			this.position=-1;
				  			this.size=function(){
				  				return this.position+1;
				  			};
				  			
				  			this.length=function(){
				  				return this.arrys.length;
				  			};
				  			
				  			this.resize=function(){
				  				tempArr=[this.size()*1.25+5];
				  				for(var i=0;i<this.size();i++){
				  					tempArr[i]=this.arrys[i];
				  				}
				  				this.arrys=tempArr;
				  			};
				  			
				  			this.exists=function(id){
				  				exists=false;
				  				var i=0;
				  				for(;i<this.size();i++){
				  					if(this.arrys[i]==id){
				  						exists=true;
				  						break;
				  					}
				  				}
				  				return exists;
				  			};
				  			
				  			
				  			this.indexOf=function(id){
				  				exists=false;
				  				var i=0;
				  				for(;i<this.size();i++){
				  					if(this.arrys[i]==id){
				  						exists=true;
				  						break;
				  					}
				  				}
				  				return exists?i:-1;
				  			};
				  			
				  			this.add=function(id){
				  				if(this.size()==this.length()){
				  					this.resize();
				  				}
								this.position++;
								this.arrys[this.position]=id;
				  			};
				  			
				  			this.compress=function(){
				  				var tempPosition=-1;
				  				for(var i=0;i<=this.position;i++){
				  					if(this.get(i)==null){
				  						var j=i+1;
				  						while(j<=this.position){
				  							if(this.get(j)!=null){
				  								this.arrys[i]=this.get(j);
				  								this.arrys[j]=null;
				  								tempPosition=i;
				  								break;
				  							}
				  							else{
				  								j++;
				  							}
				  						}
				  					}
				  					else{
				  						tempPosition=i;
				  					}
				  				}
				  				this.position=tempPosition;
				  			};
				  			
				  			this.get=function(index){
				  				return this.arrys[index];
				  			}
				  			
				  			this.remove=function(id){
				  				var index=this.indexOf(id);
				  				if(index!=-1){
				  					this.arrys[index]=null;
				  				}
				  				this.compress();
				  			};
				  			
				  			this.removeNull=function(){
				  				var tempArr=[];
				  				for(var i=0;i<this.size();i++){
				  					if(this.get(i)!=null){
				  						tempArr[i]=this.get(i);
				  					}
				  				}
				  				return tempArr;
				  			}
				  			
				  		}
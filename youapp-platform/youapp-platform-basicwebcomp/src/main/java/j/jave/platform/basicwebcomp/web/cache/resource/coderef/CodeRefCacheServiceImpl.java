package j.jave.platform.basicwebcomp.web.cache.resource.coderef;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.support.resourceuri.IdentifierGenerator;
import j.jave.kernal.jave.support.resourceuri.SimpleStringIdentifierGenerator;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.platform.basicwebcomp.web.cache.resource.ResourceCacheServiceSupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("j.jave.platform.basicwebcomp.web.cache.resource.coderef.CodeRefCacheServiceImpl")
public class CodeRefCacheServiceImpl extends ResourceCacheServiceSupport<CodeRefCacheModel, Object>
		implements CodeRefCacheService,CodeRefCacheRefreshListener {

	@Autowired(required=false)
	private CodeRefCacheModelService codeRefCacheModelService;
	
	private static SimpleStringIdentifierGenerator simpleStringIdentifierGenerator=new SimpleStringIdentifierGenerator(){
		public String namespace() {
			return "/coderef/";
		};
	};
	
	@Override
	public IdentifierGenerator generator() {
		return simpleStringIdentifierGenerator;
	}

	@Override
	public void initResource(JConfiguration configuration) {
		List<? extends CodeRefCacheModel> codeRefCacheModels= codeRefCacheModelService.getResourceCacheModels();
		if(JCollectionUtils.hasInCollect(codeRefCacheModels)){
			for(CodeRefCacheModel codeRefCacheModel:codeRefCacheModels){
				set(generator().key(codeRefCacheModel.getUri()), codeRefCacheModel);
			}
		}
	}

	@Override
	public Object trigger(CodeRefCacheRefreshEvent event) {
		initResource(JConfiguration.get());
		return true;
	}

}

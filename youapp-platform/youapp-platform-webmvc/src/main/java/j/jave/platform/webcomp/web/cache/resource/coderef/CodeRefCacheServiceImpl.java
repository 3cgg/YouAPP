package j.jave.platform.webcomp.web.cache.resource.coderef;

import j.jave.platform.webcomp.web.cache.resource.ResourceCacheServiceSupport;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.support.resourceuri.IdentifierGenerator;
import me.bunny.kernel._c.support.resourceuri.SimpleStringIdentifierGenerator;
import me.bunny.kernel._c.utils.JCollectionUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(CodeRefCacheServiceImpl.BEAN_NAME)
public class CodeRefCacheServiceImpl extends ResourceCacheServiceSupport<CodeRefCacheModel, Object>
		implements CodeRefCacheService<CodeRefCacheModel>,CodeRefCacheRefreshListener {
	
	public static final String BEAN_NAME="default-codeRefCacheServiceImpl";
	

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
				set(codeRefCacheModel.getUri(), codeRefCacheModel);
			}
		}
	}

	@Override
	public Object trigger(CodeRefCacheRefreshEvent event) {
		initResource(JConfiguration.get());
		return true;
	}

	@Override
	public String getName(String type, String code) {
		String name=null;
		CodeRefCacheModel codeRefCacheModel=super.get(CodeRefCacheModelUtil.key(type, code));
		if(codeRefCacheModel!=null){
			name=codeRefCacheModel.name();
		}
		return name;
	}
}

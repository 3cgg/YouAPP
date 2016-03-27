/**
 * 
 */
package j.jave.platform.basicwebcomp.param.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.param.model.CodeTableCacheModel;
import j.jave.platform.basicwebcomp.param.model.ParamCode;
import j.jave.platform.basicwebcomp.param.model.ParamType;
import j.jave.platform.basicwebcomp.spirngjpa.query.QueryBuilder;
import j.jave.platform.basicwebcomp.web.cache.resource.coderef.CodeRefCacheModelService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * parameter basic service.
 * @author J
 */
@Service(value="paramService.transation.jpa")
public class ParamServiceImpl extends ServiceSupport<ParamCode> implements ParamService{

	@Autowired
	private InternalParamTypeServiceImpl internalParamTypeServiceImpl;
	
	@Autowired
	private InternalParamCodeServiceImpl internalParamCodeServiceImpl;
	
	@Autowired
	private  CodeRefCacheModelService classd;
	
	@Override
	public void saveParam(ServiceContext context, ParamType paramType,
			ParamCode paramCode) throws JServiceException {
		
		internalParamTypeServiceImpl.saveOnly(context, paramType);
		paramCode.setTypeId(paramType.getId());
		internalParamCodeServiceImpl.saveOnly(context, paramCode);
	}

	@Override
	public void updateParam(ServiceContext context, ParamType paramType,
			ParamCode paramCode) throws JServiceException {
		internalParamTypeServiceImpl.updateOnly(context, paramType);
		internalParamCodeServiceImpl.updateOnly(context, paramCode);
	}

	@Override
	public List<CodeTableCacheModel> getCodeTableCacheModels(ServiceContext context) {
		String nativeSql=
				"SELECT PT.CODE TYPE, PC.CODE,PC.NAME from PARAM_CODE PC , PARAM_TYPE PT"
				+ " WHERE PC.TYPEID = PT.ID";
		List<CodeTableCacheModel> codes=QueryBuilder.get(getEntityManager()).setNativeSql(nativeSql)
				.setResultSetMapping("CodeTableQueryMapping")
		.build().execute();
		
		nativeSql=
				"SELECT PT.CODE TYPE, PT.CODE , PT.NAME from PARAM_TYPE PT";
		List<CodeTableCacheModel> types=QueryBuilder.get(getEntityManager()).setNativeSql(nativeSql)
				.setResultSetMapping("CodeTableQueryMapping")
		.build().execute();
		codes.addAll(types);
		return codes;
	}

	@Override
	public JIPersist<?, ParamCode> getRepo() {
		return null;
	}
	
	
}

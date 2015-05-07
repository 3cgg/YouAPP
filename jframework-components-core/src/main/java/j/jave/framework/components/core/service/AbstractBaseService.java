/**
 * 
 */
package j.jave.framework.components.core.service;

import j.jave.framework.components.login.model.User;
import j.jave.framework.exception.JConcurrentException;
import j.jave.framework.model.JBaseModel;
import j.jave.framework.model.support.interceptor.JDefaultModelInvocation;
import j.jave.framework.mybatis.JMapper;
import j.jave.framework.utils.JUniqueUtils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * any service must not extend the class from now , that is deprecated.
 * now ,you should use the latest service support {@link ServiceSupport }
 * @see ServiceSupport 
 * @author J
 */
@Deprecated
public abstract class AbstractBaseService {

	
	
	/**
	 * fill in common info.  to execute 
	 * @param jMapper
	 * @param authorizer    generally its login user 
	 * @param jBaseModel
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void proxyOnSave(JMapper jMapper, User authorizer, JBaseModel jBaseModel){
		jBaseModel.setCreateId(authorizer.getId());
		jBaseModel.setCreateTime(new Timestamp(new Date().getTime()));
		jBaseModel.setUpdateId(authorizer.getId());
		jBaseModel.setUpdateTime(new Timestamp(new Date().getTime()));
		jBaseModel.setVersion(1);
		jBaseModel.setDeleted("N");
		jBaseModel.setId(JUniqueUtils.unique());
		
		// give a chance to do something containing model intercepter
		new JDefaultModelInvocation(jBaseModel).proceed();
		
		jMapper.save(jBaseModel);
	}
	
	protected JBaseModel get(JMapper<JBaseModel> jMapper,String id){
		return jMapper.get(id);
	}
	
	/**
	 * fill in common info.
	 * also validate whether the version changes, then to execute 
	 * @param jMapper
	 * @param user
	 * @param jBaseModel
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void proxyOnUpdate(JMapper jMapper, User authorizer, JBaseModel jBaseModel){
		jBaseModel.setUpdateId(authorizer.getId());
		jBaseModel.setUpdateTime(new Timestamp(new Date().getTime()));
		
		// give a chance to do something containing model intercepter
		new JDefaultModelInvocation(jBaseModel).proceed();
		
		JBaseModel dbModel=get(jMapper, jBaseModel.getId());
		if(dbModel.getVersion()!=jBaseModel.getVersion()){
			throw new JConcurrentException("version chaged , db verion is "+dbModel.getVersion()
					+" , but current version is  "+jBaseModel.getVersion());
		}
		jBaseModel.setVersion(jBaseModel.getVersion()+1);
		int affect=jMapper.update(jBaseModel);
		if(affect==0) throw new JConcurrentException(
				"record conflict on "+jBaseModel.getId()+" of "+jBaseModel.getClass().getName());
		System.out.println(affect);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

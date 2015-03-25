/**
 * 
 */
package j.jave.framework.components.core.service;

import j.jave.framework.components.core.exception.ConcurrentException;
import j.jave.framework.components.login.model.User;
import j.jave.framework.model.JBaseModel;
import j.jave.framework.mybatis.JMapper;
import j.jave.framework.utils.JUtils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Administrator
 *
 */
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
		jBaseModel.setId(JUtils.unique());
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
		
		JBaseModel dbModel=get(jMapper, jBaseModel.getId());
		if(dbModel.getVersion()!=jBaseModel.getVersion()){
			throw new ConcurrentException("version chaged , db verion is "+dbModel.getVersion()
					+" , but current version is  "+jBaseModel.getVersion());
		}
		jBaseModel.setVersion(jBaseModel.getVersion()+1);
		int affect=jMapper.update(jBaseModel);
		if(affect==0) throw new ConcurrentException(
				"record conflict on "+jBaseModel.getId()+" of "+jBaseModel.getClass().getName());
		System.out.println(affect);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

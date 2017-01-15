package me.bunny.kernel.jave.xml.xmldb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bunny.kernel.jave.exception.JOperationNotSupportedException;
import me.bunny.kernel.jave.model.JBaseModel;
import me.bunny.kernel.jave.model.JPageable;
import me.bunny.kernel.jave.persist.JIPersist;
import me.bunny.kernel.jave.persist.JPersistException;
import me.bunny.kernel.jave.reflect.JClassUtils;

/**
 * the xml persist.
 * @author J
 *
 * @param <T>
 */
public class JXMLPersist<T extends JBaseModel> extends JXMLSessionSupport implements JIPersist<JXMLPersist<T>,T,String> {

	@Override
	public void saveModel(T baseModel) {
		try {
			getXmlSession().insert(baseModel);
		} catch (Exception e) {
			throw new JPersistException(e);
		}
	}

	@Override
	public int updateModel(T baseModel) {
		try {
			getXmlSession().update(baseModel);
			return 1;
		} catch (Exception e) {
			throw new JPersistException(e);
		}
	}

	@Override
	public T getModel(String id, String... entryName) {
		try {
			Class<? extends JBaseModel> clazz=JClassUtils.load(entryName[0]);
			return getXmlSession().get(id, clazz);
		} catch (Exception e) {
			throw new JPersistException(e);
		}
	}

	@Override
	public void deleteModel(T baseModel) {
		throw new JOperationNotSupportedException("XML Database doesnot support delete.");
	}

	@Override
	public void markModelDeleted(T baseModel) {
		try {
			getXmlSession().update(baseModel);
		} catch (Exception e) {
			throw new JPersistException(e);
		}
	}

	@Override
	public JXMLPersist<T> getInstance() {
		return this;
	}

	public List<Map<String, Object>> select(String jql){
		try {
			return getXmlSession().select(jql, new HashMap<String, Object>());
		} catch (Exception e) {
			throw new JPersistException(e);
		}
	}
	
	public List<Map<String, Object>> select(String jql,Map<String, Object> params){
		try {
			return getXmlSession().select(jql, params==null?new HashMap<String, Object>():params);
		} catch (Exception e) {
			throw new JPersistException(e);
		}
	}
	
	public <M extends JBaseModel> List<M> select(String jql,Map<String, Object> params,Class<M> clazz){
		try {
			return getXmlSession().select(jql, params, clazz);
		} catch (Exception e) {
			throw new JPersistException(e);
		}
	} 
	
	public <M extends JBaseModel> List<M> select(String jql,Class<M> clazz){
		try {
			return getXmlSession().select(jql, clazz);
		} catch (Exception e) {
			throw new JPersistException(e);
		}
	}

	@Override
	public void markModelDeleted(String id) {
		throw new JOperationNotSupportedException("XML PERSIST DOSENOT SUPPORT THIS.");
	}

	@Override
	public List<T> getModelsByPage(JPageable pagination) {
		throw new JOperationNotSupportedException("XML PERSIST DOSENOT SUPPORT THIS.");
	}
	
	@Override
	public List<T> getAllModels() {
		throw new JOperationNotSupportedException("XML PERSIST DOSENOT SUPPORT THIS.");
	}
}

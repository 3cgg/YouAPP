package j.jave.kernal.jave.xml.xmldb;

import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.persist.JPersistException;
import j.jave.kernal.jave.reflect.JClassUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the xml persist.
 * @author J
 *
 * @param <T>
 */
public class JXMLPersist<T extends JBaseModel> extends JXMLSessionSupport implements JIPersist<JXMLPersist<T>,T> {

	@Override
	public void save(T baseModel) {
		try {
			getXmlSession().insert(baseModel);
		} catch (Exception e) {
			throw new JPersistException(e);
		}
	}

	@Override
	public int update(T baseModel) {
		try {
			getXmlSession().update(baseModel);
			return 1;
		} catch (Exception e) {
			throw new JPersistException(e);
		}
	}

	@Override
	public T get(String id, String... entryName) {
		try {
			Class<? extends JBaseModel> clazz=JClassUtils.load(entryName[0]);
			return getXmlSession().get(id, clazz);
		} catch (Exception e) {
			throw new JPersistException(e);
		}
	}

	@Override
	public void delete(T baseModel) {
		throw new JOperationNotSupportedException("XML Database doesnot support delete.");
	}

	@Override
	public void markDeleted(T baseModel) {
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
	
	
}

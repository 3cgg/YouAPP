package me.bunny.kernel._c.model.support.interceptor;

import java.util.Iterator;
import java.util.List;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.model.support.JFieldValidator;
import me.bunny.kernel._c.model.support.JSQLJavaTypeHelper;
import me.bunny.kernel._c.model.support.JTYPE;
import me.bunny.kernel._c.model.support.JTable;
import me.bunny.kernel._c.model.support.detect.JColumnInfo;
import me.bunny.kernel._c.model.support.detect.JModelDetect;

/**
 * 
 * @author J
 *
 * @param <T>
 */
public class JModelValidatorIntercepter<T> implements JModelIntercepter<T> {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JModelValidatorIntercepter.class);
	
	/**
	 * the entity that maps to a concrete table, with annotaion 
	 * {@link JTable} 
	 */
	private final T object;
	
	public JModelValidatorIntercepter(T object) {
		this.object=object;
	}
	
	private StringBuffer invalidMessage=new StringBuffer();
	
	@Override
	public T intercept(JModelInvocation<T> modelInvocation) {
		Class<?> superClass=object.getClass();
		JTable table=superClass.getAnnotation(JTable.class);
		if(table==null){
			return modelInvocation.proceed();
		}
		
		boolean valid=true;
		List<JColumnInfo> columnInfos=  JModelDetect.get().getColumnInfos(superClass);
		Iterator<JColumnInfo> iterator= columnInfos.iterator();
		while(iterator.hasNext()){
			JColumnInfo columnInfo=iterator.next();
			
			JTYPE<?> type=JSQLJavaTypeHelper.get(columnInfo.getColumn());
			if(JFieldValidator.class.isInstance(type)){
				JFieldValidator<Object> validator= (JFieldValidator<Object>) type;
				Object obj=null;
				try {
					obj = columnInfo.getField().get(object);
				} catch (Exception e) {
					LOGGER.info(e.getMessage(), e);
				} 
				if(!validator.validate(obj)){
					invalidMessage.append(type.name()+","+validator.invalidMessage());
					valid=false;
				}
			}
		}
		
		if(!valid){
			throw new RuntimeException(invalidMessage.toString());
		}

		return modelInvocation.proceed();
	}


}

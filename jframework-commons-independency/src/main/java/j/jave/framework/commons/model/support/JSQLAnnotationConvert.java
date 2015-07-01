package j.jave.framework.commons.model.support;

/**
 * 
 * @author J
 *
 */
public class JSQLAnnotationConvert {

	public static <T> JTYPE<T> get(JColumn column){
		
		JTYPE<T> type=null;
		
		switch (column.type()) {
			case VARCHAR:
				new JVARCHAR(column.type(), column.length());
				break;
			case TIMESTAMP:
				new JTIMESTAMP(column.type());
				break;
			default:
				new JNULL(column.type());
		}
		return type ;
	}
}

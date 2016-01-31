package j.jave.kernal.jave.model.support;


/**
 * 
 * @author J
 *
 */
public class JSQLJavaTypeHelper {

	public static  JAbstractType<?> get(JColumn column){
		
		JAbstractType<?> type=null;
		
		switch (column.type()) {
			case VARCHAR:
				type=new JVARCHAR(column.type(), column.length());
				break;
			case TIMESTAMP:
				type=new JTIMESTAMP(column.type());
				break;
			case DOUBLE:
				type=new JDouble(column.type());
			case INTEGER:
				type=new JINT(column.type());
				break;
			default:
				type=new JNULL(column.type());
		}
		return type ;
	}
}

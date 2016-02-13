package j.jave.kernal.jave.xml.xmldb;

import java.util.List;
import java.util.Map;



/**
 * The class implements this interface is a proxy that can search and do filter on 
 * one or many result set that mapping to different class inherit 
 * or implements {@link Model}
 * Like SQL expression, you can define alias when more than one elements exist
 * from part. not include any type inside is not the java plain type.
 * @author zhongjin
 *
 */
public interface JSelect {
	
	public static final String DEFAULT_ALIAS="zhongjin";
	
	public static final String keyword="SELECT,FROM,WHERE,GROUP,BY,ORDER,ASC,DESC,SUM,COUNT,AVG,MAX,MIN";
	
	public static final String[] KEYWORD=keyword.split(",");
	
	/**
	 * parse a string like sql . 
	 * sample 
	 * 1. select id from User where id='a' group by id order by id 
	 * 2. select count(1) from User where...
	 * 3. from User 
	 * "select" must be lower case .  String begins with "SELECT" is invalid.
	 * @param aql
	 * @return
	 */
	public JSelect parse(String aql);
	
	
	/**
	 * parse a string like sql . 
	 * sample 
	 * 1. select id from User where id='a' group by id order by id 
	 * 2. select count(1) from User where...
	 * 3. from User 
	 * "select" must be lower case .  String begins with "SELECT" is invalid.
	 * 
	 * if param "isNewSelect" is set true, a new Select instance will be created and returned.
	 * @param aql isNewSelect
	 * @return
	 */
	public JSelect parse(String aql,boolean isNewSelect);
	
	/**
	 * parse a string like sql . 
	 * sample 
	 * 1. select id from User where id=:id group by id order by id 
	 * 2. select count(1) from User where...
	 * 3. from User 
	 * "select" must be lower case .  String begins with "SELECT" is invalid.
	 * @param aql
	 * @return
	 */
	public JSelect parse(String aql,Map<String, Object> params);
	
	
	/**
	 * parse a string like sql . 
	 * sample 
	 * 1. select id from User where id=:id group by id order by id 
	 * 2. select count(1) from User where...
	 * 3. from User 
	 * "select" must be lower case .  String begins with "SELECT" is invalid.
	 * if param "isNewSelect" is set true, a new Select instance will be created and returned.
	 * @param aql params isNewSelect
	 * @return
	 */
	public JSelect parse(String aql,Map<String, Object> params,boolean isNewSelect);
	
	/**
	 * return the collection of map.
	 * a map express a record.
	 * @return
	 */
	public List<Map<String, Object>> select();
	
	/**
	 * convert the result data to type.
	 * @param clazz
	 * @return
	 */
	public <T> List<T> select(Class<T> clazz);
	
	
	/**
	 * Set limit result count.
	 * @param maxsize
	 */
	public void setMaxsize(int maxsize);
	
	
	/**
	 * return aql string.
	 * @return
	 */
	public String jql();
	
}

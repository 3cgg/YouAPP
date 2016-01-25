package j.jave.framework.commons.xml.node;

import java.util.List;


public interface JNodeGetter {
	
	public static final String XPATH_PROTOCOL="xpath";
	public static final String TAG_PROTOCOL="tag";
	public static final String NAME_PROTOCOL="name";
	public static final String CLASS_PROTOCOL="class";
	public static final String ID_PROTOCOL="id";
	public static final String MIXED_PROTOCOL="mixed";
	
	/**
	 * @param keyValue // {"tag:table,id:j-area-filter","tag:table,id:j-area-filter","tag:table,id:j-area-filter",...}
	 * @return
	 */
	public List<?> getNodes(String... keyValues);
	
}

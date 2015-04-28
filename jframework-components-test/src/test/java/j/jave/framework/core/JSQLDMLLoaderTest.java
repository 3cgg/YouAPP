/**
 * 
 */
package j.jave.framework.core;

import junit.framework.Assert;
import j.jave.framework.support.sqlloader.JSQLConfigure;
import j.jave.framework.support.sqlloader.dml.JSQLDMLCreateFactory;
import j.jave.framework.utils.JStringUtils;

/**
 * @author Administrator
 *
 */
public class JSQLDMLLoaderTest extends StandaloneTest {


	public void testLoadDMLSQL(){
		try{
			String packString="j";
			Class<?> clazz= Thread.currentThread().getContextClassLoader().loadClass("j.jave.framework.sqlloader.dml.PropertiesSQLDMLCreateFactory");
			JSQLDMLCreateFactory createFactory=(JSQLDMLCreateFactory) clazz.newInstance();
			JSQLConfigure configure=(JSQLConfigure) createFactory;
			if(JStringUtils.isNotNullOrEmpty(packString)){
				configure.setPackageName(packString);
			}
			createFactory.getObject().create();
		}catch(Exception e){
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	
}

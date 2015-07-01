/**
 * 
 */
package j.jave.framework.core;

import j.jave.framework.commons.reflect.JClassUtils;
import j.jave.framework.commons.sqlloader.JSQLConfigure;
import j.jave.framework.commons.sqlloader.dml.JSQLDMLCreateFactory;
import j.jave.framework.commons.utils.JStringUtils;
import junit.framework.Assert;

/**
 * @author Administrator
 *
 */
public class JSQLDMLLoaderTest extends StandaloneTest {


	public void testLoadDMLSQL(){
		try{
			String packString="j";
			Class<?> clazz=JClassUtils.load("j.jave.framework.sqlloader.dml.PropertiesSQLDMLCreateFactory", Thread.currentThread().getContextClassLoader()) ;
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

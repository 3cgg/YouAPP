package j.jave.framework.core;

import java.io.File;
import java.util.Properties;

import j.jave.framework.io.JFileResource;
import j.jave.framework.utils.JFileUtils;
import j.jave.framework.utils.JPropertiesUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public void testPropertiesD(){
    	Properties properties=JPropertiesUtils.loadProperties(new JFileResource(new File("/jframework-core.properties")));
    	String value=JPropertiesUtils.getKey("a", properties);
    	assertNotNull(value);
    }
    
    public void testPropertiesCLASSPATH(){
    	Properties properties=JPropertiesUtils.loadProperties(new JFileResource(JFileUtils.getFileFromClassPath("jfwramework-core.properties")));
    	String value=JPropertiesUtils.getKey("a", properties);
    	assertNotNull(value);
    }
    
    
    
    
    
    
}

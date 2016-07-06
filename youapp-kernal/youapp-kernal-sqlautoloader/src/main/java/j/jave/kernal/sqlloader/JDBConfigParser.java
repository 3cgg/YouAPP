package j.jave.kernal.sqlloader;

import java.io.InputStream;

import j.jave.kernal.jave.support.parser.JParser;

public interface JDBConfigParser extends JParser{

	public JDBConfig parse(InputStream inputStream) ;
	
}

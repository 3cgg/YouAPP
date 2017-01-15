package j.jave.kernal.sqlloader;

import java.io.InputStream;

import me.bunny.kernel._c.support.parser.JParser;

public interface JDBConfigParser extends JParser{

	public JDBConfig parse(InputStream inputStream) ;
	
}

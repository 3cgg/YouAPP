package j.jave.kernal.sqlloader;

import java.io.InputStream;

import me.bunny.kernel.jave.support.parser.JParser;

public interface JDBConfigParser extends JParser{

	public JDBConfig parse(InputStream inputStream) ;
	
}

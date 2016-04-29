package j.jave.kernal.jave.support.parser;

import java.io.File;


public interface JFileParser<T> extends JParser {

	T parse(File file) throws Exception;
	
}

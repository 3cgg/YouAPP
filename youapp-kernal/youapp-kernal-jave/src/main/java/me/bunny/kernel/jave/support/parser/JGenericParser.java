package me.bunny.kernel.jave.support.parser;

public interface JGenericParser<T> extends JParser {

	T parse() throws Exception;
	
}

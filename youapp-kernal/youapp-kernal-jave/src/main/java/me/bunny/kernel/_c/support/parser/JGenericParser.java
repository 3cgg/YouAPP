package me.bunny.kernel._c.support.parser;

public interface JGenericParser<T> extends JParser {

	T parse() throws Exception;
	
}

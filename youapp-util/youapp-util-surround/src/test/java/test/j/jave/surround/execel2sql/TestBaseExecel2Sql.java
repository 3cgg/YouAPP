package test.j.jave.surround.execel2sql;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support.parser.JGenericParser;
import j.jave.kernal.jave.support.parser.JParseException;
import j.jave.kernal.jave.utils.JIOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public abstract class TestBaseExecel2Sql implements JGenericParser<String>{

	protected JLogger logger=JLoggerFactory.getLogger(getClass());
	
	private String input;
	
	private String output;

	public TestBaseExecel2Sql(String input, String output) {
		super();
		this.input = input;
		this.output = output;
	}
	
	@Override
	public String parse() throws Exception {
		try{
			StringBuffer stringBuffer=doParse(new FileInputStream(new File(input)));
			File outputFile=new File(output);
			String str=stringBuffer.toString();
			JIOUtils.write(outputFile, str.getBytes("utf-8"));
			logger.info("-----------------------------------end----------------------------------------------------------------------end---------------------------------end ");
			return str;
		}catch(Exception e){
			throw new JParseException(e);
		}
	}
	
	protected abstract StringBuffer doParse(InputStream inputStream) throws Exception;
	
}

package j.jave.platform.streaming.classcount;

import java.io.Serializable;

import me.bunny.kernel._c.support.parser.JParser;

public class RecordInfoParser implements JParser , Serializable{

	public RecordInfo parse(String record){
		String info=" INFO ";
		String debug="DEBUG ";
		String split="";
		if(record.indexOf(info)!=-1){
			split=info;
		}else if(record.indexOf(debug)!=-1){
			split=debug;
		}
		String partString=record.substring(record.indexOf(split)+split.length());
		String className=partString.substring(0, partString.indexOf(":")).trim();
		
		RecordInfo recordInfo=new RecordInfo();
		recordInfo.setLogTime(record.substring(0, 20));
		recordInfo.setClassName(className);
		recordInfo.setLogLevel(split.trim());
		return recordInfo;
	}
	
}

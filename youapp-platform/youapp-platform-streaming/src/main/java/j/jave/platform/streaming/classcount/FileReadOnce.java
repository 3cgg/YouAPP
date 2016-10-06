package j.jave.platform.streaming.classcount;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import j.jave.kernal.jave.utils.JIOUtils;

public class FileReadOnce {
	
	private boolean isOne(String line){
		String num=line.substring(0, 4);
		try{
			Integer.parseInt(num);
			return true;
		}catch (NumberFormatException e) {
			return false;
		}
	}

	public List<String> read(File file) throws Exception{
		List<String> data=new ArrayList<String>(10000);
		byte[] bytes=JIOUtils.getBytes(new FileInputStream(file),true);
		String string=new String(bytes,"utf-8");
		String temp="";
		for(String line:string.split("\\r\\n")){
			boolean isOne=isOne(line);
			if(temp.length()==0&&isOne){
				temp=line;
			}
			else{
				if(isOne){
					data.add(temp);
					temp=line;
				}
				else{
					temp=temp+line;
				}
			}
		}
		data.add(temp);
		return data;
	}
}

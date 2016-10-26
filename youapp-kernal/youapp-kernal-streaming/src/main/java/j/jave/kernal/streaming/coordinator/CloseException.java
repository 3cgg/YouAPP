package j.jave.kernal.streaming.coordinator;

import java.util.ArrayList;
import java.util.List;

import j.jave.kernal.jave.model.JModel;

@SuppressWarnings("serial")
public class CloseException extends RuntimeException implements JModel{
	
	private List<String> messages=new ArrayList<>();
	
	public CloseException() {
	}
	
	public void addMessage(String message){
		messages.add(message);
	}
	
	public boolean has(){
		return !messages.isEmpty();
	}
	
	@Override
	public String getMessage() {
		StringBuffer stringBuffer=new StringBuffer();
		for(String mesg:messages){
			stringBuffer.append(mesg+"\r\n");
		}
		return stringBuffer.toString();
	}
}
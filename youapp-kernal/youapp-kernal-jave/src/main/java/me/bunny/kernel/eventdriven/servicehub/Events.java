package me.bunny.kernel.eventdriven.servicehub;

import java.util.List;

public class Events  {
	
	private List<JYouAPPEvent<?>> events;
	
	public void add(JYouAPPEvent<?> event){
		if(!events.contains(event)){
			events.add(event);
		}
	}
	
	public void remove(JYouAPPEvent<?> event){
		events.remove(event);
	}
	
	public void add(JYouAPPEvent<?>... event){
		for(JYouAPPEvent<?> e:event){
			add(e);
		}
	}
	
}

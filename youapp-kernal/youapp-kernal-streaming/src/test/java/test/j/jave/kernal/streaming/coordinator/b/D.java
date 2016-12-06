package test.j.jave.kernal.streaming.coordinator.b;

import java.util.Queue;

import com.google.common.collect.Queues;

public class D {
	
	public static void main(String[] args) {

		Queue<Integer> queue=Queues.newArrayBlockingQueue(10);
		
		for(int i=0;i<100;i++){
			if(queue.size()==10){
				queue.remove();
			}
			queue.offer(i);
			System.out.println(queue.size());
		}
		
	}
	
}

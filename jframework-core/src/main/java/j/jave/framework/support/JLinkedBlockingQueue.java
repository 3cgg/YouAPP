/**
 * 
 */
package j.jave.framework.support;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p><strong>Note that this implementation is synchronized</strong>
 * @author J
 */
public class JLinkedBlockingQueue<T> extends AbstractQueue<T> {

	private LinkedBlockingQueue<T> blockingQueue=new LinkedBlockingQueue<T>();

	@Override
	public boolean offer(T e) {
		return blockingQueue.offer(e);
	}
	
	@Override
	public T poll() {
		return blockingQueue.poll();
	}

	@Override
	public T peek() {
		return blockingQueue.peek();
	}

	@Override
	public Iterator<T> iterator() {
		return blockingQueue.iterator();
	}

	@Override
	public int size() {
		return blockingQueue.size();
	}

	
	
	
	
}

package j.jave.kernal.streaming.coordinator;

import java.io.Serializable;

import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;

import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;

public class DistAtomicLong implements Serializable{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(DistAtomicLong.class);

	private DistributedAtomicLong atomicLong;
	
	private final String _path;
	
	/**
	 * 
	 * @param executor
	 * @param path  /locks/atomic-long
	 */
	public DistAtomicLong(ZookeeperExecutor executor,String path) {
		this._path=path;
		atomicLong=new DistributedAtomicLong(executor.backend(),
				path, new ExponentialBackoffRetry(1000, 3));
	}
	
	public long getSequence(){
		while(true){
			try {
				AtomicValue<Long>  atomicValue=  atomicLong.increment();
				if(atomicValue.succeeded()){
					long val= atomicValue.postValue();
					return val;
				}
			} catch (Exception e) {
				LOGGER.error(_path+" {atomic long error}. ", e);
			}
		}
	}
	
	
}

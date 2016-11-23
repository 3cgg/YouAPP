package j.jave.kernal.streaming.coordinator;

import java.io.Serializable;

import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;

import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public class DistAtomicLong implements Serializable{

	private DistributedAtomicLong atomicLong;
	
	public DistAtomicLong(ZookeeperExecutor executor) {
		atomicLong=new DistributedAtomicLong(executor.backend(),
				"/locks/atomic-long", new ExponentialBackoffRetry(1000, 3));
	}
	
	public long getSequence(){
		while(true){
			try {
				AtomicValue<Long>  atomicValue=  atomicLong.increment();
				if(atomicValue.succeeded()){
					long val= atomicValue.postValue();
					System.out.println("got value "+val+" from dist atomic long ... ");
					return val;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
}

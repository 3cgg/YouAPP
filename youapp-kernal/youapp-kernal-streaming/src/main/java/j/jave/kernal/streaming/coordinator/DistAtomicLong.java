package j.jave.kernal.streaming.coordinator;

import java.io.Serializable;

import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;

import j.jave.kernal.streaming.zookeeper.JZooKeeperConnecter.ZookeeperExecutor;

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
					return atomicValue.postValue();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
}

package j.jave.platform.streaming.classcount;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.Aggregator;
import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.ReducerAggregator;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.TridentOperationContext;
import org.apache.storm.trident.operation.builtin.FilterNull;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.testing.Split;
import org.apache.storm.trident.topology.TransactionAttempt;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import me.bunny.kernel.jave.utils.JStringUtils;

@SuppressWarnings({"serial","rawtypes"})
public class ClassUsedCountTopology {
	
	public static void main(String[] args) {
		
		TridentTopology tridentTopology=new TridentTopology();
		TridentState tridentState=tridentTopology.newStream("class-count", new SimpleLoggingLoadingSpout(60))
		.shuffle()
		.each(new Fields("record"), new BaseFilter() {
			@Override
			public boolean isKeep(TridentTuple tuple) {
				String record=tuple.getString(0);
				return record.length()>0;
			}
		})
		.parallelismHint(3)
		.shuffle()
		.each(new Fields("record"), new BaseFunction() {
			private RecordInfoParser recordInfoParser=new RecordInfoParser();
			@Override
			public void execute(TridentTuple tuple, TridentCollector collector) {
				String record=tuple.getString(0);
				RecordInfo recordInfo=recordInfoParser.parse(record);
				String className=recordInfo.getClassName();
				collector.emit(new Values(className,1));
			}
		}, new Fields("class","count"))
		.parallelismHint(2)
		.groupBy(new Fields("class"))
		.partitionAggregate(new Fields("class","count"),new Aggregator<ClassCount>() {

			@Override
			public void prepare(Map conf, TridentOperationContext context) {
				//System.out.println(" partitionAggregate( prepare ) ...... ");
			}

			@Override
			public void cleanup() {
				//System.out.println(" partitionAggregate( cleanup ) ...... ");
			}

			@Override
			public ClassCount init(Object batchId, TridentCollector collector) {
				TransactionAttempt attempt=(TransactionAttempt) batchId;
				ClassCount classCount=new ClassCount();
				classCount.setBatchId(attempt.getTransactionId());
				//System.out.println("[init] batch id : "+attempt.getTransactionId()+";CLASS COUNT IDENTIFY: "+classCount);
				return classCount;
			}

			@Override
			public void aggregate(ClassCount val, TridentTuple tuple, TridentCollector collector) {
				if(JStringUtils.isNullOrEmpty(val.getClassName())){
					val.setClassName(tuple.getString(0));
				}
				val.setCount(val.getCount()+tuple.getInteger(1));
				//System.out.println("[aggregate] batch id : "+val.getBatchId()+";CLASS COUNT IDENTIFY: "+val+";CLASS NAME : "+tuple.getString(0));
			}

			@Override
			public void complete(ClassCount val, TridentCollector collector) {
				collector.emit(new Values(val.getCount()));
				//System.out.println("[complete] batch id : "+val.getBatchId()+";CLASS COUNT IDENTIFY: "+val+";CLASS NAME : "+val.getClassName());
			}
			
		}, new Fields("partition_count_aggregator"))
		.toStream()
		.groupBy(new Fields("class"))
		.aggregate(new Fields("class","partition_count_aggregator"),new Aggregator<ClassCount>() {
			
			@Override
			public void prepare(Map conf, TridentOperationContext context) {
				System.out.println(" [aggregate ->  prepare] ...... "+this);
			}

			@Override
			public void cleanup() {
				System.out.println(" [aggregate ->  cleanup] ...... "+this);
			}

			@Override
			public ClassCount init(Object batchId, TridentCollector collector) {
				TransactionAttempt attempt=(TransactionAttempt) batchId;
				ClassCount classCount=new ClassCount();
				classCount.setBatchId(attempt.getTransactionId());
				//System.out.println("[aggregate -> init] batch id : "+attempt.getTransactionId()+";CLASS COUNT IDENTIFY: "+classCount);
				return classCount;
			}

			@Override
			public void aggregate(ClassCount val, TridentTuple tuple, TridentCollector collector) {
				if(JStringUtils.isNullOrEmpty(val.getClassName())){
					val.setClassName(tuple.getString(0));
				}
				val.setCount(val.getCount()+tuple.getLong(1));
				//System.out.println("[aggregate -> aggregate] batch id : "+val.getBatchId()+";CLASS COUNT IDENTIFY: "+val+";CLASS NAME : "+tuple.getString(0));
			}

			@Override
			public void complete(ClassCount val, TridentCollector collector) {
				collector.emit(new Values(val.getCount()));
				System.out.println("[aggregate -> complete] batch id : "+val.getBatchId()
				+";CLASS COUNT IDENTIFY: "+val
				+";CLASS NAME : "+val.getClassName()
				+";CLASS COUNT NUM: "+val.getCount());
			}
			
		}, new Fields("batch_count_aggregator"))
		.groupBy(new Fields("class"))
		.persistentAggregate(new MemoryMapState.Factory(), new Fields("class","batch_count_aggregator"),
			new ReducerAggregator<ClassCount>() {
			
			@Override
			public ClassCount init() {
				System.out.println(" [persistentAggregate ->  init] ...... "+this);
				return new ClassCount();
			}

			@Override
			public ClassCount reduce(ClassCount curr, TridentTuple tuple) {
				if(JStringUtils.isNullOrEmpty(curr.getClassName())){
					curr.setClassName(tuple.getString(0));
				}
				curr.setCount(curr.getCount()+tuple.getLong(1));
				System.out.println(" [persistentAggregate ->  reduce] ...... "+this);
				return curr;
			}
			
			}
		
		, new Fields("final_count_aggregator") )
		
//		.peek(new Consumer() {
//			@Override
//			public void accept(TridentTuple input) {
//				System.out.println("accpet :  ---- >  "+input.getString(0) +"["+input.get(0)+"]" );
//			}
//		})
		;
		
		Config conf = new Config();
		conf.put(ClassCountConfig.FILE_URI, new File("D:\\youapp-log-dev\\youap-html.log").toURI().toString());
		conf.setMessageTimeoutSecs(5000);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("test", conf, tridentTopology.build());
		
		LocalDRPC localDRPC = new LocalDRPC();
		
		tridentTopology.newDRPCStream("class-count-search", localDRPC)
		.each(new Fields("args"), new Split(), new Fields("word"))
	       .groupBy(new Fields("word"))
	       .stateQuery(tridentState, new Fields("word"), new MapGet(), new Fields("count"))
	       .each(new Fields("count"), new FilterNull())
	       .aggregate(new Fields("word","count"), new Aggregator<HashMap<String,Long>>() {
			@Override
			public void prepare(Map conf, TridentOperationContext context) {
			}

			@Override
			public void cleanup() {
			}

			@Override
			public HashMap<String,Long> init(Object batchId, TridentCollector collector) {
				return new HashMap<String,Long>();
			}

			@Override
			public void aggregate(HashMap<String,Long> val, TridentTuple tuple, TridentCollector collector) {
				String className=tuple.getString(0);
				if(val.containsKey(className)){
					val.put(className, val.get(className)+((ClassCount)tuple.get(1)).getCount());
				}
				else{
					val.put(className, ((ClassCount)tuple.get(1)).getCount());
				}
			}

			@Override
			public void complete(HashMap<String,Long> val, TridentCollector collector) {
				collector.emit(new Values(val));
			}
	    	   
		}, new Fields("sum"));
		cluster.submitTopology("test-search", conf, tridentTopology.build());
		
		String result=localDRPC.execute("class-count-search", "JServiceLazyProxy JCglibAopProxy"
				+ " JEventQueuePipeline$JEventQueueEndPipe JEventExecutionQueueElementDistributer");
		
		System.out.println(result);
		
		Utils.sleep(10000000);
		cluster.killTopology("test");
		cluster.shutdown();
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}

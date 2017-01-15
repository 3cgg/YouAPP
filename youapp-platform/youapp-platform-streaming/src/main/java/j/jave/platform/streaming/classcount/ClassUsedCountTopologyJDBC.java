package j.jave.platform.streaming.classcount;

import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.jdbc.common.ConnectionProvider;
import org.apache.storm.jdbc.common.HikariCPConnectionProvider;
import org.apache.storm.jdbc.mapper.JdbcMapper;
import org.apache.storm.jdbc.mapper.SimpleJdbcMapper;
import org.apache.storm.jdbc.trident.state.JdbcState;
import org.apache.storm.jdbc.trident.state.JdbcStateFactory;
import org.apache.storm.jdbc.trident.state.JdbcUpdater;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.Aggregator;
import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.TridentOperationContext;
import org.apache.storm.trident.topology.TransactionAttempt;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import com.google.common.collect.Maps;

import me.bunny.kernel._c.utils.JStringUtils;

@SuppressWarnings({"serial","rawtypes"})
public class ClassUsedCountTopologyJDBC {
	
	public static void main(String[] args) {
		
		Map hikariConfigMap = Maps.newHashMap();
		hikariConfigMap.put("dataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		hikariConfigMap.put("dataSource.url", "jdbc:mysql://localhost/test");
		hikariConfigMap.put("dataSource.user","root");
		hikariConfigMap.put("dataSource.password","jiazhongjin");
		ConnectionProvider connectionProvider = new HikariCPConnectionProvider(hikariConfigMap);
		String tableName = "user_details";
		JdbcMapper simpleJdbcMapper = new SimpleJdbcMapper(tableName, connectionProvider);
		JdbcState.Options options = new JdbcState.Options()
		        .withConnectionProvider(connectionProvider)
		        .withMapper(simpleJdbcMapper)
		        .withTableName("user_details")
		        .withQueryTimeoutSecs(30);

		
		
		TridentTopology tridentTopology=new TridentTopology();
		TridentState tridentState=tridentTopology.newStream("class-count", new SimpleLoggingLoadingSpout(10000))
		.shuffle()
		.each(new Fields("record"), new BaseFilter() {
			@Override
			public boolean isKeep(TridentTuple tuple) {
				String record=tuple.getString(0);
				return record.length()>0;
			}
		})
		.parallelismHint(20)
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
		}, new Fields("class","single-count"))
		.parallelismHint(35)
		.groupBy(new Fields("class"))
		.partitionAggregate(new Fields("class","single-count"),new Aggregator<ClassCount>() {

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
		//next to batch aggregate ...
		.aggregate(new Fields("class","partition_count_aggregator"),new Aggregator<ClassCount>() {
			
			@Override
			public void prepare(Map conf, TridentOperationContext context) {
				System.out.println(" [aggregate ->  prepare] ...... "+this);
			}

			@Override
			public void cleanup() {
				System.out.println(" [aggregate ->  cleanup] ...... "+this);
			}
			
			private  AtomicInteger  i = new AtomicInteger(0);
			
			@Override
			public ClassCount init(Object batchId, TridentCollector collector) {
				TransactionAttempt attempt=(TransactionAttempt) batchId;
				ClassCount classCount=new ClassCount();
				classCount.setId(String.valueOf(i.incrementAndGet()));
				classCount.setBatchId(attempt.getTransactionId());
				//System.out.println("[aggregate -> init] batch id : "+attempt.getTransactionId()+";CLASS COUNT IDENTIFY: "+classCount);
				return classCount;
			}

			@Override
			public void aggregate(ClassCount val, TridentTuple tuple, TridentCollector collector) {
				System.out.println("class name : "+tuple.getString(0)
				+" sequence id : "+val.getId()
				+" class count : "+tuple.getLong(1)
				+" batch id : "+val.getBatchId());
				if(JStringUtils.isNullOrEmpty(val.getClassName())){
					val.setClassName(tuple.getString(0));
				}
				val.setCount(val.getCount()+tuple.getLong(1));
				//System.out.println("[aggregate -> aggregate] batch id : "+val.getBatchId()+";CLASS COUNT IDENTIFY: "+val+";CLASS NAME : "+tuple.getString(0));
			}

			@Override
			public void complete(ClassCount val, TridentCollector collector) {
				collector.emit(new Values(val));
				System.out.println("[batch aggregate -> complete] batch id : "+val.getBatchId()
				+";CLASS NAME : "+val.getClassName()
				+";CLASS COUNT IDENTIFY: "+val.getId()
				+";CLASS COUNT NUM: "+val.getCount());
			}
			
		}, new Fields("batch_count_aggregator"))
		.shuffle()
		.each(new Fields("class","batch_count_aggregator"),new BaseFunction() {
			long i=0;
			@Override
			public void execute(TridentTuple tuple, TridentCollector collector) {
				ClassCount classCount=(ClassCount) tuple.get(1);
				collector.emit(new Values(i++,classCount.getClassName(),classCount.getCount(),classCount.getBatchId()));
			}
		}, new Fields("id","classname","classcount","batchid"))
		.parallelismHint(18)
		.shuffle()
		.partitionPersist(new JdbcStateFactory(options),
				new Fields("id","classname","classcount","batchid"), new JdbcUpdater())
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
		
		Utils.sleep(10000000);
		cluster.killTopology("test");
		cluster.shutdown();
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}

package j.jave.platform.streaming.classcount;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.tuple.Fields;

import j.jave.kernal.jave.utils.JIOUtils;

@SuppressWarnings({"serial","unchecked"})
public class UsingFixedBatchSpout implements LoggingAnalyseSpout{

	private FixedBatchSpout fixedBatchSpout;
	
	private long pre;
	
	public UsingFixedBatchSpout() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void open(Map conf, TopologyContext context) {
		try {
			List<List<String>> data=new ArrayList<List<String>>();
			String fileURI=(String) conf.get(ClassCountConf.FILE_URI);
			File file=new File(new URI(fileURI));
			byte[] bytes=JIOUtils.getBytes(new FileInputStream(file));
			String string=new String(bytes,"utf-8");
			for(String line:string.split("\\r\\n")){
				data.add(new ArrayList<String>(){{add(line);}});
			}
			fixedBatchSpout=new FixedBatchSpout(new Fields("record"), 20, data.toArray(new ArrayList[]{}));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		System.out.println("open  batch...");
	}

	@Override
	public void emitBatch(long batchId, TridentCollector collector) {
		long now=new Date().getTime();
		long inte=now-pre;
		pre=now;
		System.out.println("ready to emit  batch..."+ batchId+" time : "+ inte);
		
		fixedBatchSpout.emitBatch(batchId, collector);
	}

	@Override
	public void ack(long batchId) {
		fixedBatchSpout.ack(batchId);
	}

	@Override
	public void close() {
		fixedBatchSpout.close();
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return fixedBatchSpout.getComponentConfiguration();
	}

	@Override
	public Fields getOutputFields() {
		return fixedBatchSpout.getOutputFields();
	}

}

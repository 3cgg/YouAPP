package test.j.jave.platform.streaming.wordcount;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class WordCountTopology {

	public static class RandomSentenceSpout extends BaseRichSpout{

		private SpoutOutputCollector collector;
		Random rand;
		
		String[] sentences = new String[]{ 
				"the cow jumped over the moon", 
				"an apple a day keeps the doctor away",
		        "four score and seven years ago", 
		        "snow white and the seven dwarfs", 
		        "i am at two with nature" };
		
		@Override
		public void open(Map conf, TopologyContext context,
				SpoutOutputCollector collector) {
			this.collector=collector;
			this.rand=new Random();
		}

		@Override
		public void nextTuple() {
			Utils.sleep(1000);
			String sentence = sentences[rand.nextInt(sentences.length)];
			collector.emit(new Values(sentence));
		}

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("sentence"));
		}
		
	}
	
	public static class SplitSentence extends BaseBasicBolt{

		@Override
		public void prepare(Map stormConf, TopologyContext context) {
			super.prepare(stormConf, context);
		}
		
		@Override
		public void execute(Tuple input, BasicOutputCollector collector) {
			String sentence=input.getString(0);
			for(String word:sentence.split("\\s+")){
				collector.emit(new Values(word));
			}
		}

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("word"));
		}
		
	}
	
	
	public static class WordCount extends BaseBasicBolt{
		
		private Map<String, Integer> counts=new HashMap<>();
		
		@Override
		public void execute(Tuple input, BasicOutputCollector collector) {
			String word=input.getString(0);
			Integer count=counts.get(word);
			if(count==null){
				count=0;
			}
			count++;
			counts.put(word, count);
			collector.emit(new Values(word,count));
		}
		
		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			declarer.declare(new Fields("word","count"));
		}
		
	}
	
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();

	    builder.setSpout("spout", new RandomSentenceSpout(), 1);

	    builder.setBolt("split", new SplitSentence(), 1).shuffleGrouping("spout");
	    builder.setBolt("count", new WordCount(), 1).fieldsGrouping("split", new Fields("word"));

	    Config conf = new Config();
	    conf.registerMetricsConsumer(org.apache.storm.metric.LoggingMetricsConsumer.class);

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("test", conf, builder.createTopology());
		Utils.sleep(10000);
		cluster.killTopology("test");
		cluster.shutdown();
	    
	}
	
	
}

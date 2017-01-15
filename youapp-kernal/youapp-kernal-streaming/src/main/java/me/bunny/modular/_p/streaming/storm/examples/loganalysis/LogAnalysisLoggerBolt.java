package me.bunny.modular._p.streaming.storm.examples.loganalysis;

import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import me.bunny.modular._p.streaming.storm.BaseSimpleRichBolt;

public class LogAnalysisLoggerBolt extends BaseSimpleRichBolt {

	@Override
	protected void doExecute(Tuple tuple) throws Exception {
		Object record=tuple.getValueByField("record");
		logger().info(record);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}

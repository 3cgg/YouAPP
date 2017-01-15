package me.bunny.modular._p.streaming.storm.examples.loganalysis;

import org.apache.storm.Config;
import org.apache.storm.generated.StormTopology;

import me.bunny.modular._p.streaming.storm.StormConfigNames;
import me.bunny.modular._p.streaming.storm.StormRunner;


public class LogAnalysisTopologyCluster {

	public static void main(String[] args) throws Exception {
		Config conf=new ConfigParser().parse(args);
		conf.put(Config.TOPOLOGY_WORKERS, Integer.parseInt(args[2]));
		
		StormTopology stormTopology=new LogAnalysisTopologyBuilder().build(conf);
		StormRunner.runTopologyRemotely(stormTopology, 
				(String)conf.get(StormConfigNames.STORM_TOPOLOGY_NAME)
				, conf);
	}
	
}

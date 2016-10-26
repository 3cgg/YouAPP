package j.jave.kernal.streaming.kafka;

import j.jave.kernal.jave.model.JModel;

@SuppressWarnings("serial")
public class JKafkaConfig implements JModel {

	/**
	 * bootstrap.servers i.e.  localhost:9092
	 */
	private String bootstrapServers;
	

	/**
	 * @return the bootstrapServers
	 */
	public String getBootstrapServers() {
		return bootstrapServers;
	}

	/**
	 * @param bootstrapServers the bootstrapServers to set
	 */
	public void setBootstrapServers(String bootstrapServers) {
		this.bootstrapServers = bootstrapServers;
	}
	
}

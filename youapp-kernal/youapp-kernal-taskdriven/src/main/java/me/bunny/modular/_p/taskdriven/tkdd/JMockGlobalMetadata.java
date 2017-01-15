package me.bunny.modular._p.taskdriven.tkdd;

/**
 * consider the metadata as the pre-defined one for those tasks that miss the metadata.
 * @author J
 *
 */
public class JMockGlobalMetadata  extends  JDefaultTaskMetadata{

	{
		setDescriber("Global Mock Metadata");
		setName(JMockGlobalMetadata.class.getName());
	}
	
	@Override
	public void setName(String name) {
		this.name=name;	
	}

	@Override
	public void setDescriber(String describer) {
		this.describer=describer;
	}

	@Override
	public Class<? extends JTask> task() {
		return null;
	}
	
	
}

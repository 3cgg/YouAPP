package j.jave.kernal.container;

public class JResourceRunnerLoader implements JRunnerLoader{

	@Override
	public JResourceRunner load(JMicroContainerConfig microContainerConfig) {
		return new JResourceRunner((JResourceMicroContainerConfig) microContainerConfig);
	}
	
}

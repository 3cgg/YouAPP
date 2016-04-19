package j.jave.platform.basicsupportcomp.core.container;

public class MicroContainerConfig  implements JIdentifier{

	private JLoader loader;
	
	private String unique;
	
	private String name;

	public JLoader getLoader() {
		return loader;
	}

	public void setLoader(JLoader loader) {
		this.loader = loader;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String unique() {
		return unique;
	}

	@Override
	public String name() {
		return name;
	}
	
}

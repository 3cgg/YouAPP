package j.jave.kernal.container;

public class ContainerConfig  implements JIdentifier{
	
	private String unique;
	
	private String name;

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

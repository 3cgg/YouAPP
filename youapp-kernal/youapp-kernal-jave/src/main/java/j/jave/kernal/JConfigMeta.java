package j.jave.kernal;

import j.jave.kernal.jave.model.JModel;

public class JConfigMeta implements JModel {

	private String name;
	
	private String value;
	
	private String description;
	
	private boolean override;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}
	
}

package j.jave.kernal.jave.model;


public class JOrder implements JModel {

	/**
	 * sort column 
	 */
	private String column;
	
	/**
	 * sort type .  desc/asc
	 */
	private String type;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

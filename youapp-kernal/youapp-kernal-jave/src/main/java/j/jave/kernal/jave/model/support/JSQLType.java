package j.jave.kernal.jave.model.support;

public enum JSQLType  {
	
	VARCHAR,TIMESTAMP,INTEGER,DOUBLE;

	public Object defaultValue(){
		switch (this) {
			case VARCHAR: return "";
			case TIMESTAMP: return null;
			case INTEGER: return 0;
			case DOUBLE: return 0.0d;
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

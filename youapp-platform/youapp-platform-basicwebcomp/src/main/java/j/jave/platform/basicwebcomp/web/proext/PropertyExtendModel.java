package j.jave.platform.basicwebcomp.web.proext;

import j.jave.platform.basicwebcomp.web.proext.annotation.CodeExtend;
import j.jave.platform.basicwebcomp.web.proext.annotation.ObjectExtend;
import j.jave.platform.basicwebcomp.web.proext.annotation.PropertyExtend;

import java.lang.reflect.Field;

public class PropertyExtendModel {

	private PropertyExtend propertyExtend;
	
	private CodeExtend codeExtend;
	
	private ObjectExtend objectExtend; 
	
	private Field field;
	
	private Object object;

	public PropertyExtend getPropertyExtend() {
		return propertyExtend;
	}

	public void setPropertyExtend(PropertyExtend propertyExtend) {
		this.propertyExtend = propertyExtend;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public CodeExtend getCodeExtend() {
		return codeExtend;
	}

	public void setCodeExtend(CodeExtend codeExtend) {
		this.codeExtend = codeExtend;
	}

	public ObjectExtend getObjectExtend() {
		return objectExtend;
	}

	public void setObjectExtend(ObjectExtend objectExtend) {
		this.objectExtend = objectExtend;
	}
	
}

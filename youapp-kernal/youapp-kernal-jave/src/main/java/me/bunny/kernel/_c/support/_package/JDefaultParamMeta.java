package me.bunny.kernel._c.support._package;

import java.lang.annotation.Annotation;

import me.bunny.kernel._c.model.JModel;

public class JDefaultParamMeta implements JModel {

	private String name;
	
	private Class<?> type;
	
	private Annotation[] annotations;
	
	private int index;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Annotation[] getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Annotation[] annotations) {
		this.annotations = annotations;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}

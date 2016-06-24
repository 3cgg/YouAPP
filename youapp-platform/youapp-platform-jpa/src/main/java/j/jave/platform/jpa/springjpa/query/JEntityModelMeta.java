package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.model.JModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JEntityModelMeta implements JModel{
	
	private List<JEntityColumnMeta> columnMetas=new ArrayList<JEntityColumnMeta>();
	
	private Class<?> entityClass;
	
	public JEntityModelMeta(Class<?> entityClass) {
		this.entityClass=entityClass;
	}
	
	public Class<?> getEntityClass() {
		return entityClass;
	}
	
	public Collection<JEntityColumnMeta> columnMetas() {
		return Collections.unmodifiableCollection(columnMetas);
	}

	void setColumnMetas(List<JEntityColumnMeta> columnMetas) {
		this.columnMetas = columnMetas;
	}
	
}

package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.platform.data.web.mapping.MappingMeta;
import me.bunny.kernel._c.model.JModel;

import java.util.Collection;

public class ContainerMappingMeta implements JModel{

	/**
	 * container unique identifier
	 */
	private String unique;
	
	private Collection<MappingMeta> mappingMetas;

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public Collection<MappingMeta> getMappingMetas() {
		return mappingMetas;
	}

	public void setMappingMetas(Collection<MappingMeta> mappingMetas) {
		this.mappingMetas = mappingMetas;
	}
	
}

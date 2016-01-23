package test.j.jave.module.crawl.model;

import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.def.JWebModelDefProperties;
import j.jave.module.crawl.def.JWebNodeFieldKey;
import j.jave.module.crawl.def.JWebNodeModel;

@JWebNodeModel(url="http://www.w3schools.com/tags/tag_table.asp",
	scope={"tag:table,class:w3-table-all","id:j-area-filter"},
	tableOrient=JWebModelDefProperties.TABLE_ORIENT_HORIZONTAL,
	single=false
		)
public class Attributes implements JWebModel{
	
	private String attribute;
	
	private String value;
	
	private String description;

	
	
	@JWebNodeFieldKey(matchesValue="Attribute")
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	@JWebNodeFieldKey(matchesValue="Value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@JWebNodeFieldKey(matchesValue="Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}

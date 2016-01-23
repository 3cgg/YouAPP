package test.j.jave.module.crawl.model;

import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.def.JWebModelDefProperties;
import j.jave.module.crawl.def.JWebNodeFieldKey;
import j.jave.module.crawl.def.JWebNodeFieldValue;
import j.jave.module.crawl.def.JWebNodeModel;

@JWebNodeModel(url="http://t10sc.nuomi.com/pc/goods/detail?tiny_url=xtlixspo&s=b882ac07620cb5f13f1fb2ff33246ff5",
	scope={"tag:table,class:consume","id:j-area-filter"},
	tableOrient=JWebModelDefProperties.TABLE_ORIENT_VERTICAL
		)
public class Alert implements JWebModel{
	
	private String validDate;
	
	private String availableDate;
	
	private String orderMeg;
	
	private String rule;
	
	@JWebNodeFieldKey(xpath="//*[@id=\"j-info-all\"]/li[1]/div[3]/div/table/tbody/tr[1]/th/div",
			matchesValue="有效期",
			requireMatch=true
			)
	@JWebNodeFieldValue(xpath="//*[@id=\"j-info-all\"]/li[1]/div[3]/div/table/tbody/tr[1]/td/div")
	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	@JWebNodeFieldKey(xpath="//*[@id=\"j-info-all\"]/li[1]/div[3]/div/table/tbody/tr[2]/th/div",
			matchesValue="可用时间",
			requireMatch=true
			)
	@JWebNodeFieldValue(xpath="//*[@id=\"j-info-all\"]/li[1]/div[3]/div/table/tbody/tr[2]/td/div")
	public String getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(String availableDate) {
		this.availableDate = availableDate;
	}
	
	@JWebNodeFieldKey(xpath="//*[@id=\"j-info-all\"]/li[1]/div[3]/div/table/tbody/tr[3]/th/div",
			matchesValue="预约提示",
			requireMatch=true
			)
	@JWebNodeFieldValue(xpath="//*[@id=\"j-info-all\"]/li[1]/div[3]/div/table/tbody/tr[3]/td/div")
	public String getOrderMeg() {
		return orderMeg;
	}

	public void setOrderMeg(String orderMeg) {
		this.orderMeg = orderMeg;
	}

	@JWebNodeFieldKey(xpath="//*[@id=\"j-info-all\"]/li[1]/div[3]/div/table/tbody/tr[4]/th/div",
			matchesValue="使用规则",
			requireMatch=true
			)
	@JWebNodeFieldValue(xpath="//*[@id=\"j-info-all\"]/li[1]/div[3]/div/table/tbody/tr[4]/td/div")
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
	
	
	
	
	
}

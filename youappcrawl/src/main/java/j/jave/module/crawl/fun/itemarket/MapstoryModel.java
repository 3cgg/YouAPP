package j.jave.module.crawl.fun.itemarket;

import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.def.JWebNodeModel;

@JWebNodeModel(url="http://www.itemarket.com/market.asp",
scope={"tag:table,style:background-color|#4c4c3c;,style:color|8f8f8f;","id:j-area-filter"},
single=false,
analyse={"j.jave.module.crawl.fun.itemarket.MapstoryAnalyse"}
	)
public class MapstoryModel implements JWebModel {

	private String location;
	
	private String seller;
	
	private String time;
	
	private String goodType;
	
	private String goodOccupation;
	
	private String goodLevel;
	
	private String goodProperties;
	
	private String goodUpgradeLevel;
	
	private String goodUpgradeProperties;
	
	private String goodUpgradePropertiesNum;

	private String price;
	
	private String name;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getGoodType() {
		return goodType;
	}

	public void setGoodType(String goodType) {
		this.goodType = goodType;
	}

	public String getGoodOccupation() {
		return goodOccupation;
	}

	public void setGoodOccupation(String goodOccupation) {
		this.goodOccupation = goodOccupation;
	}

	public String getGoodLevel() {
		return goodLevel;
	}

	public void setGoodLevel(String goodLevel) {
		this.goodLevel = goodLevel;
	}

	public String getGoodProperties() {
		return goodProperties;
	}

	public void setGoodProperties(String goodProperties) {
		this.goodProperties = goodProperties;
	}

	public String getGoodUpgradeLevel() {
		return goodUpgradeLevel;
	}

	public void setGoodUpgradeLevel(String goodUpgradeLevel) {
		this.goodUpgradeLevel = goodUpgradeLevel;
	}

	public String getGoodUpgradeProperties() {
		return goodUpgradeProperties;
	}

	public void setGoodUpgradeProperties(String goodUpgradeProperties) {
		this.goodUpgradeProperties = goodUpgradeProperties;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoodUpgradePropertiesNum() {
		return goodUpgradePropertiesNum;
	}

	public void setGoodUpgradePropertiesNum(String goodUpgradePropertiesNum) {
		this.goodUpgradePropertiesNum = goodUpgradePropertiesNum;
	}
	
	
}

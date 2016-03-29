package com.youappcorp.project.unittest.model;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.platform.basicwebcomp.web.proext.PropertyExtendable;
import j.jave.platform.basicwebcomp.web.proext.annotation.CodeExtend;
import j.jave.platform.basicwebcomp.web.proext.annotation.ObjectExtend;
import j.jave.platform.basicwebcomp.web.proext.annotation.PropertyExtend;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestModel extends JBaseModel  implements PropertyExtendable{

	private String name;
	
	private int age;
	
	private Integer max;
	
	private long co;
	
	private long cox;
	
	private double dd;
	
	private Double ddx;
	
	private byte bb;
	
	private Byte bbx;
	
	private BigDecimal money;
	
	private Date time;
	
	private Timestamp tttime;
	
	private boolean bl;
	
	private Boolean blx;

	private List<String> alist;
	
	private String[] astrs;
	
	private String code;
	
	@CodeExtend(property="code",codeType="SEX")
	private String codeName;
	
	private String refId="M";
	
	@ObjectExtend(property="refId")
	private Object refObject;
	
	@PropertyExtend(active=true,objectExtend=@ObjectExtend(property="refId"))
	private Object refObject2;
	
	private List<TestModel> testModelList=new ArrayList<TestModel>();
	
	private Set<TestModel> testModelSet=new HashSet<TestModel>();
	
	private Map<String, TestModel> map=new HashMap<String, TestModel>();
	
	private Map<String, Collection<TestModel>> mapList=new HashMap<String, Collection<TestModel>>();
	
	public TestModel() {
	}
	
	public TestModel(String code){
		this.code=code;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public Object getRefObject() {
		return refObject;
	}

	public void setRefObject(Object refObject) {
		this.refObject = refObject;
	}

	public List<String> getAlist() {
		return alist;
	}

	public void setAlist(List<String> alist) {
		this.alist = alist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public long getCo() {
		return co;
	}

	public void setCo(long co) {
		this.co = co;
	}

	public long getCox() {
		return cox;
	}

	public void setCox(long cox) {
		this.cox = cox;
	}

	public double getDd() {
		return dd;
	}

	public void setDd(double dd) {
		this.dd = dd;
	}

	public Double getDdx() {
		return ddx;
	}

	public void setDdx(Double ddx) {
		this.ddx = ddx;
	}

	public byte getBb() {
		return bb;
	}

	public void setBb(byte bb) {
		this.bb = bb;
	}

	public Byte getBbx() {
		return bbx;
	}

	public void setBbx(Byte bbx) {
		this.bbx = bbx;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Timestamp getTttime() {
		return tttime;
	}

	public void setTttime(Timestamp tttime) {
		this.tttime = tttime;
	}

	public boolean isBl() {
		return bl;
	}

	public void setBl(boolean bl) {
		this.bl = bl;
	}

	public Boolean getBlx() {
		return blx;
	}

	public void setBlx(Boolean blx) {
		this.blx = blx;
	}

	public String[] getAstrs() {
		return astrs;
	}

	public void setAstrs(String[] astrs) {
		this.astrs = astrs;
	}

	public Object getRefObject2() {
		return refObject2;
	}

	public void setRefObject2(Object refObject2) {
		this.refObject2 = refObject2;
	}

	public List<TestModel> getTestModelList() {
		return testModelList;
	}

	public void setTestModelList(List<TestModel> testModelList) {
		this.testModelList = testModelList;
	}

	public Set<TestModel> getTestModelSet() {
		return testModelSet;
	}

	public void setTestModelSet(Set<TestModel> testModelSet) {
		this.testModelSet = testModelSet;
	}

	public Map<String, TestModel> getMap() {
		return map;
	}

	public void setMap(Map<String, TestModel> map) {
		this.map = map;
	}

	public Map<String, Collection<TestModel>> getMapList() {
		return mapList;
	}

	public void setMapList(Map<String, Collection<TestModel>> mapList) {
		this.mapList = mapList;
	}
	
	
	
}

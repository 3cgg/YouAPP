package test.j.jave.platform.jpa.springjpa;

import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.jpa.springjpa.JJpaBaseModel;
import j.jave.platform.jpa.springjpa.query.Condition;
import j.jave.platform.jpa.springjpa.query.SingleEntityQuery;
import j.jave.platform.jpa.springjpa.query.Condition.LinkType;
import j.jave.platform.jpa.springjpa.query.Order;

import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

public class TestSingleEntityQuery extends TestCase {

	
	@Test
	public void testCond(){
		
		String whereClause=new Condition(JJpaBaseModel.class,LinkType.AND)
		.equals("id", JUniqueUtils.unique())
		.larger("createTime", new Date())
		.largerAndEquals("createTime", new Date())
		.smaller("updateTime", new Date())
		.smallerAndEqual("updateTime", new Date())
		.notEquals("deleted", "0")
		.toWhereClause();
		
		System.out.println(whereClause);
		
	}
	
	@Test
	public void testOrder(){
		
		String orderClause=new Order(JJpaBaseModel.class)
		.asc("id")
		.desc("createTime")
		.asc("createTime")
		.desc("updateTime")
		.toOrderClause();
		
		System.out.println(orderClause);
		
	}
	
	
	@Test
	public void testSQL(){
		SingleEntityQuery singleEntityQuery=new SingleEntityQuery(JJpaBaseModel.class);
		singleEntityQuery.condition(LinkType.AND)
				.equals("id", JUniqueUtils.unique())
				.larger("createTime", new Date())
				.largerAndEquals("createTime", new Date())
				.smaller("updateTime", new Date())
				.smallerAndEqual("updateTime", new Date())
				.notEquals("deleted", "0");
		singleEntityQuery.order()
		.asc("id")
		.desc("createTime")
		.asc("createTime")
		.desc("updateTime")
		.toOrderClause();
		
		String sql=singleEntityQuery.toJPQL();
		
		System.out.println(sql);
		
	}
	
}

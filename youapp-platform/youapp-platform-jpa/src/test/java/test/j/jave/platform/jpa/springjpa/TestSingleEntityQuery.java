package test.j.jave.platform.jpa.springjpa;

import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.jpa.springjpa.JJpaBaseModel;
import j.jave.platform.jpa.springjpa.query.Condition;
import j.jave.platform.jpa.springjpa.query.SingleEntityQueryMeta;
import j.jave.platform.jpa.springjpa.query.Condition.LinkType;
import j.jave.platform.jpa.springjpa.query.Order;

import java.util.Date;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

public class TestSingleEntityQuery extends TestCase {

	
	@Test
	public void testCond(){
		
		Condition condition=new Condition(JJpaBaseModel.class)
		.equals("id", JUniqueUtils.unique())
		.larger("createTime", new Date())
		.largerAndEquals("createTime", new Date())
		.smaller("updateTime", new Date())
		.smallerAndEqual("updateTime", new Date())
		.notEquals("deleted", "0")
		.link(LinkType.OR)
		.equals("id", JUniqueUtils.unique())
		.larger("createTime", new Date(),LinkType.OR)
		.link(LinkType.AND)
		.equals("id", JUniqueUtils.unique())
		.larger("createTime", new Date(),LinkType.OR)
		;
		String whereClause= condition.toWhereClause();
		Map<String, Object> params=condition.toParams();
		
		
		System.out.println(whereClause+params);
		
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
		SingleEntityQueryMeta singleEntityQuery=new SingleEntityQueryMeta(JJpaBaseModel.class);
		singleEntityQuery.condition()
				.equals("id", JUniqueUtils.unique())
				.larger("createTime", new Date())
				.largerAndEquals("createTime", new Date())
				.smaller("updateTime", new Date())
				.smallerAndEqual("updateTime", new Date())
				.notEquals("deleted", "0")
				.link(LinkType.OR)
				.equals("id", JUniqueUtils.unique())
				.larger("createTime", new Date(),LinkType.OR);
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

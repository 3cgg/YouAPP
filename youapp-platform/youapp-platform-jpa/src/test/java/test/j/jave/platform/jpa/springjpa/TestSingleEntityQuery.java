package test.j.jave.platform.jpa.springjpa;

import j.jave.platform.jpa.springjpa.JJpaBaseModel;
import j.jave.platform.jpa.springjpa.query.JCondition;
import j.jave.platform.jpa.springjpa.query.JSingleEntityQueryMeta;
import j.jave.platform.jpa.springjpa.query.JCondition.LinkType;
import j.jave.platform.jpa.springjpa.query.JOrder;

import java.util.Date;
import java.util.Map;

import junit.framework.TestCase;
import me.bunny.kernel.jave.utils.JUniqueUtils;

import org.junit.Test;

public class TestSingleEntityQuery extends TestCase {

	
	@Test
	public void testCond(){
		
		JCondition condition=new JCondition(JJpaBaseModel.class)
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
		
		String orderClause=new JOrder(JJpaBaseModel.class)
		.asc("id")
		.desc("createTime")
		.asc("createTime")
		.desc("updateTime")
		.toOrderClause();
		
		System.out.println(orderClause);
		
	}
	
	
	@Test
	public void testSQL(){
		JSingleEntityQueryMeta singleEntityQuery=new JSingleEntityQueryMeta(JJpaBaseModel.class);
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

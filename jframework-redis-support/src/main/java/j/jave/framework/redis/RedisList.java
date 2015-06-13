package j.jave.framework.redis;

import j.jave.framework.utils.JUniqueUtils;

import java.util.List;

import redis.clients.jedis.Jedis;

public class RedisList {

	
	
	public static void main(String[] args) {
		
		Jedis jedis=new Jedis("127.0.0.1",6379);
		String key="name-key"+JUniqueUtils.unique();
		jedis.rpush(key, new String[]{JUniqueUtils.unique(),JUniqueUtils.unique(),JUniqueUtils.unique()});
		
		List<String> value=jedis.lrange(key, 0, -1);
		
		System.out.println(value);
		
		jedis.close();
	}
	
	
}

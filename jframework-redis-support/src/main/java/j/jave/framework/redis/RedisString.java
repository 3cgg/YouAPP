package j.jave.framework.redis;

import j.jave.framework.utils.JUniqueUtils;
import redis.clients.jedis.Jedis;

public class RedisString {

	public static void main(String[] args) {
		
		Jedis jedis=new Jedis("127.0.0.1",6379);
		String key="name-key"+JUniqueUtils.unique();
		jedis.set(key, "name-key-value");
		
		String value=jedis.get(key);
		
		System.out.println(value);
		
		jedis.close();
	}
	
	
}

package j.jave.framework.redis;

import j.jave.framework.commons.utils.JUniqueUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPoolTest {

	public static void main(String[] args) {
		JedisPoolConfig poolConfig=new JedisPoolConfig();
		JedisPool jedisPool=new JedisPool(poolConfig, "127.0.0.1",6379);
		Jedis jedis=jedisPool.getResource();
		
		String key="name-key"+JUniqueUtils.unique();
		jedis.set(key, "name-key-value");
		
		String value=jedis.get(key);
		
		System.out.println(value);
	
		jedisPool.close();
		
	}
}

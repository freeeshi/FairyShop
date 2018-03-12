package cn.fairyshop.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {
	
	@Test
	public void testJedisCluster() throws Exception {
		// 设置集群中每个节点的host和port
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.0.151", 7001));
		nodes.add(new HostAndPort("192.168.0.151", 7002));
		nodes.add(new HostAndPort("192.168.0.151", 7003));
		nodes.add(new HostAndPort("192.168.0.151", 7004));
		nodes.add(new HostAndPort("192.168.0.151", 7005));
		nodes.add(new HostAndPort("192.168.0.151", 7006));
		
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxTotal(1000);
//		config.setMaxIdle(100);
		
		// 创建JedisCluster对象，并指定对应nodes
		JedisCluster cluster = new JedisCluster(nodes);
		
		// 设置、获取key-value
		cluster.set("key3", "value3");
		String value = cluster.get("key3");
		System.out.println(value);
		
		// 在系统关闭时关闭JedisCluster
		cluster.close();
	}
	
	/**
	 * 使用连接池
	 * @throws Exception
	 */
	@Test
	public void testJedisPool() throws Exception {
		// 创建连接池对象JedisPool，这是是一个单例
		JedisPool pool = new JedisPool("192.168.0.151", 6379);
		
		// 获取Jedis对象
		Jedis jedis = pool.getResource();
		
		// 设置、获取key-value
		jedis.set("key2", "value2");
		String value = jedis.get("key2");
		System.out.println(value);
		
		// 关闭连接
		jedis.close();
	}
	
	/**
	 * 测试单机版
	 * @throws Exception
	 */
	@Test
	public void testJedisSingle() throws Exception {
		// 创建Jedis对象
		Jedis jedis = new Jedis("192.168.0.151", 6379);

		// 设置、获取key-value
		jedis.set("key1", "value2");
		String value = jedis.get("key1");
		System.out.println(value);
		
		// 关闭连接
		jedis.close();
	}

}

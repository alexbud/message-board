package messages.hibernate.cache;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CacheProvider;

import redis.clients.jedis.Jedis;

public class RedisCacheProvider implements CacheProvider {
	private Logger logger = Logger.getLogger(this.getClass());

	private Jedis jedis;

	public Cache buildCache(String regionName, Properties properties)
			throws CacheException {
		// TODO Auto-generated method stub
		logger.info("Building cache for region " + regionName);
		RedisCache cache = new RedisCache(regionName, jedis);
		return cache;
	}

	public boolean isMinimalPutsEnabledByDefault() {
		// TODO Auto-generated method stub
		return false;
	}

	public long nextTimestamp() {
		// TODO Auto-generated method stub
		return System.currentTimeMillis() / 100;
	}

	public void start(Properties properties) throws CacheException {
		// TODO Auto-generated method stub
		logger.info("Start Hibernate-Redis second level cache");
		int port = Integer.parseInt(properties.getProperty("redis.port"));
		int timeout = Integer.parseInt(properties.getProperty("redis.timeout"));
		String host = properties.getProperty("redis.host");
		String auth = properties.getProperty("redis.auth");
		logger.info("Jedis " + host + ":" + port + ":" + auth + ":" + timeout);
		jedis = new Jedis(host, port, timeout);
		jedis.auth(auth);
		jedis.flushAll();
	}

	public void stop() {
		// TODO Auto-generated method stub
		logger.info("Stop Hibernate-Redis second level cache");
	}
}

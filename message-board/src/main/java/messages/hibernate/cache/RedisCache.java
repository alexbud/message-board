package messages.hibernate.cache;

import java.util.Map;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;

import redis.clients.jedis.Jedis;

import com.sun.istack.logging.Logger;

public class RedisCache implements Cache {

	private Logger logger = Logger.getLogger(RedisCache.class);
	private final String regionName;
	private final Jedis jedis;

	public RedisCache(String regionName, Jedis jedis) {
		this.regionName = (regionName != null) ? regionName : "default";
		this.jedis = jedis;
		logger.info("RedisCache ok.");
	}

	public void clear() throws CacheException {
		// TODO Auto-generated method stub
		logger.info(regionName + " - clear ");
	}

	public void destroy() throws CacheException {
		// TODO Auto-generated method stub
		logger.info(regionName + " destroy - ReturResuorce ");
	}

	public long getElementCountInMemory() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getElementCountOnDisk() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getRegionName() {
		// TODO Auto-generated method stub
		return regionName;
	}

	public long getSizeInMemory() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void lock(Object key) throws CacheException {
		// TODO Auto-generated method stub
	}

	public long nextTimestamp() {
		// TODO Auto-generated method stub
		return System.currentTimeMillis() / 100;
	}

	private byte[] serializeObject(Object obj) {
		SerializingConverter sc = new SerializingConverter();
		return sc.convert(obj);
	}

	private Object deserializeObject(byte[] b) {
		DeserializingConverter dc = new DeserializingConverter();
		return dc.convert(b);
	}

	public Object get(Object key) throws CacheException {
		// TODO Auto-generated method stub
		// logger.info("get " + key.toString() + ":" +
		// key.getClass().getName());
		byte[] k = serializeObject(key.toString());
		byte[] v = jedis.get(k);
		if (v != null && v.length > 0) {
			Object o = deserializeObject(v);
			// logger.info("return: " + o.getClass().getName());
			return o;
		}
		return null;
	}

	public void put(Object key, Object value) throws CacheException {
		// TODO Auto-generated method stub
		// logger.info("put " + key.toString() + " - " +
		// value.getClass().getName());
		byte[] k = serializeObject(key.toString());
		byte[] v = serializeObject(value);
		jedis.set(k, v);
	}

	public Object read(Object key) throws CacheException {
		// TODO Auto-generated method stub
		// logger.info("read " + key.toString());
		byte[] k = serializeObject(key.toString());
		byte[] v = jedis.get(k);
		if (v != null && v.length > 0) {
			Object o = deserializeObject(v);
			return o;
		}
		return null;
	}

	public void remove(Object key) throws CacheException {
		// TODO Auto-generated method stub
		// logger.info("remove " + key.toString());
		byte[] k = serializeObject(key.toString());
		jedis.del(k);
	}

	public Map toMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public void unlock(Object key) throws CacheException {
		// TODO Auto-generated method stub
	}

	public void update(Object key, Object value) throws CacheException {
		// TODO Auto-generated method stub
		// logger.info("update " + key.toString());
		byte[] k = serializeObject(key.toString());
		byte[] v = serializeObject(value);
		jedis.set(k, v);
	}
}

package com.suny.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.suny.entity.Seckill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final JedisPool jedisPool;
	
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	
	public RedisDao(String ip, int port) {
		this.jedisPool = new JedisPool(ip, port);
	}
	
	public Seckill getSeckill(long seckillId) {
		try (Jedis jedis = jedisPool.getResource()) {
			String key = "seckill:" + seckillId;
			byte[] bytes = jedis.get(key.getBytes());
			if(bytes != null) {
				Seckill seckill = schema.newMessage();
				ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
				return seckill;
			}			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;	
	}
	
	public String putSeckill(Seckill seckill) {
		try (Jedis jedis = jedisPool.getResource()) {
			String key = "seckill:" + seckill.getSeckillId();
			byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
			int timeout = 60*60;
			return jedis.setex(key.getBytes(), timeout, bytes);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
}

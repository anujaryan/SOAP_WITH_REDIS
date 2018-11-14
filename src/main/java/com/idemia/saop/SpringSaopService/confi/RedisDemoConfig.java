package com.idemia.saop.SpringSaopService.confi;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.idemia.saop.SpringSaopService.model.Student;

@Configuration
@EnableCaching
public class RedisDemoConfig {

	
	@SuppressWarnings("deprecation")
	@Bean
	JedisConnectionFactory getJedisConnectionFactory() {
		
		JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
		
		jedisConnectionFactory.setHostName("localhost");
		jedisConnectionFactory.setPort(6379);
		jedisConnectionFactory.setUsePool(true);
		
		return jedisConnectionFactory;
		
		
		
	}
	
	@Bean
	RedisTemplate<String, Student> redisTemplate(){
		
		RedisTemplate<String, Student> redisTemplate=new RedisTemplate<>();
		
		redisTemplate.setConnectionFactory(getJedisConnectionFactory());
		/*redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		
		redisTemplate.setHashValueSerializer(new StringRedisSerializer());
		
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		
		redisTemplate.setValueSerializer(new StringRedisSerializer());*/
		
		//redisTemplate.sete
		
		return redisTemplate;
		
		
		
	}
		
}

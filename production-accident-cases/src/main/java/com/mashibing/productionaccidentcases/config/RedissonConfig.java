package com.mashibing.productionaccidentcases.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xcy
 * @date 2023/3/11 - 8:39
 */
@Configuration
public class RedissonConfig {

	@Value("${spring.redis.database}")
	private int database;
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private String port;
	@Value("${spring.redis.timeout}")
	private int timeout;

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		SingleServerConfig singleServerConfig = config.useSingleServer();
		singleServerConfig.setAddress("redis://" + host + ":" + port);
		singleServerConfig.setTimeout(timeout);
		singleServerConfig.setDatabase(database);
		return Redisson.create(config);
	}
}

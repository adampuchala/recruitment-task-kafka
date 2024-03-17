package com.adampuchala.springbootkafkapoc.storer.configuration;

import com.adampuchala.springbootkafkapoc.storer.model.TemperatureMeasurementEntry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {

    @Bean
    RedisTemplate<String, TemperatureMeasurementEntry> averageRedis(RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, TemperatureMeasurementEntry> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    RedisTemplate<String, TemperatureMeasurementEntry> anomalyRedis(RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, TemperatureMeasurementEntry> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}

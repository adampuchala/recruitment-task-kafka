package com.adampuchala.springbootkafkapoc.samplesconsumer;

import com.adampuchala.springbootkafkapoc.samplesconsumer.util.MockDataProducer;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Properties;

@Testcontainers
@RunWith(SpringJUnit4ClassRunner.class)
public class EndToEndTest {

    @Container
    public static final KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.4.0")
    ).withKraft();

    @Container
    public static final RedisContainer redisContainer = new RedisContainer(
            RedisContainer.DEFAULT_IMAGE_NAME.withTag(RedisContainer.DEFAULT_TAG)
    );

    ConfigurableApplicationContext consumerApplicationContext = null;

    MockDataProducer mockDataProducer;

    @BeforeAll
    public static void init() {
        kafkaContainer.start();
        redisContainer.start();
    }

    @BeforeEach
    public void setUp() {

        mockDataProducer = new MockDataProducer(
                kafkaContainer.getBootstrapServers(),
                "topic_temperature_samples"
        );

        mockDataProducer.startProducer();

        var properties = new Properties();

        properties.put("spring.kafka.bootstrap-servers", kafkaContainer.getBootstrapServers());
        properties.put("spring.data.redis.url",redisContainer.getRedisURI());
        properties.put("spring.data.redis.timeout", 60000);

        consumerApplicationContext = new SpringApplicationBuilder(SamplesConsumerApplication.class)
                .properties(properties)
                .run();
    }

    @Test
    public void runTest() throws InterruptedException {
        Thread.sleep(10_000L);
    }

    @AfterEach
    public void tearDown() {
        mockDataProducer.stopProducer();
        consumerApplicationContext.stop();
        consumerApplicationContext = null;
    }

    @AfterAll
    public static void shutDown() {
        kafkaContainer.stop();
        redisContainer.stop();
    }
}

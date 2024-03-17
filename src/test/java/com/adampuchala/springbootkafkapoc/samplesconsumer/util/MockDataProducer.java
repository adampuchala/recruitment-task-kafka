package com.adampuchala.springbootkafkapoc.samplesconsumer.util;

import com.adampuchala.springbootkafkapoc.samplesconsumer.model.TemperatureMeasurementEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

import java.time.Instant;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MockDataProducer {

    private static final Logger logger = LoggerFactory.getLogger(MockDataProducer.class);

    private static final int PRODUCER_INTERVAL = 500;

    static final String MOCK_ROOM_ID = UUID.randomUUID().toString();
    static final String MOCK_THERMOMETER_ID = UUID.randomUUID().toString();

    private final KafkaProducer<String, String> producer;
    private final String samplesTopic;
    public MockDataProducer(
            String bootstrapServers,
            String samplesTopic
    ) {
        this.samplesTopic = samplesTopic;

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        this.producer = new KafkaProducer<>(properties);
    }

    ScheduledFuture<?> producerFuture;
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public void startProducer() {
        var random = new Random();
        var objectMapper = new ObjectMapper().findAndRegisterModules().enable(SerializationFeature.INDENT_OUTPUT);
        producerFuture = executor.scheduleAtFixedRate(() -> {
            var temperature = random.nextDouble(15.0, 17.0);

            var shouldGenerateAnomaly = random.nextInt(0, 10) < 3;
            if (shouldGenerateAnomaly) {
                temperature = temperature + random.nextDouble(5.0, 8.0);
            }
            var sample = new TemperatureMeasurementEvent(
                    temperature,
                    Instant.now(),
                    MOCK_ROOM_ID,
                    MOCK_THERMOMETER_ID
            );
            try {
                var json = objectMapper.writeValueAsString(sample);
                var record = new ProducerRecord<String, String>(samplesTopic, json);
                producer.send(record);
            } catch (JsonProcessingException e) {
                logger.warn("Json serialization error", e);
                throw new RuntimeException(e);
            }
        }, 0, PRODUCER_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public void stopProducer() {
        producerFuture.cancel(true);
        executor.shutdown();
    }
}

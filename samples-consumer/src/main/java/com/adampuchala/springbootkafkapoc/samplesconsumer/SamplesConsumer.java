package com.adampuchala.springbootkafkapoc.samplesconsumer;

import com.adampuchala.springbootkafkapoc.domain.TemperatureMeasurement;
import com.adampuchala.springbootkafkapoc.samplesconsumer.model.TemperatureMeasurementEvent;
import com.adampuchala.springbootkafkapoc.storer.AnomalyStorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class SamplesConsumer {

    private final AnomalyStorer anomalyStorer;

    @Autowired
    public SamplesConsumer(
            AnomalyStorer anomalyStorer
    ) {
        this.anomalyStorer = anomalyStorer;
    }

    private static final double ANOMALY_THRESHOLD = 5.0;

    private static final Logger logger = LoggerFactory.getLogger(SamplesConsumerApplication.class);

    @KafkaListener(id = "${consumer_group}", topics = "${samples_topic}")
    public void consumeSamples(TemperatureMeasurementEvent event) {
        logger.info("Consumed temperature level: %s".formatted(event));
        anomalyStorer.addMeasurement(event.toDomain());
        var lastMeasurementsAverage = anomalyStorer.getLastMeasurements(event.thermometerId(), 10).stream()
                .mapToDouble(TemperatureMeasurement::temperature)
                .average()
                .orElse(0L);

        if(Math.abs(event.temperature() - lastMeasurementsAverage) > ANOMALY_THRESHOLD) {
            anomalyStorer.addAnomalyForThermometer(event.thermometerId(), event.toDomain());
        }
    }
}

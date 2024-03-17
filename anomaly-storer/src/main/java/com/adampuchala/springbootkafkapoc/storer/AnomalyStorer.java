package com.adampuchala.springbootkafkapoc.storer;

import com.adampuchala.springbootkafkapoc.domain.TemperatureMeasurement;
import com.adampuchala.springbootkafkapoc.storer.model.TemperatureMeasurementEntry;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnomalyStorer {

    private static final Logger logger = LoggerFactory.getLogger(AnomalyStorer.class);

    @Resource(name="averageRedis")
    private ListOperations<String, TemperatureMeasurementEntry> measurements;

    @Resource(name="anomalyRedis")
    private ListOperations<String, TemperatureMeasurementEntry> anomalies;

    public List<TemperatureMeasurement> getAnomaliesForThermometer(String thermometerId) {
        var anomaliesSize = Optional.ofNullable(anomalies.size(thermometerId)).orElse(0L);

        return Objects.requireNonNull(anomalies.range(thermometerId, 0, anomaliesSize)).stream()
                .map(TemperatureMeasurementEntry::toDomain)
                .collect(Collectors.toList());
    }

    public void addAnomalyForThermometer(String thermometerId, TemperatureMeasurement value) {
        anomalies.leftPush(thermometerId, TemperatureMeasurementEntry.fromDomain(value));
    }

    public void addMeasurement(TemperatureMeasurement measurement) {
        measurements.leftPush(measurement.thermometerId(), TemperatureMeasurementEntry.fromDomain(measurement));
    }

    public List<TemperatureMeasurement> getLastMeasurements(String thermometerId, Integer count) {
        return Objects.requireNonNull(measurements.range(thermometerId, 0, count)).stream()
                .map(TemperatureMeasurementEntry::toDomain)
                .collect(Collectors.toList());
    }
}

package com.adampuchala.springbootkafkapoc.samplesconsumer.model;

import com.adampuchala.springbootkafkapoc.domain.TemperatureMeasurement;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;

@JsonSerialize
public record TemperatureMeasurementEvent(
        Double temperature,
        Instant timestamp,
        String roomId,
        String thermometerId
) {

    public TemperatureMeasurement toDomain() {
        return new TemperatureMeasurement(
                this.temperature,
                this.timestamp,
                this.roomId,
                this.thermometerId
        );
    }
}

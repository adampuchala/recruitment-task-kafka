package com.adampuchala.springbootkafkapoc.storer.model;

import com.adampuchala.springbootkafkapoc.domain.TemperatureMeasurement;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;

@JsonSerialize
public record TemperatureMeasurementEntry(
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

    public static TemperatureMeasurementEntry fromDomain(TemperatureMeasurement value) {
        return new TemperatureMeasurementEntry(
                value.temperature(),
                value.timestamp(),
                value.roomId(),
                value.thermometerId()
        );
    }
}

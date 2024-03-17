package com.adampuchala.springbootkafkapoc.domain;

import java.time.Instant;

public record TemperatureMeasurement(
        Double temperature,
        Instant timestamp,
        String roomId,
        String thermometerId
) {}

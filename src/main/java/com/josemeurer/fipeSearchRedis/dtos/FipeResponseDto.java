package com.josemeurer.fipeSearchRedis.dtos;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public record FipeResponseDto(
        UUID id,
        String make,
        String model,
        Integer modelYear,
        BigDecimal price,
        String referenceMonth) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}

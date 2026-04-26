package com.josemeurer.fipeSearchRedis.dtos;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public record FipeRequestDto(
        UUID modelId,
        Integer modelYear) {
}

package dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record FipeResponseDto(
        UUID id,
        String make,
        String model,
        Integer modelYear,
        BigDecimal price,
        String referenceMonth) {
}

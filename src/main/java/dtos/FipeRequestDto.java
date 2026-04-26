package dtos;

import java.util.UUID;

public record FipeRequestDto(
        UUID modelId,
        Integer modelYear) {
}

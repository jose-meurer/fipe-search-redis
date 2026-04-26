package com.josemeurer.fipeSearchRedis.services;

import com.josemeurer.fipeSearchRedis.dtos.FipeResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.josemeurer.fipeSearchRedis.repositories.ReferenceRepository;

import java.util.UUID;

@Service
public class FipeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FipeService.class);
    private final ReferenceRepository referenceRepository;

    public FipeService(ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    @Cacheable
    public FipeResponseDto getFipe(UUID modelId, Integer modelYear){
        LOGGER.debug("Buscando referencias na base de dados. Motivo: Dados nao encontrados no cache. Parametros: modelId={}, modelYear={}", modelId, modelYear);

        return referenceRepository
                .findReferences(modelId, modelYear)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Modelo não encontrado: modelo=%s, ano=%d"
                                .formatted(modelId, modelYear)));
    }

    @CacheEvict
    public void invalidate(UUID modelId, Integer modelYear) {
        LOGGER.debug("Cache invalidado: modelo={}, ano={}", modelId, modelYear);
    }
}

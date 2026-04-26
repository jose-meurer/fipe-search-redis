package com.josemeurer.fipeSearchRedis.controllers;

import com.josemeurer.fipeSearchRedis.dtos.FipeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.josemeurer.fipeSearchRedis.services.FipeService;

import java.util.UUID;

@RestController
@RequestMapping("api/fipe")
public class FipeController {

    private final FipeService fipeService;

    public FipeController(FipeService fipeService) {
        this.fipeService = fipeService;
    }

    @GetMapping
    public ResponseEntity<FipeResponseDto> getFipe(
            @RequestParam(name = "modelId", required = true) UUID modelId,
            @RequestParam(name = "modelYear", required = true) Integer modelYear){

        var result =  fipeService.getFipe(modelId, modelYear);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{modelId}/{modelYear}/cache")
    public ResponseEntity<Void> invalidateCache(
            @PathVariable UUID modelId,
            @PathVariable Integer modelYear){

        fipeService.invalidate(modelId, modelYear);
        return ResponseEntity.noContent().build();
    }
}

package com.josemeurer.fipeSearchRedis.repositories;

import com.josemeurer.fipeSearchRedis.dtos.FipeResponseDto;
import com.josemeurer.fipeSearchRedis.entities.ReferenceEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReferenceRepository extends JpaRepository<ReferenceEntity, UUID> {

    @Query("""
            SELECT new com.josemeurer.fipeSearchRedis.dtos.FipeResponseDto(
                    r.id, ma.name,mo.name, r.modelYear, r.price, r.referenceMonth
                )
            FROM ReferenceEntity r
                JOIN r.model mo
                JOIN mo.make ma
            WHERE r.model.id = :modelId AND r.modelYear = :modelYear
            ORDER BY r.referenceMonth DESC
        """)
    List<FipeResponseDto> findReferences(@Param("modelId") UUID modelId, @Param("modelYear") Integer modelYear);
}

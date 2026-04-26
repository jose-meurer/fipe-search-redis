package com.josemeurer.fipeSearchRedis.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "reference_cars")
public class ReferenceEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private ModelEntity model;

    @Column(name = "model_year")
    private Integer modelYear;

    private BigDecimal price;

    @Column(name = "reference_month", length = 20)
    private String referenceMonth;

    public ReferenceEntity() {
    }

    public ReferenceEntity(UUID id, ModelEntity model, Integer modelYear, BigDecimal price, String referenceMonth) {
        this.id = id;
        this.model = model;
        this.modelYear = modelYear;
        this.price = price;
        this.referenceMonth = referenceMonth;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ModelEntity getModel() {
        return model;
    }

    public void setModel(ModelEntity model) {
        this.model = model;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getReferenceMonth() {
        return referenceMonth;
    }

    public void setReferenceMonth(String referenceMonth) {
        this.referenceMonth = referenceMonth;
    }
}

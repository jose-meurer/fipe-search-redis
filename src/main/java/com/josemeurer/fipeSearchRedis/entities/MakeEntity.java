package com.josemeurer.fipeSearchRedis.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "makes")
public class MakeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, length = 100, nullable = false)
    private String name;

    public MakeEntity() {
    }

    public MakeEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

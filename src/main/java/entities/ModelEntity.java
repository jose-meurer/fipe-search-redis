package entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Models")
public class ModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "make_id")
    private MakeEntity make;

    @Column(unique = true, length = 100, nullable = false)
    private String name;

    public ModelEntity() {
    }

    public ModelEntity(UUID id, MakeEntity make, String name) {
        this.id = id;
        this.make = make;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public MakeEntity getMake() {
        return make;
    }

    public void setMake(MakeEntity make) {
        this.make = make;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package springboot.webapp.usersmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolygonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private double area;

    @Column(nullable = false)
    private String geometry;

    public PolygonEntity(long id, String geometry) {
        this.id = id;
        this.geometry = geometry;
    }
}


package springboot.webapp.usersmanager.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERole role;

    public User(String name, String surname, String email, ERole role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
    }
}

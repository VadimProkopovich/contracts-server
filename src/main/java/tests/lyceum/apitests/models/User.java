package tests.lyceum.apitests.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tests.lyceum.apitests.models.dto.UserDTO;
import tests.lyceum.apitests.models.enums.UserRole;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    public User(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.role = userDTO.getRole();
    }
}
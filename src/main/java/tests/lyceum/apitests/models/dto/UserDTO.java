package tests.lyceum.apitests.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import tests.lyceum.apitests.models.User;
import tests.lyceum.apitests.models.enums.UserRole;

@Data
@NoArgsConstructor
public class UserDTO {
    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    private Integer id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
}

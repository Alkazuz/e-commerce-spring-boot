package website.marcosfernandes.ecommerce.api.v1.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import website.marcosfernandes.ecommerce.domain.Role;
import website.marcosfernandes.ecommerce.domain.User;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String email;
    private String name;
    private Set<Role> roles;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.roles = user.getRoles();
    }
}

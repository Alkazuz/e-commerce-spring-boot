package website.marcosfernandes.ecommerce.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import website.marcosfernandes.ecommerce.domain.Role;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String email;
    private String name;
    private Set<Role> roles;
}

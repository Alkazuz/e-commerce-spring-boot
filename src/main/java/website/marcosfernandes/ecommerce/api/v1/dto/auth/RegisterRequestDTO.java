package website.marcosfernandes.ecommerce.api.v1.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import website.marcosfernandes.ecommerce.domain.Role;

import java.util.Set;

@Getter @Setter
public class RegisterRequestDTO {
    @NotBlank(message = "{validation.user.email.required}")
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, message = "{validation.user.password.min}")
    private String password;
    @NotBlank
    private String confirmPassword;
    @NotBlank(message = "{validation.user.name.required}")
    private String name;
    private Set<Role> roles;
}

package website.marcosfernandes.ecommerce.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import website.marcosfernandes.ecommerce.domain.Role;

import java.util.Set;

public class RegisterRequestDTO {
    @NotBlank @Email
    private String email;
    @NotBlank
    @Size(min=6, max=100)
    private String password;
    @NotBlank
    private String confirmPassword;
    @NotBlank private String name;
    private Set<Role> roles;
}

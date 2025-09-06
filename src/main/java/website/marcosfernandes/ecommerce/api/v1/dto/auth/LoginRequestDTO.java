package website.marcosfernandes.ecommerce.api.v1.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequestDTO {
    @NotBlank(message = "{validation.user.email.required}")
    @Email
    private String email;
    @NotBlank(message = "{validation.user.password.required}")
    private String password;
}

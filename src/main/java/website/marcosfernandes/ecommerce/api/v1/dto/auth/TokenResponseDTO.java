package website.marcosfernandes.ecommerce.api.v1.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponseDTO {
    private String token;
    private String tokenType = "Bearer";
    private UserResponseDTO user;
}

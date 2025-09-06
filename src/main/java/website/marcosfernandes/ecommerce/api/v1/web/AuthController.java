package website.marcosfernandes.ecommerce.api.v1.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import website.marcosfernandes.ecommerce.api.v1.dto.auth.LoginRequestDTO;
import website.marcosfernandes.ecommerce.api.v1.dto.auth.RegisterRequestDTO;
import website.marcosfernandes.ecommerce.api.v1.dto.auth.TokenResponseDTO;
import website.marcosfernandes.ecommerce.domain.User;
import website.marcosfernandes.ecommerce.services.auth.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService auth;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDTO req){
        User user = new User();
        user.setEmail(req.getEmail());
        user.setName(req.getName());
        user.setPassword(req.getPassword());

        auth.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@Valid @RequestBody LoginRequestDTO req){
        User token = auth.authenticate(req);
        return new TokenResponseDTO(token);
    }
}

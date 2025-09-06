package website.marcosfernandes.ecommerce.api.v1.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import website.marcosfernandes.ecommerce.api.v1.dto.auth.LoginRequestDTO;
import website.marcosfernandes.ecommerce.api.v1.dto.auth.RegisterRequestDTO;
import website.marcosfernandes.ecommerce.api.v1.dto.auth.TokenResponseDTO;
import website.marcosfernandes.ecommerce.api.v1.dto.auth.UserResponseDTO;
import website.marcosfernandes.ecommerce.domain.User;
import website.marcosfernandes.ecommerce.security.AuthJwtSecurity;
import website.marcosfernandes.ecommerce.services.auth.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService auth;
    private final AuthJwtSecurity jwtService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@Valid @RequestBody RegisterRequestDTO req) {
        User toSave = new User();
        toSave.setEmail(req.getEmail());
        toSave.setName(req.getName());
        toSave.setPassword(req.getPassword());

        User saved = auth.register(toSave);

        var userDetails = org.springframework.security.core.userdetails.User
                .withUsername(saved.getEmail())
                .password(saved.getPassword())
                .authorities(saved.getRoles())
                .build();

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new TokenResponseDTO(token, "Bearer", new UserResponseDTO(saved)));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO req) {
        User user = auth.authenticate(req);
        var userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles())
                .build();

        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new TokenResponseDTO(token, "Bearer", new UserResponseDTO(user)));
    }
}

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
    private final UserDetailsService userDetailsService;


    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@Valid @RequestBody RegisterRequestDTO req){
        User user = new User();
        user.setEmail(req.getEmail());
        user.setName(req.getName());
        user.setPassword(req.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TokenResponseDTO(token, "Bearer", new UserResponseDTO(user)));
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@Valid @RequestBody LoginRequestDTO req){
        User user = auth.authenticate(req);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);
        return new TokenResponseDTO(token, "Bearer", new UserResponseDTO(user));
    }
}

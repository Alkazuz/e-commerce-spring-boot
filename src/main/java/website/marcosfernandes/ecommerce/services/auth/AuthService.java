package website.marcosfernandes.ecommerce.services.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import website.marcosfernandes.ecommerce.domain.User;
import website.marcosfernandes.ecommerce.dto.auth.LoginRequestDTO;
import website.marcosfernandes.ecommerce.dto.auth.RegisterRequestDTO;
import website.marcosfernandes.ecommerce.repository.UserRepository;
import website.marcosfernandes.ecommerce.security.AuthJwtSecurity;

import javax.naming.AuthenticationException;

@Service
public class AuthService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequestDTO register) {
        User user = User.builder()
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .name(register.getName())
                .build();
        return userRepository.save(user);
    }

    public User authenticate(LoginRequestDTO login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );

        return userRepository.findByEmail(login.getEmail())
                .orElseThrow();
    }
}

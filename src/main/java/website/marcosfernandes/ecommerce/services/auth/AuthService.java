package website.marcosfernandes.ecommerce.services.auth;

import jakarta.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import website.marcosfernandes.ecommerce.domain.User;
import website.marcosfernandes.ecommerce.api.v1.dto.auth.LoginRequestDTO;
import website.marcosfernandes.ecommerce.exceptions.EmailAlreadyExistsException;
import website.marcosfernandes.ecommerce.exceptions.UserNotFoundException;
import website.marcosfernandes.ecommerce.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final MessageSource messageSource;

    public AuthService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            MessageSource messageSource
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    @Transactional
    public User register(User user) {
        var locale = LocaleContextHolder.getLocale();

        if (userRepository.existsByEmail(user.getEmail())) {
            String msg = messageSource.getMessage("user.email.already.exists", null, locale);
            throw new EmailAlreadyExistsException(msg);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User authenticate(LoginRequestDTO login) {
        var locale = LocaleContextHolder.getLocale();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.getEmail(),
                            login.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            String msg = messageSource.getMessage("auth.bad.credentials", null, locale);
            throw new BadCredentialsException(msg);
        }

        return userRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> {
                    String msg = messageSource.getMessage("user.not.found", null, locale);
                    return new UserNotFoundException(msg);
                });
    }
}

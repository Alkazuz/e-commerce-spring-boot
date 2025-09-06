package website.marcosfernandes.ecommerce.services.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import website.marcosfernandes.ecommerce.api.v1.dto.auth.LoginRequestDTO;
import website.marcosfernandes.ecommerce.domain.Role;
import website.marcosfernandes.ecommerce.domain.User;
import website.marcosfernandes.ecommerce.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void authenticateReturnsUserWhenCredentialsValid() {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail("user@example.com");
        login.setPassword("secret");

        User user = User.builder()
                .id(UUID.randomUUID())
                .name("User")
                .email("user@example.com")
                .password("encoded")
                .build();

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        User result = authService.authenticate(login);

        assertEquals(user, result);

        ArgumentCaptor<UsernamePasswordAuthenticationToken> captor =
                ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(authenticationManager).authenticate(captor.capture());
        UsernamePasswordAuthenticationToken token = captor.getValue();
        assertEquals("user@example.com", token.getPrincipal());
        assertEquals("secret", token.getCredentials());
    }

    @Test
    void authenticateThrowsWhenUserNotFound() {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail("missing@example.com");
        login.setPassword("pwd");

        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> authService.authenticate(login));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
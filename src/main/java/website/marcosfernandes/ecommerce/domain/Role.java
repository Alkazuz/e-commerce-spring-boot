package website.marcosfernandes.ecommerce.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(length = 255)
    private String description;

    @Override
    public String getAuthority() {
        return name;
    }
}
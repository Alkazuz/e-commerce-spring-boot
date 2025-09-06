package website.marcosfernandes.ecommerce.domain;

import jakarta.persistence.*;
import lombok.*;
import website.marcosfernandes.ecommerce.enums.RoleEnum;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity @Table(name = "users")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SuppressWarnings("unused")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    public boolean hasRole(RoleEnum role) {
        return roles.stream()
                .anyMatch(r -> r.getName().equals(role.name()));
    }

    public boolean isAdmin() {
        return hasRole(RoleEnum.ADMIN);
    }

    public boolean isUser() {
        return hasRole(RoleEnum.USER);
    }

    public boolean isSeller() {
        return hasRole(RoleEnum.SELLER);
    }

    @PrePersist
    void prePersist() {
        var now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}

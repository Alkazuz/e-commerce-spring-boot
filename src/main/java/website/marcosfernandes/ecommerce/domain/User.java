package website.marcosfernandes.ecommerce.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity @Table(name = "users")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SuppressWarnings("unused")
public class User {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="user_roles", joinColumns=@JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable=false)
    private Set<Role> roles;

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    public boolean isAdmin() {
        return hasRole(Role.ADMIN);
    }

    public boolean isCustomer() {
        return hasRole(Role.CUSTOMER);
    }

    public boolean isSeller() {
        return hasRole(Role.SELLER);
    }
}

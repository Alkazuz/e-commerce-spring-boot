package website.marcosfernandes.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import website.marcosfernandes.ecommerce.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    boolean hasByEmail(String email);
    Optional<User> findByEmail(String email);
}

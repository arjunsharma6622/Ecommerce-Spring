package dev.arjunsharma.ecommerce.repository;

import dev.arjunsharma.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

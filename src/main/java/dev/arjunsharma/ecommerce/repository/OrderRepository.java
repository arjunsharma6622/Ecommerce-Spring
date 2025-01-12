package dev.arjunsharma.ecommerce.repository;

import dev.arjunsharma.ecommerce.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

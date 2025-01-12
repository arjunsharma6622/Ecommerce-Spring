package dev.arjunsharma.ecommerce.services;

import dev.arjunsharma.ecommerce.exceptions.OrderNotFoundException;
import dev.arjunsharma.ecommerce.models.Order;
import dev.arjunsharma.ecommerce.models.Product;
import dev.arjunsharma.ecommerce.models.User;
import dev.arjunsharma.ecommerce.repository.OrderRepository;
import dev.arjunsharma.ecommerce.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    OrderRepository orderRepository;
    UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Order getSingleOrder(Long id) throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        Order order;

        if(optionalOrder.isEmpty()){
            throw new OrderNotFoundException("The order with ID " + id + " is not in our DB!");
        }

        order = optionalOrder.get();

        return order;
    }

    public List<Order> getAllOrders(){
        List<Order> orders;
        orders = orderRepository.findAll();

        return orders;
    }

    public Long createOrder(User user, List<Product> products){
        // first calculate amount by mapping on product ids
        Double amount = 0.0;

        for(Product p : products){
            amount += p.getPrice();
        }

        Order order = new Order();
        order.setUser(user);
        order.setAmount(amount);
        order.setProducts(products);
        order.setStatus("created");

        Order savedOrder = orderRepository.save(order);

        return savedOrder.getId();
    }
}

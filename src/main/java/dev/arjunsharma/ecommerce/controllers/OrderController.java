package dev.arjunsharma.ecommerce.controllers;

import dev.arjunsharma.ecommerce.dto.ErrorDTO;
import dev.arjunsharma.ecommerce.exceptions.OrderNotFoundException;
import dev.arjunsharma.ecommerce.models.Order;
import dev.arjunsharma.ecommerce.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getSingleOrder(@PathVariable("id") Long id){
        Order order = orderService.getSingleOrder(id);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/order")
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orders;
        orders = orderService.getAllOrders();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<Long> createOrder(@RequestBody Order order){
        Long orderId;
        orderId = orderService.createOrder(order.getUser(), order.getProducts());
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleOrderNotFoundException(Exception e){
        ErrorDTO errorDTO;
        errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }
}

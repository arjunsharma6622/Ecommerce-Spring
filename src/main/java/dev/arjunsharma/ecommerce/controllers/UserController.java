package dev.arjunsharma.ecommerce.controllers;

import dev.arjunsharma.ecommerce.dto.ErrorDTO;
import dev.arjunsharma.ecommerce.exceptions.UserNotFoundException;
import dev.arjunsharma.ecommerce.models.User;
import dev.arjunsharma.ecommerce.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id){
        User user;
        user = userService.getUser(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedProduct;
        savedProduct = userService.createUser(
                user.getName(),
                user.getEmail(),
                user.getGender()
        );

        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(Exception e){
        ErrorDTO errorDTO;
        errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }
}

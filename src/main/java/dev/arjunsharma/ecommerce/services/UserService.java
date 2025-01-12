package dev.arjunsharma.ecommerce.services;

import dev.arjunsharma.ecommerce.exceptions.UserNotFoundException;
import dev.arjunsharma.ecommerce.models.User;
import dev.arjunsharma.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    public User getUser(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);

        User user;
        
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User with ID " + id + " is not in our DB!");
        }
        else{
            user = optionalUser.get();
        }
        
        return user;
    }

    public User createUser(String name, String email, String gender){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setGender(gender);

        User savedUser;
        savedUser = userRepository.save(user);

        return savedUser;
    }

}

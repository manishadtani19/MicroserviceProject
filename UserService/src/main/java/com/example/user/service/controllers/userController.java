package com.example.user.service.controllers;

import com.example.user.service.entities.User;
import com.example.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class userController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //Fallback method for circuitBreaker
    public ResponseEntity<User> ratingHotelFallBack(String userId, Exception e){
        logger.info("Fallback is executed because service is down: ", e.getMessage());
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created because some service is down")
                .userId("123344")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUSer(){
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}

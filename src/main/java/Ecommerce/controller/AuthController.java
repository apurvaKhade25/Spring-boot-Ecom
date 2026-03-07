package Ecommerce.controller;


import Ecommerce.model.LoginRequest;
import Ecommerce.model.User;
import Ecommerce.service.UserService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userservice;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userservice.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return userservice.verify(request);
    }
}
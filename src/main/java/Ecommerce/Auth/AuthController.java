package Ecommerce.Auth;


import Ecommerce.Auth.Dto.LoginRequest;
import Ecommerce.UserRepo.User;
import org.springframework.beans.factory.annotation.Autowired;
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
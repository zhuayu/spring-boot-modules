package com.aitschool.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="users",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody User store (@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping(path = "users")
    @ResponseBody
    public Page<User> index(Pageable request) {
        return userRepository.findAll(request);
    }
}

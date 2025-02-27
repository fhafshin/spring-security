package ir.setad.springsecurity.controller;

import ir.setad.springsecurity.dto.UserRestLoginRequest;
import ir.setad.springsecurity.dto.UserRestLoginResponse;
import ir.setad.springsecurity.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1")
public class LoginRestController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    public LoginRestController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;

        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<UserRestLoginResponse> login(@RequestBody UserRestLoginRequest userRestLoginRequest){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRestLoginRequest.getUsername()
                , userRestLoginRequest.getPassword()));
       return ResponseEntity.ok(userService.login(userRestLoginRequest));

    }

    @GetMapping
    public  String test(){
        return "test";
    }
}

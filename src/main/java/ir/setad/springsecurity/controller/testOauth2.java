package ir.setad.springsecurity.controller;

import ir.setad.springsecurity.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/oauth2")
public class testOauth2 {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    public testOauth2(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/info")
    @ResponseBody
    public Principal getInfo(Principal principal) {
        return principal;

    }
}

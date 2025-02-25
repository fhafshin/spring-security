package ir.setad.springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/find-all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok("this is all user");
    }
}

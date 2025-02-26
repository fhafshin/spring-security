package ir.setad.springsecurity.controller;

import ir.setad.springsecurity.config.SecurityHelper;
import ir.setad.springsecurity.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/find-all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok("this is all user");
    }

    @GetMapping("/user-details")
    public ResponseEntity<User> userDetails() {

       return ResponseEntity.ok(SecurityHelper.getCurrentUser());
    }
}

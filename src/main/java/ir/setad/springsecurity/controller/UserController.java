package ir.setad.springsecurity.controller;

import ir.setad.springsecurity.config.SecurityHelper;
import ir.setad.springsecurity.dto.UserResponse;
import ir.setad.springsecurity.model.User;
import ir.setad.springsecurity.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/find-all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok("this is all user");
    }

    @GetMapping("/user-details")
    public ResponseEntity<User> userDetails() {

       return ResponseEntity.ok(SecurityHelper.getCurrentUser());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable int id) {
        User user = userService.findOneById(id);

        UserResponse userResponse= new UserResponse(user.getId(),user.getUsername(),user.getRole());

        return ResponseEntity.ok(userResponse);
    }
    @GetMapping("/test/id/{id}")
    @PostAuthorize("@authService.checkLoadUser(#returnObject)")
    public ResponseEntity<UserResponse> testAuthService(@PathVariable int id){
        User user = userService.findOneById(id);

        UserResponse userResponse= new UserResponse(user.getId(),user.getUsername(),user.getRole());

        return ResponseEntity.ok(userResponse);
    }
}

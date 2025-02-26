package ir.setad.springsecurity.config;

import ir.setad.springsecurity.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {

    public static User getCurrentUser() {
        Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userDetails= (User) principal;
        return userDetails;
    }
}

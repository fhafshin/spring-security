package ir.setad.springsecurity.dto;


import ir.setad.springsecurity.model.Role;
import ir.setad.springsecurity.model.User;

import java.util.List;
import java.util.Optional;

public record UserResponse(int id, String username, List<Role> role) {



}

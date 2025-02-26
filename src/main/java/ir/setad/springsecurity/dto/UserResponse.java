package ir.setad.springsecurity.dto;


import ir.setad.springsecurity.model.Role;

import java.util.List;

public record UserResponse(int id, String username, List<Role> role) {

}

package ir.setad.springsecurity.service;

import ir.setad.springsecurity.config.JwtService;
import ir.setad.springsecurity.dto.UserRestLoginRequest;
import ir.setad.springsecurity.dto.UserRestLoginResponse;
import ir.setad.springsecurity.model.Token;
import ir.setad.springsecurity.model.User;
import ir.setad.springsecurity.repository.TokenRepository;
import ir.setad.springsecurity.repository.UserRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    public UserService(UserRepository userRepository, JwtService jwtService, TokenRepository tokenRepository) {
        this.userRepository = userRepository;

        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("user.not.found"));


    }

    @PostAuthorize("returnObject.username == authentication.principal.username")
    public User findOneById(int id) {

        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("user.not.found"));
    }

    public UserRestLoginResponse login(UserRestLoginRequest userRestLoginRequest) {


        UserDetails userDetails = loadUserByUsername(userRestLoginRequest.getUsername());


        String token = jwtService.generateToken(userDetails);
  saveToken(token,userDetails);
        return new UserRestLoginResponse(token);
    }

    private void saveToken(String token, UserDetails userDetails) {

        Token tokenEntity=new Token();
        tokenEntity.setToken(token);
        tokenEntity.setExpired(false);
        tokenEntity.setRevoked(false);
        tokenEntity.setUser((User) userDetails);
        tokenRepository.save(tokenEntity);


    }


}

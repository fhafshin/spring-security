package ir.setad.springsecurity.config;

import ir.setad.springsecurity.dto.UserResponse;
import ir.setad.springsecurity.model.User;
import ir.setad.springsecurity.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Oauth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public Oauth2Service(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User= super.loadUser(userRequest);
        Optional<User> email=userRepository.findByEmail(oAuth2User.getAttribute("email"));
       User user;
        if(email.isPresent()){
            user=email.get();

        }
        else
        {
           user = new User();
            user.setUsername(oAuth2User.getAttribute("name"));
            user.setEmail(oAuth2User.getAttribute("email"));
            user.setName(oAuth2User.getAttribute("name"));
        }
        user.setPicture(oAuth2User.getAttribute("picture"));
userRepository.save(user);
        return user;
    }
}

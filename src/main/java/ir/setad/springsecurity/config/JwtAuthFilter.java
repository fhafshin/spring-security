package ir.setad.springsecurity.config;

import ir.setad.springsecurity.model.Token;
import ir.setad.springsecurity.repository.TokenRepository;
import ir.setad.springsecurity.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenRepository tokenRepository;

    private final JwtService jwtService;
private final UserService userService;
    public JwtAuthFilter(TokenRepository tokenRepository, JwtService jwtService, UserService userService) {
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.userService = userService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        if(jwtService.isExpire(jwt)){
            filterChain.doFilter(request, response);
            return;
        }
        String username = jwtService.extractToken(jwt);
        if (Objects.nonNull(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            Token tokenFetch=  tokenRepository.findByToken(jwt).stream().filter(token -> !token.isExpired() && !token.isRevoked()).collect(Collectors.toList()).get(0);
            if(Objects.isNull(tokenFetch)) {return;}
            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
       usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
       SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}

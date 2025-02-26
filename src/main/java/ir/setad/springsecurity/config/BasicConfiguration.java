package ir.setad.springsecurity.config;

import ir.setad.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class BasicConfiguration {
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        UserDetails users = User.withUsername("user")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder().encode("123456"))
//                .roles("ADMIN", "USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(users, admin);
//
//    }

    private final UserService userService;

    public BasicConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/css/**", "/fonts/**", "/images/**", "/js/**", "/scss/**", "/main").permitAll()
                                .requestMatchers(HttpMethod.GET,"/index").permitAll()
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form.loginPage("/login").permitAll())
                .logout(logout -> logout.permitAll())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }
}

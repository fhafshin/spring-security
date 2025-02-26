package ir.setad.springsecurity;

import ir.setad.springsecurity.config.BasicConfiguration;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest

class SpringSecurityApplicationTests {
    @Autowired
    private  PasswordEncoder passwordEncoder;



    @Test
    void contextLoads() {
        System.out.println(passwordEncoder.encode("123456"));
    }

}

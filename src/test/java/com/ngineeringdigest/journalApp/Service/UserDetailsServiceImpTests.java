package com.ngineeringdigest.journalApp.Service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.ngineeringdigest.journalApp.Repository.UserRepository;

@SpringBootTest
public class UserDetailsServiceImpTests {

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @MockitoBean
    private UserRepository userRepository;

//    @Test
//    void loadUserByUsernameTest() {
//
//        com.ngineeringdigest.journalApp.Entity.User mockUser =
//                com.ngineeringdigest.journalApp.Entity.User.builder()
//                        .username("raiyan")
//                        .password("inrinrick")
//                        .roles(List.of("USER"))
//                        .build();
//
//        when(userRepository.findByUsername("raiyan"))
//                .thenReturn(mockUser);
//
//        UserDetails user = userDetailsServiceImp.loadUserByUsername("raiyan");
//
//        assertEquals("raiyan", user.getUsername());
//    }
}
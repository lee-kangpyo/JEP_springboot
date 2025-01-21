package com.akmz.jep.config.jwt;

import com.akmz.jep.domain.JepmUser;
import com.akmz.jep.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.NoSuchElementException;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @Test
    void generateToken() throws Exception{
        JepmUser user = userRepository.findByUserId("lee").orElseThrow(()-> new NoSuchElementException("해당하는 유저가 없습니다."));
        String accesstoken = tokenProvider.GenerateToken("access", user, Duration.ofHours(1));
        String refreshtoken = tokenProvider.GenerateToken("refresh", user, Duration.ofDays(30));

        System.out.println(accesstoken);
        System.out.println(refreshtoken);
        System.out.println("---------------------------------");
        System.out.println(user.getAccess());
        System.out.println(user.getRefresh());
        JepmUser user1 = tokenProvider.validtoken(accesstoken);
        JepmUser user2 = tokenProvider.validtoken(refreshtoken);
        JepmUser user3 = tokenProvider.validtoken(user.getAccess());
        JepmUser user4 = tokenProvider.validtoken(user.getRefresh());

        assertThat(user.getUserId()).isEqualTo(user1.getUserId());
        assertThat(user.getUserId()).isEqualTo(user2.getUserId());
        assertThat(user.getUserId()).isEqualTo(user3.getUserId());
        assertThat(user.getUserId()).isEqualTo(user4.getUserId());
    }
}
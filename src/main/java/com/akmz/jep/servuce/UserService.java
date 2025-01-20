package com.akmz.jep.servuce;

import com.akmz.jep.domain.JepmUser;
import com.akmz.jep.dto.LoginRequest;
import com.akmz.jep.dto.LoginResponse;
import com.akmz.jep.dto.SaveUserRequest;
import com.akmz.jep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class UserService {
    private final UserRepository userRepository;

    public Integer save(SaveUserRequest request){
        System.out.println(request);
        return 1;
    }

    public JepmUser findUserById(Integer userId){
        return userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("Unexpected User"));
    }

    public LoginResponse login(LoginRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        JepmUser user = userRepository.findByUserId(request.getId()).orElseThrow(()->new IllegalArgumentException("Unexpected User"));
        boolean passwordCheck = encoder.matches(request.getPassword(), user.getPassword());
        if(!passwordCheck){
            return LoginResponse.builder().status_code("400").access_token("").refresh_token("").token_type("").build();
        }
        //access토큰생성
        //refresh토큰생성
        return LoginResponse.builder().status_code("200").access_token("a").refresh_token("b").build();
    }
}

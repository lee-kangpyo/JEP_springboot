package com.akmz.jep.servuce;

import com.akmz.jep.domain.JepmUser;
import com.akmz.jep.dto.LoginRequest;
import com.akmz.jep.dto.LoginResponse;
import com.akmz.jep.dto.SaveUserRequest;
import com.akmz.jep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Controller
public class UserService {
    private final UserRepository userRepository;

    public Integer save(SaveUserRequest request){
        System.out.println(request);
        return 1;
    }

    public JepmUser findUserById(Integer userNo){
        return userRepository.findById(userNo).orElseThrow(()-> new NoSuchElementException("Unexpected User"));

    }

    public JepmUser findUserByUserId(String userId){
        JepmUser user = userRepository.findByUserId(userId).orElseThrow(()-> new NoSuchElementException(userId+"에 해당하는 유저가 없습니다."));
        if("Y".equals(user.getUseYn())){
            return user;
        }else{
            throw new IllegalStateException("해당 유저는 활성화되지 않았습니다.");
        }
    }

    public LoginResponse login(LoginRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        JepmUser user = userRepository.findByUserId(request.getId()).orElseThrow(()->new NoSuchElementException("Unexpected User"));
        boolean passwordCheck = encoder.matches(request.getPassword(), user.getPassword());
        if(!passwordCheck){
            return LoginResponse.builder().status_code("400").access_token("").refresh_token("").token_type("").build();
        }
        //access토큰생성
        //refresh토큰생성
        return LoginResponse.builder().status_code("200").access_token("a").refresh_token("b").build();
    }
}

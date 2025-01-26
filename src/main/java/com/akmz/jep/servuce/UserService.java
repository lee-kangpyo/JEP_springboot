package com.akmz.jep.servuce;

import com.akmz.jep.config.jwt.TokenProvider;
import com.akmz.jep.domain.JepmSummary;
import com.akmz.jep.domain.JepmUser;
import com.akmz.jep.dto.LoginRequest;
import com.akmz.jep.dto.LoginResponse;
import com.akmz.jep.dto.SaveUserRequest;
import com.akmz.jep.repository.UserRepository;
import com.akmz.jep.repository.UserSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Controller
public class UserService {
    private final UserSummaryRepository userSummaryRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public JepmUser saveUser(SaveUserRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        JepmUser entity = JepmUser.builder()
                .userId(request.getUserId())
                .password(encoder.encode(request.getPassword()))
                .type("00")
                .iUserId(request.getUserId())
                .iDate(LocalDateTime.now())
                .build();
        JepmUser result = userRepository.save(entity);
        return result;
    }

    public JepmSummary saveSummary(char studyKana, JepmUser saveUser){
        JepmSummary summary = JepmSummary.builder()
                .kanaType(studyKana)
                .jepmUser(saveUser)
                .build();
        JepmSummary result = userSummaryRepository.save(summary);
        return result;
    }

    public JepmUser findUserById(Integer userNo){
        return userRepository.findById(userNo).orElseThrow(()-> new NoSuchElementException("Unexpected User"));
    }

    public JepmUser findUserByUserId(String userId){
        JepmUser user = userRepository.findByUserId(userId).orElse(null);
        return user;
    }

    public LoginResponse login(LoginRequest request){
        // 사용자 인증 (ID, 비밀번호)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword())
        );

         // 인증된 사용자 정보 가져오기
        JepmUser user = (JepmUser) authentication.getPrincipal();

        // JWT 토큰 생성
        String accessToken = tokenProvider.GenerateToken("access", user, Duration.ofHours(1));
        String refreshToken = tokenProvider.GenerateToken("refresh", user, Duration.ofDays(30));

        user.setAccess(accessToken);
        user.setRefresh(refreshToken);
        user.setMDate(LocalDateTime.now());
        userRepository.save(user);

        // 응답 생성
        return LoginResponse.builder()
                .status_code("200")
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();
    }

    public LoginResponse login2(LoginRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        JepmUser user = userRepository.findByUserId(request.getId()).orElseThrow(()->new NoSuchElementException("Unexpected User"));
        boolean passwordCheck = encoder.matches(request.getPassword(), user.getPassword());
        if(!passwordCheck){
            return LoginResponse.builder().status_code("400").access_token("").refresh_token("").token_type("").build();
        }
        String accessToken = tokenProvider.GenerateToken("access", user, Duration.ofHours(1));
        String refreshToken = tokenProvider.GenerateToken("refresh", user, Duration.ofDays(30));
        user.setAccess(accessToken);
        user.setRefresh(refreshToken);
        user.setMDate(LocalDateTime.now());
        userRepository.save(user);
        return LoginResponse.builder().status_code("200").access_token(accessToken).refresh_token(refreshToken).build();
    }

    @Transactional
    public void createTmpUserAndSummary(SaveUserRequest request, char studyKana) {
        JepmUser user = findUserByUserId(request.getUserId());
        if (user != null) {
            throw new RuntimeException("임시 계정 생성 중 오류가 발생했습니다.");
        }

        // 사용자 저장
        JepmUser saveUser = saveUser(request);
        if (saveUser == null) {
            throw new RuntimeException("회원 가입 중 오류가 발생했습니다.");
        }

        // Summary 저장
        JepmSummary saveSummary = saveSummary(studyKana, saveUser);
        if (saveSummary == null) {
            throw new RuntimeException("회원 가입 후 Summary 생성 중 오류가 발생했습니다.");
        }
    }

    public void test(){
        JepmUser user = userRepository.findById(98).orElse(null);
        JepmSummary summary = userSummaryRepository.findById(45).orElse(null);
        System.out.println("Asdf");
    }
}

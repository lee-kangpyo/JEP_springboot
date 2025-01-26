package com.akmz.jep.controller;

import com.akmz.jep.domain.JepmUser;
import com.akmz.jep.servuce.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@Tag(name = "테스트", description = "테스트 API 엔드포인트")
@RestController
public class TestController {

    private final UserService userService;

    @GetMapping("/test")
    @Operation(summary = "TestController.test", description = "asdf를 리턴합니다.", security = @SecurityRequirement(name = "JWT"))
    @ApiResponse(responseCode = "200", description = "성공")
    public ResponseEntity<String> test(@AuthenticationPrincipal JepmUser user){
        userService.test();

//        JWT 토큰 테스트
//        JepmUser user = userService.findUserById(1);
//        System.out.println(user.toString());
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String a = encoder.encode("lee");
//        String pw = user.getPassword();
//        System.out.println(a);
//        System.out.println(pw);
//        System.out.println(encoder.matches("lee" ,a));
//        System.out.println(encoder.matches("lee" ,pw));

        //$2a$10$cYPhQLpJ8LiTL3JkqR3Pk.SVqrkZJ.XA2u3Fk849JyqZuAuAjHCXC
        //$2b$12$t2OU9BGtIApH3FlWkWRnqeC6ak3sUwqzf4dBxAJDMAbjjhgGw6fIC
        return ResponseEntity.ok().body("테스트 컨트롤러");
    }
}

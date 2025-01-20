package com.akmz.jep.controller;

import com.akmz.jep.dto.LoginRequest;
import com.akmz.jep.dto.LoginResponse;
import com.akmz.jep.dto.SaveUserRequest;
import com.akmz.jep.servuce.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name = "유저", description = "유저 관련")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "UserController.create", description = "회원가입 api tempId는 null String으로 입력 상태코드 201 이면 성공, 404면 오류, 200 이면 아이디 중복.")
    @ApiResponse(responseCode = "200", description = "성공")
    @PostMapping("/create")
    public ResponseEntity<String> create(SaveUserRequest request){
        Integer userNo = userService.save(request);

        return ResponseEntity.ok().body("userNo:"+userNo);
    }

    @Operation(summary = "UserController.login2", description = "로그인 api tempId는 null String으로 입력 상태코드 201 이면 성공, 404면 오류, 200 이면 아이디 중복.")
    @ApiResponse(responseCode = "200", description = "성공")
    @PostMapping(value = "/login2", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestParam String id, @RequestParam String password){
        LoginResponse response = userService.login(LoginRequest.builder().id(id).password(password).build());
        if(response.getStatus_code().equals("200")){
            return ResponseEntity.ok().body(response);
        }else{
            return ResponseEntity.status(400).build();
        }

    }

}

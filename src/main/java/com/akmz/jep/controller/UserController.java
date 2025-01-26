package com.akmz.jep.controller;

import com.akmz.jep.domain.JepmUser;
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

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Tag(name = "유저", description = "유저 관련")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "UserController.isDuplicate", description = "아이디 중복체크 api.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "중복 체크 중 오류 발생")
    @GetMapping("/checkIdDuplicate/id/{userId}")
    public ResponseEntity<Object> isDuplicate(@PathVariable String userId){
        Map<String, Object> body = new HashMap<>();
        try {
            JepmUser isDuplicateUser = userService.findUserByUserId(userId);
            if(isDuplicateUser == null){
                body.put("isDuplicate", false);
                return ResponseEntity.status(200).body(body);
            }else{
                body.put("isDuplicate", true);
                return ResponseEntity.status(200).body(body);
            }
        }catch (Exception e){
            return ResponseEntity.status(400).body("아이디 중복체크 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }

    }

    @Operation(summary = "UserController.create", description = "회원가입 api tempId는 null String으로 입력 상태코드 201 이면 성공, 404면 오류, 200 이면 아이디 중복.")
    @ApiResponse(responseCode = "200", description = "성공")
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody  SaveUserRequest request){
        JepmUser user = userService.findUserByUserId(request.getUserId());
        if(user != null){
            return ResponseEntity.ok().body("아이디가 중복되었습니다.");
        }else{
            JepmUser saveUser = userService.saveUser(request);
            if(saveUser != null){
                return ResponseEntity.status(201).body("성공.");
            }else{
                return ResponseEntity.status(40).body("회원 가입 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }

        }
    }

    @Operation(summary = "UserController.createTmpUser", description = "초기에 히라가나 카타카나 선택 후 다음 버튼 클릭 시 임시 계정 생성하기.")
    @ApiResponse(responseCode = "201", description = "성공")
    @PostMapping("/createTmpUser/studyKana/{studyKana}")
    public ResponseEntity<String> createTmpUser(@PathVariable char studyKana, @RequestBody  SaveUserRequest request){
        try {
            userService.createTmpUserAndSummary(request, studyKana);
            return ResponseEntity.status(201).body("성공.");
        }catch (RuntimeException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
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

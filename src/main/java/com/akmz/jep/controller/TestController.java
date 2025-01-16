package com.akmz.jep.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "테스트", description = "테스트 API 엔드포인트")
@RestController
public class TestController {
    @GetMapping("/test")
    @Operation(summary = "TestController.test", description = "asdf를 리턴합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("asdf");
    }
}

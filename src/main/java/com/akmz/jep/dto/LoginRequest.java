package com.akmz.jep.dto;

import lombok.*;

@NoArgsConstructor
@Getter
public class LoginRequest {
    String id;
    String password;

    @Builder

    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
